package fileparser.dao.impl;

import fileparser.dao.CVPServersDao;
import fileparser.dao.repository.CVPServerRepository;
import fileparser.model.CVPServer;
import fileparser.model.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CVPServersDaoImpl implements CVPServersDao {
    @Autowired
    CVPServerRepository repository;

    @Override
    public List<Server> getServers() {
        List<Server> list = repository.getServerList();
        return list;
    }

    @Override
    public List<String> getApplacationsName() {
        List<String> list = repository.getApplicationNameList();
        return list;
    }

    @Override
    public Server addServer(Server newServer) {
        return repository.addServer(newServer);
    }

    @Override
    public CVPServer getCvp() {
        return repository.getCVPServerFromFile();
    }
}

