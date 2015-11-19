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

<html style = "background-color:lightblue">
	<head><center>
		<link type = "text/css" rel = "stylesheet" href = "LoginPage.css"/>
		<title>Log-In Page</title>
		
		<h1>Sign-In Here!</h1>
		<!--<meta name="google-signin-scope" content="profile email">
		<meta name="google-signin-client_id" content="504928432041-6ivaiei584ib5vueh5hjult3o2v9o49v.apps.googleusercontent.com">
		<script src="https://apis.google.com/js/platform.js" async defer></script>-->
	</center></head>
	<body><center>
		<%
		   	String login_email = request.getParameter("login_email");
		    if (login_email == null) {
		        login_email = "default";
		    }
		    pageContext.setAttribute("login_email", login_email);
		    UserService userService = UserServiceFactory.getUserService();
		    User user = userService.getCurrentUser();
		    /*String user_email = userService.getCurrentUser().getEmail();
		    if (user_email != null) {
		        pageContext.setAttribute("user_email", user_email);*/
		    if (user != null) {
		        pageContext.setAttribute("user", user);
		%>
		<p>Hello, ${fn:escapeXml(user.nickname)}! (You can
		<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>
		<li class = "temp"><a href="rideshare.jsp">Click here to continue after signing in</a></li>
		<% 
		   } else {
		%>
		<p> Hello!
			<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</p>
		<% }%>


		<!-- Google Sign-In Button -->
		<!--<div class="g-signin2" data-onsuccess="onSignIn" data-theme="dark"></div>
		<script>
			var login_success = Boolean(false);
			var profile;
			function onSignIn(googleUser) {
				var profile = googleUser.getBasicProfile();
				login_success = Boolean(true);
			};
		</script>
		</br></br>
		
		<script>
			function Redirect() {
				if (login_success) {
					window.location.assign("GetEmail.jsp");
				}
			};
		</script>
		<button type = "button" onclick = "Redirect()">Click here to continue after signing in</button>-->
	</center></body>
</html>