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
import com.googlecode.objectify.annotation.AlsoLoad;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.IgnoreSave;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.OnLoad;
import static com.googlecode.objectify.ObjectifyService.ofy;

import java.net.MalformedURLException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Date;
import com.google.appengine.api.datastore.GeoPt;
import java.io.PrintWriter;
import java.util.List;
import java.util.Properties;
import java.lang.Iterable;
import java.util.Iterator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;

import com.googlecode.objectify.ObjectifyService;

public class RideShareServlet extends HttpServlet {

   @Override
   public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
      Ride ride;
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      String name = req.getParameter("name");
      String email = req.getParameter("email"); 
      String origin = req.getParameter("origin");
      String dest = req.getParameter("dest");
      String date = req.getParameter("date");
      System.out.println(date);
      // A date is mm/dd/yyyy
      Date ridedate = null;
      try{  
         ridedate = new Date(date);
      }catch(IllegalArgumentException e){
         resp.sendRedirect("/dateError.jsp");
      }
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
      String seatstring = req.getParameter("seats");

    //Error checking for blank spots
    if ((name.length() == 0)     || (email.length() == 0) || 
          (origin.length() == 0) || (dest.length() == 0)  ||  
          (depart.length() == 0) || (arrive.length() == 0)||
          (seatstring.length() == 0)                      || 
             (ridedate == null)  || (date.length() == 0)){
        resp.sendRedirect("/blanksubmissionerror.jsp");
      }
  else { 
    //error checking for time formats
    String time = depart;
    int hour = 0;
    int minutes = 0;
    boolean b = true;
    
    if(time.matches(".*[a-zA-Z]+.*") == true) {
      b = false;
      System.out.println("Invalid Characters");
    }
    time = arrive;
    if(time.matches(".*[a-zA-Z]+.*") == true) {
      b = false;
      System.out.println("Invalid Characters");
    }
    if (!b){
      resp.sendRedirect("/timesubmissionerror.jsp");
    }

    else {
      int index = time.indexOf(':');
      if(index != -1) {
              hour = Integer.parseInt(time.substring(0, index));
              if(index != time.length() - 3) {
                 b = false;
                 System.out.println("There is a colon at the wrong spot for arrive");
              }else { 
                 minutes = Integer.parseInt(time.substring(index+1));
              }
      }else {hour = Integer.parseInt(time);}
      if ((hour>23) || (minutes>59)) {
          b= false;
          System.out.println("Time out of range");
        }
        float arriveTime = hour + (float) minutes/60;
        minutes = 0;
        time = depart;
        index = time.indexOf(':');
        if(index != -1) {
              hour = Integer.parseInt(time.substring(0, index));
              if(index != time.length() - 3) {
                 b = false;
                 System.out.println("There is a colon at the wrong spot for arrive");
              }else { 
                 minutes = Integer.parseInt(time.substring(index+1));
              }
        } else {
           hour = Integer.parseInt(time);
        }
        if ((hour>23) || (minutes>59)) {
           b= false;
           System.out.println("Time out of range");
        }
        float departTime = hour + (float) minutes /60;
        if (arriveTime <= departTime) {
          b = false;
          System.out.println("Departure is before arrival.");
        }
        if (!b){
            resp.sendRedirect("/timesubmissionerror.jsp");
        }
        else {
      int seats = -1; 
      try {
         seats = Integer.parseInt(seatstring);
      }catch(NumberFormatException e){
         System.err.println("Thats not a number for seats");
         resp.sendRedirect("/seatserror.jsp");
         return;
      }
      if(seats == -1){ 
         resp.sendRedirect("/seatserror.jsp");
         return;
      } 
      String status ="";

      String lat = "0", lng = "0";
      origin = origin.replaceAll(" ", "%20");
      List<Keys> listkey = ObjectifyService.ofy().load().type(Keys.class).list();
      String geokey = null;
      for(int i = 0; i < listkey.size(); ++i){
         if(listkey.get(i).type.compareToIgnoreCase("Server") == 0)geokey = listkey.get(i).value;
      }
      try {
         String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + origin + "key=" + geokey; 
         URL geocodeOri = new URL(url);
         BufferedReader reader = new BufferedReader(new InputStreamReader(geocodeOri.openStream()));
         String line;
         while ((line = reader.readLine()) != null){
            int colon = 0;
            colon = line.indexOf(":");
            if(line.contains("formatted_address")){
               origin = line.substring(colon + 3, line.length() - 2); 
            }
            if(line.contains("lat")) lat = line.substring(colon + 2, line.lastIndexOf(",") - 1);
            if(line.contains("lng")) lng = line.substring(colon + 2);
            if(line.contains("status")){
               status = line.substring(colon + 2);
            }
         }   
        
      } catch (MalformedURLException e) {
        b=false;
      } catch (IOException e) {
        b=false;
      } 
      finally {
        if (status.contains("OK") == false) {
          b=false;
          System.out.println("Status for origin is " + status);
        }
        if (!b) {
          resp.sendRedirect("/addresssubmissionerror.jsp");
        }
      else {
      float lt = Float.parseFloat(lat);
      float ln = Float.parseFloat(lng);
      GeoPt start = new GeoPt(lt, ln); 
    
      String sla = "0"; 
      String slg = "0";
      dest = dest.replaceAll(" ", "%20");
      try {
         String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + dest + "key="+geokey;
         URL geocodeOri = new URL(url);
         BufferedReader reader = new BufferedReader(new InputStreamReader(geocodeOri.openStream()));
         String line;
         while ((line = reader.readLine()) != null){
            int colon = 0;
            colon = line.indexOf(":");
            if(line.contains("formatted_address")){
               dest = line.substring(colon + 3, line.length() - 2);
            }
            if(line.contains("lat")) sla = line.substring(colon + 2, line.lastIndexOf(",") - 1);
            if(line.contains("lng")) slg = line.substring(colon + 2);
            if(line.contains("status")){
               status = line.substring(colon + 2);
            }
         }             
      } catch (MalformedURLException e) {
        b=false;
      } catch (IOException e) {
        b=false;
      }
      finally {
      if (status.contains("OK") == false) {
        b=false;
        System.out.println("Status for dest is " + status);
      }
      if (!b) {
        resp.sendRedirect("/addresssubmissionerror.jsp");
      }
      else {
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
        for (int i = 0; i < weekdays.length; i++) {
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

    
    ride = new Ride(name, email, origin, dest, depart, arrive, ridedate,
                    start, end, drive, su, mo, tu, we, th, fr, sa, seats);
    ObjectifyService.ofy().save().entity(ride).now();
    resp.sendRedirect("/rideshare.jsp");
  }
  }
 }
}
}
}
}
}
}
