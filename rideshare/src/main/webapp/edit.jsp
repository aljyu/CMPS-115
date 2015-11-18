
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
      .order("-email")
      .list();
   %>
   <% for (int i = 0; i < rides.size(); ++i){
         // if users don't match remove ride 
      } %>
   <!-- replace with rides.get(i) where i is from the url -->
<%   Ride chosen = rides.get(0); %>
<% 
   pageContext.setAttribute("key_content", chosen.id);
   pageContext.setAttribute("name_content", chosen.name);
   pageContext.setAttribute("email_content", chosen.email);
   pageContext.setAttribute("origin_content", chosen.origin);
   pageContext.setAttribute("destination_content", chosen.destination);
   pageContext.setAttribute("depart_content", chosen.depart);
   pageContext.setAttribute("arrive_content", chosen.arrive);
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
      <label for = "seats"> Seats Avaliable: </label>
      <input id = "seats" type = "text" name = "seats"><br>
      <label for "drive"> Are you driving or riding? </label><br>
      <label for "drive"> Driving </label>
      <input type="radio" name="drive" value="true" checked>
      <label for "drive"> Riding </label>
      <input type="radio" name="drive" value="false"> <br>
      <br>
      <input type="submit" name="Submit" value=Submit>
   </form>

</body>
</html>
