package temp;

import java.util.Scanner;

/**
 * UI.java deals with user input
 * 
 * @author Zindah, Novak, Eisenstein
 *
 */

public class UI {
	/* data members */
	private boolean shouldRun = true;
	private static FantasyDraft draft;

	public UI(FantasyDraft newDraft) {
		draft = newDraft;
	}

	/** Method to prompt user 
	 * 
	 */
	public void interactWithUser() {

		Scanner input = new Scanner(System.in);

		draft.initDatabase();

		while (shouldRun) {
			promptUser();
			String userCommand = input.nextLine();
			shouldRun = parseCommand(userCommand);
		}
		input.close();
	}

	/** Menu method calls appropriate fantasyDraft methods
	 * 
	 * @param userCommand
	 * @return run value
	 */
	private boolean parseCommand(String userCommand) {
		Scanner command = new Scanner(userCommand);
		String commandName = command.next();
		commandName = commandName.toLowerCase();

		switch (commandName) {

		case "odraft":
			draft.draftPlayer(command, "o");
			break;

		case "idraft":
			draft.draftPlayer(command, "i");
			break;

		case "overall":
			draft.overallHelper(command, "");
			break;

		case "poverall":
			draft.overallHelper(command, "P");
			break;

		case "team":
			draft.team(command, "team");
			break;
			
		case "stars":
			draft.team(command, "stars");
			break;
			
		case "save":
			draft.save(command);
			break;
			
		case "restore":
			draft.restore(command);
			break;

		case "evalfun":
			draft.setEvalPeval(command, "eval");
			break;

		case "pevalfun":
			draft.setEvalPeval(command, "pEval");
			break;

		case "weight":
			draft.weight(command);
			break;

		case "help":
			help();
			break;

		case "quit":
			System.out.println("Would you like to save? (y/n) ");
			command.close();
			
			command = new Scanner(System.in);
			
			boolean commandGiven = false;
			while(!commandGiven) {
				
				switch (command.next()) {
				case "y":
					System.out.println("Enter file name: ");
					String fileName = command.next();
					draft.save(fileName);
					commandGiven = true;
					break;
				case "n":
					commandGiven = true;
					break;
				default:
					System.out.println("Invalid command. Would you like to save? (y/n)");
					break;
				}
			}
			command.close();
			return false;

		default:
			System.out.println("Invalid command. Type HELP to see available commands.");
		}
		return true;
	}

	/** Method prints help menu
	 * 
	 */
	public void help() {
		
		System.out.println("Options:");
		System.out.println("ODRAFT \"playername\" leagueMember: Draft player to specified team");
		System.out.println("IDRAFT \"playername\": Draft player to own team");
		System.out.println("OVERALL position: View ranking for players in given (non-pitcher) position");
		System.out.println("POVERALL: View rankings of pitchers");
		System.out.println("TEAM LeagueLetter: View roster of given team");
		System.out.println("STARS LeagueLetter: View roster of given team in drafting order");
		System.out.println("SAVE filename.txt: Save file");
		System.out.println("RESTORE filename.txt: Restore saved file");
		System.out.println("EVALFUN stats");
		System.out.println("PEVALFUN stats: ");
		System.out.println("WEIGHT: change ranking weight for each position (ex: 1B 2 2B 3");
		System.out.println("HELP: See available commands");
		System.out.println("QUIT: Quit program");
	}

	/** Method prompts user for input
	 * 
	 */
	private void promptUser() {
		System.out.print("Enter a command: ");

	}
}