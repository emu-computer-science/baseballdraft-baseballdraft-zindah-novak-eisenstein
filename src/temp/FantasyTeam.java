package temp;

import java.util.ArrayList;
import java.util.Hashtable;

public class FantasyTeam {
	private String league;
	
	// String key is player position
	private Hashtable<String, FantasyPlayer> teamPlayers = new Hashtable<String, FantasyPlayer>();

	// constructors
	public FantasyTeam(String league) {
		this.league = league;
	}
	
	public FantasyTeam(Hashtable<String, FantasyPlayer> teamPlayers, String league) {
		this.teamPlayers = teamPlayers;
		this.league = league;
	}
	
	// Draft player to controlling league member
	public void addPlayer(FantasyPlayer player) {
		this.teamPlayers.put(player.getPosition(), player);
	}
	
	public void removePlayer(String playerName) {
		this.teamPlayers.remove(playerName);
	}
	
	public static boolean onTeam() {
		// Fill in
		
		boolean present = false;
		
		return present;
	}
	
	public void printTeam() {
		
	}
	
	public String toString() {
		
		return teamPlayers.values().toString();
	}
}

