package dad.gesaula.ui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.gesaula.ui.model.Alumno;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.LocalDateStringConverter;

public class AlumnosController implements Initializable {
	
	// controllers
	
	private AlumnoEditController alumnoEditController = new AlumnoEditController();

	// model

	private ListProperty<Alumno> alumnos = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObjectProperty<Alumno> seleccionado = new SimpleObjectProperty<>();

	// view

	@FXML
	private SplitPane view;

	@FXML
	private TableView<Alumno> alumnosTable;

	@FXML
	private TableColumn<Alumno, String> nombreColumn;

	@FXML
	private TableColumn<Alumno, String> apellidosColumn;

	@FXML
	private TableColumn<Alumno, LocalDate> nacimientoColumn;

	@FXML
	private Button nuevoButton;

	@FXML
	private Button eliminarbutton;
	
    @FXML
    private BorderPane rightPane;

    @FXML
    private VBox noAlumnoPane;

	public AlumnosController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AlumnosView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// bindings
		
		alumnosTable.itemsProperty().bind(alumnos);
		seleccionado.bind(alumnosTable.getSelectionModel().selectedItemProperty());
		
		// cell factories
		
		nombreColumn.setCellValueFactory(v -> v.getValue().nombreProperty());
		apellidosColumn.setCellValueFactory(v -> v.getValue().apellidosProperty());
		nacimientoColumn.setCellValueFactory(v -> v.getValue().fechaNacimientoProperty());
		
		nacimientoColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));

		alumnoEditController.alumnoProperty().bind(seleccionado);

		// listeners
		
		seleccionado.addListener(this::onSeleccionadoChanged);
		
		
	}
	
	private void onSeleccionadoChanged(ObservableValue<? extends Alumno> o, Alumno ov, Alumno nv) {
		if (nv != null) {
			rightPane.setCenter(alumnoEditController.getView());
		} else {
			rightPane.setCenter(noAlumnoPane);
		}
	}

	public SplitPane getView() {
		return view;
	}

	@FXML
	void onEliminarAlumnoAction(ActionEvent event) {
		Alumno seleccionado = alumnosTable.getSelectionModel().getSelectedItem();
		if (seleccionado == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(GesAulaApp.primaryStage);
			alert.setTitle("Eliminar alumno");
			alert.setHeaderText("Error al intentar eliminar un alumno.");
			alert.setContentText("No se ha seleccionado ningún alumno.");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.initOwner(GesAulaApp.primaryStage);
			alert.setTitle("Eliminar alumno");
			alert.setHeaderText("Se va a eliminar al alumno '" + seleccionado + "'.");
			alert.setContentText("¿Está seguro?");
			Optional<ButtonType> opcion = alert.showAndWait();
			if (opcion.get().equals(ButtonType.OK)) {
				this.alumnos.remove(seleccionado);
			}
		}
	}

	@FXML
	void onNuevoAlumnoAction(ActionEvent event) {
		Alumno nuevo = new Alumno();
		nuevo.setNombre("Sin nombre");
		nuevo.setApellidos("Sin apellidos");
		this.alumnos.add(nuevo);
	}

	public ListProperty<Alumno> alumnosProperty() {
		return this.alumnos;
	}

	public ObservableList<Alumno> getAlumnos() {
		return this.alumnosProperty().get();
	}

	public void setAlumnos(final ObservableList<Alumno> alumnos) {
		this.alumnosProperty().set(alumnos);
	}

}
