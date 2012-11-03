import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class School implements Serializable, SortByName {
	private static final long serialVersionUID = 1L;
	// Attributes
	private String name;
	private HashMap<Integer, Student> students;
	private HashMap<Integer, Professor> professors;
	private HashMap<Integer, Course> courses;
	
	// Constructors
	public School(String name) {
		this.name = name;
		this.students = new HashMap<Integer, Student>();
		this.professors = new HashMap<Integer, Professor>();
		this.courses = new HashMap<Integer, Course>();
	}
	
	// Getters and Setters
	public String getName() { return name; }
	public HashMap<Integer, Student> getStudents() { return students; }
	public HashMap<Integer, Professor> getProfessors() { return professors; }
	public HashMap<Integer, Course> getCourses() { return courses; }
	
	public void setName(String name) { this.name = name; }
	public void setStudents(HashMap<Integer, Student> students) { this.students = students; }
	public void setProfessors(HashMap<Integer, Professor> profs) { this.professors = profs; }
	public void setCourses(HashMap<Integer, Course> courses) { this.courses = courses; }
	
	// Specific Methods
	public Student getStudent(int id) throws KeyErrorException {
		if (students.containsKey(id)) return students.get(id);
		else throw new KeyErrorException(String.format("Student ID %d does not exist.", id));
	}
	public Professor getProfessor(int id) throws KeyErrorException {
		if (professors.containsKey(id)) return professors.get(id);
		else throw new KeyErrorException(String.format("Professor ID %d does not exist.", id));
	}
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
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof School)) return false;
		return (this.name == ((School) obj).getName());
	}
	public static void main(School school, Scanner scan) {
		// Declarations
		int choice = 0;
		boolean done = false;
		
		while (!done) {
			System.out.printf("Welcome to %s\n", school.getName());
			System.out.println("Please select an option: ");
			System.out.println("\t1. Manage Students");
			System.out.println("\t2. Manage Professors");
			System.out.println("\t3. Manage Courses");
			System.out.println("\t4. Manage Semesters");
			System.out.println("\t0. Go back to previous menu");
			choice = scan.nextInt();
			
			switch(choice){
				case 0:
					done = true;
					break;
				case 1:
					Student.main(school, scan);
					break;
				case 2:
					Professor.main(school, scan);
					break;
				case 3:
					Course.main(school, scan);
					break;
				case 4: 
					Semester.main(school, scan);
					break;
				default:
					System.out.println("Error: Invalid choice.");
					break;
			}
		}
	}
}