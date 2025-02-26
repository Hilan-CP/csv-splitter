package com.example.csv_splitter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class Controller implements Initializable {
	
	private Splitter splitter;
	
    @FXML
    private Spinner<Integer> amountSpinner;

    @FXML
    private TextField fileField;

    @FXML
    private CheckBox headerChechBox;
    
    @FXML
    public void browse(ActionEvent event) {
    	ExtensionFilter text = new ExtensionFilter("Texto", "*.csv", "*.txt");
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Selecione o arquivo");
    	fileChooser.getExtensionFilters().add(text);
    	File file = fileChooser.showOpenDialog(getCurrentStage(event));
    	if(file != null) {
    		fileField.setText(file.getAbsolutePath());
    	}
    }

    @FXML
    public void process(ActionEvent event) {
    	try {
    		splitter = new Splitter(validatedFile());
    		int amount = amountSpinner.getValue();
    		boolean header = headerChechBox.isSelected();
    		splitter.split(amount, header);
    	}
    	catch(IOException e) {
    		showError(e.getMessage());
    	}
    }
    
    private Stage getCurrentStage(ActionEvent event) {
    	Node node = (Node) event.getSource();
    	return (Stage) node.getScene().getWindow();
    }
    
    private File validatedFile() throws IOException {
    	String filePath = fileField.getText();
    	if(filePath.endsWith(".csv") || filePath.endsWith(".txt")) {
    		return new File(filePath);
    	}
    	else if(filePath.trim().equals("")) {
    		throw new IOException("Nenhum arquivo selecionado.");
    	}
    	else {
    		throw new IOException("Extensão de arquivo inválida. Utilize arquivos csv ou txt.");
    	}
    }
    
    private void showError(String message) {
    	Alert alert = new Alert(AlertType.ERROR);
    	alert.setHeaderText(null);
    	alert.setTitle("Erro");
    	alert.setContentText(message);
    	alert.show();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeSpinnerValueFactory();
		initializeSpinnerFormatter();
	}
	
	private void initializeSpinnerValueFactory() {
		SpinnerValueFactory<Integer> spinnerFactory = new IntegerSpinnerValueFactory(1, Integer.MAX_VALUE);
		amountSpinner.setValueFactory(spinnerFactory);
	}
	
	private void initializeSpinnerFormatter() {
		TextFormatter<Integer> formatter = new TextFormatter<>(customConverter(), 1, customFilter());
		amountSpinner.getEditor().setTextFormatter(formatter);
	}
	
	private UnaryOperator<Change> customFilter(){
		UnaryOperator<Change> filter = change -> {
			String value = change.getText();
			if(value.matches("\\d*")) {
				return change;
			}
			return null;
		};
		return filter;
	}
	
	private StringConverter<Integer> customConverter(){
		StringConverter<Integer> converter = new StringConverter<Integer>() {
			@Override
			public String toString(Integer object) {
				return object.toString();
			}
			
			@Override
			public Integer fromString(String string) {
				try {
					return Integer.parseInt(string);
				}
				catch(NumberFormatException e) {
					return Integer.MAX_VALUE;
				}
			}
		};
		return converter;
	}
}
