package fileparser.dao;

import fileparser.model.CVPServer;
import fileparser.model.Server;

import java.util.List;


public interface CVPServersDao {
    List<Server> getServers();
    List<String> getApplacationsName();
    Server addServer(Server newServer);
    CVPServer getCvp();
}
