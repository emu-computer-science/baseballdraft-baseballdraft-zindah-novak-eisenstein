
public class FantasyPlayer {
	private String name;
	private String position;
	private int ranking;

		public FantasyPlayer(String name,String position, int ranking)
		{
			this.name = name;
			this.position = position;
			this.ranking = ranking;
		}
		public String getPosition()
		{
			return this.position;
		}
		public String getName()
		{
			return this.name;
		}
		public int getRanking()
		{
			return this.ranking;
		}
		public void setPosition(String position)
		{
			this.position = position;
		}
		
}
