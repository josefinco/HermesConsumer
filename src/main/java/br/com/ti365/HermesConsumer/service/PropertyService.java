package br.com.ti365.HermesConsumer.service;

import java.util.Properties;
import com.rabbitmq.client.ConnectionFactory;

public interface PropertyService {
	
	
	/*
	 * Get all propiertes from application.properties file 
	 */
	 Properties loadFileProperties();
	 
	 /*
	  * Get Kafka Properties from file
	  */
	 Properties loadKafkaProperties();
	 	 
	 /*
	  * Get rabbitMQ Factory with properties parameters injected
	  */
	 ConnectionFactory loadRabbitProperties(ConnectionFactory factory, Properties properties);
	
}
