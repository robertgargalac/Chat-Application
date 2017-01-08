package personal.project.server;

import java.awt.List;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import personal.project.server.ServerPeer;
import personal.project.server.config.ServerConfig;
import personal.project.server.exceptions.InvalidFormatException;
import personal.project.server.exceptions.MissingKeyException;
import personal.project.server.exceptions.UnknownKeyException;
import personal.project.structs.Message;
import personal.project.structs.PrivateMessage;

public class Server {

	private final ArrayList<ServerPeer> mPeers;

    public Server() throws IOException, InvalidFormatException, UnknownKeyException, MissingKeyException {
        mPeers = new ArrayList<>();
    }
    

    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.listen();

        } catch (Throwable t) {
            System.err.println("Exception in thread main: " + t.getMessage());
        }
    }

    public synchronized void dispatch(Message message) {
        if (message instanceof PrivateMessage) {
            PrivateMessage privateMessage = (PrivateMessage) message;
            for (ServerPeer peer : mPeers) {
                if (privateMessage.getRecipient().equals(peer.getClientName())
                        || privateMessage.getSender().equals(peer.getClientName())) {
                    peer.sendMessage(message);
                }
            }
        } else {
            for (ServerPeer peer : mPeers) {
                peer.sendMessage(message);
            }
        }
    }

    public synchronized void cleanup(ServerPeer peer) {
        mPeers.remove(peer);
        dispatch(new Message("SERVER", peer.getClientName() + " has left the server."));
    }

    private void listen() throws IOException, InvalidFormatException, UnknownKeyException, MissingKeyException {
        ServerConfig config = new ServerConfig();
        ServerSocket serverSocket = new ServerSocket(config.getTcpPort());

        while (true) {
            Socket socket = serverSocket.accept();
            synchronized (this) {
                if (mPeers.size() >= config.getMaxClients()) {
                    socket.close();
                    continue;
                }

                ServerPeer peer = new ServerPeer(socket, this);
                dispatch(new Message("SERVER", "New client connected!"));
                mPeers.add(peer);
                peer.start();
            }
        }
    }
}
