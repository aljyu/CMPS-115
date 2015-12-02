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
      <p> Something went wrong when you tried to submit your ride. Please press the back button and try again. </p>
      <p> Make sure that none of the following are blank: Full Name, Email, Origin, Destination, Departure Time, Arrival Time, Seats Available. </p>
      <p> Your Origin and Destination should be as specific addresses as possible. </p>
      <p> In addition, make sure that your times are entered in 24-hour time: Enter 11:00 for 11:00 AM, and 15:00 for 3:00 PM. </p> 

</body>
</html>

