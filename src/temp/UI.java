package temp;

import java.util.Scanner;

import javax.script.ScriptException;

public class UI {
	private boolean shouldRun = true;
	private static FantasyDraft draft;

	public UI(FantasyDraft newDraft) {
		draft = newDraft;
	}

	public void interactWithUser() throws ScriptException {
		Scanner input = new Scanner(System.in);

		draft.initDatabase();

		while (shouldRun) {
			promptUser();
			String userCommand = input.nextLine();
			shouldRun = parseCommand(userCommand);
		}

		input.close();
	}

	private boolean parseCommand(String userCommand) throws ScriptException {
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
			draft.save();
			break;

		case "restore":
			draft.restore();
			break;

		case "evalfun":
//			// get the evalfun expression
			Scanner expression = new Scanner(System.in);
			System.out.println("Enter expression: ");
	        String holder = expression.nextLine();
			
//			
//			database.setEvalFun("e"); //////////////////////////CHANGE THIS AFTER IMPLEMENTING
			draft.evalFun(holder);
			break;

		case "pevalfun":
			// pEvalFun();
//			draft.pEvalFun(command);
			break;

		case "weight":
			draft.weight(command);
			break;

		case "help":
			help();
			break;

		case "quit":
			System.out.println("Would you like to save? (y/n) ");
			boolean commandGiven = false;
			while(!commandGiven) {
			switch (command.next()) {
			case "y":
				draft.save();
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