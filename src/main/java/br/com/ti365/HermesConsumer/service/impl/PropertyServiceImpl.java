package br.com.ti365.HermesConsumer.service.impl;

import java.io.InputStream;
import java.util.Map.Entry;
import java.util.Properties;

import com.rabbitmq.client.ConnectionFactory;

import br.com.ti365.HermesConsumer.service.PropertyService;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class PropertyServiceImpl implements PropertyService {

	public Properties loadFileProperties() {
		Properties properties = new Properties();

		// Para testes locais, utilizar a String abaixo
		String propFileName = "application.properties";
//		String propFileName = "./resources/application.properties";
		try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName)) {

			log.debug("Buscando arquivo " + propFileName + "...");

			if (inputStream != null) {
				properties.load(inputStream);
				log.debug("Arquivo carregando com sucesso");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return properties;
	}

	public Properties loadKafkaProperties() {
		Properties propertiesfile = new Properties();
		propertiesfile = loadFileProperties();

		Properties kafkaProp = new Properties();

		for (Entry<Object, Object> entry : propertiesfile.entrySet()) {
			if (entry.getKey().toString().contains("kafka"))
				kafkaProp.put(entry.getKey().toString().replace("kafka.", ""), entry.getValue());
		}
		return kafkaProp;
	}

	public ConnectionFactory loadRabbitProperties(ConnectionFactory factory, Properties properties) {
		factory.setHost(properties.getProperty("rabbitmq.host"));
		factory.setUsername(properties.getProperty("rabbitmq.username"));
		factory.setPassword(properties.getProperty("rabbitmq.password"));
		factory.setPort(Integer.parseInt(properties.getProperty("rabbitmq.port")));
		factory.setVirtualHost(properties.getProperty("rabbitmq.virtualhost"));
		return factory;
	}

}
