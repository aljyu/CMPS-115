<%
   String email = request.getParameter("login_email");
   session.setAttribute( "user_email", email );
   System.out.println("Your email is: " + email);
%>
<HTML>
	<head></head>
	<BODY>
		<a href = "ProfilePage.jsp">Contine</a>
	</BODY>
</HTML>