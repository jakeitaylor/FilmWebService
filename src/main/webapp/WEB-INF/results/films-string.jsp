<%@page trimDirectiveWhitespaces="true" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Film" %>

<%

	ArrayList<Film> filmList = (ArrayList) request.getAttribute("films");
	out.println(filmList);

%>