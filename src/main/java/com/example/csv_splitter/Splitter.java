package com.example.csv_splitter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Splitter {

	private File sourceFile;
	private String header;
	private int fileNumber;
	String line;
	
	public Splitter(File sourceFile) {
		this.sourceFile = sourceFile;
		this.fileNumber = 0;
	}

	public void split(int amount, boolean hasHeader) throws IOException {
		try(BufferedReader reader = new BufferedReader(new FileReader(sourceFile))){
			getFirstLineHeader(hasHeader, reader);
			while(line != null) {
				try(BufferedWriter writer = createNewWriter()){
					writeIfHasHeader(hasHeader, writer);
					for(int count = 1; count <= amount && line != null; ++count) {
						writeLine(writer);
						line = reader.readLine();
					}
					++fileNumber;
				}
			}
		}
		//deixar exceção chegar até o controller
	}
	
	private BufferedWriter createNewWriter() throws IOException {
		String folder = sourceFile.getParent();
		String fileName = getFileName() + "_" + fileNumber + getFileExtension();
		String path = folder + File.separator + fileName;
		return new BufferedWriter(new FileWriter(path));
	}
	
	private String getFileName() {
		String name = sourceFile.getName();
		return name.substring(0, name.lastIndexOf("."));
	}
	
	private String getFileExtension() {
		String name = sourceFile.getName();
		return name.substring(name.lastIndexOf("."));
	}
	
	private void getFirstLineHeader(boolean hasHeader, BufferedReader reader) throws IOException {
		line = reader.readLine();
		if(hasHeader) {
			header = line;
			line = reader.readLine();
		}
	}
	
	private void writeIfHasHeader(boolean hasHeader, BufferedWriter writer) throws IOException {
		if(hasHeader) {
			writer.write(header);
			writer.newLine();
		}
	}
	
	private void writeLine(BufferedWriter writer) throws IOException {
		writer.write(line);
		writer.newLine();
	}
}
