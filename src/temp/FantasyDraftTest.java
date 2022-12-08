package temp;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Scanner;

import org.junit.BeforeClass;
import org.junit.Test;
/**
 * FantasyDraftTest.java class tests the main method FantasyDraft
 * 
 * @author Zindah, Novak, Eisenstein
 * @version (11-18-2022)
 *
 */
public class FantasyDraftTest {

	static FantasyDraft testDraft  = new FantasyDraft();
	static FantasyTeam teamA;
	static FantasyTeam teamB;
	static FantasyTeam teamC;
	static FantasyTeam teamD;

	String playerName ="Alvarez, Y";
	String pitcherName = "Verlander, J";
	String league ="memberB";
	
	Scanner command;

	@BeforeClass
	public static void setUp() throws Exception {
		testDraft.initDatabase();
		teamA = testDraft.getTeam('A');
		teamB = testDraft.getTeam('B');
		teamC = testDraft.getTeam('C');
		teamD = testDraft.getTeam('D');
	}
	
	@Test
	public void testoDraft() {
		assertNull(teamB.getPlayer(pitcherName));

		command = new Scanner(" \"" + pitcherName + "\" B");

		testDraft.draftPlayer(command, "o");
		
		assertNotNull(teamB.getPlayer(pitcherName));
	}
	
	@Test
	public void testiDraft() {

		assertNull(teamA.getPlayer(playerName));
		
		command = new Scanner("\"" + playerName + "\"");
		
		testDraft.draftPlayer(command, "i");
		
		assertNotNull(teamA.getPlayer(playerName));
	}
	
	@Test
	public void testOverall() {//Not sure how to test this with JUnit
		//Drosn't seem to be printing to begin with 
		FantasyTeam data = new FantasyTeam('B');
		testDraft.overall("hitter");
		//assertNotNull();
	}
	
	@Test
	public void testpOverall() {//Not sure how to test this with JUnit
		FantasyTeam data = new FantasyTeam('A');
		testDraft.overall("pitchers"); //not actually a function in the driver, just calls overall("pitchers", leagueA);
	}
	
	@Test
	public void testTeam() {//Not sure how to test this with JUnit

		command = new Scanner("C");
		
		testDraft.team(command, "team");
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
	
	@Test
	public void testWeight() {//What does function this even do? 
		fail("Not yet implemented");
	}

}
