package com.paczek.demo.tests.functional.kafka;

import com.paczek.demo.tests.BaseTest;
import lombok.Cleanup;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class KafkaTest extends BaseTest {
    private final static Duration TIMEOUT = Duration.ofSeconds(5);
    protected static final String DEMO_GROUP = "demo-group";
    protected static final String DEMO_TOPIC = "demo-topic";

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Autowired
    protected KafkaTemplate<String, String> kafkaTemplate;

    private KafkaConsumer<String, String> getKafkaConsumer(String group) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, group);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return new KafkaConsumer<>(properties);
    }

    protected ConsumerRecords<String, String> getKafkaTopicRecords(String group, String topic) {
        @Cleanup KafkaConsumer<String, String> consumer = getKafkaConsumer(group);
        TopicPartition tp = new TopicPartition(topic, 0);
        consumer.assign(Collections.singleton(tp));
        consumer.seekToBeginning(Collections.singleton(tp));
        return consumer.poll(TIMEOUT);
    }

    protected ConsumerRecords<String, String> getKafkaTopicRecords() {
        return getKafkaTopicRecords(DEMO_GROUP, DEMO_TOPIC);
    }

    protected List<String> getKafkaMessages() {
        return IteratorUtils.toList(getKafkaTopicRecords().iterator())
                .stream()
                .map(ConsumerRecord::value)
                .toList();
    }
}
