import java.util.HashMap;

public class School {
	// Attributes
	private String name;
	private HashMap<Integer, Student> students;
	private HashMap<Integer, Professor> professors;
	private HashMap<Integer, Course> courses;
	
	// Constructors
	public School(String name) {
		this.name = name;
	}
	
	// Getters and Setters
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	// Specific Methods
	public void addStudent(Student student) throws DuplicateKeyException {
		if (students.containsValue(student)) throw new DuplicateKeyException();
		else students.put(student.getID(), student);
	}
	public void addProfessor(Professor prof) throws DuplicateKeyException {
		if (professors.containsValue(prof)) throw new DuplicateKeyException();
		else professors.put(prof.getID(), prof);
	}
	public void addCourse(Course course) throws DuplicateKeyException {
		if (courses.containsValue(course)) throw new DuplicateKeyException();
		else courses.put(course.getID(), course);
	}
	public void rmStudent(Student student) throws KeyErrorException {
		if (students.containsKey(student.getID())) students.remove(student.getID());
		else throw new KeyErrorException();
	}
	public void rmProfessor(Professor prof) throws KeyErrorException {
		if (professors.containsKey(prof.getID())) professors.remove(prof.getID());
		else throw new KeyErrorException();
	}
	public void rmCourse(Course course) throws KeyErrorException {
		if (courses.containsKey(course.getID())) courses.remove(course.getID());
		else throw new KeyErrorException();
	}
}