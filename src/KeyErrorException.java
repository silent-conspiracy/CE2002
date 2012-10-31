
public class KeyErrorException extends Exception {
	public KeyErrorException() {
		super("KeyErrorException: Invalid key.");
	}
	public KeyErrorException(String msg) {
		super("Error: " + msg + ".");
	}
	private static final long serialVersionUID = 1L;
}
