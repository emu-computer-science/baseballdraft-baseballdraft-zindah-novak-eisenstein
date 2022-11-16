package temp;
import java.util.HashMap;

public class FantasyDatabase {
	// Accessed using player first name
	HashMap<String, FantasyPlayer> nPitchers = new HashMap<>();

	HashMap<String, FantasyPlayer> pitchers = new HashMap<>();
	
	public FantasyDatabase(HashMap<String, FantasyPlayer> nPitchers, 
			HashMap<String, FantasyPlayer> pitchers)
	{
		this.nPitchers = nPitchers;
		this.pitchers = pitchers;
	}
	
	public HashMap<String, FantasyPlayer> getPitchers()
	{
		return pitchers;
	}
	
	public HashMap<String, FantasyPlayer> getNonPitchers()
	{
		return nPitchers;
	}
	
	@Override
	public String toString()
	{
		return pitchers.toString() + nPitchers.toString();
	}
}
