package temp;

import java.util.Arrays;
import java.util.Scanner;

public class UI {
	private boolean shouldRun = true;
	private FantasyDraft draft;
	
	public UI (FantasyDraft newDraft){
		draft = newDraft;
	}
	
	public void interactWithUser() {
		
		if (draft.isNew())
			draft.initDatabase();
		
		Scanner input = new Scanner(System.in);
		while (shouldRun) {
			promptUser();
			String userCommand = input.nextLine();
			shouldRun = parseCommand(userCommand);
			
		}
		input.close();
	}
	
	private boolean parseCommand(String userCommand) {
		Scanner command = new Scanner(userCommand);
		String commandName = command.next();
		commandName = commandName.toLowerCase();
		
		switch(commandName) {
		case "odraft":
		case "idraft":
			draft.draft(command);
			break;
			
		case "overall":
//			// check if there is a second command after overall
//			if (command.split(" ").length == 2)
//				 secondCommand = command.split(" ")[1];
//			
//			if (!secondCommand.toUpperCase().equals("P"))
//				overall(secondCommand, fantasyLeagues.get('A'));
//			else
//				System.out.println("Overall doesn't print pitchers");
			draft.overall(command);
			break;
			
		case "poverall":
//			overall("pitchers", fantasyLeagues.get('A'));
			draft.poverall(command);
			break;
		case "team":
//			System.out.println("Enter participant letter");
//			leagueMember = keyboard.next().charAt(0);
//			char leagueMemberCap = Character.toUpperCase(leagueMember);
//			
//			team(fantasyLeagues.get(leagueMemberCap));
			draft.team(command);
			break;
		case "stars":
//			System.out.println("Enter participant letter");
//			leagueMember = keyboard.next().charAt(0);
//			char member = Character.toUpperCase(leagueMember);
//			if(member == 'A' || member == 'B' || member == 'C' || member == 'D') {
//				stars(fantasyLeagues.get(leagueMember));
//			}
			draft.stars(command);
			break;
		case "save":
//			System.out.println("Enter file name");
//			secondCommand = keyboard.nextLine();
//			save(secondCommand);
			draft.save(command);
			break;

		case "restore":
//			System.out.println("Enter file name");
//			secondCommand = keyboard.nextLine();
//			restore(secondCommand);
			draft.restore(command);
			break;
			
		case "evalfun": 
//			// get the evalfun expression
//			String exp = command.split(" ")[1];
//			
//			database.setEvalFun("e"); //////////////////////////CHANGE THIS AFTER IMPLEMENTING
			draft.evalFun(command);
			break;
			
		case "pevalfun":
			//pEvalFun();
			draft.pEvalFun(command);
			break;
		
		case "weight":
//			// split the input string into command and inputs
//			String[] commandArr = command.split(" ");
//			String[] c = Arrays.copyOfRange(commandArr, 1, commandArr.length);
//			// call the weight function
//			weight(c);
			draft.weight(command);
			break; 
		case "help":
			help();
			break;

		case "quit":
			return false;
		default:
			 System.out.println("Invalid command. Type HELP to see available commands.");
		}
		return true;
	}

	private void help() {
		// finish filling in later
		System.out.println("Options:");
		System.out.println("ODRAFT \"playername\" leagueMember: Draft player to specified team");
		System.out.println("IDRAFT \"playername\": Draft player to own team");
		System.out.println("OVERALL: View ranking for players in given (non-pitcher) position");
		System.out.println("POVERALL: View ranking pr pitchers");
		System.out.println("TEAM: View roster of given team");
		System.out.println("STARS: View roster of given team in drafting order");
		System.out.println("SAVE: Save file");
		System.out.println("RESTORE: Restore saved file");
		System.out.println("EVALFUN: ");
		System.out.println("PEVALFUN: ");
		System.out.println("WEIGHT: change ranking weight for each position");
		System.out.println("HELP: See available commands");
		System.out.println("QUIT: Quit program");
	}
		
	private void promptUser() {
		System.out.print("Enter a command: ");
		
	}
}