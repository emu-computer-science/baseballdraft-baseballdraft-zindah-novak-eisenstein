package temp;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;


/**
 * FantasyDraftTest.java class tests the main method FantasyDraft
 * 
 * @author Zindah, Novak, Eisenstein
 * @version (11-18-2022)
 *
 */
public class FantasyDraftTest {
	
	// OutputStreams for for text output
	private final PrintStream standardOut = System.out;
	private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

	// test setup data from FantasyDraft
	private static FantasyDraft testDraft;
	private static FantasyDatabase testDatabase;
	private static FantasyTeam teamA, teamB, teamC, teamD;

	// test player data
	String playerName ="Alvarez, Y";
	String pitcherName = "Verlander, J";
	
	Scanner command;

	/*
	 *  Setup and Teardown methods 
	 */
	@BeforeClass
	public static void initDraft() throws Exception {
		testDraft = new FantasyDraft();
		testDraft.initDatabase();
		testDatabase = testDraft.getDatabase();
		
		teamA = testDraft.getTeams().get('A');
		teamB = testDraft.getTeams().get('B');
		teamC = testDraft.getTeams().get('C');
		teamD = testDraft.getTeams().get('D');
	}
	
	@Before
	public void setUp() {
		// Test text output: reassign standard outputstream to a new PrintStream 
		// with a ByteArrayOutputStream
		// Values will now be printed to this output stream. 
	    System.setOut(new PrintStream(outputStreamCaptor));
	    testDatabase.setPosWeight("1B", 2);
	}
	
	@After
	public void tearDown() {
	    System.setOut(standardOut);
	}
	
	/* test overall method */
	@Test(timeout=20)
	public void testOverallLF() {
		testOverallAndPoverall("LF", "Yordan    	Alvarez   	HOU	LF	0.306\r\n"
	    		+ "Josh      	Bell      	SD	LF	0.266\r\n"
	    		+ "Kyle      	Schwarber 	PHI	LF	0.218");
	}
	
	@Test(timeout=20)
	public void testInvalidOverall() {
		testOverallAndPoverall("1", "");
	}
	
	/* test poverall method */
	@Test(timeout=20)
	public void testpOverall() {
		testOverallAndPoverall("P", "Nick      	Pivetta   	BOS	P	33.0\r\n"
	    		+ "Corbin    	Burnes    	MIL	P	33.0\r\n"
	    		+ "Gerrit    	Cole      	NYY	P	33.0"); 
	}

//	/* test odraft and idraft method */
//	@Test(timeout=20)
//	public void testoDraft() {
//		testIdraftOdraft(pitcherName, 'B', "o");
//	}
//	
//	@Test(timeout=20)
//	public void testiDraft() {
//		testIdraftOdraft(playerName, ' ', "i");
//	}
//	
//	public void testIdraftOdraft(String player, char league, String typeOfDraft) {
//		command = new Scanner("\"" + player + "\" " + league);
//
//		testDraft.draftPlayer(command, typeOfDraft);
//		
//		if (league == ' ')
//			assertNotNull(teamA.getPlayer(player));
//		else {
//			assertNotNull(teamB.getPlayer(player));
//		}
//	}
	
	// fiTRUING OUT TEAM TESTS
//	
//	
	@Test(timeout=20)
	public void testTeam() {//Not sure how to test this with JUnit
		//testTeamStars("A", "team", )
	}

	@Test(timeout=20)
	public void testStars() {//Not sure how to test this with JUnit

		command = new Scanner("C");
		
		testDraft.team(command, "stars");
	}
 
	
//	@Test(timeout=20)
//	public void testEvalFun() {//What does function this even do?
//		//assertEquals();
//		fail("Not yet implemented");
//	}
//	
//	@Test(timeout=20)
//	public void testPEvalFun() {//What does function this even do? 
//		//assertEquals();
//		fail("Not yet implemented");
//	}
//	
	// test weight method
	@Test(timeout=20)
	public void testValidWeight() {
		testWeight("1B", 2, 2);
	}
	
	@Test(timeout=20)
	public void testInvalidWeight() {
		testWeight("1B", -1, 2);
	}
	
	@Test(timeout=50)
	public void testLargeWeight() {
		testWeight("1B", 1000, 1000);
	}
	
	/*
	 * Helper method to test weight
	 */
	public void testWeight(String pos, double weight, double actual) {
		command = new Scanner(pos + " " + weight);
		
		testDraft.weight(command);
		assertEquals(testDatabase.getPosWeight(pos), actual, 0.1);
	}
	
	/* 
	 * Helper method to test overall and poverall
	 */
	public void testOverallAndPoverall(String positionToPrint, String correctOutput) {
	    testDraft.overall(positionToPrint);
	    
	    assertTrue(outputStreamCaptor.toString().trim().startsWith(correctOutput));
	}
	
	/* 
	 * Helper method to test overall and poverall
	 */
	public void testTeamStars(String team, String orderCommand, String correctOutput) {
		command = new Scanner(team);
		
		testDraft.team(command, "orderCommand");
	    
	    assertEquals(correctOutput, outputStreamCaptor.toString().trim());
	}
}
