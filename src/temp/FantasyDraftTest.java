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
	
	String playerName ="";
	String league ="memberB";
	
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testoDraft() {
		testDraft.oDraft(playerName, 'B');
		assertEquals(playerName,);
		
		fail("Not yet implemented");
	}
	
	@Test
	public void testiDraft() {
		fail("Not yet implemented");
		
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
