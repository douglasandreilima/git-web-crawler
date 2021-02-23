package dev.douglas.api.info;

public class GitWebCralwerItem {

	private long totalLines;

	private long totalBytes;

	private String fileExtension;

	private String fileName;

	public long getTotalLines() {
		return totalLines;
	}

	public void setTotalLines(final long totalLines) {
		this.totalLines = totalLines;
	}

	public long getTotalBytes() {
		return totalBytes;
	}

	public void setTotalBytes(final long totalBytes) {
		this.totalBytes = totalBytes;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(final String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(final String fileName) {
		this.fileName = fileName;
	}

}
