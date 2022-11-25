package dad.gesaula.ui;

import dad.gesaula.ui.controller.MainController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

	public static Stage primaryStage;

	private MainController mainController = new MainController();

	@Override
	public void start(Stage primaryStage) throws Exception {
		App.primaryStage = primaryStage;

		primaryStage.setTitle("GesAula");
		primaryStage.setScene(new Scene(mainController.getView()));
		primaryStage.getIcons().add(new Image("/images/app-icon-64x64.png"));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
