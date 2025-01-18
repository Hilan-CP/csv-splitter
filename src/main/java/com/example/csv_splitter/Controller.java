package com.example.csv_splitter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

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
    	else {
    		throw new IOException("Invalid file extension");
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
		initializeSpinner();
	}
	
	private void initializeSpinner() {
		SpinnerValueFactory<Integer> spinnerFactory = new IntegerSpinnerValueFactory(1, Integer.MAX_VALUE);
		amountSpinner.setValueFactory(spinnerFactory);
	}
}
