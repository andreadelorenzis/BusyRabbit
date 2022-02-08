package main.Controllers.Notifications;

public class NotificationsManager {
	
	private static NotificationsManager istanza = null;
	
	private int num;
	
	private NotificationsManager() {
		num = 0;
	}
	
	public static NotificationsManager getInstance() {
		if(istanza == null) {
			istanza = new NotificationsManager();
		}
		
		return istanza;
	}
	
	public void incrementaNumNotifiche() {
		num++;
	}
	
	public void decrementaNumNotifiche() {
		if(num > 0) {
			num--;
		}
	}
	
	public int getNumNotifiche() {
		return num;
	}
	
}
