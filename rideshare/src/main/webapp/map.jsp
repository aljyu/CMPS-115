<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.rideshare.Keys" %>
<%@ page import="com.rideshare.Ride" %>
<%@ page import="com.googlecode.objectify.Key" %>
<%@ page import="com.googlecode.objectify.ObjectifyService" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>

<body>
<%
   List<Ride> rides = ObjectifyService.ofy()
      .load()
      .type(Ride.class)
      .order("-email")
      .list(); 
%>
<% String usersemail = session.getAttribute("user_email").toString(); %>
<% int top = rides.size(); %>
<% List <Ride> finalrides = new ArrayList<Ride>(); %>
<% for (int i = 0; i < top; ++i){ %>
<%    String rideemail = rides.get(i).email; %>
<%    if(rideemail.equals(usersemail)) %>
<%      finalrides.add(rides.get(i)); %>
<% } %>
<% 
   List<Keys> keys = ObjectifyService.ofy()
      .load()
      .type(Keys.class)
      .list();
%>
<% String browkey = null; %>
<% for(int i = 0; i < keys.size(); ++i){ %>
<%    if(keys.get(i).type.compareToIgnoreCase("Browser") == 0) browkey = keys.get(i).value; %>
<% } %>
<% String originpt = finalrides.get(finalrides.size() - 1).origin; %>
<% String destpt = finalrides.get(finalrides.size() - 1).destination; %>
<% originpt = originpt.substring(1, originpt.length() - 2); %>
<% destpt = destpt.substring(1, destpt.length() - 2); %>
<% String testUrl = "https://www.google.com/maps/embed/v1/directions?key=" + browkey + "&origin="+ originpt + "&destination=" + destpt; %>

<iframe
  id = "map"
  width="600"
  height="450"
  frameborder="0" style="border:0"
  src="<%=testUrl%>" allowfullscreen>
</iframe>

</body>
</html>

