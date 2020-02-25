package home;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ApplicationWarContainer extends SpringBootServletInitializer {

	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		// TODO Auto-generated method stub
		return application.sources(Main.class);
	}
}
