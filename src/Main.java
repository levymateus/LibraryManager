
import gui.GUIInicial;

public class Main {

	public static void main(String[] args) {
		
		GUIInicial frame = new GUIInicial();
		frame.initSistema("localhost:5432", "sistema-biblioteca", "postgres", "postgres");
		
	}

}
