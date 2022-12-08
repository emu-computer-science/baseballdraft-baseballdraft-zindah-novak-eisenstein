package temp;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * FantasyTeam.java class represents a fantasy baseball team
 * 
 * @author Zindah, Novak, Eisenstein
 * @version (11-18-2022)
 *
 */
public class FantasyTeam {
	
	/**Data Members*/
	private char League;
	private int numP = 0;
	private String[] positions = { "C", "1B", "2B", "3B", "SS", "LF", "CF", "RF", "P1", "P2", "P3", "P4", "P5"};
	private String name = " ";
	private ArrayList<String> draftOrder = new ArrayList<String>();
	private int counter = 0;

	private LinkedHashMap<String,FantasyPlayer> teamPlayers; //string name, player

	/** Constructor an empty team */
	public FantasyTeam(char league) {
		teamPlayers = new LinkedHashMap<>();
		this.League = league;
	}

	/** Construct a team with the given players*/
	public FantasyTeam(LinkedHashMap<String, FantasyPlayer> teamPlayers, char league) {
		this.teamPlayers = teamPlayers;
		this.League = league;
	}

	
	
	public LinkedHashMap<String, FantasyPlayer> getTeamPlayers() {
		return teamPlayers;
	}

	/** Draft player to the team */
	public boolean addPlayer(FantasyPlayer player) {

		// check if the player is valid
		if (player == null)
			return false;

		// if it's a pitcher, set appropriate position
		if (player.getPosition().equals("P")) {
			numP += 1;
			
			if (numP <= 5)
			{
				teamPlayers.put(positions[7 + numP], player);
			    name = player.getName() + " " + player.getLast();
			    draftOrder.add(counter, name);
			    name = " ";
			    counter++;
			    
			}	
			else
			{
				return false;
			}
				
		} 
		else
		{
			teamPlayers.put(player.getPosition(), player);
			name = player.getName() + " " + player.getLast();
			draftOrder.add(counter, name);
			name = " ";
			counter++;
		
		}
          return true;
	}

	/** Check if a team already has a player */
	public boolean hasPlayer(FantasyPlayer player) {
		return teamPlayers.containsValue(player);
	}

	/** Check if a team has a position */
	public boolean hasPosition(String position) {
		if (teamPlayers.get(position) != null)
			return true;
		return false;
	}
	
	/** Return team league */
	public char getLeague()
	{
		return League;
	}
	
	
	
	@Override
	public String toString() {
		String team = "";

		for (String pos : positions)
		{
			if (teamPlayers.get(pos) != null)
				team += pos + ": " + teamPlayers.get(pos).getName() + " " + teamPlayers.get(pos).getLast() + "\n";
		}
		return team;
	}
	public String draftOrderString()
	{
		  String order = "";
		  int counter = 1;
		  for(int x = 0; x < draftOrder.size(); x++)
		  {
			  order = order + counter + ". " + draftOrder.get(x) + "\n";
			  counter++;
		  }
		 
		 
	     return order;
	}
}
