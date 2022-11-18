package temp;
import java.util.HashMap;

public class FantasyDatabase {
	// Accessed using player first name
	HashMap<String, FantasyPlayer> players = new HashMap<>();
	
	public FantasyDatabase(HashMap<String, FantasyPlayer> players)
	{
		this.players = players;
	}
	
	public HashMap<String, FantasyPlayer> getPlayersMap()
	{
		return players;
	}
	
	public FantasyPlayer getPlayer(String player)
	{
		return players.get(player);
	}
	public String getPosition(String player)
	{
		return players.get(player).getPosition();
	}
	
	@Override
	public String toString()
	{
		return players.values().toString();
	}
}
