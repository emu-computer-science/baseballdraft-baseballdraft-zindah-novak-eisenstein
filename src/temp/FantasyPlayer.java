package temp;

import java.util.Comparator;
/**
 * FantasyPlayer.java class represents a fantasy baseball player
 * 
 * @author Zindah, Novak, Eisenstein
 * @version (11-18-2022)
 *
 */
public class FantasyPlayer {
	
	/** Data members */
	private String firstName;
	private String lastName;
	private String position;
	private String team;
	private double ranking;

	/** Construct a player with the given values */
	public FantasyPlayer(String firstName, String lastName, String position, 
			String team, double ranking) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.position = position;
		this.team = team;
		this.ranking = ranking;
	}

	/** Get player position */
	public String getPosition() {
		return this.position;
	}

	/** Get player first name */
	public String getName() {
		return this.firstName;
	}

	/** Get player last name */
	public String getLast() {
		return this.lastName;
	}	
	
	/** Get player ranking */
	public double getRanking() {
		return this.ranking;
	}

	/** Return a string representing the player */
	@Override 
	public String toString() {
		return firstName + " " + lastName + " " + team + " " + position + " " + ranking;
	}

}

/**
 * 
 * @author Zindah
 * Comparator class to compare FantasyPlayer values
 *
 */
class playerComparator implements Comparator<FantasyPlayer>
{
    public int compare(FantasyPlayer p1, FantasyPlayer p2)
    {
        return p1.getRanking() > p2.getRanking() ? 1 : -1;
    }
}
