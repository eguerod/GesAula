package dad.gesaula.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.gesaula.ui.model.Grupo;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;

public class RootController implements Initializable {

	// model

	private ObjectProperty<Grupo> grupo = new SimpleObjectProperty<>();

	// view

	@FXML
	private BorderPane view;

	@FXML
	private Tab grupoTab;

	@FXML
	private Tab alumnosTab;

	// controllers

	private ToolbarController toolbarController = new ToolbarController();
	private GrupoController grupoController = new GrupoController();
	private AlumnosController alumnosController = new AlumnosController();

	public RootController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RootView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// compound view with subcontrollers

		view.setTop(toolbarController.getView());
		grupoTab.setContent(grupoController.getView());
		alumnosTab.setContent(alumnosController.getView());

		// bindings

		toolbarController.grupoProperty().bindBidirectional(grupo);
		grupoController.grupoProperty().bind(grupo);
		
		// listeners
		
		grupo.addListener(this::onGrupoChanged);

		grupo.set(new Grupo());
		
	}

	private void onGrupoChanged(ObservableValue<? extends Grupo> o, Grupo ov, Grupo nv) {
		
		if (ov != null) {
			
			alumnosController.alumnosProperty().unbind();
			
		}
		
		if (nv != null) {
			
			alumnosController.alumnosProperty().bind(nv.alumnosProperty());
			
		}
		
	}

	public BorderPane getView() {
		return view;
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
