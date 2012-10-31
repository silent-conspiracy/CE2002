
public enum StudentType {
	FULL_TIME("Full Time Student"), PART_TIME("Part Time Student");
	
	private String desc;
	
	StudentType(String desc) {
		this.desc = desc;
	}
	
	public String getDescription() {
		return this.desc;
	}
}
