package client;

import java.io.IOException;
import java.net.ConnectException;

/**
 * Created by JeCisC on 22/04/2016.
 */
public class Client {

    protected ClientRepertoireList repertoires;

    public Client(String address, Integer port) {
        try {
            this.repertoires = new ClientRepertoireList(address, port);
        } catch (ConnectException e) {
            System.out.println("ERROR: Connection refused.");
        } catch (IOException e) {
            e.printStackTrace();//TODO
        }
    }

    public ClientRepertoireList getRepertoires() {
        return repertoires;
    }
}
