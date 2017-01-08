package personal.project.client;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import personal.project.structs.Message;
import personal.project.structs.PrivateMessage;

public class ClientPeer extends Thread{

	private String mSender;
	private Socket mSocket;
	private ObjectOutputStream mObjectStream;
	
	public ClientPeer(String sender,Socket socket) throws IOException {
		
		mSender=sender;
		mSocket=socket;	
		mObjectStream=new ObjectOutputStream(socket.getOutputStream());
		
	}
	@Override
	public void run () {
        try {
           ObjectInputStream stream = new ObjectInputStream(mSocket.getInputStream());

           while (true) {
               Message message = (Message) stream.readObject();//upcast explicit
               System.out.println(message.toString());
           }
       } 
        catch (EOFException ex) {
        
       } 
        catch (IOException ex) {
           System.err.println("Client connection reset: " + ex.getMessage());
       } 
        catch (ClassNotFoundException ex) {
           System.err.println("Unknown object received.");
       } 
        finally {
           System.exit(0); 
           }
       }
	
	 public void sendMessage(String message) throws IOException {
	       mObjectStream.writeObject(new Message(mSender, message));
	    }

	    public void sendMessage(String recipient, String message) throws IOException {
	        mObjectStream.writeObject(new PrivateMessage(recipient, mSender, message));
	    }
	}
		
	
