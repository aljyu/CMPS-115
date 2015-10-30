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
<!--head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>
</head-->

<body>
  <!--%
   List<Ride> rides = ObjectifyService.ofy()
      .load()
      .type(Ride.class)
      .list();
%>
     <!--% Ridelist ridelist = new Ridelist(rides); %>

    <!--%  String email = request.getParameter("email"); %>
    <!--%  String origin = request.getParameter("origin"); %>
    <!--%  String dest = request.getParameter("dest"); %-->
    <%  String depart = req.getParameter("depart"); %>
     <!--% String arrive = request.getParameter("arrive"); %-->
	 
      
     <!--% List<Ride> returnlist = ridelist.sortDepart(5.0, depart); %>
    

   <!--% if (!returnlist.isEmpty()) { %>
	  <p> The Current Rides </p>
      <!--% for (Ride ride : returnlist) { %>
         <!--% pageContext.setAttribute("ride_email", ride.email); %>
         <!--% pageContext.setAttribute("ride_origin", ride.origin); %>
         <!--% pageContext.setAttribute("ride_dest", ride.destination); %-->
         <% pageContext.setAttribute("ride_depart", depart); %>
         <!--b>${fn:escapeXml(ride_email)}</b>
         <b>${fn:escapeXml(ride_origin)}</b>
         <b>${fn:escapeXml(ride_dest)}</b-->
         <b>${fn:escapeXml(ride_depart)}</b>
		 <b>HELLO</b>
		 
      <!--% } %>
  <!--% } else { %>
      <p> There are no current rides. </p>
   <!--% } %-->
   
</body>
</html>