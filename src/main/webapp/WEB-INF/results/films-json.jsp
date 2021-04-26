<%@page trimDirectiveWhitespaces="true" %>
<%@page import="com.google.gson.Gson" %>

<% 
	
	Gson gson = new Gson();
	String filmsJson = gson.toJson(request.getAttribute("films"));
	out.println(filmsJson);
	
%>