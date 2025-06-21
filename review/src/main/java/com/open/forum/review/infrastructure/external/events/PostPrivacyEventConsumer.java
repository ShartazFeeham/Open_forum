package com.open.forum.review.infrastructure.external.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.open.forum.review.infrastructure.repository.post.PostPrivacyEntity;
import com.open.forum.review.infrastructure.repository.post.PostPrivacyJpaRepository;
import com.open.forum.review.shared.enums.PostPrivacy;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.open.forum.review.shared.enums.PostPrivacy.REVIEW_ALLOWED;
import static com.open.forum.review.shared.enums.PostPrivacy.REVIEW_NOT_ALLOWED;

@Slf4j
@Service
public class PostPrivacyEventConsumer {

    public static final String PRIVACY = "privacy";
    private final PostPrivacyJpaRepository postPrivacyJpaRepository;
    private final ObjectMapper objectMapper;

    public PostPrivacyEventConsumer(PostPrivacyJpaRepository postPrivacyJpaRepository, ObjectMapper objectMapper) {
        this.postPrivacyJpaRepository = postPrivacyJpaRepository;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "${local.kafka.properties.external.post-privacy-topic}",
            groupId = "${local.kafka.properties.external.post-privacy-listener-group}")
    public void consumeMessage(ConsumerRecord<String, String> record) {

        log.info("Consumed message: Key: {}, Value: {}, Topic: {}, Partition: {}, Offset: {}",
                record.key(), record.value(), record.topic(), record.partition(), record.offset());

        PostPrivacy postPrivacy = readPostPrivacy(record.value());
        if (postPrivacy == null) {
            log.error("Post privacy is null for value: {}", record.value());
            return;
        }

        Long postId = readPostId(record.value());
        if (postId == null) {
            log.error("Post ID is null for value: {}", record.value());
            return;
        }

        savePostPrivacyInDatabase(postPrivacy, postId);
    }

    private PostPrivacy readPostPrivacy(String value) {
        try {
            final var postPrivacyValue = readFromValue(value, PRIVACY);
            return Boolean.parseBoolean(String.valueOf(postPrivacyValue)) ? REVIEW_ALLOWED : REVIEW_NOT_ALLOWED;
        } catch (Exception e) {
            log.error("Error parsing post privacy from value: {}", value, e);
            return null;
        }
    }

    private Long readPostId(String value) {
        try {
            return (Long) readFromValue(value, "id");
        } catch (Exception e) {
            log.error("Error parsing post id from value: {}", value, e);
            return null;
        }
    }

    private Object readFromValue(String value, String key) throws JsonProcessingException {
        Map<?, ?> data = objectMapper.readValue(value, Map.class);
        return data.get(key);
    }

    private void savePostPrivacyInDatabase(PostPrivacy postPrivacy, Long postId) {
        if (postPrivacy == null) {
            log.error("Post privacy is null, cannot save to database.");
            return;
        }
        PostPrivacyEntity postPrivacyEntity = PostPrivacyEntity.builder()
                .postPrivacy(postPrivacy)
                .postId(postId)
                .build();
        try {
            postPrivacyJpaRepository.save(postPrivacyEntity);
            log.info("Post privacy saved successfully: {}", postPrivacy);
        } catch (Exception e) {
            log.error("Error saving post privacy to database: {}", postPrivacy, e);
        }
    }
}
