package temp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

import net.objecthunter.exp4j.ExpressionBuilder;
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

	public FantasyDraft() {
		super();
	}
	
	/** getters for data members*/
	public HashMap<Character, FantasyTeam> getTeams() {
		return fantasyLeagues;
	}
	
	public FantasyDatabase getDatabase() {
		return database;
	}
	
	/**
	 * Drafts a player to a given league member
	 * 
	 * @param playerName
	 * @param league
	 */
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

		playerName = commands.trim().toLowerCase();

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
		
	/**
	 * Prints out players of the given positions
	 * 
	 * @param position
	 */
	public void overall(String position) {
		
		ArrayList<FantasyPlayer> tempPlayers = null;
		FantasyTeam teamA = fantasyLeagues.get('A');

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
			// loop through relevant players and update ranking accordingly
			for (FantasyPlayer p : tempPlayers)
			{
				if (p.getPosition().contains("P"))
					p.setRanking(callEval(p));
				else {
					p.setRanking(database.getPosWeight(p.getPosition()) * callEval(p));
				}
			}
			
			// sort players
			Collections.sort(tempPlayers, new playerComparator());

			// loop through and print out sorted array of available players
			for (FantasyPlayer p : tempPlayers)
			{
				if(!teamA.hasPosition(p.getPosition()))
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
	
	/**
	 * prints out given team roster in given order (stars or team)
	 * @param command
	 * @param order
	 */
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

	/** quit program */
	public static void quit() {
		System.exit(0);
	}
	
	/**
	 * function to save draft data
	 * 
	 * @param saveDraft
	 */
	public void save(Scanner command) {

		String fileName = command.next();

		try {
			// create outputStream object
			FileOutputStream out = new FileOutputStream(fileName);
			ObjectOutputStream objectOut = new ObjectOutputStream(out);
			
			objectOut.writeObject(database);
			objectOut.writeObject(fantasyLeagues);
			
			System.out.println("Fantasy draft state saved to file.");
			objectOut.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// testing to see if save works
	@SuppressWarnings("unchecked")
	public void restore () {
		Scanner in = new Scanner(System.in);
		
		System.out.println("Enter File Name: ");
		String fileName = in.next();
		
		try
		{
			FileInputStream fin = new FileInputStream(fileName);
			ObjectInputStream oin = new ObjectInputStream(fin);
			database = (FantasyDatabase) oin.readObject();
			fantasyLeagues = (HashMap<Character, FantasyTeam>) oin.readObject();
			
			oin.close();
			fin.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally {
			in.close();
		}
	}
	
	/** restore session */
	public static void restore(String filename) {
		
	}

	/**
	 * evalfun to get user expression
	 * @param command
	 */
	public void setEvalPeval(Scanner command, String whichEval) {
		String expression = "";
		
		// get full expression
		while (command.hasNext())
			expression += command.next();
			
		// call appropriate eval function
		if (whichEval.toLowerCase().equals("eval"))
			database.setEvalFun(expression);
		else if (whichEval.toLowerCase().equals("peval"))
			database.setPEvalFun(expression);
		
		System.out.println(whichEval.toUpperCase() + " eval has been changed");
	}

	public double callEval(FantasyPlayer p) {
		String expression;
		double result = 1;
		
		// find which expression to evaluate
		if (p.getPosition().equals("P")) {
			expression = database.getPEvalFun();
			
			// make sure variables are one letter
			expression = expression.replace("ERA", "E").replace("GS", "S");
			expression = expression.replace("IP", "I").replace("BB", "B");
			result = evaluate(p, expression, new String[] {"ERA", "G", "GS", "IP", "BB"});
		}
		else
		{
			expression = database.getEvalFun();
	
			// make sure variables are one letter
			expression = expression.replace("AB", "E").replace("SB", "S").replace("AVG", "I");
			expression = expression.replace("OBP", "B").replace("SLG", "G");
			result = evaluate(p, expression, new String[] {"AB", "SB", "AVG", "OBP", "SLG"});
		}
		
		return result;

	}

	/** evaluate player ranking based on pEvalFun expression */
	public double evaluate(FantasyPlayer p, String expression, String[] statNames) {

		double result = new ExpressionBuilder(expression)
	    		.variables("E", "G", "S", "I", "B") // AB, SB, AVG, OBP, SLB
	            .build()
	            .setVariable("E", p.getStat(statNames[0]))
	            .setVariable("G", p.getStat(statNames[1]))
	            .setVariable("S", p.getStat(statNames[2]))
	            .setVariable("I", p.getStat(statNames[3]))
	            .setVariable("B", p.getStat(statNames[4]))
	            .evaluate();
		
		return result;
	}
	
	/**
	 * Method sets non-pitcher position weight
	 * @param user command
	 */
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
				if (positionWeight > 0)
					database.setPosWeight(position, (double) positionWeight);
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

		String[][] userStats = {{"AB", "ERA"}, {"SB", "G"}, {"AVG", "IP"}, {"OBP","GS"}, {"SLG", "BB"}};
		String[] positions = { "C", "1B", "2B", "3B", "SS", "LF", "CF", "RF"};
		String[] files = {"baseball-non-pitchers.csv", "baseball-pitchers.csv"};
		
		// temp objects
		BufferedReader br;
		String line = "";
		
		// create teams
		fantasyLeagues.put('A', new FantasyTeam('A'));
		fantasyLeagues.put('B', new FantasyTeam('B'));
		fantasyLeagues.put('C', new FantasyTeam('C'));
		fantasyLeagues.put('D', new FantasyTeam('D'));
		
		// initialize the weight of every position to 1
		for (String position : positions)
			database.setPosWeight(position, 1);

		try {
			// parse CSV files: i is used to decide which stats to use)
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
					int dataStartIndex = 4, numStats = 5;
					
					// set all 5 player stats (String statName and double data)
					for (int j = 0; j < numStats; j++)
						stats.put(userStats[j][i], Double.parseDouble(playerData[j + dataStartIndex]));

					// create and add a new player to the database
					FantasyPlayer player = new FantasyPlayer(playerData[0], playerData[1], playerData[2], 
							playerData[3], stats);
					
					// add the player to the database
					database.addPlayer(playerData[1].toLowerCase() + ", " 
					+ playerData[0].toLowerCase().charAt(0), player);
					
					// check which file is being parsed and set player ranking
					if (i == 0)
						player.setRanking(callEval(player));
					else
						player.setRanking(callEval(player));
					
				}
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}