package com.example.csv_spliter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
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
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Selecione o arquivo");
    	Node node = (Node) event.getSource();
    	Stage owner = (Stage) node.getScene().getWindow();
    	File file = fileChooser.showOpenDialog(owner);
    	if(file != null) {
    		fileField.setText(file.getAbsolutePath());
    	}
    }

    @FXML
    public void process(ActionEvent event) {
    	splitter = new Splitter();
    	splitter.setSourceFile(new File(fileField.getText()));
    	try {
    		int amount = amountSpinner.getValue();
    		boolean header = headerChechBox.isSelected();
    		splitter.split(amount, header);
    	}
    	catch(IOException e) {
    		e.printStackTrace();
    	}
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
