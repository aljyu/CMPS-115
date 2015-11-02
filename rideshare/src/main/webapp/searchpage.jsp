<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<%@ page import="com.rideshare.Ride" %>
<%@ page import="com.rideshare.Ridelist"%>
<%@ page import="com.googlecode.objectify.Key" %>
<%@ page import="com.googlecode.objectify.ObjectifyService" %>

<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>
</head>

<body>
  <%
  List<Ride> rides = ObjectifyService.ofy()
      .load()
      .type(Ride.class)
      .order("-depart")
      .list();
  %>

	<% Ridelist rl = new Ridelist(rides); %>
	<!--% pageContext.setAttribute("geo", rl.testFindDist().toString()); %-->
	<!--b>${fn:escapeXml(geo)}</b-->
  <form action="/search" method="post"> 
      <label for="email"> Email: </label>
      <input id = "email" type="text" name="email"><br>
      <label for="origin"> Origin: </label> 
      <input id =" origin" type="text" name = "origin"><br>
      <label for="dest"> Destination: </label>
      <input id= "dest" type ="text" name = "dest">
      <label for="depart"> Departure Time: </label>
      <input id="depart" type = "text" name = "depart">
      <label for="arrive"> Arrival Time: </label>
      <input id="arrive" type = "text" name = "arrive">
	  <label for "drive"> Are you looking for a driver or a rider?</label><br>
      <label for "drive"> Driver </label>
      <input type="radio" name="drive" value="true" checked>
      <label for "drive"> Rider </label>
      <input type="radio" name="drive" value="false"> <br>
      <br>
      <br>
	  Searching for a weekly ride? Check the day(s) that apply: <br>
    <br>
      <br>
	  <input type="checkbox" name="weekday" value="su"/>Sunday<br>
      <input type="checkbox" name="weekday" value="mo"/>Monday<br>
      <input type="checkbox" name="weekday" value="tu"/>Tuesday<br>
     <input type="checkbox" name="weekday" value="we"/>Wednesday<br>
      <input type="checkbox" name="weekday" value="th"/>Thursday<br>
      <input type="checkbox" name="weekday" value="fr"/>Friday<br>
      <input type="checkbox" name="weekday" value="sa"/>Saturday<br>
	  
      <input type="submit" name="Submit" value=Submit>
   </form>
   <!--% String departs = pageContext.getAttribute("depart");%-->
   <!--% System.out.println(departs);%-->
   <!--% rides = rl.sortDepart(5,pageContext.getAttribute("depart"));%-->
   <!--% if (!rides.isEmpty()) { %>
	  <p> The Current Rides </p>
      <!--% for (Ride ride : rides) { %>
         <!--% pageContext.setAttribute("ride_email", ride.email); %>
         <!--% pageContext.setAttribute("ride_origin", ride.origin); %>
         <!--% pageContext.setAttribute("ride_dest", ride.destination); %>
         <!--% pageContext.setAttribute("ride_depart", ride.depart); %>
         <p>
		 <b>${fn:escapeXml(ride_email)}</b>
         <b>${fn:escapeXml(ride_origin)}</b>
         <b>${fn:escapeXml(ride_dest)}</b>
         <b>${fn:escapeXml(ride_depart)}</b>
		 </p>
      <!--% } %>
  <!--% } else { %>
      <p> There are no current rides. </p>
   <!--% } %-->
   
</body>
</html>
