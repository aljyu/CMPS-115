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
<head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>
</head>
<!--script>
		/*jslint sub: true, maxerr: 50, indent: 4, browser: true */
		(function load(global) {
			document.getElementById("demo").value = global.localStorage.getItem("Email1");
			document.getElementById("demo1").innerHTML = global.localStorage.getItem("Email1");
			document.getElementById("demo2").innerHTML = "Working?";
		}(window));
	</script-->
	<script>
	(function load(global) {
		document.getElementById("email").value = global.localStorage.getItem("Email1");
	}(window));
	
	</script>
<body onload="load();">
  <%
  List<Ride> rides = ObjectifyService.ofy()
      .load()
      .type(Ride.class)
      .order("-depart")
      .list();
  %>

	<% Ridelist rl = new Ridelist(rides); %>
	<!--% pageContext.setAttribute("geo", rl.testFindDist().toString()); %-->
	<!--b>${fn:escapeXml(geo)}</b-->
	<script>
		System.out.println("1");
		//System.out.println(global.localStorage.getItem("Email1");
		//document.getElementById("demo1").innerHTML = global.localStorage.getItem("Email1");
		System.out.println("2");
		document.getElementById("demo2").innerHTML = "Working?";
		window.alert("Working???");
	</script>
	
  <form action="/search" method="post"> 
	
      <label for="email"> Email: </label>
      <input id = "email" type="text" name="email"><br>

      <label for="origin"> Origin: </label> 
      <input id ="origin" type="text" name = "origin"><br>
      <label for="originrad"> Origin Radius: </label>
      <input id ="originrad" type="text" name = "originrad"><br>

      <label for="dest"> Destination: </label>
      <input id= "dest" type ="text" name = "dest"><br>
      <label for="destrad"> Destination Radius: </label>
      <input id= "destrad" type="text" name = "destrad"><br>

      <label for="depart"> Departure Time: </label>
      <input id="depart" type = "text" name = "depart"> <br>
      
      <label for="arrive"> Arrival Time: </label>
      <input id="arrive" type = "text" name = "arrive"> <br>
	  
	  <label for="seats"> Number of Seats Avaliable: </label>
	  <input id="seats" type = "text" name = "seats"> <br><br>
	  
	  Are you looking for a driver or a rider? <br>
      <label for "drive"> Driver </label>
      <input type="radio" name="drive" value="true" checked>
      <label for "drive"> Rider </label>
      <input type="radio" name="drive" value="false"> <br>
      <br>
      <br>
	  
	Searching for a weekly ride? Check the day(s) that apply: <br>
	    <input type="checkbox" name="weekday" value="su"/>Sunday<br>
      <input type="checkbox" name="weekday" value="mo"/>Monday<br>
      <input type="checkbox" name="weekday" value="tu"/>Tuesday<br>
      <input type="checkbox" name="weekday" value="we"/>Wednesday<br>
      <input type="checkbox" name="weekday" value="th"/>Thursday<br>
      <input type="checkbox" name="weekday" value="fr"/>Friday<br>
      <input type="checkbox" name="weekday" value="sa"/>Saturday<br>
	  
	Prioritize: <br>
      <input type="radio" name="prio" value="both" checked/>Origin and Destination<br>
      <input type="radio" name="prio" value="origin"/>Origin<br>
      <input type="radio" name="prio" value="dest"/>Destination<br>
      <input type="submit" name="Submit" value=Submit>
   </form>
   <!--% String departs = (String)pageContext.getAttribute("depart");%-->
   <!--% System.out.println(departs + " HELLO");%-->
   <p id = "demo">Yes</p>
   <p id = "demo1">No</p>
   <p id = "demo2">Maybe so</p>
   
   <% Object tRides = request.getAttribute("resultRides");%>
   <!--% System.out.println("Start");%-->
   <!--% System.out.println(ridessss);%-->
   <% Ridelist lRides = (Ridelist)tRides;%>
   <% List<Ride> listRide = lRides.getInputlist();%>
   <!--jsp:useBean id="resultRides" class="com.rideshare.Ridelist" scope="request" typeSpec/-->
	<!--c:forEach var="ride" items="${names}">
		<c:out value=${ride}/><p>
	</c:forEach-->
   
   <!--% rides = rl.sortDepart(5,pageContext.getAttribute("depart"));%-->
   <% if (!listRide.isEmpty()) { %>
	  <p> The Current Rides </p>
      <% for (Ride ride : listRide) { %>
		 <!--% pageContext.setAttribute("ride_",ride.);%-->
         <% pageContext.setAttribute("ride_email", ride.email); %>
         <% pageContext.setAttribute("ride_origin", ride.origin); %>
         <% pageContext.setAttribute("ride_dest", ride.destination); %>
         <% pageContext.setAttribute("ride_depart", ride.depart); %>
		 <% pageContext.setAttribute("ride_arrive",ride.arrive);%>
		 <% pageContext.setAttribute("ride_seats",ride.seats);%>
         <p>
		 <!--b>${fn:escapeXml(ride_)}</b-->
		 <b>${fn:escapeXml(ride_email)}</b>
         <b>${fn:escapeXml(ride_origin)}</b>
         <b>${fn:escapeXml(ride_dest)}</b>
         <b>${fn:escapeXml(ride_depart)}</b>
		 <b>${fn:escapeXml(ride_arrive)}</b>
		 <% if(ride.drive){ %>
			<b> driver </b>
		 <% } else { %>
			<b> rider </b>
		 <% } %>
		 <% if(ride.su){ %>
			<b> sunday </b>
		 <% } %>
		 <% if(ride.mo){ %>
			<b> monday </b>
		 <% } %>
		 <% if(ride.tu){ %>
			<b> tuesday </b>
		 <% } %>
		 <% if(ride.we){ %>
			<b> wednesday </b>
		 <% } %>
		 <% if(ride.th){ %>
			<b> thursday </b>
		 <% } %>
		 <% if(ride.fr){ %>
			<b> friday </b>
		 <% } %>
		 <% if(ride.sa){ %>
			<b> saturday </b>
		 <% } %>
		 <b> seats: ${fn:escapeXml(ride_seats)}</b>
		 </p>
      <% } %>
  <% } else { %>
      <p> There are no current rides. </p>
   <% } %>
   
</body>
</html>