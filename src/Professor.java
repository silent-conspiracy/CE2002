import java.util.HashMap;

public class Professor extends Person implements PrimaryKeyManager{
	// Attributes
	private static int pk;
	private int pid;
	private String pmail;
	private String office;
	private String officeContact;
	private School school;
	private String position;
	private HashMap<Integer, Course> courses; // Coordinator of courses
	private HashMap<Integer, Group> classes; // Tutor or lecturer of classes.
	
	// Constructors
	public Professor(String name, Gender gender) {
		super(name, gender);
		this.pid = pk;
		autoIncrement(this.pid);
		this.courses = new HashMap<Integer, Course>();
		this.classes = new HashMap<Integer, Group>();
	}
	public Professor(int id, String name, Gender gender) {
		super(name, gender);
		this.pid = id;
		autoIncrement(this.pid);
		this.courses = new HashMap<Integer, Course>();
		this.classes = new HashMap<Integer, Group>();
	}
	
	// Getters and Setters
	@Override
	public int getID() { return pid; }
	public String getPmail() { return pmail; }
	public String getOffice() { return office; }
	public String getOfficeContact() { return officeContact; }
	public School getSchool() { return school; }
	public String getPosition() { return position; }
	public HashMap<Integer, Course> getCourses() { return courses; }
	public HashMap<Integer, Group> getClasses() { return classes; }
	
	@Override
	public void setID(int id) { this.pid = id; }
	public void setPmail(String email) { this.pmail = email; }
	public void setOffice(String address) { this.office = address; }
	public void setOfficeContact(String contact) { this.officeContact = contact; }
	public void setSchool(School school) { this.school = school; }
	public void setPosition(String position) { this.position = position; }
	
	// Specific methods
	public void addCourse(Course course) throws DuplicateKeyException {
		if (courses.containsKey(course.getID())) throw new DuplicateKeyException();
		else courses.put(course.getID(), course);
	}
	public void addClass(Group group) throws DuplicateKeyException {
		if (classes.containsKey(group.getID())) throw new DuplicateKeyException();
		else classes.put(group.getID(), group);
	}
	public void rmCourse(Course course) throws KeyErrorException {
		if (courses.containsKey(course.getID())) courses.remove(course.getID());
		else throw new KeyErrorException();
	}
	public void rmClass(Group group) throws KeyErrorException {
		if (classes.containsKey(group.getID())) classes.remove(group.getID());
		else throw new KeyErrorException();
	}
	
	@Override
	public void autoIncrement(int id) {
		pk = id++;
	}
	
}
