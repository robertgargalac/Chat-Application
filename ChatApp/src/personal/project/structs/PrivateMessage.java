package personal.project.structs;

import java.io.Serializable;

public class PrivateMessage extends Message implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private final String mRecipient;

	public PrivateMessage(String recipient, String sender,String content) {
		super(sender, content);
		mRecipient=recipient;
	}

	public String getRecipient(){
		
		return mRecipient;
		
	}
	@Override
	public String toString() {
		return "private"+ super.toString();	
	}
	
}
