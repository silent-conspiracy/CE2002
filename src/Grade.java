import java.util.HashMap;
import java.util.Set;

public class Grade {
	// Grade Point Average only.
	// Attributes
	private Course course;
	private HashMap<String, Double> grades;
	
	// Constructors
	public Grade(Course course) {
		this.course = course;
		this.grades = new HashMap<String, Double>();
		// Constructing grading fields according to course weights.
		for (String type : course.getWeights().keySet()) {
			this.grades.put(type, 0.0);
		}
	}
	
	// Getters and Setters
	public Course getCourse() { return course; }
	public HashMap<String, Double> getGrades() { return grades; }
	
	// Specific methods
	public Set<String> getComponents() { return grades.keySet(); }
}
