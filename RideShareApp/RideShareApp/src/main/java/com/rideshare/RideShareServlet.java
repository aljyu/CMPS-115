package com.rideshare;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.ObjectifyService;

public class RideShareServlet extends HttpServlet {

   @Override
   public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
      Ride ride;
      UserService userService = UserServiceFactory.getUserService();
      User user = userService.getCurrentUser();
      
      String riderEmail = req.getParameter("riderEmail"); 
      String start = req.getParameter("startLocation");
      String end = req.getParameter("endLocation");
      String time = req.getParameter("departureTime");
      ride = new Ride(riderEmail, start, end, time);

      ObjectifyService.ofy().save().entity(ride).now();
      resp.sendRedirect("/rideshare.jsp");
   }
}