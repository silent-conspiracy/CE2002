
public enum Program {
	CE("Computer Engineering"), CS("Computer Science");
	
	private String name;
	
	Program(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}
