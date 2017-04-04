package fileparser.dao.util;

import fileparser.model.CVPServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.net.URI;


public class JAXBFileParserUtil {
    private static Logger logger = LoggerFactory.getLogger(JAXBFileParserUtil.class);

    public static CVPServer unmarshal(File importFile) {
        CVPServer servers = null;
        logger.warn("Try to unmarshal servers and application from File " + importFile.getAbsolutePath());

        JAXBContext context = null;
        try {
            context = JAXBContext.newInstance(CVPServer.class);
            Unmarshaller um = context.createUnmarshaller();
            servers = (CVPServer) um.unmarshal(importFile);

        } catch (JAXBException e) {
            logger.error("Failed to unmarshal file " + importFile.getAbsolutePath(), e);
            throw new IllegalArgumentException(e);
        }

        return servers;
    }

    public static File getFileByPath(String path) {
        File file;
        try {
            logger.warn("Reading content from file " + path);
            URI uri = JAXBFileParserUtil.class.getClassLoader().getResource(path).toURI();
            file = new File(uri);
            return file;
        } catch (Exception e) {
            logger.error("Reading content for file " + path + "is failed: ", e);
            throw new RuntimeException(e);
        }
    }
}
