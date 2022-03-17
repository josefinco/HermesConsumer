package br.com.ti365.HermesConsumer.service.impl;

import java.io.InputStream;
import java.util.Properties;

import br.com.ti365.HermesConsumer.service.PropertyService;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class PropertyServiceImpl implements PropertyService {

	public Properties loadProperties() {
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

}
