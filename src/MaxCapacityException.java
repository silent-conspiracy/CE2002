
public class MaxCapacityException extends Exception {
	public MaxCapacityException() {
		super("MaxCapacityException: Maximum capacity reached.");
	}
	public MaxCapacityException(String msg) {
		super("Error: " + msg +".");
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
