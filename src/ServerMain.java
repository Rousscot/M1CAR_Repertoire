import server.Server;

import java.io.IOException;

/**
 * Created by Cyril on 10/04/2016.
 */
public class ServerMain {

    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.launch();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
