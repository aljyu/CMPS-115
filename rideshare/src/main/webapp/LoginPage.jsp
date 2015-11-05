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
	<head><center>
		<link type = "text/css" rel = "stylesheet" href = "LoginPage.css"/>
		
		<h1>Returning User? Sign-In Here!</h1>
		<meta name="google-signin-scope" content="profile email">
		<meta name="google-signin-client_id" content="504928432041-6ivaiei584ib5vueh5hjult3o2v9o49v.apps.googleusercontent.com">
		<script src="https://apis.google.com/js/platform.js" async defer></script>
		
		<!-- Google Sign-In Button -->
		<div class="g-signin2" data-onsuccess="onSignIn" data-theme="dark"></div>
		<script>
		  function onSignIn(googleUser) {
			// Useful data for your client-side scripts:
			var profile = googleUser.getBasicProfile();
			console.log("ID: " + profile.getId()); // Don't send this directly to your server!
			console.log("Name: " + profile.getName());
			console.log("Image URL: " + profile.getImageUrl());
			console.log("Email: " + profile.getEmail());

			// The ID token you need to pass to your backend:
			var id_token = googleUser.getAuthResponse().id_token;
			console.log("ID Token: " + id_token);
		  };
		</script>
		
		<title>Log-In Page</title>
	</center></head>
	<body><center>
		<h1>New User? Register <a href = "Register.jsp">Here</a>!</h1>
	</center></body>
</html>