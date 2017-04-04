package fileparser.service;

import fileparser.dao.CVPServersDao;
import fileparser.model.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CVPServersServiceImpl implements CVPServersService {

    @Autowired
    CVPServersDao cvpServersDao;

    @Override
    public Server addServer(Server newServer) {
        return cvpServersDao.addServer(newServer);

    }
}
