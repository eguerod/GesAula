package dad.gesaula.ui.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.gesaula.ui.model.Alumno;
import dad.gesaula.ui.model.Sexo;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.util.converter.LocalDateStringConverter;

public class AlumnosController implements Initializable {

	private int posicionDejada;
	private boolean added, deleted;

	@FXML
	private GridPane alumnoGrid;

	@FXML
	private Label alumnoNoSeleccionadoLabel;

	@FXML
	private TableView<Alumno> alumnosTab;

	@FXML
	private TableColumn<Alumno, String> apellidosCol;

	@FXML
	private TextField apellidosText;

	@FXML
	private Button eliminarButton;

	@FXML
	private TableColumn<Alumno, LocalDate> fechaNacimientoCol;

	@FXML
	private DatePicker fechaNacimientoDate;

	@FXML
	private TableColumn<Alumno, String> nombreCol;

	@FXML
	private TextField nombreText;

	@FXML
	private CheckBox repiteCheck;

	@FXML
	private ComboBox<Sexo> sexoCombo;

	@FXML
	private GridPane view;

	private ListProperty<Alumno> alumnos = new SimpleListProperty<>(FXCollections.observableArrayList());
	private ObjectProperty<Alumno> selectedAlumno = new SimpleObjectProperty<>();

	public AlumnosController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AlumnosView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		alumnosTab.itemsProperty().bind(alumnos);
		selectedAlumno.bind(alumnosTab.getSelectionModel().selectedItemProperty());
//		eliminarButton.disableProperty().bind(selectedAlumno.isNull());

		selectedAlumno.addListener((num) -> seleccionar());

		alumnoGrid.visibleProperty().bind(selectedAlumno.isNotNull());
		alumnoNoSeleccionadoLabel.visibleProperty().bind(selectedAlumno.isNull());

		sexoCombo.getItems().addAll(Sexo.values());

		fechaNacimientoCol.setCellValueFactory(v -> v.getValue().fechaNacimientoProperty());
		nombreCol.setCellValueFactory(v -> v.getValue().nombreProperty());
		apellidosCol.setCellValueFactory(v -> v.getValue().apellidosProperty());

		fechaNacimientoCol.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
		nombreCol.setCellFactory(TextFieldTableCell.forTableColumn());
		apellidosCol.setCellFactory(TextFieldTableCell.forTableColumn());

	}

	public GridPane getView() {
		return view;
	}

	@FXML
	void onEliminarAlumnoAction(ActionEvent event) {
		Alert alerta;
		if (selectedAlumno.isNull().get()) {
			alerta = new Alert(AlertType.ERROR);
			alerta.setTitle("Error");
			alerta.setHeaderText("Error al eliminar el alumno");
			alerta.setContentText("No hay ningún alumno seleccionado.");
			alerta.showAndWait();
		} else {
			alerta = new Alert(AlertType.CONFIRMATION);
			alerta.setTitle("Eliminar alumno");
			alerta.setHeaderText("Se va a eliminar el alumno '" + selectedAlumno.get().getNombre() + " "
					+ selectedAlumno.get().getApellidos() + "'.");
			alerta.setContentText("¿Está seguro?");
			Optional<ButtonType> result = alerta.showAndWait();
			if (result.get() == ButtonType.OK) {
				deleted = true;
				alumnos.remove(selectedAlumno.get());
			}
		}
	}

	@FXML
	void onNuevoAlumnoAction(ActionEvent event) {
		if (alumnos.size() > 0)
			added = true;

		Alumno al = new Alumno();
		al.setNombre("Sin nombre");
		al.setApellidos("Sin apellidos");
		alumnos.add(al);
	}

	private void seleccionar() {

		if (alumnos.size() > 0) {
			alumnos.get(posicionDejada).nombreProperty().unbind();
			alumnos.get(posicionDejada).apellidosProperty().unbind();
			alumnos.get(posicionDejada).fechaNacimientoProperty().unbind();
			alumnos.get(posicionDejada).sexoProperty().unbind();
			alumnos.get(posicionDejada).repiteProperty().unbind();
		}

		if (!added && !deleted) {
			nombreText.setText(selectedAlumno.get().getNombre());
			apellidosText.setText(selectedAlumno.get().getApellidos());
			fechaNacimientoDate.setValue(selectedAlumno.get().getFechaNacimiento());
			sexoCombo.setValue(selectedAlumno.get().getSexo());
			repiteCheck.setSelected(selectedAlumno.get().isRepite());

			selectedAlumno.get().nombreProperty().bind(nombreText.textProperty());
			selectedAlumno.get().apellidosProperty().bind(apellidosText.textProperty());
			selectedAlumno.get().fechaNacimientoProperty().bind(fechaNacimientoDate.valueProperty());
			selectedAlumno.get().sexoProperty().bind(sexoCombo.valueProperty());
			selectedAlumno.get().repiteProperty().bind(repiteCheck.selectedProperty());

			posicionDejada = alumnos.indexOf(selectedAlumno.get());
		}

		added = false;
		deleted = false;
	}

	public ListProperty<Alumno> getAlumnos() {
		return alumnos;
	}

	public void nuevoGrupo() {
		alumnos.clear();
		deleted = true;
	}
}
