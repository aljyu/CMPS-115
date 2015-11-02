<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<%@ page import="com.rideshare.Ride" %>
<%@ page import="com.googlecode.objectify.Key" %>
<%@ page import="com.googlecode.objectify.ObjectifyService" %>

<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript" src="conversion.js"></script>
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
  <form action="/sign" method="post"> 
      <p> Please submit your ride here: </p>

      <label for="name"> Full Name: </label>
      <input id="name" type="text" name="name"><br>
      <label for="email"> Email: </label>
      <input id = "email" type="text" name="email"><br>
      <label for="origin"> Origin: </label> 
      <input id =" origin" type="text" name = "origin"><br>
      <label for="dest"> Destination: </label>
      <input id= "dest" type ="text" name = "dest"><br>
      <label for="depart"> Departure Time: </label>
      <input id="depart" type ="text" name= "depart"><br>
      <label for "arrive"> Arrival Time: </label>
      <input id = "arrive" type ="text" name = "arrive"><br>
      <label for "drive"> Are you driving or riding? </label><br>
      <label for "drive"> Driving </label>
      <input type="radio" name="drive" value="true" checked>
      <label for "drive"> Riding </label>
      <input type="radio" name="drive" value="false"> <br>
      <br>
      <p> Offering or looking for a weekly ride? Check the day(s) that apply: </p>
      <p><input type="checkbox" name="weekday" value="su"/>Sunday</p>
      <p><input type="checkbox" name="weekday" value="mo"/>Monday</p>
      <p><input type="checkbox" name="weekday" value="tu"/>Tuesday</p>
      <p><input type="checkbox" name="weekday" value="we"/>Wednesday</p>
      <p><input type="checkbox" name="weekday" value="th"/>Thursday</p>
      <p><input type="checkbox" name="weekday" value="fr"/>Friday</p>
      <p><input type="checkbox" name="weekday" value="sa"/>Saturday</p>
      <input type="submit" name="Submit" value=Submit>
   </form>

</body>
</html>

