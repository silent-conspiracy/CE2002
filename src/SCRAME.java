import java.util.Scanner;

public class SCRAME{
	
	private int schoolYear = 2012;
	private int semester = 1;
	private int choice;
	
	public static void main (String[] args){
		//UI
		while(true){
			System.out.println("\n\n\tWelcome to Course Registration portal!" +
					"\n\t(powered by KTCube)");
			System.out.println("\n");		//new line
			System.out.println("\t1. ");
			System.out.println("\t2. ");
			System.out.println("\t3. ");
			System.out.println("\t4. ");
			System.out.println("\t5. ");
			System.out.println("\n");		//new line
			System.out.println("\t6. ");
			System.out.println("\t7. ");
			System.out.println("\t8. ");
			System.out.println("\t9. ");
			System.out.println("\t10. ");
			System.out.println("\n");		//new line
			System.out.print("Please select your option: ");
			
			Scanner scan = new Scanner(System.in);
			choice = scan.nextInt();
			
			System.out.println("\n");		//new line
			
			switch(choice){
			case 1: 
				break;
			case 2:
				break;
			case 3:
				break;
			case 4: 
				break;
			case 5:
				break;
			case 6:
				break;
			case 7: 
				break;
			case 8:
				break;
			case 9:
				break;
			case 10:
				break;
			default: break;
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