package com.lovelyn.course_advizor;

import javax.ws.rs.ApplicationPath;

import com.lovelyn.course_advizor.course_adviser.CourseAdviserController;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.stereotype.Component;

@Component
@ApplicationPath("api")
public class JerseyConfig extends ResourceConfig {
  
  public JerseyConfig() {
    packages("com.lovelyn.course_advizor");
    property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
  }

}
