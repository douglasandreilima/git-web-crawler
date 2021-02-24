package dev.douglas.api.validators;

import java.util.function.Predicate;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GitUrlFileValidator implements Predicate<String> {

	@Override
	public boolean test(final String url) {
		return url.matches("/.*\\.\\w+");
	}

}
