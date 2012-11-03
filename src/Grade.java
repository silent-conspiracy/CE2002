import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

public class Grade implements Serializable {
	// Grade Point Average only.
	private static final long serialVersionUID = 1L;

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
	
	public void setCourse(Course course) { this.course = course; }
	public void setGrades(HashMap<String, Double> grades) { this.grades = grades; }
	
	// Specific methods
	public Set<String> getComponents() { return grades.keySet(); }
	public void setGrade(String string, double grade) throws KeyErrorException {
		if (getComponents().contains(string)) getGrades().put(string, grade);
		else throw new KeyErrorException("Grade component does not exist.");
	}
	public String printGrade(String tabs) {
		String msg = new String();
		msg += String.format("%sGrades:\n", tabs);
		for (String type : getComponents()) {
			msg += String.format("%s\t%s: %.2f", tabs, type, grades.get(type));
		}
		return msg+'\n';
	}
}
