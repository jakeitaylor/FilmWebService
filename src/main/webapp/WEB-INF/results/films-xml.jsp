<%@page trimDirectiveWhitespaces="true" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Film" %>
<%@ page import="model.Films" %>
<%@ page import="javax.xml.bind.JAXBContext" %>
<%@ page import="javax.xml.bind.JAXBException" %>
<%@ page import="javax.xml.bind.Marshaller" %>
<%@ page import="javax.xml.bind.Unmarshaller" %>

<%

	ArrayList<Film> filmList = (ArrayList) request.getAttribute("films");
	Films films = new Films();
	films.setFilmList(filmList);

	JAXBContext context = JAXBContext.newInstance(Films.class);
    Marshaller m = context.createMarshaller();
    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

    m.marshal(films, System.out);

%>