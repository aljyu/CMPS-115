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

<html>
	<head>
		<title>Profile Page</title>
		<link rel="icon" type="image/png" href= "LittleCaricon.jpeg">
    
		<!-- Latest compiled and minified CSS -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" integrity="sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ==" crossorigin="anonymous">

		<!-- Optional theme -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css" integrity="sha384-aUGj/X2zp5rLCbBxumKTCw2Z50WgIr1vs/PFN4praOTvYXWlVyh2UtNUU0KAUhAX" crossorigin="anonymous">
	</head>
	<body background = "LoginPageBackground1.jpg">
		<nav class="navbar navbar-default">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="LoginPage.jsp">Home</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li class="active"><a href="ProfilePage.jsp">Profile <span class="sr-only">(current)</span></a></li>
        <li><a href="searchpage.jsp">Searching a Ride</a></li>
      </ul>
      <ul class="nav navbar-nav navbar">
        <li><a href="submissionpage.jsp">Adding a Ride</a></li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
		<% 
			List<Ride> rides = ObjectifyService.ofy()
				.load()
				.type(Ride.class)
				.order("-email")
				.list();
		%>
		<!-- profile.getEmail() make this a global variable -->
		<center>
		<h2>My Profile Page</h2>
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
		      
			<% String browkey = "null"; %>
			<% for(int i = 0; i < keys.size(); ++i){ %>
			<%    if(keys.get(i).type.compareToIgnoreCase("Browser") == 0) browkey = keys.get(i).value; %>
			<% } %>
		<h2>My Rides</h2>
			<!-- Output rides -->
		<% if (!finalrides.isEmpty()) { %>
                        <% String originpt = finalrides.get(finalrides.size() - 1).origin; %>
                        <% String destpt = finalrides.get(finalrides.size() - 1).destination; %>
                        <% originpt = originpt.substring(1, originpt.length() - 2); %>
                        <% destpt = destpt.substring(1, destpt.length() - 2); %>
                        <% String testUrl = "https://www.google.com/maps/embed/v1/directions?key=" + browkey + "&origin="+ originpt + "&destination=" + destpt; %>
		<%	for(Ride ride : finalrides) { %>
		<%		pageContext.setAttribute("ride_email", ride.email); %>
		<% 		pageContext.setAttribute("ride_origin", ride.origin); %>
		<%		pageContext.setAttribute("ride_dest", ride.destination); %>
                <%		pageContext.setAttribute("ride_date", ride.ridedate.toString().substring(0, 11) + ride.ridedate.toString().substring(24)); %>
		<%		pageContext.setAttribute("ride_depart", ride.depart); %>
			<div class = "container" ng-controller = "AppCtrl">
			  <table class = "table">
			  	<thead> 
			  		<tr>
			  			<th> Email </th>
			  			<th> Origin </th>
			  			<th> Destination </th>
                                                <th> Date </th>
	                                        <th> Departure </th>
						<th> Action </th>
			  		</tr>	
			  	</thead>
			  	<tbody>	
			  	  <tr>	
					<td><b>${fn:escapeXml(ride_email)}</b></td>
					<td><b>${fn:escapeXml(ride_origin)}</b></td>
					<td><b>${fn:escapeXml(ride_dest)}</b></td>
					<td><b>${fn:escapeXml(ride_date)}</b></td>
   					<td><b>${fn:escapeXml(ride_depart)}</b> </td>        
                    <td><form class="button" action="/edit.jsp" method="post">
                       <input type="hidden" value = "<%=ride.id %>" name="key">
        	     	   <button type="submit" class = "btn btn-warning" value="Edit!">Edit </button>
      				</form></td>
      			    <td><form class="button" action="/delete.jsp" method="post">
         				<input type="hidden" value= <%=ride.id %> name="key">
           			    <button type="submit" class = "btn btn-danger" value = "Delete!">Delete </button>
      				</form>	</td>
      			  </tr>	
      			</tbody>
      		  </table>
      		</div>   	  
	        	<% } %>		
			<iframe
                          id = "map"
                          width="600"
                          height="450"
                          frameborder="0" style="border:0"
                          src="<%=testUrl%>" allowfullscreen>
                        </iframe>
                        <br><br>
	
		<% } else { %> 
			<p>There are no current rides</p>
		<% } %> 
	</center>
	<!-- Latest compiled and minified JavaScript -->
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js" integrity="sha512-K1qjQ+NcF2TYO/eI3M6v8EiNYZfA95pQumfvcVrTHtwQVDG+aHRqLi/ETn2uB+1JqwYqVG3LIvdm9lj6imS/pQ==" crossorigin="anonymous"></script>

</body>
</html>
