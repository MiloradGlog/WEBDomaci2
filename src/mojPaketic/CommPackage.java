package mojPaketic;

public class CommPackage {
	
	private int clientUUID;
	private String message;
	private boolean clientPlaying;
	
	public CommPackage(String message) {
		this.message = message;
	}

	public void setClientUUID(int clientUUID) {
		this.clientUUID = clientUUID;
	}
	
	public int getClientUUID() {
		return clientUUID;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setClientPlaying(boolean clientPlaying) {
		this.clientPlaying = clientPlaying;
	}
	
	public boolean isClientPlaying() {
		return clientPlaying;
	}
}
