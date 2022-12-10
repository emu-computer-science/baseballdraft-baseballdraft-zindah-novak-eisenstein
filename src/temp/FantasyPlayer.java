package temp;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
/**
 * FantasyPlayer.java class represents a fantasy baseball player
 * 
 * @author Zindah, Novak, Eisenstein
 * @version (11-18-2022)
 *
 */
public class FantasyPlayer implements Serializable {
	
	/** id for restore method*/
	private static final long serialVersionUID = 1L;
	
	/** Data members */
	private String firstName;
	private String lastName;
	private String position;
	private String team;
	private HashMap<String, Double> stats;
	private double ranking;
	
	/** Construct a player with the given values */
	public FantasyPlayer(String firstName, String lastName, String position, 
			String team, HashMap<String, Double> stats) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.position = position.toUpperCase();
		this.team = team;
		this.stats = stats;
	}

	/** Get player position */
	public String getPosition() {
		return this.position;
	}

	/** Get player first name */
	public String getName() {
		return this.firstName;
	}

	/** Get player last name and first initial */
	public String getFullName() {
		return this.lastName + ", " + firstName.charAt(0);
	}

	/** Get player last name */
	public String getLast() {
		return this.lastName;
	}	
	
	/** Get player ranking */
	public double getRanking() {
		return this.ranking;
	}
	
	/** set player ranking */
	public void setRanking(double rank) {
		this.ranking = rank;
	}
	
	/** get player stats */
	public double getStat(String data) {
		double v = stats.get(data);
		return v;
	}
	
	public HashMap<String, Double> getStats() {
		return stats;
	}

	/** Return a string representing the player */
	@Override 
	public String toString() {
		return String.format("%" + (-10) + "s", firstName) 
				+ "\t" + String.format("%" + (-10) + "s", lastName) 
				+ "\t" + team + "\t" + position + "\t" + String.format("%,.3f", ranking);
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
        return p1.getRanking() > p2.getRanking() ? -1 : 1;
    }
}
