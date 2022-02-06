package main.Controllers.Notifications;

public class NotificationsManager {

	public static int num = 0;
	
	public static void incrementaNumNotifiche() {
		num++;
	}
	
	public static void decrementaNumNotifiche() {
		num--;
	}
	
	public static int getNumNotifiche() {
		return num;
	}
	
}
