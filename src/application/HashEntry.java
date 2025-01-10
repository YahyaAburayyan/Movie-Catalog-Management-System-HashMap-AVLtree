package application;

public class HashEntry {

	private String key; // which is the title 
 	private AVL value;  // the movies 
	private int status  =0; //0= empty , 1=full, 2=deleted
	
	// Constructors 
	public HashEntry () {
		
	}

	public HashEntry(String key) {
		super();
		this.key = key;
		this.value = new AVL();
		this.status = 1;
	}

	// Setters and Getters
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public AVL getValue() {
		return value;
	}

	public void setValue(AVL value) {
		this.value = value;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
