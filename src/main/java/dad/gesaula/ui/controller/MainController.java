package dad.gesaula.ui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class MainController implements Initializable {
	
	private AlumnosController alumnosController;
	private GrupoController grupoController;
	
	@FXML
	private Tab alumnosTab;

	@FXML
	private TextField ficheroText;

	@FXML
	private Tab grupoTab;

	@FXML
	private BorderPane view;

	public MainController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		alumnosController = new AlumnosController();
		grupoController = new GrupoController();

		alumnosTab.setContent(alumnosController.getView());
		grupoTab.setContent(grupoController.getView());
	}

	public BorderPane getView() {
		return view;
	}
	
	@FXML
	void onGuardarGrupoAction(ActionEvent event) {

	}

	@FXML
	void onNuevoGrupoAction(ActionEvent event) {

	}

}
