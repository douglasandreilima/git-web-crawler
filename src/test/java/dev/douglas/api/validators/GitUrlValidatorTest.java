package dev.douglas.api.validators;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.Test;

class GitUrlValidatorTest {

	@Test
	void test() throws MalformedURLException {
		final GitUrlValidator validator = new GitUrlValidator();
		assertTrue(validator.test(new URL("https://github.com/douglasandreilima/git-web-crawler")));
	}

}
