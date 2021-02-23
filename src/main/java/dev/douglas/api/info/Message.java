package dev.douglas.api.info;

import java.time.Instant;

public class Message {

	private final Instant time;

	private final String message;

	private final Integer status;

	public Message(final String message, final Integer status) {
		this.message = message;
		this.status = status;
		time = Instant.now();
	}

	public Instant getTime() {
		return time;
	}

	public String getMessage() {
		return message;
	}

	public Integer getStatus() {
		return status;
	}

}
