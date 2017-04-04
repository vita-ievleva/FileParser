package fileparser.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "server")
public class Server implements Serializable {

    private String ipAddress;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Server server = (Server) o;
        return ipAddress.equals(server.ipAddress) | name.equals(server.name);
    }

    @Override
    public int hashCode() {
        int result = ipAddress.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    private String name;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "    <server>\n"
                + "        <ipAddress>" + ipAddress + "</ipAddress>\n"
                + "        <name>" + name + "</name>\n"
                + "    </server>\n";
    }

}
