package dev.douglas.api.validators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class GitUrlFileValidatorTest {

	static GitUrlPossibleFileValidator gitUrlFileValidator;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		gitUrlFileValidator = new GitUrlPossibleFileValidator();
	}

	@Test
	void testValidFile1() {
		assertTrue(gitUrlFileValidator.test("/douglasandreilima/data-structure-js/blob/main/README.md"));
	}

	@Test
	void testValidFile2() {
		assertTrue(gitUrlFileValidator.test("/jbloch/effective-java-3e-source-code/blob/master/.gitignore"));
	}

	@Test
	void testInValidFile1() {
		assertFalse(gitUrlFileValidator.test("/jbloch/effective-java-3e-source-code"));
	}

	@Test
	void testInValidFile2() {
		assertFalse(gitUrlFileValidator.test("/douglasandreilima/data-structure-js/"));
	}

}
