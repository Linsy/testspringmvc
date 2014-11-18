package demo;


import org.lightadmin.api.config.LightAdmin;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        LightAdmin.configure(servletContext)
                .basePackage("demo.administration")
                .baseUrl("/admin")
                .security(false);

        super.onStartup(servletContext);
    }


	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

		return application.sources(Application.class);
	}

//    @Override
//    public void onStartup(ServletContext container) {
//        ServletRegistration.Dynamic registration = container.addServlet("dispatcher", new DispatcherServlet());
//        registration.setLoadOnStartup(1);
//        registration.addMapping("/example/*");
//    }

//    @Override
//    public void onStartup(ServletContext servletContext) throws javax.servlet.ServletException {
//        servletContext.addListener(ResteasyBootstrap.class.getName());
//
//    }


}
