
public class DuplicateKeyException extends Exception {
	public DuplicateKeyException() {
		super("DuplicateKeyException: Duplicate key insertion.");
	}
	public DuplicateKeyException(String msg) {
		super("Error: " + msg + ".");
	}
	
	
	private static final long serialVersionUID = 1L;
	
}
