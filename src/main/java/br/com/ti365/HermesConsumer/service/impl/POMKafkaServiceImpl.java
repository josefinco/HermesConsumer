package br.com.ti365.HermesConsumer.service.impl;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.rabbitmq.client.Channel;

import br.com.ti365.HermesConsumer.service.POMKafkaService;
import br.com.ti365.HermesConsumer.service.PropertyService;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class POMKafkaServiceImpl implements POMKafkaService {

	private final KafkaConsumer<String, String> consumer;
	private PropertyService propertyService = new PropertyServiceImpl();
	private Properties Kafkaproperties = new Properties();

	public POMKafkaServiceImpl() {
		consumer = createConsumer();
	}

	public KafkaConsumer<String, String> createConsumer() {
		if (consumer != null)
			return consumer;
		Kafkaproperties = propertyService.loadKafkaProperties();
		return new KafkaConsumer<String, String>(Kafkaproperties);
	}

	public void startConsumerKafka(Channel channel) {
		List<String> topicos = new ArrayList<>();
		topicos = Arrays.asList(Kafkaproperties.get("topic").toString());

		try {
			log.info("Iniciando consumo do topico: " + topicos);
			consumer.subscribe(topicos);

			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
				for (ConsumerRecord<String, String> record : records) {
					log.info("Publicando mensagem: " + record.value());
					RabbitServiceImpl rabbit = new RabbitServiceImpl();
					rabbit.publish(channel, record.value());
					consumer.commitAsync();
				}
			}
		} catch (Exception e) {
			log.error("Falha ao Iniciar consumo Kafka");
		}

	}



}
