package com.lovelyn.course_advizor.session;

import com.lovelyn.course_advizor.ResponseDTO;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("session")
@Produces(MediaType.APPLICATION_JSON)
public class SessionController {

  @Autowired
  @Setter
  private SessionRepository sessionRepository;

  @Autowired
  @Setter
  private ModelMapper modelMapper;


  @GET
  public Response getList() {

    final List<Session> sessions = sessionRepository.findAll();

    final List<SessionDTO> sessionDTOList = sessions.stream()
      .map(session -> modelMapper.map(session, SessionDTO.class))
      .toList();

    return Response
      .ok(ResponseDTO.success("Sessions fetched", sessionDTOList))
      .build();
  }

}
