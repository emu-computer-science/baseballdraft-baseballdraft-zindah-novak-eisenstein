package temp;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

@SuppressWarnings("resource")
public class FantasyDraft {
	Scanner keyboard = new Scanner(System.in);
	static FantasyDatabase database;

	public static void main(String[] args) {
		Scanner key = new Scanner(System.in); // Keyboard won't work in static method
		// TODO Auto-generated method stub
		initDatabase();
		
		
		while(true)
		{
			String command;
			System.out.println("Enter command or enter HELP to see list of available commands");
			command = key.nextLine();
			recieveCommand(command);
		}
		// database format prints a little funny rn because it's printing a hashmap, 
		// printing this just to make sure it works.
		
		//System.out.println(database); 
		
		
		//leagueA.addPlayer("1B", "Aaron");
		//System.out.println("Team: " + leagueA.toString());
	}

	//METHODS BEGIN HERE
	public static void oDraft(String playerName, FantasyTeam leagueMember) {
		// Draft player to league member
		if(database.getPlayer(playerName) == null)
		{
			System.out.println("There is no such player in the database");
		}
		else
		{
			leagueMember.addPlayer(database.getPosition(playerName),playerName);
		}
		
		
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
	
	public static void team (FantasyTeam leagueMember) {
		//print roster for given member
	    leagueMember.toString();
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
	
	public static int evalFun(double d1, double d2, double d3, double d4, double d5) {
		return 0;
	}
	
	public static void pEvalFun() {
		
	}
	
	public static void help () {
		//list commands/syntax
	}
	
	
	public static void initDatabase() {
		
		HashMap<String, FantasyPlayer> players = new HashMap<>();
		FantasyPlayer player;
		
		// non-pitcher data is stored as First, Last, Position, Team, ERA, G, GS, IP, BB
		// pitcher data is stored as First, Last, Position, team, AB, SB, AVG, OBP, SLG
		double d1, d2, d3, d4, d5;

		String[] files = {"baseball-non-pitchers.csv", "baseball-pitchers.csv"};

		String line = "";
		
		try {
			// parse the CSV files into a BufferedReader class constructor
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
	
					player = new FantasyPlayer(playerData[0], playerData[1].charAt(0), playerData[2], playerData[3],
							evalFun(d1, d2, d3, d4, d5));

					players.put(playerData[0], player);
				}
			}
			// initialize database
			database = new FantasyDatabase(players);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void showMenu() {
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
	
	public static void recieveCommand(String command) {
		
		Scanner keyboard = new Scanner(System.in);
		String secondCommand;
		char leagueMember;
	    FantasyTeam leagueA = new FantasyTeam('A');
		FantasyTeam leagueB = new FantasyTeam('B');
		FantasyTeam leagueC = new FantasyTeam('C');
		FantasyTeam leagueD = new FantasyTeam('D');
		
		switch(command.toLowerCase()) {
		//TODO:rename commands to something more natural/user friendly?
		
			
			case "odraft":
				System.out.println("Enter player name");
				secondCommand = keyboard.nextLine();
				System.out.println("Enter participant letter");
				leagueMember = keyboard.next().charAt(0);
				if(leagueMember == 'A') {
					oDraft(secondCommand, leagueA);
				}
				else if(leagueMember == 'B')
				{
					oDraft(secondCommand, leagueB);
				}
				else if(leagueMember == 'C')
				{
					oDraft(secondCommand, leagueC);
				}
				else if(leagueMember == 'D')
				{
					oDraft(secondCommand, leagueD);
				}
				
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
				if(leagueMember == 'A') {
					team(leagueA);
				}
				else if(leagueMember == 'B')
				{
					team(leagueB);
				}
				else if(leagueMember == 'C')
				{
					team(leagueC);
				}
				else if(leagueMember == 'D')
				{
					team(leagueD);
				}
				
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
				pEvalFun();
				break;
				
			case "help":
				showMenu();
				break;
		
		
			default: System.out.println("Invalid command. Type HELP to see available commands.");
		}
	
		
		
	}
	
}
