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
	private String[] positions = { "C", "1B", "2B", "3B", "SS", "LF", "CF", "RF", "P"};
	private char League;
	private ArrayList<FantasyPlayer> teamPlayers;

	/** Constructor an empty team */
	public FantasyTeam(char league) {
		teamPlayers = new ArrayList<>();
		this.League = league;
	}

	/** Construct a team with the given players*/
	public FantasyTeam(ArrayList<FantasyPlayer> teamPlayers, char league) {
		this.teamPlayers = teamPlayers;
		this.League = league;
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
	
	public int getNumPitchers()
	{
		int num = 0;
		for (FantasyPlayer p : teamPlayers)
			if (p.getPosition() == "P")
				num += 1;
		return num;
	}
	
	/** Return team league */
	public char getLeague()
	{
		return League;
	}
	
	@Override
	public String toString() {
		String team = "";
		int numP = 0;
		
		for (String pos : positions)
		{
			for (FantasyPlayer p : teamPlayers) {
				if (pos.equals("P") && p.getPosition().equals(pos))
				{	
					numP += 1;
					team += "P" + numP + " " + p.getName() + " " + p.getLast() + "\n";
				}
				else if (p.getPosition().equals(pos))
					team += p.getPosition() + " " + p.getName() + " " + p.getLast() + "\n";
			}
		}
		return team;
	}
}
