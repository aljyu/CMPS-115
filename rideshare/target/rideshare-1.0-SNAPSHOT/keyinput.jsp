<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<%@ page import="com.rideshare.Keys" %>
<%@ page import="com.googlecode.objectify.Key" %>
<%@ page import="com.googlecode.objectify.ObjectifyService" %>

<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html style = "background-color:lightblue">
   <head>
      <link type = "text/css" rel = "stylesheet" href = "Register.css"/>
      <title>Enter a key</title>
   </head>
   <body><center>
      <h1>Create a key</h1>
      <p>
      <form action = "/key" method = "post">
         <input placeholder = "Please input the keys type (ex. server)" type = "text" name = "type"> <br></br>
         <input placeholder = "Please input the keys value " type = "text" name = "value"> <br></br>
         <input type="submit" value="Submit">  	
      </form>
      </p>
   </center></body>
</html>
