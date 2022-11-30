package temp;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
/**
 * FantasyDraft.java class implements a fantasy baseball draft
 * 
 * @author Zindah, Novak, Eisenstein
 * @version (11-18-2022)
 *
 */
public class FantasyDraft {
	
	/** Data members */
	static FantasyDatabase database = new FantasyDatabase();

	public static void main(String[] args) {
		// initialize scanner
		Scanner key = new Scanner(System.in); 
		
		// populate mlb database
		initDatabase();

		// create teams
		FantasyTeam leagueA = new FantasyTeam('A');
		FantasyTeam leagueB = new FantasyTeam('B');
		FantasyTeam leagueC = new FantasyTeam('C');
		FantasyTeam leagueD = new FantasyTeam('D');
		
		// Variable to hold command
		String command;

		// run menu
		while(true)
		{
			System.out.println("Enter command or enter HELP to see list of available commands");
			command = key.nextLine();
			recieveCommand(command, leagueA, leagueB, leagueC, leagueD);
		}
	}
	
	// returns team object (for testing purposes)
	public FantasyTeam getTeam(FantasyTeam team) {
		return team;
	}

	/** Drafts a player to the given league member */
	public static void oDraft(String playerName, FantasyTeam leagueMember) {

		FantasyPlayer tempP = database.getPlayer(playerName);

		// check if player can be drafted
		if(tempP == null)
		{
			System.out.println("There is no such player in the database");
		}
		else if (!database.isAvailable(tempP))
		{
			System.out.println("That player has already been drafted");
		}
		// add player to the league and print confirmation
		else if (!(leagueMember.hasPosition(tempP.getPosition())))
		{
			if (leagueMember.addPlayer(tempP))
			{
				database.addDrafted(tempP);
				System.out.println(tempP.getName() + " has been drafted to team " + leagueMember.getLeague());
			}
			else
				System.out.println("The " + tempP.getPosition() + " position is full");
		}
		else
			System.out.println("You already have a player for position " + tempP.getPosition());
	}
	
	/** Drafts a player to league member A */
	public static void iDraft(String playerName, FantasyTeam memberA) {
		oDraft(playerName, memberA);
	}
	
	/** Prints out players of the given non-pitching positions */
	public static void overall(String position, FantasyTeam memberA) {
		ArrayList<FantasyPlayer> tempPlayers = null;
		
		// get player rankings in given position
		if (position == " ")
			tempPlayers = database.getPlayersByPosition("hitters");
		else if (memberA.hasPosition(position.toUpperCase()))
			System.out.println("You already have a player for position " + position);
		else 
			tempPlayers = database.getPlayersByPosition(position.toUpperCase());

		// sort players by ranking
		if (tempPlayers != null)
		{
			// print headings
			System.out.println("_____________________________________________________\nFirst     "
					+ "\tLast          \tTeam\tPos\tRank\n_______________________________" +
					"______________________");
			
			// loop through relevant players and update ranking accordingly
			for (FantasyPlayer p : tempPlayers)
			{
				if (p.getPosition().contains("P"))
				{
					p.setRanking(pEvalFun(p));
				}
				else
					p.setRanking(database.getPosWeight(p.getPosition()) * evalFun(p));
			}
			
			// sort players
			Collections.sort(tempPlayers, new playerComparator());

			// loop through and print out sorted array of available players
			for (FantasyPlayer p : tempPlayers)
			{
				if((!memberA.hasPosition(p.getPosition())) && (database.isAvailable(p)))
					System.out.println(p);
			}
		}
	}
	
	/** prints out given team roster in position order */
	public static void team (FantasyTeam leagueMember) {
		//print roster for given member
		System.out.println(leagueMember.toString());	
	}
	
	/** prints out given team roster in order of draft*/
	public static void stars(FantasyTeam leagueMember) {
		System.out.println(leagueMember.draftOrderString());
		//Same as TEAM, but the ordering of the players matches the 
		//order in which they were drafted overall
	}
	
	/** save team data */
	public static void save (String filename) {
		
	}
	
	/** quit program */
	public static void quit() {
		System.exit(0);
	}
	
	/** restore session */
	public static void restore(String filename) {
		
	}

	/** evaluate player ranking based on evalFun expression */
	public static double evalFun(FantasyPlayer p) {
		return p.getStat("AVG");
	}
	
	/** evaluate player ranking based on pEvalFun expression */
	public static double pEvalFun(FantasyPlayer p) {
		return p.getStat("IP");
	}
	
	/** weight position importance */
	public static void weight (String[] arr) {
		// user data
		String position = "";
		int weight = 0;
		
		// loop through the input array and change position weight
		for(int i = 0; i < arr.length - 1; i+=2) {
			
			position = arr[i];
			
			// validate input
			try 
			{
				weight = Integer.parseInt(arr[i+1]);
			}
			catch (Exception e) 
			{
				System.out.println("Please enter the correct format: [pos] [weight] \n"
			+ "(ex: weight 1b 1 2b 4)");
			}
			
			// set position weight in database
			if (weight > 0)
				database.setPosWeight(position, weight);
		}
	}
	
	/** list commands/syntax */
	public static void help () {
		//list commands/syntax
	}
	
	/** Populate MLB player database */
	public static void initDatabase() {
		
		// declare data and ranking variables
		HashMap<String, Double> stats;
		double d1, d2, d3, d4, d5;
		
		String[][] userStats = {{"AB", "ERA"}, {"SB", "G"}, {"AVG", "IP"}, {"OBP","GS"}, {"SLG", "BB"}};
		String[] positions = { "C", "1B", "2B", "3B", "SS", "LF", "CF", "RF"};
		String[] files = {"baseball-non-pitchers.csv", "baseball-pitchers.csv"};
		
		// temp objects
		BufferedReader br;
		String line = "";
		
		// initialize the weight of every position to 1
		for (int i = 0; i < positions.length; i++) {
			database.setPosWeight(positions[i], 1);
		}

		try {
			// parse the CSV files 
			for (int i = 0; i < 2; i++) 
			{
				br = new BufferedReader(new FileReader(files[i]));
				
				// skip first line
				line = br.readLine();
				
				while ((line = br.readLine()) != null && line.charAt(1) != ',')
				{
					stats = new HashMap<>();
					
					// parse the player data string and initialize the data variables
					String[] playerData = line.split(",");
					
					d1 = Double.parseDouble(playerData[4]);
					d2 = Double.parseDouble(playerData[5]);
					d3 = Double.parseDouble(playerData[6]);
					d4 = Double.parseDouble(playerData[7]);
					d5 = Double.parseDouble(playerData[8]);
					
					double[] data = {d1, d2, d3, d4, d5};
					
					// set player stats 
					for (int j = 0; j < 5; j++) {
						stats.put(userStats[j][i], data[j]);
					}

					// create and add a new player to the database
					FantasyPlayer player = new FantasyPlayer(playerData[0], playerData[1], playerData[2], 
							playerData[3], stats);
					
					if (i == 0)
						player.setRanking(evalFun(player));
					else
						player.setRanking(pEvalFun(player));
					
					// add the player to the database
					database.addPlayer(playerData[1] + ", " + playerData[0].charAt(0), player);
				}
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** Run menu program */
	public static void showMenu() {
		//finish filling in later
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
		System.out.println("WEIGHT: ");
		System.out.println("HELP: See available commands");
		System.out.println("QUIT: Quit program");
	}
	
	public static void recieveCommand(String command, FantasyTeam leagueA, FantasyTeam leagueB,
			FantasyTeam leagueC, FantasyTeam leagueD) {
		
		Scanner keyboard = new Scanner(System.in);

		char leagueMember;

		// get command keyword from the user input (to use appropriate switch statement)
		String commandFirst = command.split(" ")[0].toLowerCase();
		String secondCommand = " ";
		
		switch(commandFirst) {
		//TODO:rename commands to something more natural/user friendly?
			
			case "odraft":
				//odraft has to Enter player name surrounded by quotes and league member");
				//Ex: odraft "Martinez, A";
				
				// get first name
				String name = command.split("\"")[1];
				// get league member
				leagueMember = Character.toUpperCase(command.split("\"")[2].trim().charAt(0));
	
				if(leagueMember == 'A') {
					oDraft(name,leagueA);
				}
				else if(leagueMember == 'B')
				{
					oDraft(name,leagueB);
				}
				else if(leagueMember == 'C')
				{
					oDraft(name,leagueC);
				}
				else if(leagueMember == 'D')
				{
					oDraft(name,leagueD);
				}
				break;
			
			case "idraft":
				name = command.split("\"")[1];
				
				oDraft(name, leagueA);
				break;
			
			case "overall":
				// check if there is a second command after overall
				if (command.split(" ").length == 2)
					 secondCommand = command.split(" ")[1];
				
				if (!secondCommand.toUpperCase().equals("P"))
					overall(secondCommand, leagueA);
				else
					System.out.println("Overall doesn't print pitchers");
				break;
				
			case "poverall":
				overall("pitchers", leagueA);
				break;
				
			case "team":
				System.out.println("Enter participant letter");
				leagueMember = keyboard.next().charAt(0);
				char leagueMemberCap = Character.toUpperCase(leagueMember);
				switch(leagueMemberCap) {
					case 'A':
						team(leagueA);
						break;
					case 'B':
						team(leagueB);
						break;
					case 'C':
						team(leagueC);
						break;
					case 'D':
						team(leagueD);
						break;
					default:
						System.out.println("Invalid team name");
						break;
				}
				break;
				
			case "stars":
				System.out.println("Enter participant letter");
				leagueMember = keyboard.next().charAt(0);
				if(leagueMember == 'A') {
					stars(leagueA);
				}
				else if(leagueMember == 'B')
				{
					stars(leagueB);
				}
				else if(leagueMember == 'C')
				{
					stars(leagueC);
				}
				else if(leagueMember == 'D')
				{
					stars(leagueD);
				}
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
				// get the evalfun expression
				String exp = command.split(" ")[1];
				
				database.setEvalFun("e"); //////////////////////////CHANGE THIS AFTER IMPLEMENTING
				break;
				
			case "pevalfun":
				//pEvalFun();
				break;
			
			case "weight":
				// split the input string into command and inputs
				String[] commandArr = command.split(" ");
				String[] c = Arrays.copyOfRange(commandArr, 1, commandArr.length);
				// call the weight function
				weight(c);
				break;
			case "help":
				showMenu();
				break;

			default: System.out.println("Invalid command. Type HELP to see available commands.");
		}
		
		
	}
	
}
