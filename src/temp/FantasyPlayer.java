package temp;

import java.util.Comparator;

public class FantasyPlayer {
	private String firstName;
	private char lastInitial;
	private String position;
	private String team;
	private double ranking;

	public FantasyPlayer(String firstName, char lastInitial, String position, 
			String team, double ranking) {
		this.firstName = firstName;
		this.lastInitial = lastInitial;
		this.position = position;
		this.team = team;
		this.ranking = ranking;
	}

	public String getPosition() {
		return this.position;
	}

	public String getName() {
		return this.firstName;
	}

	public double getRanking() {
		return this.ranking;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String toString() {
		return firstName + " " + team + " " + position + " " + ranking;
	}

}

class playerComparator implements Comparator<FantasyPlayer>
{
    public int compare(FantasyPlayer p1, FantasyPlayer p2)
    {
        return p1.getRanking() > p2.getRanking() ? 1 : -1;
    }
}
