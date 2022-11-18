package temp;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;

public class FantasyTeam {
	private String league;
	
	// String key is player position, playerName
	private LinkedHashMap<String, String> teamPlayers = new LinkedHashMap<String, String>();

	// constructors
	public FantasyTeam(String league) {
		this.league = league;
	}
	
	public FantasyTeam(LinkedHashMap<String, String> teamPlayers, String league) {
		this.teamPlayers = teamPlayers;
		this.league = league;
	}
	
	// Draft player to controlling league member
	public void addPlayer(FantasyDatabase db, String playerName) {
		teamPlayers.put(db.getPlayer(playerName).getPosition(), playerName);
	}
	
	public void removePlayer(String playerName) {
		teamPlayers.remove(playerName);
	}
	
	public boolean onTeam(String playerName) {
		return teamPlayers.containsKey(playerName);
	}
	
	public String toString() {
		
		return teamPlayers.toString();
	}
}

