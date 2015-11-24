<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<%@ page import="com.rideshare.Ride" %>
<%@ page import="com.rideshare.Ridelist" %>
<%@ page import="com.rideshare.Keys" %>
<%@ page import="com.googlecode.objectify.Key" %>
<%@ page import="com.googlecode.objectify.ObjectifyService" %>

<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html style = "background-color:lightblue">
	<head>
		<title>Profile Page</title>
	</head>
	<body>
		<a href = "rideshare.jsp"><center>Click here to go back to the main page</center></a>
		<% 
			List<Ride> rides = ObjectifyService.ofy()
				.load()
				.type(Ride.class)
				.order("-email")
				.list();
		%>
		<!-- profile.getEmail() make this a global variable -->
		<center>
		<h1>My Profile Page</h1>
		<p>
			<% UserService userService = UserServiceFactory.getUserService(); %>
			<% User user = userService.getCurrentUser(); %>
		        <% String user_email = userService.getCurrentUser().getEmail(); %>
			<% int top = rides.size(); %>
			<% List <Ride> finalrides = new ArrayList<Ride>(); %>
			<% for (int i = 0; i < top; ++i){ %>
			<%    if(rides.get(i).email.equals(user_email)){ %>
			<%       finalrides.add(rides.get(i)); %>
                        <%    } %>
			<% } %>
		    <% List<Keys> keys = ObjectifyService.ofy()
		      .load()
		      .type(Keys.class)
		      .list();
		    %>
		      
			<% String browkey = null; %>
			<% for(int i = 0; i < keys.size(); ++i){ %>
			<%    if(keys.get(i).type.compareToIgnoreCase("Browser") == 0) browkey = keys.get(i).value; %>
			<% } %>
		<h1>My Rides</h1>
			<!-- Output rides -->
		<% if (!finalrides.isEmpty()) { %>
                        <% String originpt = finalrides.get(finalrides.size() - 1).origin; %>
                        <% String destpt = finalrides.get(finalrides.size() - 1).destination; %>
                        <% originpt = originpt.substring(1, originpt.length() - 2); %>
                        <% destpt = destpt.substring(1, destpt.length() - 2); %>
                        <% String testUrl = "https://www.google.com/maps/embed/v1/directions?key=" + browkey + "&origin="+ originpt + "&destination=" + destpt; %>
                        <iframe
                          id = "map"
                          width="600"
                          height="450"
                          frameborder="0" style="border:0"
                          src="<%=testUrl%>" allowfullscreen>
                        </iframe>
		<%	for(Ride ride : finalrides) { %>
		<%		pageContext.setAttribute("ride_email", ride.email); %>
		<% 		pageContext.setAttribute("ride_origin", ride.origin); %>
		<%		pageContext.setAttribute("ride_dest", ride.destination); %>
		<%		pageContext.setAttribute("ride_depart", ride.depart); %>
				<b>${fn:escapeXml(ride_email)}</b>
				<b>${fn:escapeXml(ride_origin)}</b>
				<b>${fn:escapeXml(ride_dest)}</b>
				<b>${fn:escapeXml(ride_depart)}</b>         
                                <form class="button" action="/edit.jsp" method="post">
                                <input type="hidden" value = "<%=ride.id %>" name="key"><br>
        	     		<input type="submit" value="Edit!">
      				</form>
      			        <form class="button" action="/delete.jsp" method="post">
         			<input type="hidden" value= <%=ride.id %> name="key"><br>
           			<input type="submit" value = "Delete!">
      				</form>	
	        	<% } %>			
		<% } else { %> 
			<p>There are no current rides</p>
		<% } %> 
	</center></body>
</html>
