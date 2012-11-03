import java.util.ArrayList;
import java.util.Scanner;

public class SCRAME {
	
	public static SaveData data;
	
	public static void main (String[] args){
		// Variable declaration
		Scanner scan = new Scanner(System.in);
		int choice = 0;
		boolean done = false;
		String stringInput = null;
		School school = null;
		ArrayList<School> schoolList = null;
		
		// Initialization
		data = new SaveData();
		school = new School("School of Computer Engineering");
		try {
			data.addSchool(school);
		} catch (DuplicateKeyException e) {
			System.out.println(e.getMessage());
		}
		
		// UI
		while(true){
			System.out.println("\n\n\tWelcome to Course Registration portal!\n\t(powered by KTCube)");
			
			while (!done) {
				System.out.println("Please select a school: ");
				schoolList = new ArrayList<School>(data.getSchools());
				for (School sch : schoolList) {
					System.out.printf("\t%d. %s\n", (schoolList.indexOf(sch))+1, sch.getName());
				}
				System.out.println("\t0. Save and Quit.");
				//System.out.println("\t-1. Create new School.");
				choice = scan.nextInt();
				
				switch(choice) {
					case 0:
						// TODO SAVE DATA
						System.out.println("Thank you! Good Bye.");
						scan.close();
						return;
					case -1:
						System.out.println("Please input school name: ");
						stringInput = scan.nextLine();
						try {
							data.addSchool(school = new School(stringInput));
							done = true;
						} catch (DuplicateKeyException e) {
							System.out.println(e.getMessage());
						}
						break;
					default:
						try {
							school = schoolList.get(choice-1);
							done = true;
						} catch (IndexOutOfBoundsException e) {
							System.out.println("Error: Invalid choice.");
						}
						break;
				}
			}				
			School.main(school, scan);
		}
	}
	
	private void printTranscript(String studName){
		//search for student by name
		//check for student registered courses (by checking the acadamic year, sem, etc...)
			//for every course the student takes
				//print the final result
	}
	
	private void printTranscript(int studID){
		//search for student by ID
		//check for student registered courses (by checking the acadamic year, sem, etc...)
			//for every course the student takes
				//print the final result
	}
	
	private void printSemStat(int semester, int year){
		//for every Course in the semester of the year
			//take the average of total grade from all the student
			//compute pass/fail, 3rd, 2nd, 1st class, etc... 
			//print out the results
	}
}