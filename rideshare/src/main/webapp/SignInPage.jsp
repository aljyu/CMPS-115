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
	<head>
		<meta name="google-signin-scope" content="profile email">
		<meta name="google-signin-client_id" content="504928432041-6ivaiei584ib5vueh5hjult3o2v9o49v.apps.googleusercontent.com">
		<script src="https://apis.google.com/js/platform.js" async defer></script>
	</head>
	<body>
		<%
			String email_login = request.getParameter("login_email");
			session.setAttribute( "user_email", email_login );
		%>
		
		<!-- Google Sign-In Button -->
		<div class="g-signin2" data-onsuccess="onSignIn" data-theme="dark"></div>
		<script>
			var login_success = Boolean(false);
			var correct_email = Boolean(false);
			function onSignIn(googleUser) {
				var profile = googleUser.getBasicProfile();
				login_success = Boolean(true);
				var test_email = <% email_login %>;
				if (profile.getEmail() === test_email) {
					console.log("Hi");
					correct_email = Boolean(true);
				}
			};
		</script>
		
		<script>
			function Redirect() {
				if (login_success /*&& correct_email*/) {
					window.location.assign("rideshare.jsp");
				}
			};
		</script>
		<button type = "button" onclick = "Redirect()">Click here to continue after signing in</button>
	</body>
</html>