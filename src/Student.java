import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Student extends Person implements PrimaryKeyManager {
	private static final long serialVersionUID = 1L;
	// Attributes
	public static int pk = 1; // For PrimaryKeyManager (unique IDs)
	private int sid;
	private String smail;
	private School school;
	private Program program;
	private StudentType type;
	private double CGPA;
	private HashMap<Integer, Course> courses; // Courses taken
	private HashMap<Integer, Group> classes; // Classes enrolled
	private HashMap<Course, Grade> grades; // Grades for Course.
	
	// Constructors
	public Student(String name, Gender gender, School school) {
		super(name, gender);
		this.sid = pk;
		autoIncrement(this.sid);
		this.smail = null;
		this.school = school;
		this.program = null;
		this.type = null;
		this.CGPA = 0.0;
		courses = new HashMap<Integer, Course>();
		classes = new HashMap<Integer, Group>();
		grades = new HashMap<Course, Grade>(); //
	}
	public Student(int id, String name, Gender gender, School school) {
		super(name, gender);
		this.sid = id;
		autoIncrement(this.sid);
		this.smail = null;
		this.school = school;
		this.program = null;
		this.type = null;
		this.CGPA = 0.0;
		courses = new HashMap<Integer, Course>();
		classes = new HashMap<Integer, Group>();
		grades = new HashMap<Course, Grade>();
	}
	
	// Getters and Setters
	@Override
	public int getID() { return sid; }
	public String getSmail() { return smail; }
	public School getSchool() { return school; }
	public Program getProgram() { return program; }
	public String getType() { return type.getDescription(); }
	public double getCGPA() { return CGPA; }
	public HashMap<Integer, Course> getCourses() { return courses; }
	public HashMap<Integer, Group> getClasses() { return classes; }
	public HashMap<Course, Grade> getGrades() { return grades; }
	
	@Override
	public void setID(int id) { this.sid = id; }
	public void setSmail(String email) { this.smail = email; }
	public void setSchool(School school) { this.school = school; }
	public void setProgram(Program program) { this.program = program; }
	public void setType(StudentType type) { this.type = type; }
	public void setCGPA(double cgpa) { this.CGPA = cgpa; }
	public void setCourses(HashMap<Integer, Course> courses) { this.courses = courses; }
	public void setClasses(HashMap<Integer, Group> groups) { this.classes = groups; }
	public void setGrades(HashMap<Course, Grade> grades) { this.grades = grades; }
	
	// Specific methods
	public Course getCourse(int id) throws KeyErrorException {
		if (courses.containsKey(id)) return courses.get(id);
		else throw new KeyErrorException(String.format("Student %d, %s is not registered in Course ID %d",getID(),getName(),id));
	}
	public Group getClass(int id) throws KeyErrorException {
		if (classes.containsKey(id)) return classes.get(id);
		else throw new KeyErrorException(String.format("Student %d, %s is not enrolled in Group ID %d",getID(),getName(),id));
	}
	public Grade getGrade(Course course) throws KeyErrorException {
		if (grades.containsKey(course)) return grades.get(course);
		else throw new KeyErrorException(String.format("Student %d, %s is not enrolled in course ID %d",getID(),getName(),course.getID()));
	}
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
		else grades.put(grade.getCourse(), grade);
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
		if (grades.containsKey(grade.getCourse())) grades.remove(grade.getCourse().getID());
		else throw new KeyErrorException();
	}
	public HashMap<Semester, ArrayList<Course>> getCoursesBySemester() {
		HashMap<Semester, ArrayList<Course>> courses = new HashMap<Semester, ArrayList<Course>>();
		for (Course course : this.grades.keySet()) {
			if (!courses.containsKey(course.getSemester()))
				courses.put(course.getSemester(), new ArrayList<Course>());
			courses.get(course.getSemester()).add(course);
		}
		for (ArrayList<Course> courseList : courses.values()) {
			Collections.sort(courseList);
		}
		return courses;
	}
	public void printTranscript() {
		System.out.println(printTranscript(""));
	}
	public String printTranscript(String tabs) {
		String msg = new String();
		HashMap<Semester, ArrayList<Course>> courses = getCoursesBySemester();
		ArrayList<Semester> semList = new ArrayList<Semester>(courses.keySet());
		Collections.sort(semList);
		msg += String.format("%sCGPA: %.2f\n",tabs, this.CGPA);
		msg += String.format("%sCourses taken: \n",tabs);
		for (Semester sem : semList) {
			msg += String.format("%sSemester: %s\n", tabs, sem.getName());
			for (Course course : courses.get(sem)) {
				msg += String.format("%s\tCourse: %d, %s\n", tabs, course.getID(), course.getName());
				msg += this.grades.get(course).printMarks(tabs+"\t\t");
			}
		}
		return msg+'\n';
	}
	
	@Override
	public void autoIncrement(int id) {
		if (id < pk) return;
		pk = ++id;
	}
	@Override
	public void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
		stream.defaultReadObject();
		autoIncrement(this.getID());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Person)) return false;
		Person temp = (Person) obj;
		if (this.getID() == temp.getID()) return true;
		if (this.getName() == temp.getName()) return true;
		return false;
	}
}
