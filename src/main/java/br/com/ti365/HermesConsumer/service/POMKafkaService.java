package br.com.ti365.HermesConsumer.service;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.rabbitmq.client.Channel;

public interface POMKafkaService {

	/*
	 * Connection Factory to kafka server
	 */
	KafkaConsumer<String, String> createConsumer();
	
	/*
	 * Start Kafka topic consumer
	 */
	void startConsumerKafka(Channel channel);
	
}
