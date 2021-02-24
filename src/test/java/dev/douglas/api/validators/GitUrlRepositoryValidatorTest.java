package dev.douglas.api.validators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class GitUrlRepositoryValidatorTest {

	static GitUrlRepositoryValidator validator;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		validator = new GitUrlRepositoryValidator();
	}

	@Test
	void testValidGitHubRepository1() throws MalformedURLException {
		assertTrue(validator.test(new URL("https://github.com/douglasandreilima/git-web-crawler")));
	}

	@Test
	void testValidGitHubRepository2() throws MalformedURLException {
		assertTrue(validator.test(new URL("https://github.com/douglasandreilima/data-structure-js")));
	}

	@Test
	void testInValidGitHubRepository1() throws MalformedURLException {
		assertFalse(validator.test(new URL("https://github.com/douglasandreilima/")));
	}

	@Test
	void testInValidGitHubRepository2() throws MalformedURLException {
		assertFalse(validator.test(new URL("https://github.com/douglasandreilima")));
	}

	@Test
	void testInValidGitHubRepository3() throws MalformedURLException {
		assertFalse(validator.test(new URL("https://github.com/springfox/")));
	}

}
