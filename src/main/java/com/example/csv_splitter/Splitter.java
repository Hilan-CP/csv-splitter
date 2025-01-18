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
	private BufferedReader reader;
	private BufferedWriter writer;
	String line;
	
	public Splitter(File sourceFile) {
		this.sourceFile = sourceFile;
		this.fileNumber = 0;
	}

	public void split(int amount, boolean hasHeader) throws IOException {
		int count = 0;
		reader = new BufferedReader(new FileReader(sourceFile));
		writer = createNewWriter();
		getFirstLineHeader(hasHeader);
		writeIfHasHeader(hasHeader);
		while(line != null) {
			writer.write(line);
			writer.newLine();
			line = reader.readLine();
			++count;
			if(count >= amount && line != null) {
				writer.close();
				++fileNumber;
				count = 0;
				writer = createNewWriter();
				writeIfHasHeader(hasHeader);
			}
		}
		writer.close();
		reader.close();
	}
	
	private BufferedWriter createNewWriter() throws IOException {
		String folder = sourceFile.getParent() + "\\";
		String fileName = getFileName() + "_" + fileNumber;
		String path = folder + fileName + getFileExtension();
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
	
	private void getFirstLineHeader(boolean hasHeader) throws IOException {
		line = reader.readLine();
		if(hasHeader) {
			header = line;
			line = reader.readLine();
		}
	}
	
	private void writeIfHasHeader(boolean hasHeader) throws IOException {
		if(hasHeader) {
			writer.write(header);
			writer.newLine();
		}
	}
}
