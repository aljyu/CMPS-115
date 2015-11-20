<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<%@ page import="com.rideshare.Ride" %>
<%@ page import="com.googlecode.objectify.Key" %>
<%@ page import="com.googlecode.objectify.ObjectifyService" %>

<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript" src="conversion.js"></script>
<html>
<head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>
</head>
<% 
   List<Ride> rides = ObjectifyService.ofy()
      .load()
      .type(Ride.class)
      .order("-id")
      .list();
%>
<%   Ride chosen = null; %>

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
<% pageContext.setAttribute("key_content", chosen.id); %>
<form action="/delete" method="post">
   Please confirm you want to delete this ride(This action cannot be undone)
   <input type="hidden" name="Key" value="${fn:escapeXml(key_content)}">
   <input type="submit" name="submit" value= Delete>
