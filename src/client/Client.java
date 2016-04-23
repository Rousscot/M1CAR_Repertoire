package client;

import java.io.IOException;
import java.net.ConnectException;

/**
 * Created by JeCisC on 22/04/2016.
 */
public class Client {

    protected ClientRepertoire repertoire;

    public Client(String address, Integer port) {
        try {
            this.repertoire = new ClientRepertoire(address, port);
        } catch (ConnectException e) {
            System.out.println("ERROR: Connection refused.");
        } catch (IOException e) {
            e.printStackTrace();//TODO
        }
    }

    public ClientRepertoire getRepertoire() {
        return repertoire;
    }
}
