<!DOCTYPE HTML>
<HTML>
	<head></head>
	<BODY><center>
		<%
		   String email_login = request.getParameter("login_email");
		   session.setAttribute( "user_email", email_login );
		   System.out.println("Your email is: " + email_login);
		%>
		<a href = "ProfilePage.jsp">Contine</a>
	</center></BODY>
</HTML>