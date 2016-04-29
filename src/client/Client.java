package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by JeCisC on 22/04/2016.
 */
public class Client {

    protected ClientRepertoireList repertoires;
    protected Socket socket;
    protected ObjectInputStream ois;
    protected ObjectOutputStream oos;

    public Client(String address, Integer port) {
        try {
            this.repertoires = new ClientRepertoireList(this);
            this.socket = new Socket(address, port);
        } catch (IOException e) {
            System.out.println("ERROR: Connection refused.");
            System.exit(182);
        }
    }

    public ClientRepertoireList getRepertoires() {
        return repertoires;
    }

    public ObjectOutputStream oos() {
        if (this.oos == null) {
            try {
                this.oos = new ObjectOutputStream(this.socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error. Cannot instanciate the streams :(");
            }
        }
        return oos;

    }

    public ObjectInputStream ois() {
        if (this.ois == null) {
            try {
                this.ois = new ObjectInputStream(this.socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error. Cannot instanciate the streams :(");
            }
        }
        return ois;
    }
}
