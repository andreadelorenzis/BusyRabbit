package main.Controllers.Modals;

public class ModalsManager {
	private static boolean isModalOpen = false;
	
	public static void apriModal() {
		isModalOpen = true;
	}
	
	public static void chiudiModal() {
		isModalOpen = false;
	}
	
	public static boolean isModalOpen() {
		return isModalOpen;
	}
}
