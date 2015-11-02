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
			<div class = "menu">
			<div class="g-signin2" data-onsuccess="onSignIn" data-theme="dark"></div>
			<font size ="0">
			<form method="link" action="/submissionpage.jsp" >
				<input type="submit" value="Add a Ride?">
			</form>
			<form method="link" action="/searchpage.jsp">
				<input type="submit" value="Or Search for a Ride?">
			</form>
			</font>
		</p>
	</center></body>
</html>

