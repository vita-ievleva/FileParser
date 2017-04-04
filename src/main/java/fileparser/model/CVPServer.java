package fileparser.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "cvp-servers")
public class CVPServer  implements Serializable {
    @XmlElement(name = "servers")
    private Servers servers = getServers();

    @XmlElement(name = "applications")
    private Application applications = this.getApplications();

    public Servers getServers() {
        return servers;
    }

    public Application getApplications() {
        return applications;
    }

    public void setServers(Servers servers) {
        this.servers = servers;
    }

    public void setApplications(Application applications) {
        this.applications = applications;
    }
}
