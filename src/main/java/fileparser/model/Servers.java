package fileparser.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Servers  implements Serializable {
    @XmlElement(name = "server")
    private List<Server> server = new ArrayList<>();

    public Servers() {
    }

    public Servers(List<Server> servers) {
        this.server = servers;
    }

    public List<Server> getServerList() {
        return server;
    }

    public void setServer(List<Server> server) {
        this.server = server;
    }
}
