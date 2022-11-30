package dad.gesaula.ui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.gesaula.ui.model.Grupo;
import dad.gesaula.ui.model.Pesos;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class GrupoController implements Initializable {
	@FXML
	private DatePicker FinDate;

	@FXML
	private DatePicker InicioDate;

	@FXML
	private Label actitudLabel;

	@FXML
	private Slider actitudSlider;

	@FXML
	private TextField denominacionText;

	@FXML
	private Label examenesLabel;

	@FXML
	private Slider examenesSlider;

	@FXML
	private Label practicasLabel;

	@FXML
	private Slider practicasSlider;

	@FXML
	private GridPane view;

	private Grupo model = new Grupo();
	private Pesos p = new Pesos();
	private ObjectProperty<Pesos> pesos = new SimpleObjectProperty<>();

	public GrupoController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GrupoView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		examenesLabel.textProperty().bind(examenesSlider.valueProperty().asString("%.2f").concat("%"));
		practicasLabel.textProperty().bind(practicasSlider.valueProperty().asString("%.2f").concat("%"));
		actitudLabel.textProperty().bind(actitudSlider.valueProperty().asString("%.2f").concat("%"));

		model.denominacionProperty().bindBidirectional(denominacionText.textProperty());
		model.iniCursoProperty().bindBidirectional(InicioDate.valueProperty());
		model.finCursoProperty().bindBidirectional(FinDate.valueProperty());

		p.examenesProperty().bindBidirectional(examenesSlider.valueProperty());
		p.practicasProperty().bindBidirectional(practicasSlider.valueProperty());
		p.actitudProperty().bindBidirectional(actitudSlider.valueProperty());

		pesos.setValue(p);

		model.pesosProperty().bindBidirectional(pesos);

	}

	public GridPane getView() {
		return view;
	}

	public void nuevoGrupo() {
		denominacionText.setText("");
		InicioDate.setValue(null);
		FinDate.setValue(null);
		examenesSlider.setValue(0);
		practicasSlider.setValue(0);
		actitudSlider.setValue(0);
	}

	public Grupo getModel() {
		return model;
	}
}
