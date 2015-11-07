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
		<title>Log-In Page</title>
		
		<h1>Sign-In Here!</h1>
		<meta name="google-signin-scope" content="profile email">
		<meta name="google-signin-client_id" content="504928432041-6ivaiei584ib5vueh5hjult3o2v9o49v.apps.googleusercontent.com">
		<script src="https://apis.google.com/js/platform.js" async defer></script>
		
		<!-- Google Sign-In Button -->
			<div class="g-signin2" data-onsuccess="onSignIn" data-theme="dark"></div>
			<script>
				var login_success = "false";
				function onSignIn(googleUser) {
					//Gets all of the basic information from Google
					var profile = googleUser.getBasicProfile();
					login_success = "true";
				};
			</script>
			</br></br>
			
			<script>
				function Redirect() {
					if (login_success === "true") {
						window.location.assign("rideshare.jsp");
					}
				};
			</script>
			
			<button type = "button" onclick = "Redirect()">Click here to continue after signing in</button>
			</script>
	</center></head>
	<body><center>
	</center></body>
</html>