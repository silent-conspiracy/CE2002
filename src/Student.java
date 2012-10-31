import java.util.HashMap;

public class Student extends Person implements PrimaryKeyManager{
	// Attributes
	private static int pk;
	private int sid;
	private String smail;
	private School school;
	private Program program;
	private StudentType type;
	private double CGPA;
	private HashMap<Integer, Course> courses; // Courses taken
	private HashMap<Integer, Group> classes; // Classes enrolled
	private HashMap<Integer, Grade> grades; // Grades for Course IDs.
	
	// Constructors
	public Student(String name, Gender gender) {
		super(name, gender);
		this.sid = pk;
		autoIncrement(this.sid);
		courses = new HashMap<Integer, Course>();
		classes = new HashMap<Integer, Group>();
		grades = new HashMap<Integer, Grade>();
	}
	public Student(int id, String name, Gender gender) {
		super(name, gender);
		this.sid = id;
		autoIncrement(this.sid);
		courses = new HashMap<Integer, Course>();
		classes = new HashMap<Integer, Group>();
		grades = new HashMap<Integer, Grade>();
	}
	
	// Getters and Setters
	@Override
	public int getID() { return sid; }
	public String getSmail() { return smail; }
	public School getSchool() { return school; }
	public Program getProgram() { return program; }
	public String getType() { return type.getDescription(); }
	public double getCGPA() { return CGPA; }
	
	@Override
	public void setID(int id) { this.sid = id; }
	public void setSmail(String email) { this.smail = email; }
	public void setSchool(School school) { this.school = school; }
	public void setProgram(Program program) { this.program = program; }
	public void setType(StudentType type) { this.type = type; }
	public void setCGPA(double cgpa) { this.CGPA = cgpa; } 
	
	// Specific methods
	public void addCourse(Course course) throws DuplicateKeyException {
		if (courses.containsKey(course.getID())) throw new DuplicateKeyException();
		else courses.put(course.getID(), course);
	}
	public void addClass(Group group) throws DuplicateKeyException {
		if (classes.containsKey(group.getID())) throw new DuplicateKeyException();
		else classes.put(group.getID(), group);
	}
	public void addGrade(Grade grade) throws DuplicateKeyException {
		if (grades.containsKey(grade.getCourse().getID())) throw new DuplicateKeyException();
		else grades.put(grade.getCourse().getID(), grade);
	}
	public void rmCourse(Course course) throws KeyErrorException {
		if (courses.containsKey(course.getID())) courses.remove(course.getID());
		else throw new KeyErrorException();
	}
	public void rmClass(Group group) throws KeyErrorException {
		if (classes.containsKey(group.getID())) classes.remove(group.getID());
		else throw new KeyErrorException();
	}
	public void rmGrade(Grade grade) throws KeyErrorException {
		if (grades.containsKey(grade.getCourse().getID())) grades.remove(grade.getCourse().getID());
		else throw new KeyErrorException();
	}
	
	@Override
	public void autoIncrement(int id) {
		pk = id++;
	}
}
