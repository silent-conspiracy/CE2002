import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class SCRAME {
	public static final String SAVE_FILE = "SCRAME.sav";
	public static SaveData data;
	public static void saveData(SaveData data) throws IOException {
		FileOutputStream fileWriter = new FileOutputStream(SAVE_FILE);
		BufferedOutputStream bufferedWriter = new BufferedOutputStream(fileWriter);
		ObjectOutputStream objectWriter = new ObjectOutputStream(bufferedWriter);
		objectWriter.writeObject(data);
		objectWriter.close();
	}
	public static SaveData loadData() throws ClassNotFoundException, IOException {
		FileInputStream fileReader = new FileInputStream(SAVE_FILE);
		BufferedInputStream bufferedReader = new BufferedInputStream(fileReader);
		ObjectInputStream objectReader = new ObjectInputStream(bufferedReader);
		SaveData data = (SaveData) objectReader.readObject();
		objectReader.close();
		return data;
	}
	public static void main (String[] args){
		// Variable declaration
		Scanner scan = new Scanner(System.in);
		int choice = 0;
		boolean done = false;
		String stringInput = null;
		School school = null;
		ArrayList<School> schoolList = null;

		System.out.println("Welcome to Course Registration portal! (powered by KTCube)");
		
		// Load data
		try {
			data = loadData();
		} catch (IOException e) {
			System.out.println("Error: "+SAVE_FILE+" not found.");
		} catch (ClassNotFoundException f) {
			System.out.println("Error: "+SAVE_FILE+" corrupt!");
		} finally {
			if (data == null) {
				// Initialization
				data = new SaveData();
				school = new School("School of Computer Engineering");
				try {
					data.addSchool(school);
				} catch (DuplicateKeyException e) {
					System.out.println(e.getMessage());
				}
			}
		}
			
		while (!done) {
			System.out.println("Please select a school: ");
			schoolList = new ArrayList<School>(data.getSchools());
			for (School sch : schoolList) {
				System.out.printf("\t%d. %s\n", (schoolList.indexOf(sch))+1, sch.getName());
			}
			System.out.println("\t0. Save and Quit.");
			System.out.println("\t-1. Create new School.");
			System.out.print("Choice: ");
			choice = scan.nextInt(); scan.nextLine();
			
			switch(choice) {
				case 0:
					try {
						saveData(data);
					} catch (IOException e) {
						System.out.println("Error: Unable to save data.");
					}
					System.out.print("Proceed to quit? (Y/N): ");
					if (scan.nextLine().toUpperCase().charAt(0) != 'Y') break;
					System.out.println("Thank you! Good Bye.");
					scan.close();
					done = true;
					break;
				case -1:
					System.out.println("Please input school name: ");
					stringInput = scan.nextLine();
					try {
						data.addSchool(school = new School(stringInput));
					} catch (DuplicateKeyException e) {
						System.out.println(e.getMessage());
					}
					break;
				default:
					try {
						school = schoolList.get(choice-1);
						School.main(school, data, scan);
					} catch (IndexOutOfBoundsException e) {
						System.out.println("Error: Invalid choice.");
					}
					break;
			}
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