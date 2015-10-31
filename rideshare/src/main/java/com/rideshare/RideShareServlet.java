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
      String returner = req.getRequestURL().toString(); 
      
      String name = req.getParameter("name");
      String email = req.getParameter("email"); 
      String origin = req.getParameter("origin");
      String dest = req.getParameter("dest");
      String depart = req.getParameter("depart");
      String arrive = req.getParameter("arrive");
      String driving = req.getParameter("drive");
      boolean drive;
      if (driving.equals("true")) {
         drive = true;
      }
      else {
         drive = false;
      }
      String weekdays[]= req.getParameterValues("weekday");
      boolean su = false;
      boolean mo = false;
      boolean tu = false;
      boolean we = false;
      boolean th = false;
      boolean fr = false;
      boolean sa = false;

      for (int i = 0; i< weekdays.length; i++) {
        if (weekdays[i].equals("su")) {
          su = true;
        }
        if (weekdays[i].equals("mo")) {
          mo = true;
        }
        if (weekdays[i].equals("tu")) {
          tu = true;
        }
        if (weekdays[i].equals("we")) {
          we = true;
        }
        if (weekdays[i].equals("th")) {
          th = true;
        }
        if (weekdays[i].equals("fr")) {
          fr = true;
        }
        if (weekdays[i].equals("sa")) {
          sa = true;
        }
      }

     // if (name.isEmpty() || email.isEmpty() || 
     //     origin.isEmpty() || dest.isEmpty() ||  
      //    depart.isEmpty() || arrive.isEmpty()){
     //    resp.sendRedirect(returner);
     // }
      ride = new Ride(name, email, origin, dest, depart, arrive, drive, su, mo, tu, we, th, fr, sa);
      ObjectifyService.ofy().save().entity(ride).now();
      System.out.println (name);
      resp.sendRedirect("/rideshare.jsp");
   }
}
