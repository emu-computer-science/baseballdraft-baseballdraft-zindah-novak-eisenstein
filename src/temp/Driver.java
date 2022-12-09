package temp;

import javax.script.ScriptException;

public class Driver {
	public static void main(String[] args) throws ScriptException  {
		FantasyDraft draftInstance = new FantasyDraft();
		UI userMenu = new UI(draftInstance);
		userMenu.interactWithUser();
	}
}
