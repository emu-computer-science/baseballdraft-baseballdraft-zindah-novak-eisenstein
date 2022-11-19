package temp;

import java.util.ArrayList;

/**
 * FantasyTeam.java class represents a fantasy baseball team
 * 
 * @author Zindah, Novak, Eisenstein
 * @version (11-18-2022)
 *
 */
public class FantasyTeam {
	
	/**Data Members*/
	private String[] positions = { "C", "1B", "2B", "3B", "SS", "LF", "CF", "RF", "P1", "P2", "P3", "P4", "P5" };

	private ArrayList<FantasyPlayer> teamPlayers;

	/** Constructor an empty team */
	public FantasyTeam() {
		teamPlayers = new ArrayList<>();
	}

	/** Construct a team with the given players*/
	public FantasyTeam(ArrayList<FantasyPlayer> teamPlayers) {
		this.teamPlayers = teamPlayers;
	}

	/** Draft player to the team */
	public void addPlayer(FantasyPlayer player) {
		if (player != null)
			teamPlayers.add(player);
	}
	
	/** Remove player from the team */
	public void removePlayer(FantasyPlayer player) {
		teamPlayers.remove(player);
	}

	/** Check if a team already has a player */
	public boolean hasPlayer(FantasyPlayer player) {
		return teamPlayers.contains(player);
	}

	/** Check if a team has a position */
	public boolean hasPosition(String position) {
		for (FantasyPlayer p : teamPlayers) {
			if (p.getPosition().equals(position))
				return true;
		}
		return false;
	}

	@Override
	public String toString() {
		String team = "";

		for (FantasyPlayer p : teamPlayers) {
			team += p.getPosition() + " " + p.getName() + " " + p.getLast() + "\n";
		}
		return team;
	}
}
