public enum Gender {
	MALE("Male"), FEMALE("Female");
	
	// Attributes
	private String desc;
	
	// Constructors
	Gender(String desc) {
		this.desc = desc;
	}
	
	// Getters
	public String getDescription() {
		return desc;
	}
}