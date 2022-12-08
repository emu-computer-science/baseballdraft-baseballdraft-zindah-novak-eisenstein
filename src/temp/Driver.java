package temp;

public class Driver {
	public static void main(String[] args) {
		FantasyDraft draftInstance = new FantasyDraft();
		UI userMenu = new UI(draftInstance);
		userMenu.interactWithUser();
	}
}
