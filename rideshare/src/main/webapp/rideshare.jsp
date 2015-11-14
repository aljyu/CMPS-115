<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<%@ page import="com.rideshare.Ride" %>
<%@ page import="com.googlecode.objectify.Key" %>
<%@ page import="com.googlecode.objectify.ObjectifyService" %>

<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
	<head></head>
	<body><center>
		<p>
			<%
				String email_login = request.getParameter("login_email");
				session.setAttribute( "user_email", email_login );
				System.out.println("Your email is: " + email_login);
			%>
			<h1><a href = "ProfilePage.jsp">My Profile Page</a><form action = "ProfilePage.jsp"></form></h1>
			</br></br></br>
			<form method = "link" action = "submissionpage.jsp" >
				<input type = "submit" value = "Add a Ride?">
			</form>
			<form method = "link" action = "searchpage.jsp">
				<input type = "submit" value = "Or Search for a Ride?">
			</form>
		</p>
	</center></body>
</html>

