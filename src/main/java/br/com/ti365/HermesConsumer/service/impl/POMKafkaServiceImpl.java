package br.com.ti365.HermesConsumer.service.impl;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class POMKafkaConsumer {

	private final KafkaConsumer<String, String> consumer;
	private PropertyServiceImpl propertyService = new PropertyServiceImpl();
	Properties propertiesfile = propertyService.loadProperties();
	
	
	public POMKafkaConsumer() {
		consumer = criarConsumer();
	}
	

	private KafkaConsumer<String, String> criarConsumer() {
		if (consumer != null)
			return consumer;

		Properties properties = new Properties();
		
		for (Entry<Object, Object> entry : propertiesfile.entrySet()) {
			if(entry.getKey().toString().contains("kafka"))
				properties.put(entry.getKey().toString().replace("kafka.",""), entry.getValue());
		}


		return new KafkaConsumer<String, String>(properties);
	}
	
	public void executar() {
		List<String> topicos = new ArrayList<>();
		topicos= Arrays.asList(propertiesfile.get("kafka.topic").toString());
		
		try {
			log.info("Iniciando consumo do topico: " + topicos);
			consumer.subscribe(topicos);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		boolean continua = true;
		while (continua) {
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
			for (ConsumerRecord<String, String> record : records) {
				gravarMensagem(record.value(), record.topic(), record.partition(), record.offset());
				consumer.commitAsync();
			}

		}
	}

	public void gravarMensagem(String messages, String topic, int particao, long offset) {
		System.out.println("*******************************************************************************");
		System.out.println("Offset:" + offset  + " Mensagem recebida: " + messages + " topic: " + topic + " Particao: " + particao);
		System.out.println("*******************************************************************************");
	}
}
