import client.Client;
import repertoire.AuthentificationIHM;
import repertoire.IHM;

/**
 * Created by JeCisC on 22/04/2016.
 */
public class ClientMain {

    public static void main(String args[]) {

        Client client = new Client(args[0], Integer.valueOf(args[1]));

        /*IHM ihm = new IHM();

        ihm.fixerRepertoire(client.getRepertoire());
        */

        AuthentificationIHM ihm = new AuthentificationIHM(client);
        ihm.setVisible(true);
    }
}
