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
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>
</head>

<body>
  <%
  List<Ride> rides = ObjectifyService.ofy()
      .load()
      .type(Ride.class)
      .order("-riderEmail")
      .limit(5)
      .list();
  %>
  <% if (rides.isEmpty()) { %>
      <p> There are no current rides. </p>
  <% } else { %>
      <p> The Current Rides </p>
      <% for (Ride ride : rides) { %>
         <% pageContext.setAttribute("ride_riderEmail", ride.riderEmail); %>
         <% pageContext.setAttribute("ride_startLocation", ride.startLocation); %>
         <% pageContext.setAttribute("ride_endLocation", ride.endLocation); %>
         <% pageContext.setAttribute("ride_departureTime", ride.departureTime); %>
         <b>${fn:escapeXml(ride_riderEmail)}</b>
         <b>${fn:escapeXml(ride_startLocation)}</b>
         <b>${fn:escapeXml(ride_endLocation)}</b>
         <b>${fn:escapeXml(ride_departureTime)}</b>
      <% } %>
   <% } %>
   <form action="/sign" method="post"> 
      <label for="rideEmail"> Email: </label>
      <input id = "riderEmail" type="text" name="riderEmail"><br>
      <label for="startLocation"> Origin: </label> 
      <input id =" startLocation" type="text" name = "startLocation"><br>
      <label for="endLocation"> Destination: </label>
      <input id= "endLocation" type ="text" name = "endLocation">
      <label for="time"> Time: </label>
      <input id="time" type = "text" name = "time">
      <input type="submit" name="Submit" value=Submit/>
   </form>
</body>
</html>
