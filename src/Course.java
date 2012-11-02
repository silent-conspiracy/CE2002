import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;

public class Course implements PrimaryKeyManager, Serializable, SortByName {
	private static final long serialVersionUID = 1L;
	// Attributes
	public static int pk; // To ensure id is unique.
	private int id;
	private String name;
	private int AU;
	private CourseType type;
	private HashMap<String, Double> weights;
	private CourseGroup groups;
	private Professor coordinator;
	private Semester semester;
	
	// Constructors
	public Course(String name, CourseType type, Professor prof, int capacity) {
		this.id = pk;
		autoIncrement(this.id);
		this.name = name;
		this.AU = 1;
		this.type = type;
		this.weights = new HashMap<String, Double>();
		this.groups = new CourseGroup(type, capacity);
		this.coordinator = prof;
		this.semester = null;
	}
	public Course(
			String name, 
			int AU, 
			CourseType type, 
			Professor prof, 
			int capacity, 
			HashMap<String, Double> weights) 
	{
		this.id = pk;
		autoIncrement(this.id);
		this.name = name;
		this.AU = AU;
		this.type = type;
		this.weights = weights;
		this.groups = new CourseGroup(type, capacity);
		this.coordinator = prof;
		this.semester = null;
	}
	public Course(Course course) { // Deep copy purpose.
		this.id = course.getID();
		this.name = course.getName();
		this.AU = course.getAU();
		this.type = course.getType();
		this.weights = course.getWeights();
		this.groups = course.getGroups();
		this.coordinator = course.getCoordinator();
		this.semester = course.getSemester();
	}
	
	// Getters and Setters
	public int getID() { return id; }
	public String getName() { return name; }
	public int getAU() { return AU; }
	public CourseType getType() { return this.type; }
	public HashMap<String, Double> getWeights() { return this.weights; }
	public CourseGroup getGroups() { return this.groups; }
	public Professor getCoordinator() { return this.coordinator; }
	public Semester getSemester() { return this.semester; }
	
	public void setID(int id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	public void setAU(int number) { this.AU = number; }
	public void setType(CourseType type) { this.type = type; setGroups(type); }
	public void setWeights(HashMap<String, Double> weights) { this.weights = weights; }
	public void setGroups(CourseGroup groups) { this.groups = groups; }
	public void setCoordinator(Professor prof) { this.coordinator = prof; }
	public void setSemester(Semester sem) { this.semester = sem; }
	
	// Specific methods
	public void setGroups(CourseType type) {
		// Overwrites current CourseGroup
		int capacity = this.groups.getCapacity();
		this.groups = new CourseGroup(type, capacity);
	}
	@Override
	public void autoIncrement(int id) {
		if (id < pk) return;
		pk = ++id;
	}
	@Override
	public void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
		stream.defaultReadObject();
		autoIncrement(this.id);
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