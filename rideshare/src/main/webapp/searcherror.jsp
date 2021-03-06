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
      <p>  Something went wrong when you tried to search for a ride. Please press the back button and try again. </p>
      <br></br>
      <p>  Your address(es) need to be valid and your Departure and Arrival times must be in 24-hour time (1PM = 13:00), with format 14 or 14:00, and no AM or PM. </p>
      <br></br>
      <p>  If you enter Origin or Destination Radius(i), they should only have numbers. All distances are in kilometers. </p>
      

</body>
</html>