package temp;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;

public class FantasyTeam {
	private char league;
	
	// String key is player position, playerName
	private LinkedHashMap<String, String> teamPlayers = new LinkedHashMap<String, String>();

	// constructors
	public FantasyTeam(char league) {
		this.league = league;
	}
	
	public FantasyTeam(LinkedHashMap<String, String> teamPlayers, char league) {
		this.teamPlayers = teamPlayers;
		this.league = league;
	}
	
	// Draft player to controlling league member
	public void addPlayer(String position, String playerName) {
		teamPlayers.put(position, playerName);
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

