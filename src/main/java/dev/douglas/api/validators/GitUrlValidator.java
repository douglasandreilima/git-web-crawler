package dev.douglas.api.validators;

import java.net.URL;
import java.util.function.Predicate;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GitUrlValidator implements Predicate<URL> {

	@Override
	public boolean test(final URL url) {
		return url.getHost().contains("github");
	}

}
