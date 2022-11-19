package temp;

import java.util.ArrayList;

public class FantasyTeam {
	private char league;

	private String[] positions = { "C", "1B", "2B", "3B", "SS", "LF", "CF", 
			"RF", "P1", "P2", "P3", "P4", "P5" };

	private ArrayList<FantasyPlayer> teamPlayers = new ArrayList<>();

	// constructors
	public FantasyTeam(char league) {
		this.league = league;
	}

	public FantasyTeam(ArrayList<FantasyPlayer> teamPlayers, char league) {
		this.teamPlayers = teamPlayers;
		this.league = league;
	}

	// Draft player to controlling league member
	public void addPlayer(FantasyPlayer player) {
		if (player != null)
			teamPlayers.add(player);
	}

	public void removePlayer(FantasyPlayer player) {
		teamPlayers.remove(player);
	}

	public boolean hasPlayer(FantasyPlayer player) {
		return teamPlayers.contains(player);
	}

	public boolean hasPosition(String position) {
		for (FantasyPlayer p : teamPlayers) {
			if (p.getPosition() == position)
				return true;
		}
		return false;
	}

	@Override
	public String toString() {
		String team = "";

		for (FantasyPlayer p : teamPlayers) {
			team += p.getPosition() + " " + p.getName() + "\n";
		}

		return team;
	}
}
