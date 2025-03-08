package comp3111.examsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * Main class object of an exam system for HKUST
 */
public class Main extends Application {
	/**
	 * Default controller for this class
	 */
	public Main(){

	}
	/**
	 * A method to start the system
	 * @param primaryStage	Stage assigned
	 */
	public void start(Stage primaryStage) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("LoginUI.fxml"));
			Scene scene = new Scene(fxmlLoader.load(), 640, 480);
			//FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("StudentMainUI.fxml"));
			//Scene scene = new Scene(fxmlLoader.load());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * A method to launch the object
	 * @param args	any argus for the launch
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
