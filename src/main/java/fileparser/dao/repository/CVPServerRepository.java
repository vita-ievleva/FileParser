package fileparser.dao.repository;


import fileparser.dao.util.JAXBFileParserUtil;
import fileparser.model.CVPServer;
import fileparser.model.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.List;


@Repository
public class CVPServerRepository {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public CVPServer getCVPServerFromFile() {
        File file = JAXBFileParserUtil.getFileByPath("serverApplications.xml");
        return JAXBFileParserUtil.unmarshal(file);
    }

    public List<Server> getServerList() {
        logger.debug("Getting List of available server");
        return getCVPServerFromFile().getServers().getServerList();
    }

    public List<String> getApplicationNameList() {
        logger.debug("Getting List of available apps");
        List<String> appNameList = getCVPServerFromFile().getApplications().getName();

        return appNameList;
    }

    public Server addServer(Server server) {
        File file = JAXBFileParserUtil.getFileByPath("serverApplications.xml");
        logger.debug("Adding server to the file " + file.getPath());

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file.getPath(), true))) {

            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            raf.setLength(raf.length() - 26);

            bw.write(server.toString() + "</servers>\n</cvp-servers>");

            return server;

        } catch (IOException ex) {
            logger.error("New Server was not added due to: ", ex);
            return null;
        }

    }

}
