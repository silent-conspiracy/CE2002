public enum Gender {
	MALE("Male"), FEMALE("Female");
	
	private String desc;
	
	Gender(String desc) {
		this.desc = desc;
	}
	
	public String getDescription() {
		return desc;
	}
}