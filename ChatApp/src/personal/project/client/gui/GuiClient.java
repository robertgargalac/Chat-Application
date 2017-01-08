package personal.project.client.gui;
import java.awt.*;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import personal.project.client.ClientPeer;

public class GuiClient extends JFrame implements ActionListener{
		 
	  private JPanel mainPanel;
	    private JLabel mSenderNameLabel;
	    private JTextField mSenderTextField;
	    private JButton mChangeSenderButton;
	    private JTextArea mMessageDisplayTextArea;
	    private JTextField mMessageTextField;
	    private JScrollPane mMessageAreaScrollPane;
	    private JButton mSendMessageButton; 
	    private ClientPeer peer;
	    private Socket sock = null;
	    private static String user;
	    
	    public GuiClient(Socket sock, String user){
	        this.sock = sock;
	        this.user = user;
	        initComponents();
	        pack();
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	       setSize(600,600);
	    }
	    
	    void display(){
	       peer.start();
	        setVisible(true);
	    }
	    
	    private void initComponents() {
	        
	        
	        mainPanel = new JPanel(new FlowLayout());
	        mSenderNameLabel = new JLabel("Name:");
	        mMessageTextField = new JTextField(10);
	        mSenderTextField = new JTextField(10);
	        mChangeSenderButton = new JButton("Change Name");
	        mSendMessageButton = new JButton("Send");
	        mMessageDisplayTextArea=  new JTextArea(30,30);
	        mMessageAreaScrollPane = new JScrollPane(mMessageDisplayTextArea);

	        mMessageDisplayTextArea.setEditable(false);
	        
	        BoxLayout layout = new BoxLayout(mainPanel,BoxLayout.Y_AXIS);

	        add(mainPanel);
	        mainPanel.setLayout(layout);

	        JPanel rowOne = new JPanel(new FlowLayout());
	        rowOne.add(mSenderNameLabel);
	        rowOne.add(mSenderTextField);
	        rowOne.add(mChangeSenderButton);

	        JPanel rowTwo = new JPanel(new FlowLayout());
	        rowTwo.add(mMessageAreaScrollPane);

	        JPanel rowThree = new JPanel(new FlowLayout());
	        rowThree.add(mMessageTextField);
	        rowThree.add(mSendMessageButton);

	        rowOne.setVisible(true);
	        rowTwo.setVisible(true);
	        rowThree.setVisible(true);

	        mainPanel.add(rowOne);
	        mainPanel.add(rowTwo);
	        mainPanel.add(rowThree);
	    }
	       
	           
	    
	    
	    public static void main(String[] args){
	        String host = null;
	        int port = 0;
	        for (String s: args){
	            user = args[0];
	            host = args[1];
	            if (args.length > 0) {
	            try {
	                port = Integer.parseInt(args[2]);
	            } catch (NumberFormatException e) {
	                System.err.println("Argument" + args[2] + " must be an integer.");
	                System.exit(1);
	                }
	            }
	        }
	        Socket sock = null;
	        try {
	            sock = new Socket(host,port);
	        } catch (IOException ex) {
	            Logger.getLogger(GuiClient.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        GuiClient gui = new GuiClient(sock,user);
	        
	        gui.display();
	    }
	   
	    public void actionPerformed(ActionEvent e) {
	        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	    }
	 
	    
	}
