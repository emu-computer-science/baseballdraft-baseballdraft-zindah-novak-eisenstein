package temp;
import java.util.HashMap;

public class FantasyDatabase {
	// Accessed using player first name
	HashMap<String, FantasyPlayer> players = new HashMap<>();
	HashMap<String, FantasyPlayer> drafted = new HashMap<>();
	
	public FantasyDatabase(HashMap<String, FantasyPlayer> players)
	{
		this.players = players;
	}
	
	public FantasyDatabase()
	{
		this.players = new HashMap<>();
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
	
	public void addPlayer(String name, FantasyPlayer player)
	{
		players.put(name, player);
	}
	
	public boolean isAvailable(FantasyPlayer p)
	{
		if (drafted.containsValue(p))
			return false;
		return true;
	}
	
	public void addDrafted(FantasyPlayer p)
	{
		drafted.put(p.getName(), p);
	}
	
	@Override
	public String toString()
	{
		return players.values().toString();
	}
}

