package dad.gesaula.ui.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.gesaula.ui.model.Grupo;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class MainController implements Initializable {
	
	private StringProperty fichero = new SimpleStringProperty();

	private AlumnosController alumnosController;
	private GrupoController grupoController;

	@FXML
	private Tab alumnosTab;

	@FXML
	private TextField ficheroText;

	@FXML
	private Tab grupoTab;
	
	@FXML
	private Button guardarButon;
	
	@FXML
	private BorderPane view;

	private Grupo grupo = new Grupo();

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

		grupo.denominacionProperty().bindBidirectional(grupoController.getModel().denominacionProperty());
		grupo.iniCursoProperty().bindBidirectional(grupoController.getModel().iniCursoProperty());
		grupo.finCursoProperty().bindBidirectional(grupoController.getModel().finCursoProperty());
		grupo.pesosProperty().bindBidirectional(grupoController.getModel().pesosProperty());
		grupo.alumnosProperty().bindBidirectional(alumnosController.getAlumnos());
		
		fichero.bind(ficheroText.textProperty());

		alumnosTab.setContent(alumnosController.getView());
		grupoTab.setContent(grupoController.getView());
		
		guardarButon.disableProperty().bind(fichero.isEmpty());

	}

	public BorderPane getView() {
		return view;
	}

	@FXML
	void onGuardarGrupoAction(ActionEvent event) {
		try {
			grupo.save(new File(ficheroText.getText()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void onNuevoGrupoAction(ActionEvent event) {
		grupoController.nuevoGrupo();
		alumnosController.nuevoGrupo();
		ficheroText.setText("");
	}

}
