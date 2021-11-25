package dad.gesaula.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class GesAulaApp extends Application {
	
	public static Stage primaryStage;

	private RootController rootController;

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		GesAulaApp.primaryStage = primaryStage;
		
		rootController = new RootController();
		
		Scene scene = new Scene(rootController.getView(), 640, 480);
		
		primaryStage.getIcons().add(new Image(getClass().getResource("/images/app-icon-64x64.png").toExternalForm()));		
		primaryStage.setTitle("GesAula");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}	

}
