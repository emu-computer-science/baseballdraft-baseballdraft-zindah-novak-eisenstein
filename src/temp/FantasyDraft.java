package temp;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
	public FantasyDatabase database = new FantasyDatabase();
	private HashMap<Character, FantasyTeam> fantasyLeagues = new HashMap<>();
	private boolean empty = true;

	/* check if this class object has been initialized */
	public boolean isNew() {
		return empty;
	}
	
	public void setNew(boolean newDraft) {
		empty = newDraft;
	}

	public FantasyTeam getTeam(char team) {
		return fantasyLeagues.get(team);
	}
	
	/** Drafts a player to the given league member */
	public void oDraft(String playerName, char league) {
		
		// temporary test player/team
		FantasyPlayer tempPlayer;
		FantasyTeam team = fantasyLeagues.get(league);
		String validPlayer;
	
		if (team != null) {
			tempPlayer = database.getPlayer(playerName);
			validPlayer = checkPlayerValidity(tempPlayer);
			
			// check if player can be drafted and print error
			if(!validPlayer.equals(""))
				System.out.println(validPlayer);
	
			// add player to the league and print confirmation
			else if (!(team.hasPosition(tempPlayer.getPosition())))
			{	
				database.addDrafted(tempPlayer);
				fantasyLeagues.get(league).addPlayer(tempPlayer);
				System.out.println(tempPlayer.getName() + " has been drafted to team " + league);
			}
			else
				System.out.println("You already have a player for position " + tempPlayer.getPosition());
		}
	}
	
	/* odraft/idraft helper */
	public void draftPlayer(Scanner command, String draftType) {
		
		command.useDelimiter("\"");
		
		String commands = "";
		String playerName = "";
		String leagueLetter = "";
		int league = 3;
		int count = 0;
		
		// get player name
		while (command.hasNext()) {
			count++;
			if (count < league)
				commands += command.next() + " ";
			else if (count == league)
				leagueLetter = command.next().trim();
		}

		playerName = commands.trim();

		// call draft function
		if (draftType.equals("o") && !leagueLetter.equals(""))
			oDraft(playerName, leagueLetter.toUpperCase().charAt(0));

		else if (draftType.equals("i"))
			oDraft(playerName, 'A');	
	}
	
	/* Check if a player exists / is free to be drafted */
	public String checkPlayerValidity(FantasyPlayer player) {
		
		if(player == null)
			return "There is no such player in the database";

		else if (!database.isAvailable(player))
			return "That player has already been drafted";
		
		return "";
	}
		
	/** Prints out players of the given positions */
	public void overall(String position) {
		
		ArrayList<FantasyPlayer> tempPlayers = null;
		FantasyTeam teamA = fantasyLeagues.get('A');
		String heading ="\nFirst     \tLast\t\tTeam\tPos\tRank\n";
		
		// get player rankings in given position
		if (position == "")
			tempPlayers = database.getPlayersByPosition("hitters");
		else if (teamA.hasPosition(position.toUpperCase()))
			System.out.println("You already have a player for position " + position);
		else 
			tempPlayers = database.getPlayersByPosition(position.toUpperCase());

		// sort players by ranking
		if (tempPlayers != null)
		{
			// print headings
			System.out.println("_____".repeat(11) + heading + "_____".repeat(11));
			
			// loop through relevant players and update ranking accordingly
			for (FantasyPlayer p : tempPlayers)
			{
				if (p.getPosition().contains("P"))
					p.setRanking(pEvalFun(p));
				else
					p.setRanking(database.getPosWeight(p.getPosition()) * evalFun(p));
			}
			
			// sort players
			Collections.sort(tempPlayers, new playerComparator());

			// loop through and print out sorted array of available players
			for (FantasyPlayer p : tempPlayers)
			{
				if((!teamA.hasPosition(p.getPosition())) && (database.isAvailable(p)))
					System.out.println(p);
			}
		}
	}
	
	public void overallHelper(Scanner command, String pos) {
		command.useDelimiter(" ");

		String position = "";
		
		if (pos.toUpperCase().equals("P"))
			overall(pos);
		
		else if (command.hasNext())
		{
			position = command.next();
			
			if (!position.equals("P"))
				overall(position);
			else
				System.out.println("Overall doesn't print pitchers");
		}
		else 
			overall("");
	}
	
	/** prints out given team roster in given order (stars or team) */
	public void team(Scanner command, String order) {
		char team = ' ';
		String teamPlayers = "";

		if (command.hasNext())
			team = command.next().charAt(0);
		
		//print roster for given member
		if (order.equals("team")) 
			teamPlayers = fantasyLeagues.get(Character.toUpperCase(team)).draftOrderString();
		else if (order.equals("stars"))
			teamPlayers = fantasyLeagues.get(Character.toUpperCase(team)).toString();	
		
		System.out.println(teamPlayers);
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
	public void weight (Scanner command) {
		// user data
		String position = "", weight = "";
		double positionWeight = 0;
		
		// loop through the input array and change position weight
		while (command.hasNext()) {
			position = command.next();
			
			if (command.hasNext())
				weight = command.next();
			else
				return;
			
			// validate input
			try 
			{
				positionWeight = Double.parseDouble(weight);
				if (positionWeight > 0) {
					database.setPosWeight(position, positionWeight);
					System.out.println(position + " weight has been updated to " + database.getPosWeight(position));
				}
			}
			catch (Exception e) 
			{
				System.out.println("Please enter the correct format: [pos] [weight] \n"
			+ "(ex: weight 1b 1 2b 4)");
			}
			
			positionWeight = 0;
			position = "";
		}
	}
	
	/** Populate MLB player database */
	public void initDatabase() {
		
		// declare data and ranking variables
		HashMap<String, Double> stats;
		double d1, d2, d3, d4, d5;
		
		String[][] userStats = {{"AB", "ERA"}, {"SB", "G"}, {"AVG", "IP"}, {"OBP","GS"}, {"SLG", "BB"}};
		String[] positions = { "C", "1B", "2B", "3B", "SS", "LF", "CF", "RF"};
		String[] files = {"baseball-non-pitchers.csv", "baseball-pitchers.csv"};
		
		// temp objects
		BufferedReader br;
		String line = "";
		
		fantasyLeagues.put('A', new FantasyTeam('A'));
		fantasyLeagues.put('B', new FantasyTeam('B'));
		fantasyLeagues.put('C', new FantasyTeam('C'));
		fantasyLeagues.put('D', new FantasyTeam('D'));
		
		// initialize the weight of every position to 1
		for (String position : positions) {
			database.setPosWeight(position, 1);
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
}
