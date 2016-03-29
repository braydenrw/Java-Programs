package JobScheduler;

/**
 * Created by emanuel on 10/17/15.
 * Appended by braydenrw on 10/17/15
 */
public class Node {

    private String hostName;
    private String userName;
    private String password;
    private int port;

    public Node(String hostName, int port, String userName, String password) {
        this.hostName = hostName;
        this.userName = userName;
        this.password = password;
        this.port = port;
    }

    public String getHostName() {
        return hostName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getPort() {
        return port;
    }

}
