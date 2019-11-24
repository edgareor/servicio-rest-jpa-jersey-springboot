package paquete.spring;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import paquete.spring.service.ServicioRest;

@Configuration 
@ApplicationPath("/servlet/v1")
public class JerseyConfiguration extends ResourceConfig {
 
    public JerseyConfiguration() {
        register(ServicioRest.class);
 
    }

}
