package dad.gesaula.ui.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import dad.gesaula.ui.model.Alumno;
import dad.gesaula.ui.model.LocalDateAdapter;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.util.converter.LocalDateStringConverter;

public class AlumnosController implements Initializable {

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

		alumnoGrid.visibleProperty().bind(selectedAlumno.isNotNull());
		alumnoNoSeleccionadoLabel.visibleProperty().bind(selectedAlumno.isNull());
		
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

    }

    @FXML
    void onNuevoAlumnoAction(ActionEvent event) {

    }

}
