<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<%@ page import="com.rideshare.Ride" %>
<%@ page import="com.googlecode.objectify.Key" %>
<%@ page import="com.googlecode.objectify.ObjectifyService" %>

<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html style = "background-color:lightblue">
	<head>
		<link type = "text/css" rel = "stylesheet" href = "Register.css"/>
		<title>Profile Page</title>
	</head>
	<body><center>
		<h1>My Profile Page</h1>
		<p>
			Full Name: <!-- Output specific name -->
			</br>
			Username: <!-- Output specific username -->
			</br>
			Email: <!-- Output specific email -->
		</p>
		</br></br>
		<h3>My Rides</h3>
		<p>
			<!-- Output rides -->
		</p>
	</center></body>
</html>