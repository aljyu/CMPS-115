<!DOCTYPE HTML>
<%
   String email = request.getParameter("login_email");
   session.setAttribute( "user_email", email );
   System.out.println("Your email is: " + email);
%>
<HTML>
	<head></head>
	<BODY>
		<%
		   String email_login = request.getParameter("login_email");
		   session.setAttribute( "user_email", email_login );
		   System.out.println("Your email is: " + email_login);
		%>
		<a href = "ProfilePage.jsp">Contine</a>
	</BODY>
</HTML>