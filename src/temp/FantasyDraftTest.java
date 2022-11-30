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
	
	String playerName ="Verlander";
	String league ="memberB";
	
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testoDraft() {
		FantasyTeam data = new FantasyTeam('B');
		
		assertNull(data.getTeamPlayers().get(playerName));
		testDraft.oDraft(playerName, data);
		assertNotNull(data.getTeamPlayers().get(playerName));
		
	}
	
	@Test
	public void testiDraft() {
		FantasyTeam data = new FantasyTeam('A');
		
		assertNull(data.getTeamPlayers().get(playerName));
		testDraft.oDraft(playerName, data);
		assertNotNull(data.getTeamPlayers().get(playerName));
		
	}
	
	@Test
	public void testOverall() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testpOverall() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testTeam() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testStars() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testEvalFun() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testPEvalFun() {
		fail("Not yet implemented");
	}

}
