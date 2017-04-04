package fileparser.dao.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesReader {
    private static Logger logger = LoggerFactory.getLogger(PropertiesReader.class);

    public static Map<String, String> getSearchCriteria() {
        Map<String, String> result = new HashMap<>();
        File file = JAXBFileParserUtil.getFileByPath("searchCriteria.properties");

        try (FileInputStream fileInput = new FileInputStream(file)) {
            Properties properties = new Properties();
            properties.load(fileInput);
            properties.forEach((k, v) -> result.put((String) k, (String) v));

        } catch (IOException e) {
            logger.error("Failed to get Search Attributes..", e);
        }

        return result;

    }

}
