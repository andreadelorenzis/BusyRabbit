package main.views.modal;

/**
 * Gestisce il numero di Modal aperti simultaneamente
 */
public class ModalsManager {
	private static ModalsManager instance = null;
	
	private boolean isModalOpen;
	
	private ModalsManager() {
		this.isModalOpen = false;
	}
	
	public static ModalsManager getInstance() {
		if(instance == null) {
			instance = new ModalsManager();
		}
		return instance;
	}
	
	public void apriModal() {
		isModalOpen = true;
	}
	
	public void chiudiModal() {
		isModalOpen = false;
	}
	
	public boolean isModalOpen() {
		return isModalOpen;
	}
}
