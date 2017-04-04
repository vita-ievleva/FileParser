package fileparser.controller;

import fileparser.dao.CVPServersDao;
import fileparser.dao.util.PropertiesReader;
import fileparser.model.CVPServer;
import fileparser.model.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class FileParserController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    CVPServersDao cvpServersDao;

    @GetMapping(value = "/servers")
    public ResponseEntity getServers() {
        logger.debug("Getting list of servers");
        List<Server> list = cvpServersDao.getServers();

        Map<String, List<Server>> response = new HashMap<>();
        response.put("servers", list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

  @GetMapping(value = "/app")
    public ResponseEntity getApplications() {
        logger.debug("Getting list of applications");
        List<String> list = cvpServersDao.getApplacationsName();

        Map<String, List<String>> responce = new HashMap<>();
        responce.put("applications", list);

        return new ResponseEntity<>(responce, HttpStatus.OK);
    }


  @GetMapping(value = "/cvp")
    public ResponseEntity getCVPServers() {
        logger.debug("Getting list of cvp");
        CVPServer cvp = cvpServersDao.getCvp();

        Map<String, List<?>> responce = new HashMap<>();
        responce.put("applications", cvp.getApplications().getName());
        responce.put("servers", cvp.getServers().getServerList());

        return new ResponseEntity<>(responce, HttpStatus.OK);
    }

    @GetMapping(value = "/searchcriteria")
    public ResponseEntity getSearchCriteriaList() {
        logger.debug("Getting list of Search Criteria");

        return new ResponseEntity<>(PropertiesReader.getSearchCriteria().values(), HttpStatus.OK);
    }

}
