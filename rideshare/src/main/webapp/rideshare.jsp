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
  
   <font size ="26">
<p> Team #NoLife </p>
 </font>
<p>
<div class = "menu">
<center>
<font size ="0">
<form method="link" action="/submissionpage.jsp" >
<input type="submit" value="Add a Ride?">
</form>
<form method="link" action="/searchingpage.jsp">
<input type="submit" value="Or Search for a Ride?">
</font>

</p>
</center>
</body>
</html>

