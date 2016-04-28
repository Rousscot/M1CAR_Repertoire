# TP2 CAR

**Authors:**
- __Aurelien Rousseau__
- __Cyril Ferlicot__

## Description

To launch a Server you can use `ServerMain`. 
A Server has a `Map` of pseudo and passwords to manage users. 
It also has a `ServerRepertoireList` to manage the repertoires.

To launch a Client you can use `ClientMain`. This main takes 2 parameter. 
A `String` that is the inet address of the server and an `Integer`, the port.

## How te use the interface

The interface is composed by 3 parts:
- The first one allow to connect to the server. For now there is only one user. The id is `test` and the password is `test`.
- The second allow to manage your repertoires. If you select `Accéder à un repertoire` you will go to the third one.
- The last allow to manage the contacts of the repertoire.

## Description of the protocols

Each command begin by sending an unique command from the Client to the server.
Here are the commands and the descriptions.

### Login

**connexion**
- *Client:* send a `String` with the login and the password te the Server.
- *Server:* send a boolean. `true` if the password is good, `false` if the password is wrong.

### Repertoire list management

**listeRep**
- *Server:* send a `String` containing all the repertoires's id separated by a space.

**chercheRep**
- *Client:* send the `Repertoire`'s id to search.
- *Server:* send the `Repertoire` to the client.

**retirerRep**
- *Client:* send the `Repertoire`'s id to remove.
- *Server:* send a Boolean. `true` if the `Repertoire` existed else `false`.

**ajouterRep**
- *Client:* send the `Repertoire` instance to add.
- *Server:* send a Boolean. `true` if the `Repertoire` did not exist, else `false`.

**accederRep**
- *Client:* send the `Repertoire`'s id that we need to select.
- *Server:* send `true` if the `Repertoire` exist. 

### Repertoire management

**liste**
- *Server:* send a `String` containing all the contacts's name separated by a space.

**cherche**
- *Client:* send the `Personne`'s id to search.
- *Server:* send the `Personne` to the client.

**retirer**
- *Client:* send the `Personne`'s id to remove.
- *Server:* send a Boolean. `true` if the `Personne` existed else `false`.

**ajouter**
- *Client:* send the `Personne` instance to add.
- *Server:* send a Boolean. `true` if the `Personne` did not exist, else `false`.

**modifier**
- *Client:* send the `Personne` updated.
- *Server:* send `true` if the update succeeded, else `false`.