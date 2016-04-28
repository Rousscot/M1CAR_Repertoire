import client.Client;
import repertoire.AuthentificationIHM;

/**
 * Created by JeCisC on 22/04/2016.
 */
public class ClientMain {

    public static void main(String args[]) {

        Client client = new Client(args[0], Integer.valueOf(args[1]));

        AuthentificationIHM ihm = new AuthentificationIHM(client);
        
        ihm.setVisible(true);
    }
}
