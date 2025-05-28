package com.open.forum.review.infrastructure.events.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "local.kafka.properties")
public class KafkaConfigProperties {

    private Map<String, String> consumer = new HashMap<>();
    private Map<String, String> producer = new HashMap<>();
    private Map<String, String> admin = new HashMap<>();
    private Map<String, String> dlq = new HashMap<>();
    private String bootstrapServers;

    // Retry-specific properties
    private int retryAttempts;
    private int retryInterval;
    private int retryBackoffMultiplier;
    private int retryBackoffMaxInterval;

    // Consumer-specific properties
    public String getConsumerGroupId() {
        return consumer.getOrDefault("group.id", "default-group");
    }

    public String getKeyDeserializer() {
        return consumer.getOrDefault("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    }

    public String getValueDeserializer() {
        return consumer.getOrDefault("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    }

    public String getAutoOffsetReset() {
        return consumer.getOrDefault("auto.offset.reset", "latest");
    }

    // DLQ-specific properties
    public String getDlqPrefix() {
        return dlq.getOrDefault("prefix", "");
    }

    public String getDlqSuffix() {
        return dlq.getOrDefault("suffix", "-DLQ");
    }
}