import java.io.Serializable;
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
		else throw new KeyErrorException(String.format("Student ID %d does not exist", id));
	}
	public Professor getProfessor(int id) throws KeyErrorException {
		if (professors.containsKey(id)) return professors.get(id);
		else throw new KeyErrorException(String.format("Professor ID %d does not exist", id));
	}
	public Course getCourse(int id) throws KeyErrorException {
		if (courses.containsKey(id)) return courses.get(id);
		else throw new KeyErrorException(String.format("Course ID %d does not exist", id));
	}
	public void addStudent(Student student) throws DuplicateKeyException {
		if (students.containsValue(student)) 
			throw new DuplicateKeyException(String.format("Student ID %d, %s already exists", student.getID(), student.getName()));
		else students.put(student.getID(), student);
	}
	public void addProfessor(Professor prof) throws DuplicateKeyException {
		if (professors.containsValue(prof)) 
			throw new DuplicateKeyException(String.format("Professor ID %d, %s already exists", prof.getID(), prof.getName()));
		else professors.put(prof.getID(), prof);
	}
	public void addCourse(Course course) throws DuplicateKeyException {
		if (courses.containsValue(course))
			throw new DuplicateKeyException(String.format("Course ID %d, %s already exists", course.getID(), course.getName()));
		else courses.put(course.getID(), course);
	}
	public void rmStudent(Student student) throws KeyErrorException {
		if (students.containsKey(student.getID())) students.remove(student.getID());
		else throw new KeyErrorException(String.format("Student ID %d, %s does not exist", student.getID(), student.getName()));
	}
	public void rmProfessor(Professor prof) throws KeyErrorException {
		if (professors.containsKey(prof.getID())) professors.remove(prof.getID());
		else throw new KeyErrorException(String.format("Professor ID %d, %s does not exist", prof.getID(), prof.getName()));
	}
	public void rmCourse(Course course) throws KeyErrorException {
		if (courses.containsKey(course.getID())) courses.remove(course.getID());
		else throw new KeyErrorException(String.format("Course ID %d, %s does not exist", course.getID(), course.getName()));
	}
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof School)) return false;
		return (this.getName().equals(((School) obj).getName()));
	}
	public static void main(School school, SaveData data, Scanner scan) {
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
			System.out.print("Choice: ");
			choice = scan.nextInt(); scan.nextLine();
			
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
					Semester.main(school, data, scan);
					break;
				default:
					System.out.println("Error: Invalid choice.");
					break;
			}
		}
	}
}