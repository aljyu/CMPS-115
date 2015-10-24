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
      
      String name = req.getParameter("name");
      String email = req.getParameter("email"); 
      String origin = req.getParameter("origin");
      String dest = req.getParameter("dest");
      String depart = req.getParameter("depart");
     // int depart = Integer.parseInt(departs);
      String arrive = req.getParameter("arrive");
     // int arrive = Integer.parseInt(arrives);
      String driving = req.getParameter("drive");
      boolean drive;
      if (driving.equals("true")) {
         drive = true;
      }
      else {
         drive = false;
      }
      ride = new Ride(name, email, origin, dest, depart, arrive, drive);
      ObjectifyService.ofy().save().entity(ride).now();
      System.out.println (name);
      resp.sendRedirect("/rideshare.jsp");
   }
}
