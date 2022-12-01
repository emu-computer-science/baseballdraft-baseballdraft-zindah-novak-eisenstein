package temp;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
/**
 * FantasyDraftTest.java class tests the main method FantasyDraft
 * 
 * @author Zindah, Novak, Eisenstein
 * @version (11-18-2022)
 *
 */
public class FantasyDraftTest {

	FantasyDraft testDraft  = new FantasyDraft();
	
	String playerName ="Alvarez";
	String pitcherName = "Verlander";
	String league ="memberB";
	
	@Before
	public void setUp() throws Exception {
		
	}
// throws "No Such Player error"
	@Test
	public void testoDraft() {
		FantasyTeam data = new FantasyTeam('B');
		
		assertNull(data.getTeamPlayers().get(playerName));
		testDraft.oDraft(playerName, data.getLeague());
		assertNotNull(data.getTeamPlayers().get(playerName));
		
	}
	// throws "No Such Player error"	
	@Test
	public void testiDraft() {
		FantasyTeam data = new FantasyTeam('A');
		
		assertNull(data.getTeamPlayers().get(playerName));
		testDraft.oDraft(playerName, data.getLeague());
		assertNotNull(data.getTeamPlayers().get(playerName));
		
	}
	
	@Test
	public void testOverall() {//Not sure how to test this with JUnit
		//Drosn't seem to be printing to begin with 
		FantasyTeam data = new FantasyTeam('B');
		testDraft.overall("hitter", data);
		//assertNotNull();
	}
	
	@Test
	public void testpOverall() {//Not sure how to test this with JUnit
		FantasyTeam data = new FantasyTeam('A');
		testDraft.overall("pitchers", data); //not actually a function in the driver, just calls overall("pitchers", leagueA);
	}
	
	@Test
	public void testTeam() {//Not sure how to test this with JUnit
		FantasyTeam data = new FantasyTeam('A');
		//assertNull();
		testDraft.team(data);
		testDraft.oDraft(playerName, data);
		//assertNotNull();
		testDraft.team(data);
	}
	
	@Test
	public void testStars() {//Not implemented
		fail("Not yet implemented");
	}
	
	@Test
	public void testEvalFun() {//What does function this even do?
		//assertEquals();
		fail("Not yet implemented");
	}
	
	@Test
	public void testPEvalFun() {//What does function this even do? 
		//assertEquals();
		fail("Not yet implemented");
	}

}
