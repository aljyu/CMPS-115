<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.IOException" %>
<%@ page import="javax.servlet.http.HttpServlet" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="javax.servlet.http.HttpServletResponse" %>

<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<%@ page import="com.rideshare.Ride" %>
<%@ page import="com.googlecode.objectify.Key" %>
<%@ page import="com.googlecode.objectify.ObjectifyService" %>

<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
	<head><center>
		<link rel="icon" type="image/png" href= "LittleCaricon.jpeg">
		<!-- Latest compiled and minified CSS -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" integrity="sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ==" crossorigin="anonymous">

		<!-- Optional theme -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css" integrity="sha384-aUGj/X2zp5rLCbBxumKTCw2Z50WgIr1vs/PFN4praOTvYXWlVyh2UtNUU0KAUhAX" crossorigin="anonymous">
		<link type = "text/css" rel = "stylesheet" href = "LoginPage.css"/>
		<title>Log-In Page</title>
		
		<h1><b><font color = "white">WELCOME TO RIDESHARE</font></b></h1>
		<p><b><font color = "white">Better than HitchHiking</font></b></p>
		<br></br>
		<h2><font color = "white">Sign-In Here!</font></h2>
	</center></head>
	<body background = "LoginPageBackground.jpg"><center>
		<%
		   	String login_email = request.getParameter("login_email");
		    if (login_email == null) {
		        login_email = "default";
		    }
		    pageContext.setAttribute("login_email", login_email);
		    UserService userService = UserServiceFactory.getUserService();
		    User user = userService.getCurrentUser();
		    if (user != null) {
		        pageContext.setAttribute("user", user);
		%>
		<p><b>Hello, ${fn:escapeXml(user.nickname)}!  <br> You can either</b> </p>
		<div class = "btn-group btn-justified" role = "group">
		<div class = "btn-group" role = "group">
		<a href="rideshare.jsp" class = "btn btn-success">Continue</a></button>
		</div>
		<div class = "btn-group" role = "group">
		<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>" class = "btn btn-danger">Sign out</a></button>
		</div>
		</div>
		<% 
		   } else {
		%>
		<div class = "btn-group">
			<a href="<%= userService.createLoginURL(request.getRequestURI()) %>" class = "btn btn-success">Sign in</a></button>
			</div>	
		<% }%>



		<!-- Latest compiled and minified JavaScript -->
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js" integrity="sha512-K1qjQ+NcF2TYO/eI3M6v8EiNYZfA95pQumfvcVrTHtwQVDG+aHRqLi/ETn2uB+1JqwYqVG3LIvdm9lj6imS/pQ==" crossorigin="anonymous"></script>

	</center></body>
</html>