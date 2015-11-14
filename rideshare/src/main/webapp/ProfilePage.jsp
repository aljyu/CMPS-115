<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<%@ page import="com.rideshare.Ride" %>
<%@ page import="com.rideshare.Ridelist" %>
<%@ page import="com.googlecode.objectify.Key" %>
<%@ page import="com.googlecode.objectify.ObjectifyService" %>

<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html style = "background-color:lightblue">
	<head>
		<link type = "text/css" rel = "stylesheet" href = "Register.css"/>
		<title>Profile Page</title>
		<meta name="google-signin-scope" content="profile email">
    	<meta name="google-signin-client_id" content="YOUR_CLIENT_ID.apps.googleusercontent.com">
    	<script src="https://apis.google.com/js/platform.js" async defer></script>
	</head>
	<body>
		<a href = "rideshare.jsp">Click here to go back to the main page</a>
		<% 
			List<Ride> rides = ObjectifyService.ofy()
				.load()
				.type(Ride.class)
				.order("-depart")
				.list();
		%>
		<!-- profile.getEmail() make this a global variable -->
		<center>
		<h1>My Profile Page</h1>
		<p>
			Welcome back, <%= session.getAttribute("user_email") %>
			</br>
		</p>
		</br></br>
		<h1>My Rides</h1>
			<!-- Output rides -->
		<% if (!rides.isEmpty()) { %>
		<%		for(Ride ride : rides) { %>
		<%			pageContext.setAttribute("ride_email", ride.email); %>
		<% 			pageContext.setAttribute("ride_origin", ride.origin); %>
		<%			pageContext.setAttribute("ride_dest", ride.destination); %>
		<%			pageContext.setAttribute("ride_depart", ride.depart); %>
					<b>${fn:escapeXml(ride_email)}</b>
					<b>${fn:escapeXml(ride_origin)}</b>
					<b>${fn:escapeXml(ride_dest)}</b>
					<b>${fn:escapeXml(ride_depart)}</b>	
		<% } %>			
		<% } else { %> 
			<p>There are no current rides</p>
		<% } %> 		
	</center></body>
</html>