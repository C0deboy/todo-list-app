package todolist.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class SpringWebInitializer implements WebApplicationInitializer {
  @Override
  public void onStartup(ServletContext container) {
    AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
    rootContext.register(SpringRootConfiguration.class);

    container.addListener(new ContextLoaderListener(rootContext));

    ServletRegistration.Dynamic dispatcher = container
        .addServlet("dispatcher", new DispatcherServlet(rootContext));
    dispatcher.setLoadOnStartup(1);
    dispatcher.addMapping("/");

    CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
    encodingFilter.setEncoding("UTF-8");
    encodingFilter.setForceEncoding(true);

    container.addFilter("encoding-filter", encodingFilter)
        .addMappingForUrlPatterns(null, true, "/*");

    DelegatingFilterProxy springSecurityFilterChain = new DelegatingFilterProxy();

    container.addFilter("springSecurityFilterChain", springSecurityFilterChain)
        .addMappingForUrlPatterns(null, false, "/*");
  }
}
