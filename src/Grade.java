import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

public class Grade implements Serializable {
	// Grade Point Average only.
	private static final long serialVersionUID = 1L;
	public static final double A = 90.0; // 5.0
	public static final double A_M = 80.0; // 4.5
	public static final double B_P = 70.0; // 4.0
	public static final double B = 60.0; // 3.5
	public static final double B_M = 50.0; // 3.0
	public static final double C_P = 40.0; // 2.5
	public static final double C = 30.0; // 2.0
	public static final double D_P = 20.0; // 1.5
	public static final double D = 10.0; // 1.0;
	public static final double F = 0.0; // 0.0;
	// Attributes
	private Course course;
	private HashMap<String, Double> marks;
	private double overall;
	private double gpa;
	
	// Constructors
	public Grade(Course course) {
		this.course = course;
		this.marks = new HashMap<String, Double>();
		// Constructing grading fields according to course weights.
		for (String type : course.getWeights().keySet()) {
			this.marks.put(type, 0.0);
		}
		this.overall = 0.0;
		this.gpa = -1.0;
	}
	
	// Getters and Setters
	public Course getCourse() { return course; }
	public HashMap<String, Double> getMarks() { return marks; }
	public double getOverall() { return overall; }
	public double getGPA() { return gpa; }
	
	public void setCourse(Course course) { this.course = course; }
	public void setMarks(HashMap<String, Double> grades) { this.marks = grades; }
	public void setOverall(double overall) { this.overall = overall; }
	public void setGPA(double gpa) { this.gpa = gpa; }
	
	// Specific methods
	public Set<String> getComponents() { return marks.keySet(); }
	public double getMark(String type) throws KeyErrorException {
		if (getMarks().containsKey(type)) return getMarks().get(type);
		else throw new KeyErrorException(String.format("Component: %s does not exist", type));
	}
	public void setGrade(String string, double grade) throws KeyErrorException {
		if (getComponents().contains(string)) getMarks().put(string, grade);
		else throw new KeyErrorException("Grade component does not exist.");
	}
	public double scaleMarks(String type) { 
		double weight = 0.0;
		weight = getCourse().getWeights().get(type);
		return getMarks().get(type) * weight;
	}
	public void calcOverall() {
		double results = 0.0;
		for (String type : marks.keySet()) {
			results += scaleMarks(type);
		}
		this.overall = results;
	}
	public void calcGPA() {
		setGPA(5.0);
		if (this.overall < A) setGPA(4.5);
		if (this.overall < A_M) setGPA(4.0);
		if (this.overall < B_P) setGPA(3.5);
		if (this.overall < B) setGPA(3.0);
		if (this.overall < B_M) setGPA(2.5);
		if (this.overall < C_P) setGPA(2.9);
		if (this.overall < C) setGPA(1.5);
		if (this.overall < D_P) setGPA(1.0);
		if (this.overall < D) setGPA(0.0);
	}
	public String printMarks(String tabs) {
		String msg = new String();
		msg += String.format("%sGPA: %.2f", tabs, getGPA());
		msg += String.format("%sOverall Marks: %.2f", tabs, getOverall());
		msg += String.format("%sComponent Marks:\n", tabs);
		for (String type : getComponents()) {
			msg += String.format("%s\t%s: %.2f => %.2f", tabs, type, getMarks().get(type), scaleMarks(type));
		}
		return msg+'\n';
	}
}
