package temp;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * FantasyDatabase.java class represents imported mlb data
 * 
 * @author Zindah, Novak, Eisenstein
 * @version (11-18-2022)
 *
 */
public class FantasyDatabase implements Serializable {
	
	/** id for serializing */
	private static final long serialVersionUID = 1L;
	/** Data members */
	private HashMap<String, FantasyPlayer> players = new HashMap<>();
	private HashMap<String, FantasyPlayer> drafted = new HashMap<>();
	private HashMap<String, Double> posWeights = new  HashMap<>(); 
	private String evalE = "AVG";
	private String pEvalE = "IP";
	
	/** Construct an empty database */
	public FantasyDatabase()
	{
		this.players = new HashMap<>();
	}
	
	/** Construct a database with the given players */
	public FantasyDatabase(HashMap<String, FantasyPlayer> players)
	{
		this.players = players;
	}
	
	/** return database */
	public HashMap<String, FantasyPlayer> getPlayersMap()
	{
		return players;
	}
	
	/** return a player in the database */
	public FantasyPlayer getPlayer(String player)
	{
		return players.get(player.toLowerCase());
	}
	
	/** add a player to the database **/
	public void addPlayer(String name, FantasyPlayer player)
	{
		players.put(name, player);
	}
	
	/** check if a player is available to draft */
	public boolean isAvailable(FantasyPlayer p)
	{
		if (drafted.containsValue(p))
			return false;
		
		return true;
	}
	
	/** Return an arraylist of players of the given position */
	public ArrayList<FantasyPlayer> getPlayersByPosition(String position) {
		
		ArrayList<FantasyPlayer> playersInPosition = new ArrayList<>();

		for (FantasyPlayer p : players.values()) {
			if (p.getPosition().equals(position) || (position.toUpperCase().equals("HITTERS") && (!p.getPosition().equals("P")))
					|| (position.toUpperCase().equals("P") && p.getPosition().equals("P"))) {
				if (isAvailable(p))
					playersInPosition.add(p);
			}
		}

		return playersInPosition;
	}

	/** record that a player was drafted to a team*/
	public void addDrafted(FantasyPlayer p)
	{
		drafted.put(p.getName(), p);
	}
	
	/** change position weight */
	public void setPosWeight(String pos, double weight)
	{
		posWeights.put(pos, weight);
	}
	
	/** get position weight */
	public double getPosWeight(String pos)
	{
		return posWeights.get(pos);
	}

	/** set eval value */
	public void setEvalFun(String string) {
		evalE = string;
	}
	
	/** get eval value */
	public String getEvalFun() {
		return evalE;
		
	}
	
	/** set PEval value */
	public void setPEvalFun(String string) {
		pEvalE = string;
	}
	
	/** get PEval value */
	public String getPEvalFun() {
		return pEvalE;
	}
	
	/** Return a string representing the database */
	@Override
	public String toString()
	{
		return players.values().toString();
	}
}

