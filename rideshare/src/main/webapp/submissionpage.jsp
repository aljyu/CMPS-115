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
    <link rel="icon" type="image/png" href= "LittleCaricon.jpeg">
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" integrity="sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ==" crossorigin="anonymous">

    <!-- Optional theme -->
     <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css" integrity="sha384-aUGj/X2zp5rLCbBxumKTCw2Z50WgIr1vs/PFN4praOTvYXWlVyh2UtNUU0KAUhAX" crossorigin="anonymous">

    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>

    <style>
      div.Section1 {
        padding-top: 50px;
      }
    </style>
</head>

<body background = "LoginPageBackground1.jpg">
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

  <center><h2>Adding a Ride?</h2></center>
  <%
  List<Ride> rides = ObjectifyService.ofy()
      .load()
      .type(Ride.class)
      .order("-riderEmail")
      .limit(5)
      .list();
  %>
  <form action="/sign" method="post"> 
      <p> <b> Please submit your ride here: </b> </p> 

      <label for="name"> Full Name: </label>
      <input id="name" type="text" name="name"><br>
      <label for="email"> Email: </label>
      <input id = "email" type="text" name="email"><br>
      <label for="origin"> Origin: </label> 
      <input id =" origin" type="text" name = "origin"><br>
      <label for="dest"> Destination: </label>
      <input id= "dest" type ="text" name = "dest"><br>
      <label for="depart"> Departure Time: </label>
      <input id="depart" type ="text" name= "depart"><br>
      <label for "arrive"> Arrival Time: </label>
      <input id = "arrive" type ="text" name = "arrive"><br>
	  <label for = "seats"> Seats Avaliable: </label>
	  <input id = "seats" type = "text" name = "seats"><br>
      <label for "drive"> Are you driving or riding? </label><br>
      <label for "drive"> Driving </label>
      <input type="radio" name="drive" value="true" checked>
      <label for "drive"> Riding </label>
      <input type="radio" name="drive" value="false"> <br>
      <br><br><br>
      
      <div class = "Section1">
      <p><b> Offering or looking for a weekly ride? Check the day(s) that apply: </b></p>
      </div>
      

      <p><input type="checkbox" name="weekday" value="su"/><b>Sunday</b></p>
      <p><input type="checkbox" name="weekday" value="mo"/><b>Monday</b></p>
      <p><input type="checkbox" name="weekday" value="tu"/><b>Tuesday</b></p>
      <p><input type="checkbox" name="weekday" value="we"/><b>Wednesday</b></p>
      <p><input type="checkbox" name="weekday" value="th"/><b>Thursday</b></p>
      <p><input type="checkbox" name="weekday" value="fr"/><b>Friday</b></p>
      <p><input type="checkbox" name="weekday" value="sa"/><b>Saturday</b></p>
      <button type="submit" class = "btn btn-info" name="Submit" value=Submit>Submit</button>
   </form>

   <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js" integrity="sha512-K1qjQ+NcF2TYO/eI3M6v8EiNYZfA95pQumfvcVrTHtwQVDG+aHRqLi/ETn2uB+1JqwYqVG3LIvdm9lj6imS/pQ==" crossorigin="anonymous"></script>

</body>
</html>

