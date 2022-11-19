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
		FantasyTeam leagueA = new FantasyTeam('A');
		FantasyTeam leagueB = new FantasyTeam('B');
		FantasyTeam leagueC = new FantasyTeam('C');
		FantasyTeam leagueD = new FantasyTeam('D');
		
		// run menu
		while(true)
		{
			String command;
			System.out.println("Enter command or enter HELP to see list of available commands");
			command = key.nextLine();
			recieveCommand(command, leagueA, leagueB, leagueC, leagueD);
		}
	}

	/** Drafts a player to the given league member */
	public static void oDraft(String playerName, FantasyTeam leagueMember) {

		FantasyPlayer tempP = database.getPlayer(playerName);

		if(tempP == null)
		{
			System.out.println("There is no such player in the database");
		}
		else if (!database.isAvailable(tempP))
		{
			System.out.println("That player has already been drafted");
		}
		else if (!(leagueMember.hasPosition(tempP.getPosition())))
		{
			leagueMember.addPlayer(tempP);
			database.addDrafted(tempP);
			System.out.println(tempP.getName() + " has been drafted to team " + 
			leagueMember.getLeague());
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
		else if (memberA.hasPosition(position))
			System.out.println("You already have a player for position " + position);
		else 
			tempPlayers = database.getPlayersByPosition(position);
		
		// sort players by ranking
		if (tempPlayers != null)
		{
			Collections.sort(tempPlayers, new playerComparator());

			// print out sorted array
			for (FantasyPlayer p : tempPlayers)
			{
				if((!memberA.hasPosition(p.getPosition())) && (database.isAvailable(p)))
					System.out.println(p);
			}
		}
	}
	
	/** prints out given team roster in position order */
	public static void team (char leagueMember) {
		//print roster for given member
	}
	
	/** prints out given team roster in order of draft*/
	public static void stars(char leagueMember) {
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
	
	public static double evalFun(double AB, double SB, double AVG, double OBP, double SLG) {
		return AVG;
	}
	
	public static double pEvalFun(double ERA, double G, double IP, double GS, double BB) {
		return IP;
	}
	
	/** list commands/syntax */
	public static void help () {
		//list commands/syntax
	}
	
	/** Populate MLB player database */
	public static void initDatabase() {
		
		FantasyPlayer player;
		
		// pitcher data is stored as First, Last, Position, Team, ERA, G, GS, IP, BB
		// non-pitcher data is stored as First, Last, Position, team, AB, SB, AVG, OBP, SLG
		double d1, d2, d3, d4, d5;

		String[] files = {"baseball-non-pitchers.csv", "baseball-pitchers.csv"};

		String line = "";
		
		double ranking = 0;
		
		try {
			// parse the CSV files 
			for (int i = 0; i < 2; i++) 
			{
				BufferedReader br = new BufferedReader(new FileReader(files[i]));
				
				// skip first line
				line = br.readLine();
				
				while ((line = br.readLine()) != null && line.charAt(1) != ',')
				{
					String[] playerData = line.split(",");
					d1 = Double.parseDouble(playerData[4]);
					d2 = Double.parseDouble(playerData[5]);
					d3 = Double.parseDouble(playerData[6]);
					d4 = Double.parseDouble(playerData[7]);
					d5 = Double.parseDouble(playerData[8]);
					
					// find and set ranking for pitchers and hitters
					if (i == 0)
						ranking = evalFun(d1, d2, d3, d4, d5);
					else
						ranking = pEvalFun(d1, d2, d3, d4, d5);
					
					// create and add a new player to the database
					player = new FantasyPlayer(playerData[0], playerData[1], playerData[2], playerData[3],
							evalFun(d1, d2, d3, d4, d5));

					database.addPlayer(playerData[1] + ", " + playerData[0].charAt(0), player);
				}
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** Run menu program */
	public static void showMenu() {
		//finish filling in later
		System.out.println("Options:");
		System.out.println("ODRAFT: Draft player to specified team");
		System.out.println("IDRAFT: Draft player to own team");
		System.out.println("OVERALL: View ranking for players in given (non-pitcher) position");
		System.out.println("POVERALL: View ranking pr pitchers");
		System.out.println("TEAM: View roster of given team");
		System.out.println("STARS: View roster of given team in drafting order");
		System.out.println("SAVE: Save file");
		System.out.println("RESTORE: Restore saved file");
		System.out.println("EVALFUN: ");
		System.out.println("PEVALFUN: ");
		System.out.println("HELP: See available commands");
		System.out.println("QUIT: Quit program");
	}
	
	public static void recieveCommand(String command, FantasyTeam leagueA, FantasyTeam leagueB,
			FantasyTeam leagueC, FantasyTeam leagueD) {
		
		Scanner keyboard = new Scanner(System.in);

		char leagueMember;

		String commandFirst = command.split(" ")[0].toLowerCase();
		String secondCommand = " ";
		
		if (command.split(" ").length == 2)
			 secondCommand = command.split(" ")[1];
		
		switch(commandFirst) {
		//TODO:rename commands to something more natural/user friendly?
			
		//odraft has to Enter player name surrounded by quotes and league member");
		//Ex: odraft "Martinez, A" A ;
			case "odraft":
				String name = command.split("\"")[1];
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
				
				iDraft(name, leagueA);
				break;
			
			case "overall":
				if (!secondCommand.equals("P"))
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
				evalFun(0,0,0,0,0);
				break;
				
			case "pevalfun":
				//pEvalFun();
				break;
				
			case "help":
				showMenu();
				break;
		
		
			default: System.out.println("Invalid command. Type HELP to see available commands.");
		}
		
		
	}
	
}
