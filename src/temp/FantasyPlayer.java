package temp;

public class FantasyPlayer {
	private String firstName;
	private char lastInitial;
	private String position;
	private String team;
	private int ranking;
	
		public FantasyPlayer(String firstName, char lastInitial, String position, 
				String team, int ranking)
		{
			this.firstName = firstName;
			this.lastInitial = lastInitial;
			this.position = position;
			this.team = team;
			this.ranking = ranking;
		}

		public String getPosition()
		{
			return this.position;
		}
		public String getName()
		{
			return this.firstName;
		}
		public int getRanking()
		{
			return this.ranking;
		}
		public void setPosition(String position)
		{
			this.position = position;
		}
		public String toString()
		{
			return firstName + " " + position + " " + team + " " + ranking + "\n" ; 
		}
		
}
