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
		<link type = "text/css" rel = "stylesheet" href = "LoginPage.css"/>
		<title>Create a New Account</title>
	</head>
	<body><center>
		<h1>Create a New Account</h1>
		<p>
			<form action = "rideshare.jsp" method = "post">
				</br></br>
				<input placeholder = "Full Name" name = "name" type = "text">
				</br></br>
				<input placeholder = "Email" name = "email" type = "text">
				</br></br>
				<input placeholder = "Username" name = "user" type = "text">
				</br></br>
				<input placeholder = "Password" name = "pass" type = "password">
				</br></br>
				<input type = "submit">
			</form>
		</p>
	</center></body>
</html>