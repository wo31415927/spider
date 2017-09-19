package utils.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class ConnectionInfo {
    protected String host = "";
    protected int port;
    protected String username = "";
    protected String pwd = "";

    public ConnectionInfo() {
    }

    @Override
    public String toString() {
        return "ConnectionInfo [host=" + host + ", port=" + port + ", username=" + username + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConnectionInfo)) return false;
        ConnectionInfo that = (ConnectionInfo) o;
        if (port != that.port) return false;
        if (host != null ? !host.equals(that.host) : that.host != null) return false;
        return username != null ? username.equals(that.username) : that.username == null;
    }

    @Override
    public int hashCode() {
        int result = host != null ? host.hashCode() : 0;
        result = 31 * result + port;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }

    /**
     * @return
     */
    public String toOrginString() {
        return super.toString();
    }
}
