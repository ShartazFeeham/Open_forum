package com.open.forum.review.infrastructure.events;

import com.open.forum.review.domain.events.producer.ReactionEventProducer;
import com.open.forum.review.domain.events.reaction.ReactionEvent;
import com.open.forum.review.infrastructure.events.core.AbstractProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReactionEventProducerImpl extends AbstractProducer<String, String, ReactionEvent> implements ReactionEventProducer {

    private static final Logger log = LoggerFactory.getLogger(ReactionEventProducerImpl.class);

    protected ReactionEventProducerImpl(KafkaTemplate<String, String> kafkaTemplate) {
        super(kafkaTemplate);
    }

    /**
     * @param event the event to be published
     */
    @Override
    public void publish(ReactionEvent event) {
        super.send(event, (sendResult) -> {
            log.info("Reaction event sent successfully: {}", sendResult);
        }, (throwable) -> {
            log.error("Reaction error sending event!", throwable);
        });
    }

    @Override
    public String getKey(ReactionEvent event) {
        return getTopic(event) + event.getEventData().getReactionId();
    }

    @Override
    public String getValue(ReactionEvent event) throws Exception {
        return objectMapper.writeValueAsString(event);
    }
}
