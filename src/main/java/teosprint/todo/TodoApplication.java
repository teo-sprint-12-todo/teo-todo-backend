package teosprint.todo;

import org.apache.catalina.connector.Connector;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@CrossOrigin("*")
@SpringBootApplication
public class TodoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(@NotNull CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("https://teo-sprint-12-todo.github.io/teo-todo-frontend", "http://localhost:3000", "https://localhost:3000", "**", "https://teo-sprint-12-todo.github.io/teo-todo-frontend:3000", "https://teo-sprint-12-todo.github.io");
			}
		};
	}

//	@Bean
//	public ServletWebServerFactory servletContainer() {
//		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
//		tomcat.addAdditionalTomcatConnectors(createStandardConnector());
//		return tomcat;
//	}
//
//	private Connector createStandardConnector() {
//		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//		connector.setPort(0);
//		return connector;
//	}

}
