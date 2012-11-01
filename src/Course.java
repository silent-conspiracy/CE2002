import java.io.Serializable;
import java.util.HashMap;

public class Course implements PrimaryKeyManager, Serializable, SortByName {
	private static final long serialVersionUID = 1L;
	// Attributes
	private static int pk = 1; // To ensure id is unique.
	private int id;
	private String name;
	private int AU;
	private CourseType type;
	private HashMap<String, Double> weights;
	private CourseGroup groups;
	private Professor coordinator;
	
	// Constructors
	public Course(
			String name, 
			int AU, 
			CourseType type, 
			Professor prof, 
			int vacancy, 
			HashMap<String, Double> weights) 
	{
		this.id = pk;
		autoIncrement(this.id);
		this.name = name;
		this.AU = AU;
		this.type = type;
		this.weights = weights;
		this.groups = new CourseGroup(type, vacancy);
		this.coordinator = prof;
	}
	
	// Getters and Setters
	public int getID() { return id; }
	public String getName() { return name; }
	public int getAU() { return AU; }
	public CourseType getType() { return this.type; }
	public HashMap<String, Double> getWeights() { return this.weights; }
	public CourseGroup getGroups() { return this.groups; }
	public Professor getCoordinator() { return this.coordinator; }
	
	public void setID(int id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	public void setAU(int number) { this.AU = number; }
	public void setType(CourseType type) { this.type = type; setGroups(type); }
	public void setWeights(HashMap<String, Double> weights) { this.weights = weights; }
	public void setGroups(CourseGroup groups) { this.groups = groups; }
	public void setCoordinator(Professor prof) { this.coordinator = prof; }
	
	// Specific methods
	public void setGroups(CourseType type) {
		// Overwrites current CourseGroup
		int capacity = this.groups.getCapacity();
		this.groups = new CourseGroup(type, capacity);
	}
	@Override
	public void autoIncrement(int id) {
		pk = ++id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Course)) return false;
		Course course = (Course) obj;
		if (course.getID() == this.id) return true;
		if (course.getName() == this.getName()) return true;
		return false;
	}
}