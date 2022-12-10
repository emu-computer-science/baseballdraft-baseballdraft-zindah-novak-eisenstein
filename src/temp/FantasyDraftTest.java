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
	private static FantasyTeam teamA, teamB;

	// test player data
	String playerName ="Alvarez, Y";
	String playerName2 ="Alonso, P";
	String pitcherName = "Verlander, J";
	
	Scanner command;

	/*
	 *  Setup and Teardown methods 
	 */
	@BeforeClass
	public static void initDraft() throws Exception {
		testDraft = new FantasyDraft();
	}
	
	@Before
	public void setUp() {
		testDraft.initDatabase();
		testDatabase = testDraft.getDatabase();
		
		teamA = testDraft.getTeams().get('A');
		teamB = testDraft.getTeams().get('B');

	    System.setOut(new PrintStream(outputStreamCaptor));
	    testDatabase.setPosWeight("1B", 2);
	}
	
	@After
	public void tearDown() {
	    System.setOut(standardOut);
	}
	
	/* test overall method */
	@Test(timeout=200)
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
	@Test(timeout=90)
	public void testpOverall() {
		testOverallAndPoverall("P", "Sandy     	Alcantara 	MIA	P	228.200\r\n"
				+ "Aaron     	Nola      	PHI	P	205.000\r\n"
				+ "Miles     	Mikolas   	STL	P	202.100"); 
	}

	/* test odraft and idraft method */
	@Test(timeout=20)
	public void testoDraft() {
		testDraft.oDraft(playerName2, 'B');
		assertNotNull(teamB.getPlayer(playerName2));
	}
	
	@Test(timeout=20)
	public void testiDraft() {
		testIdraftOdraft(playerName, "", "i");
	}
	
	/* test team and stars */
	@Test(timeout=20)
	public void testTeam() {
		command = new Scanner("\"Fried, M\"");
		testDraft.draftPlayer(command, "i");
		
		command = new Scanner("\"Judge, A\"");
		
		testDraft.draftPlayer(command, "i");
		outputStreamCaptor.reset();
		
		testTeamStars("A", "team", "CF: Aaron Judge\nP1: Max Fried");
	}

	@Test(timeout=20)
	public void testStars() {
		command = new Scanner("\"Fried, M\"");
		testDraft.draftPlayer(command, "i");
		
		command = new Scanner("\"Judge, A\"");
		
		testDraft.draftPlayer(command, "i");
		outputStreamCaptor.reset();
		
		testTeamStars("A", "stars", "P1: Max Fried\nCF: Aaron Judge");
	}
	
	/* test eval and peval */
	@Test(timeout=100)
	public void testEvalFun() {
		testDraft.setEvalPeval(new Scanner("AVG"), "eval");
		assertTrue(testDatabase.getEvalFun().equals("AVG"));
		testDraft.setEvalPeval(new Scanner("A"), "o");
	}
	
	@Test(timeout=100)
	public void testPEvalFun() {
		testDraft.setEvalPeval(new Scanner("IP"), "pEval");
		assertTrue(testDatabase.getPEvalFun().equals("IP"));
		testDraft.setEvalPeval(new Scanner("A"), "o");
	}
	
	// test weight method
	@Test(timeout=20)
	public void testValidWeight() {
		testWeight("1B", 2, 2);
	}
	
	@Test(timeout=20)
	public void testInvalidWeight() {
		testWeight("1B", -1, 2);
	}
	
	@Test(timeout=500)
	public void testLargeWeight() {
		testWeight("1B", 1000, 1000);
	}
	
	// test save and restore methods
	@Test(timeout=300)
	public void testSave() {
		Scanner command = new Scanner("file1.txt");
		testDraft.save(command);
		
		assertTrue(outputStreamCaptor.toString().trim().startsWith("Fantasy draft state saved to file."));
	}
	
	// test save method
	@Test(timeout=500)
	public void testRestoreEmpty() {
		Scanner command = new Scanner("");
		testDraft.restore(command);
		assertTrue(outputStreamCaptor.toString().trim().startsWith("Please enter a file name to restore"));
	}
	
	// test save method
	@Test(timeout=500)
	public void testRestore() {
		testIdraftOdraft(pitcherName, "", "i");
		testDraft.save("file1.txt");
		testIdraftOdraft("Altuve, J", "", "i");
		
		testDraft.restore(new Scanner("file1.txt"));
		outputStreamCaptor.reset();
		testDraft.team(new Scanner("A"), "team");
		
		assertEquals(outputStreamCaptor.toString().trim(), "P1: Justin Verlander");
	}
	
	// test help method
	@Test(timeout=20)
	public void testHelp() {
		UI userMenu = new UI(testDraft);
		userMenu.help();
		assertTrue(outputStreamCaptor.toString().trim().startsWith("Options:\r\n"
				+ "ODRAFT \"playername\" leagueMember: Draft player to specified team\r\n"
				+ "IDRAFT \"playername\": Draft player to own team"));
	}
	
	/* Helper method to test weight */
	private void testWeight(String pos, double weight, double actual) {
		if (weight < 0)
			command = new Scanner(pos);
		else
			command = new Scanner(pos + " " + weight);
		
		testDraft.weight(command);
		assertEquals(testDatabase.getPosWeight(pos), actual, 0.1);
	}
	
	/* Helper method to test overall and poverall */
	private void testOverallAndPoverall(String positionToPrint, String correctOutput) {
	    testDraft.overall(positionToPrint);
	    assertTrue(outputStreamCaptor.toString().trim().startsWith(correctOutput));
	}
	
	/* Helper method to test team and stars */
	private void testTeamStars(String team, String orderCommand, String correctOutput) {
		command = new Scanner(team);
		testDraft.team(command, orderCommand);
		assertEquals(outputStreamCaptor.toString().trim(), correctOutput);
	    assertTrue(outputStreamCaptor.toString().trim().startsWith(correctOutput));
	}
	/* Helper method to test idraft and odraft */
	private void testIdraftOdraft(String player, String league, String typeOfDraft) {
		command = new Scanner("\"" + player + "\" " + league);

		testDraft.draftPlayer(command, typeOfDraft);
		
		if (league.equals(""))
			assertNotNull(teamA.getPlayer(player));
		else
			assertNotNull(teamB.getPlayer(player));
	}
}
