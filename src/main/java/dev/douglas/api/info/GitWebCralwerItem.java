package dev.douglas.api.info;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class GitWebCralwerItem {

	private String fileName;

	private String fileExtension;

	private String totalLinesAndBytes;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(final String fileName) {
		this.fileName = fileName;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(final String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public String getTotalLinesAndBytes() {
		return totalLinesAndBytes;
	}

	public void setTotalLinesAndBytes(final String totalLinesAndBytes) {
		this.totalLinesAndBytes = totalLinesAndBytes;
	}

}
