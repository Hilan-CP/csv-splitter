package com.example.csv_splitter;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Files;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;

public class Splitter {

	private static final char BYTE_ORDER_MARK = '\uFEFF';

	private final File sourceFile;
	private final Charset charset;
	private boolean hasByteOrderMark;
	private String header;
	private int fileNumber;
	private String line;
	
	public Splitter(File sourceFile) throws IOException {
		this.sourceFile = sourceFile;
		this.charset = detectCharset(sourceFile);
		this.fileNumber = 0;
	}

	public void split(int amount, boolean hasHeader) throws IOException {
		try(BufferedReader reader = Files.newBufferedReader(sourceFile.toPath(), charset)){
			getFirstLineHeader(hasHeader, reader);
			while(line != null) {
				try(BufferedWriter writer = createNewWriter()){
					writeIfHasByteOrderMark(writer);
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

	private Charset detectCharset(File file) throws IOException {
		try(InputStream input = new BufferedInputStream(Files.newInputStream(file.toPath()))) {
			CharsetMatch match = new CharsetDetector().setText(input).detect();
			if(match == null) {
				return StandardCharsets.UTF_8;
			}
			String name = removeDirectionSuffix(match.getName());
			try {
				return Charset.forName(name);
			}
			catch(IllegalCharsetNameException | UnsupportedCharsetException e) {
				throw new IOException("Charset detectado (" + name + ") não é suportado pela JVM.", e);
			}
		}
	}

	private String removeDirectionSuffix(String name) {
		int suffixIndex = name.indexOf("_rtl");
		if(suffixIndex < 0) {
			suffixIndex = name.indexOf("_ltr");
		}
		return suffixIndex > 0 ? name.substring(0, suffixIndex) : name;
	}
	
	private BufferedWriter createNewWriter() throws IOException {
		String folder = sourceFile.getAbsoluteFile().getParent();
		String fileName = getFileName() + "_" + fileNumber + getFileExtension();
		String path = folder + File.separator + fileName;
		return Files.newBufferedWriter(new File(path).toPath(), charset);
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
		if(line != null && !line.isEmpty() && line.charAt(0) == BYTE_ORDER_MARK) {
			hasByteOrderMark = true;
			line = line.substring(1);
		}
		if(hasHeader) {
			header = line;
			line = reader.readLine();
		}
	}

	private void writeIfHasByteOrderMark(BufferedWriter writer) throws IOException {
		if(hasByteOrderMark) {
			writer.write(BYTE_ORDER_MARK);
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
