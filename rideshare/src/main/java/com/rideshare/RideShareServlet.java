package com.rideshare;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.datastore.GeoPt;

import java.net.MalformedURLException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Date;
import com.google.appengine.api.datastore.GeoPt;
import java.io.PrintWriter;
import java.util.List;

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
      String arrive = req.getParameter("arrive");
      String driving = req.getParameter("drive");
      boolean drive = false;
      if (driving.equals("true")) {
         drive = true;
      }
      else {
         drive = false;
      }
     
      String lat = "0", lng = "0";
      origin = origin.replaceAll(" ", "%20");
      try {
         String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + origin + "key=AIzaSyCp5fkGT7jJUzBRbexUadI1EmU1nq8pVO8"; 
         URL geocodeOri = new URL(url);
         BufferedReader reader = new BufferedReader(new InputStreamReader(geocodeOri.openStream()));
         String line;
         while ((line = reader.readLine()) != null){
            int colon = 0;
            colon = line.indexOf(":");
            if(line.contains("formatted_address")){
               origin = line.substring(colon + 2);
            }
            if(line.contains("lat")) lat = line.substring(colon + 2, line.lastIndexOf(",") - 1);
            if(line.contains("lng")) lng = line.substring(colon + 2);
         }   
      } catch (MalformedURLException e) {
        System.exit(127);
      // ...
      } catch (IOException e) {
        System.exit(127);
      // ...
      } 
      float lt = Float.parseFloat(lat);
      float ln = Float.parseFloat(lng);
      GeoPt start = new GeoPt(lt, ln); 
    
      String sla = null;
      String slg = null;
      dest = dest.replaceAll(" ", "%20");
      try {
         String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + dest + "key=AIzaSyCp5fkGT7jJUzBRbexUadI1EmU1nq8pVO8";
         URL geocodeOri = new URL(url);
         BufferedReader reader = new BufferedReader(new InputStreamReader(geocodeOri.openStream()));
         String line;
         while ((line = reader.readLine()) != null){
            int colon = 0;
            colon = line.indexOf(":");
            if(line.contains("formatted_address")){
               dest = line.substring(colon + 2);
            }
            if(line.contains("lat")) sla = line.substring(colon + 2, line.lastIndexOf(",") - 1);
            if(line.contains("lng")) slg = line.substring(colon + 2);
         }         
      } catch (MalformedURLException e) {
      // ...
      } catch (IOException e) {
      // ...
      }
      float slt = Float.parseFloat(sla);
      float sln = Float.parseFloat(slg);
      GeoPt end = new GeoPt(slt, sln);

      String weekdays[]= req.getParameterValues("weekday");
      boolean su = false;
      boolean mo = false;
      boolean tu = false;
      boolean we = false;
      boolean th = false;
      boolean fr = false;
      boolean sa = false;
      
      if ((weekdays != null) && (weekdays.length > 0)) {
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
      }

     // if (name.isEmpty() || email.isEmpty() || 
     //     origin.isEmpty() || dest.isEmpty() ||  
      //    depart.isEmpty() || arrive.isEmpty()){
     //    resp.sendRedirect(returner);
     // }
    ride = new Ride(name, email, origin, dest, depart, arrive, start, end, drive, su, mo, tu, we, th, fr, sa);
    ObjectifyService.ofy().save().entity(ride).now();
    resp.sendRedirect("/rideshare.jsp");
      
    PrintWriter writer = resp.getWriter();
    
    String htmlResp = "<html><b>Start</b>";
    
      htmlResp += "<p>";
      //htmlResp += "<b>";
      htmlResp += "Contact Email: " + ride.email + ", ";
      htmlResp += "Departure Time: " + ride.depart + ", ";
      htmlResp += "Arrival Time: " + ride.arrive + ", ";
      //htmlResp += "Days of the week offered: " + ride.su + ", " + ride.mo + ", " + ride.tu + ", " + ride.we + ", " + ride.th + ", " + ride.fr + ", " + ride.sa;
      htmlResp += "Driver? " + ride.drive;
      //htmlResp += "</b>";"
      htmlResp += "</p>";
    
    htmlResp += "</html>";
    
    writer.println(htmlResp);
   }
}
