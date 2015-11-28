
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<%@ page import="com.rideshare.Ride" %>
<%@ page import="com.googlecode.objectify.Key" %>
<%@ page import="com.googlecode.objectify.ObjectifyService" %>

<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.ArrayList" %>

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
      .order("-id")
      .list();
%>
<%   Ride chosen = null; %>
   <!-- replace with rides.get(i) where i is from the url -->
<% String idfromurl = request.getParameter("key"); %>
<% long id = Long.parseLong(idfromurl); %> 
<% 
   for(int i = 0; i < rides.size(); ++i){
      if(rides.get(i).id == id){
         chosen = rides.get(i);
         break;
      }
   }
%>
<% 
   pageContext.setAttribute("key_content", chosen.id);
   pageContext.setAttribute("name_content", chosen.name);
   pageContext.setAttribute("email_content", chosen.email);
   pageContext.setAttribute("origin_content", chosen.origin);
   pageContext.setAttribute("destination_content", chosen.destination);
   pageContext.setAttribute("depart_content", chosen.depart);
   pageContext.setAttribute("arrive_content", chosen.arrive);
   pageContext.setAttribute("seat_content", Integer.toString(chosen.seats));
%>
   <form action="/edit" method="post"> 
      <p> Please submit your ride here: </p>
      <input type="hidden" name="Key" value="${fn:escapeXml(key_content)}">
      <label for="name"> Full Name: </label>
      <input id="name" type="text" name="name" value = "${fn:escapeXml(name_content)}"><br>
      <label for="email"> Email: </label>
      <input id = "email" type="text" value="${fn:escapeXml(email_content)}" name="email"><br>
      <label for="origin"> Origin: </label> 
      <input id =" origin" type="text" value="${fn:escapeXml(origin_content)}" name = "origin"><br>
      <label for="dest"> Destination: </label>
      <input id= "dest" type ="text" value = "${fn:escapeXml(destination_content)}" name = "dest"><br>
      <label for="depart"> Departure Time: </label>
      <input id="depart" type ="text" value = "${fn:escapeXml(depart_content)}"name= "depart"><br>
      <label for "arrive"> Arrival Time: </label>
      <input id = "arrive" type ="text" value = "${fn:escapeXml(arrive_content)}"name = "arrive"><br>
      <label for "date"> Date: (mm/dd/yyyy) </label>
      <input id = "date" type="text" name="date"><br>
      <label for = "seats"> Seats Avaliable: </label>
      <input id = "seats" type = "text" name = "seats" value="${fn:escapeXml(seat_content)}"><br>
      <label for "drive"> Are you driving or riding? </label><br>
      <label for "drive"> Driving </label>
      <input type="radio" name="drive" value="true">
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
