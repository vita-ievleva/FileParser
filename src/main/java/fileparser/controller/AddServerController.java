package fileparser.controller;

import fileparser.dao.repository.CVPServerRepository;
import fileparser.model.Server;
import fileparser.service.CVPServersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
public class AddServerController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    CVPServerRepository repository;

    @Autowired
    CVPServersService service;

    @PostMapping("/newServer")
    public ResponseEntity addServer(@RequestBody Map<String, String> newServer) {
        Server server = new Server();
        server.setName(newServer.get("name"));
        server.setIpAddress(newServer.get("ip"));

        logger.debug("Adding new server to file..", server);

        Optional<Server> serverFromFile = repository.getServerList().stream().filter(s -> s.equals(server)).findAny();

        if (!serverFromFile.isPresent()) {
            Server result = service.addServer(server);

            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            logger.error("Server " + server + " is invalid or already exist.");
            return new ResponseEntity<>(server, HttpStatus.BAD_REQUEST);

        }

    }
}
