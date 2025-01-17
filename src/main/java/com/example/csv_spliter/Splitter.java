package com.example.csv_spliter;

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
	
	public Splitter() {
		fileNumber = 0;
	}

	public void split(int amount, boolean hasHeader) throws IOException {
		String folder = sourceFile.getParent() + "\\";
		String fileName = getFileName() + "_";
		String fileExtension = getFileExtension();
		int count = 0;
		reader = new BufferedReader(new FileReader(sourceFile));
		writer = newWriter(folder + fileName + fileNumber + fileExtension);

		line = reader.readLine();
		getHeader(hasHeader);
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
				writer = newWriter(folder + fileName + fileNumber + fileExtension);
				writeIfHasHeader(hasHeader);
			}
		}
		writer.close();
		reader.close();
	}
	
	private String getFileName() {
		String file = sourceFile.getName();
		return file.substring(0, file.lastIndexOf("."));
	}
	
	private String getFileExtension() {
		String file = sourceFile.getName();
		return file.substring(file.lastIndexOf("."));
	}
	
	private BufferedWriter newWriter(String path) throws IOException {
		return new BufferedWriter(new FileWriter(path));
	}
	
	private void getHeader(boolean hasHeader) throws IOException {
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

	public void setSourceFile(File sourceFile) {
		this.sourceFile = sourceFile;
	}
}
