
public class EndSemesterException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public EndSemesterException() {
		super("Error: End semester exception.");
	}
	
	public EndSemesterException(String msg) {
		super("Error: "+msg+".");
	}

}
