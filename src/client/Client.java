package client;

import java.io.IOException;
import java.net.ConnectException;

/**
 * Created by JeCisC on 22/04/2016.
 */
public class Client {

    protected ClientRepertoireList repertoires;

    protected String address;

    protected Integer port;

    public Client(String address, Integer port) {
        this.address = address;
        this.port = port;
        try {
            this.repertoires = new ClientRepertoireList(address, port);
        } catch (ConnectException e) {
            System.out.println("ERROR: Connection refused.");
        } catch (IOException e) {
            e.printStackTrace();//TODO
        }
    }

    public String getAddress() {
        return address;
    }
    public Integer getPort() {
        return port;
    }

    public ClientRepertoireList getRepertoires() {
        return repertoires;
    }
}
