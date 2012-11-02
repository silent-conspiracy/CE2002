import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;


public class Semester implements PrimaryKeyManager, Serializable{
	private static final long serialVersionUID = 1L;
	// Attributes
	public static int pk = 1;
	private int id;
	private String name;
	private HashMap<Integer, Course> courses;

	// Constructors
	public Semester(String name) {
		this.id = pk;
		autoIncrement(this.id);
		this.name = name;
		this.courses = new HashMap<Integer, Course>();
	}
	// Getters and Setter
	public int getID() { return id; }
	public String getName() { return name; }
	public HashMap<Integer, Course> getCourses() { return courses; }
	
	public void setID(int id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	public void setCourses(HashMap<Integer, Course> courses) { this.courses = courses; }
	
	// Specific methods.
	public void addCourse(Course course) throws DuplicateKeyException {
		if (courses.containsKey(course.getID()))
			throw new DuplicateKeyException(String.format("Course ID %d has already been added.", course.getID()));
		else {
			Course copyCourse = new Course(course);
			copyCourse.setSemester(this);
			courses.put(copyCourse.getID(), copyCourse);
		}
	}
	public void rmCourse(Course course) throws KeyErrorException {
		if (courses.containsKey(course.getID())) {
			courses.remove(course.getID());
		}
		else throw new KeyErrorException(String.format("Course ID %d has not yet been added.", course.getID()));
		
	}
	
	@Override
	public void autoIncrement(int id) {
		if (id < pk) return;
		pk = ++id;
		
	}

	@Override
	public void readObject(ObjectInputStream stream) throws IOException,
			ClassNotFoundException {
		stream.defaultReadObject();
		autoIncrement(this.id);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Semester)) return false;
		Semester temp = (Semester) obj;
		if (this.id == temp.getID()) return true;
		if (this.name == temp.getName()) return true;
		return false;
	}
	
}
