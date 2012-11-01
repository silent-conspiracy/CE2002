import java.util.HashSet;

public enum CourseType {
	LECTURE("Lectures only"), 
	TUTORIAL("Tutorials only"),
	LAB("Labs only"),
	LECT_TUT("Lectures and tutorials"),
	LECT_TUT_LAB("Lectures, tutorials and labs"),
	LECT_LAB("Lectures and labs"),
	TUT_LAB("Tutorials and labs");
	
	// Attributes
	private final String desc;
	private final HashSet<CourseType> lectures = new HashSet<CourseType>();
	private final HashSet<CourseType> tutorials = new HashSet<CourseType>();
	private final HashSet<CourseType> labs = new HashSet<CourseType>();
	
	// Constructor
	CourseType(String desc) {
		this.desc = desc;
		String temp = desc.toLowerCase();
		if (temp.contains("lectures")) lectures.add(this);
		if (temp.contains("tutorials")) tutorials.add(this);
		if (temp.contains("labs")) labs.add(this);
	}
	
	// Getters
	public String getDescription() { return this.desc; }
	public HashSet<CourseType> getLectures() { return lectures; }
	public HashSet<CourseType> getTutorials() { return tutorials; }
	public HashSet<CourseType> getLabs() { return labs; }
}
