package dev.douglas.api;

import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;

// @formatter:off
@OpenAPIDefinition(info = @Info(title = "Git Web Crawler API", version = "1.0.0", contact = @Contact(name = "Douglas de Lima", email = "douglasandreilima@gmail.com")))
//@formatter:on
public class ApiGitCrawlerApplication extends Application {

}
