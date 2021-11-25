package dad.gesaula.ui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.gesaula.ui.model.Grupo;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

public class ToolbarController implements Initializable {

	// model

	private StringProperty nombreFichero = new SimpleStringProperty();
	private ObjectProperty<Grupo> grupo = new SimpleObjectProperty<>();

	// view

	@FXML
	private ToolBar view;

	@FXML
	private Button nuevoButton;

	@FXML
	private TextField ficheroText;

	@FXML
	private Button guardarButton;

	public ToolbarController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ToolbarView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		nombreFichero.bind(ficheroText.textProperty());

	}

	public ToolBar getView() {
		return view;
	}

	@FXML
	void onGuardarAction(ActionEvent event) {
		String ruta = nombreFichero.get();
		if (ruta != null && !ruta.isEmpty()) {
			try {
				getGrupo().save(new File(ruta));

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.initOwner(GesAulaApp.primaryStage);
				alert.setTitle("Guardar grupo");
				alert.setHeaderText("Se ha guardado el grupo correctamente.");
				alert.setContentText("El grupo " + getGrupo().getDenominacion() + " se ha guardado en el fichero '" + ruta + "'.");
				alert.showAndWait();

			} catch (Exception ex) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.initOwner(GesAulaApp.primaryStage);
				alert.setTitle("Guardar grupo");
				alert.setHeaderText("Error al guardar el grupo.");
				alert.setContentText(ex.getMessage());
				alert.showAndWait();
				ex.printStackTrace();
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(GesAulaApp.primaryStage);
			alert.setTitle("Guardar grupo");
			alert.setHeaderText("Error al guardar el grupo.");
			alert.setContentText("Debe especificar la ruta del fichero donde se guardar� el grupo.");
			alert.showAndWait();
		}
	}

	@FXML
	void onNuevoAction(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initOwner(GesAulaApp.primaryStage);
		alert.setTitle("Nuevo grupo");
		alert.setHeaderText("Va a crear un grupo nuevo.");
		alert.setContentText("¿Está seguro?");
		Optional<ButtonType> opcion = alert.showAndWait();
		if (opcion.get().equals(ButtonType.OK)) {
			grupo.set(new Grupo());
		}
	}

	public ObjectProperty<Grupo> grupoProperty() {
		return this.grupo;
	}

	public Grupo getGrupo() {
		return this.grupoProperty().get();
	}

	public void setGrupo(final Grupo grupo) {
		this.grupoProperty().set(grupo);
	}

}
