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
	private FantasyDatabase database = new FantasyDatabase();
	private HashMap<Character, FantasyTeam> fantasyLeagues = new HashMap<>();
	private boolean empty = true;

	public boolean isNew() {
		return empty;
	}
	
	public void setNew(boolean newDraft) {
		empty = newDraft;
	}
	// returns team object (for testing purposes)
	public FantasyTeam getTeam(FantasyTeam team) {
		return team;
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
				if (team.addPlayer(tempPlayer))
				{
					database.addDrafted(tempPlayer);
					System.out.println(tempPlayer.getName() + " has been drafted to team " + league);
				}
				else
					System.out.println("The " + tempPlayer.getPosition() + " position is full");
			}
			else
				System.out.println("You already have a player for position " + tempPlayer.getPosition());
		}
	}
	
	/* Check if a player exists / is free to be drafted */
	public String checkPlayerValidity(FantasyPlayer player) {
		
		if(player == null)
			return "There is no such player in the database";

		else if (!database.isAvailable(player))
			return "That player has already been drafted";
		
		return "";
	}
	
	/* odraft/idraft helper */
	public void draft(Scanner command) {
		command.useDelimiter("\"");
		String playerName = command.next();
		char league = command.next().trim().charAt(0);
		
		if (league == ' ')
			oDraft(playerName, 'A');
		else
			oDraft(playerName, Character.toUpperCase(league));
	}

		
	/** Prints out players of the given non-pitching positions */
	public void overall(String position, FantasyTeam memberA) {
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
	public void team (FantasyTeam leagueMember) {
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
	public void weight (String[] arr) {
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
		fantasyLeagues.put('B', new FantasyTeam('A'));
		fantasyLeagues.put('C', new FantasyTeam('A'));
		fantasyLeagues.put('D', new FantasyTeam('A'));
		
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
