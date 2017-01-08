package personal.project.server;

import personal.project.server.Server;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.io.EOFException;
import java.io.IOException;

import personal.project.structs.Message;

public class ServerPeer extends Thread{
	private final Socket mSocket;
    private String mClientName;
    private final Server mServer;
    private final ObjectOutputStream mObjectOutputStream;

    public ServerPeer(Socket communicationSocket, Server server) throws IOException {
        mSocket = communicationSocket;
        mServer = server;
        mObjectOutputStream = new ObjectOutputStream(communicationSocket.getOutputStream());
    }

    public String getClientName() {
        return mClientName;
    }
@Override
    public void run() {
        try {
            ObjectInputStream stream = new ObjectInputStream(mSocket.getInputStream());

            while (true) {
                Message message = (Message) stream.readObject();
                mClientName = message.getSender();
                mServer.dispatch(message);
            }
        } catch (EOFException ex) {
        } catch (IOException ex) {
            System.err.println("Client connection reset: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.err.println("Unknown object received.");
        } finally {
            mServer.cleanup(this);
        }
    }

    public void sendMessage(Message message) {
        try {
            mObjectOutputStream.writeObject(message);
        } catch (IOException ex) {
            
        }
    }
}
