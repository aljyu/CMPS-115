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
    <link rel="icon" type="image/png" href= "LittleCaricon.jpeg">
      <!-- Latest compiled and minified CSS -->
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" integrity="sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ==" crossorigin="anonymous">

      <!-- Optional theme -->
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css" integrity="sha384-aUGj/X2zp5rLCbBxumKTCw2Z50WgIr1vs/PFN4praOTvYXWlVyh2UtNUU0KAUhAX" crossorigin="anonymous">
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>

     <style>
        div.Buffer {
          padding-top: 52px;
        }

        div.Section2 {
          padding-top: 10px;
        }
      </style>
</head>

<body style = "background-color:lightblue">
   <nav class="navbar navbar-default">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="LoginPage.jsp">Home</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li class="active"><a href="ProfilePage.jsp">Profile <span class="sr-only">(current)</span></a></li>
        <li><a href="searchpage.jsp">Searching a Ride</a></li>
      </ul>
      <ul class="nav navbar-nav navbar">
        <li><a href="submissionpage.jsp">Adding a Ride</a></li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>

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
	<!--script>
	(function (global) {
		document.getElementById("depart").value = global.localStorage.getItem("Depart");
	}(window));
	</script-->
	
  <form action="/search" method="post"> 
	<script>
		/*jslint sub: true, maxerr: 50, indent: 4, browser: true */
		(function (global) {
			document.getElementById("depart").value = global.localStorage.getItem("Depart");
		}(window));
	</script>
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
	  
	  <div class = "Buffer">
     <p>Are you looking for a driver or a rider?</p><br>
     </div>   
      <label for "drive"> Driver </label>
      <input type="radio" name="drive" value="true" checked>
      <label for "drive"> Rider </label>
      <input type="radio" name="drive" value="false"> <br>
      <br>
      <br>
	  
	<div class = "Section2"> 
    <div class = "panel-info">   
      <div class = "panel-heading"><p>Searching for a weekly ride? Check the day(s) that apply:</p>
	    <input type="checkbox" name="weekday" value="su"/>Sunday<br>
      <input type="checkbox" name="weekday" value="mo"/>Monday<br>
      <input type="checkbox" name="weekday" value="tu"/>Tuesday<br>
      <input type="checkbox" name="weekday" value="we"/>Wednesday<br>
      <input type="checkbox" name="weekday" value="th"/>Thursday<br>
      <input type="checkbox" name="weekday" value="fr"/>Friday<br>
      <input type="checkbox" name="weekday" value="sa"/>Saturday<br>
      </div>
   </div>
  </div>
	  
	Prioritize: <br>
      <input type="radio" name="prio" value="both" checked/>Origin and Destination<br>
      <input type="radio" name="prio" value="origin"/>Origin<br>
      <input type="radio" name="prio" value="dest"/>Destination<br>
      <button type="submit" class = "btn-primary" name="Submit" value=Submit>Submit</button>
   </form>
   <!--% String departs = (String)pageContext.getAttribute("depart");%-->
   <!--% System.out.println(departs + " HELLO");%-->
   
   
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
         <% pageContext.setAttribute("ride_email", ride.email); %>
         <% pageContext.setAttribute("ride_origin", ride.origin); %>
         <% pageContext.setAttribute("ride_dest", ride.destination); %>
         <% pageContext.setAttribute("ride_depart", ride.depart); %>
         <p>
		 <b>${fn:escapeXml(ride_email)}</b>
         <b>${fn:escapeXml(ride_origin)}</b>
         <b>${fn:escapeXml(ride_dest)}</b>
         <b>${fn:escapeXml(ride_depart)}</b>
		 </p>
      <% } %>
  <% } else { %>
      <p> There are no current rides. </p>
   <% } %>
   
   <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js" integrity="sha512-K1qjQ+NcF2TYO/eI3M6v8EiNYZfA95pQumfvcVrTHtwQVDG+aHRqLi/ETn2uB+1JqwYqVG3LIvdm9lj6imS/pQ==" crossorigin="anonymous"></script>
</body>
</html>