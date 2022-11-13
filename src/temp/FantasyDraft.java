package temp;
import java.util.Scanner;

public class FantasyDraft {
	Scanner keyboard = new Scanner(System.in);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		

	}

	//METHODS BEGIN HERE
	public static void oDraft(String playerName, char leagueMember) {
		// Draft player to league member
		
	}
	
	public static void iDraft(String playerName) {
		// Draft player to controling league member
		
	}
	
	public static void overall(String position) {
		//print player rankings in given position
	}
	
	public static void pOverall () {
		//Overall just for pitchers
	}
	
	public static void team (char leagueMember) {
		//print roster for given member
	}
	
	public static void stars(char leagueMember) {
		//Same as TEAM, but the ordering of the players matches the order in which they were drafted overall
	}
	
	public static void save (String filename) {
		
	}
	
	public static void quit() {
		
	}
	
	public static void restore(String filename) {
		
	}
	
	public static void evalFun() {
		
	}
	
	public static void pEvalFun() {
		
	}
	
	public static void help () {
		//list commands/syntax
	}
	
	public void showMenu() {
		//finish filling in later
		System.out.println("Options:");
		System.out.println("ODRAFT: ");
		System.out.println("IDRAFT:");
		System.out.println("OVERALL:");
		System.out.println("POVERALL:");
		System.out.println("TEAM:");
		System.out.println("STARS:");
		System.out.println("SAVE: Save file");
		System.out.println("RESTORE: Restore saved file");
		System.out.println("EVALFUN: ");
		System.out.println("PEVALFUN: ");
		System.out.println("HELP: See available commands");
		System.out.println("QUIT: Quit program");
	}
	
	public void recieveCommand(String command) {
		
		String secondCommand;
		char leagueMember;
		
		switch(command.toLowerCase()) {
		//TODO:rename commands to something more natural/user friendly?
		
			
			case "odraft":
				System.out.println("Enter player name");
				secondCommand = keyboard.nextLine();
				System.out.println("Enter participant letter");
				leagueMember = keyboard.next().charAt(0);
				
				oDraft(secondCommand, leagueMember);
				break;
			
			case "idraft":
				System.out.println("Enter player name");
				secondCommand = keyboard.nextLine();
				iDraft(secondCommand);
				break;
			
			case "overall":
				System.out.println("Enter position or press enter");
				secondCommand = keyboard.nextLine();
				overall(secondCommand);
				break;
				
			case "poverall":
				pOverall();
				break;
				
			case "team":
				System.out.println("Enter participant letter");
				leagueMember = keyboard.next().charAt(0);
				team(leagueMember);
				break;
				
			case "stars":
				System.out.println("Enter participant letter");
				leagueMember = keyboard.next().charAt(0);
				stars(leagueMember);
				break;
				
			case "save":
				System.out.println("Enter file name");
				secondCommand = keyboard.nextLine();
				save(secondCommand);
				break;
				
			case "quit":
				quit();
				break;
				
			case "restore":
				System.out.println("Enter file name");
				secondCommand = keyboard.nextLine();
				restore(secondCommand);
				break;
				
			case "evalfun":
				evalFun();
				break;
				
			case "pevalfun":
				pEvalFun();
				break;
				
			case "help":
				showMenu();
				break;
		
		
			default: System.out.println("Invalid command. Type HELP to see available commands.");
		}
		
		
	}
	
}
