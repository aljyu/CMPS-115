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
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.datastore.TransactionOptions;
import com.google.appengine.api.datastore.EntityNotFoundException;

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

import com.googlecode.objectify.ObjectifyService;

public class EditerServlet extends HttpServlet {

   String loc = "";
   public GeoPt geocode(String location){
      List<Keys> listkey = ObjectifyService.ofy().load().type(Keys.class).list();
      String geokey = null;
      String lat = "0";
      String lng = "0";
      for(int i = 0; i < listkey.size(); ++i){
         if(listkey.get(i).type.compareToIgnoreCase("Server") == 0)geokey = listkey.get(i).value;
      }
      location = location.replaceAll(" ", "%20");
      location = location.replaceAll("[\"]", "");
      try {
         String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + location + "key="+geokey; 
         URL geocodeOri = new URL(url);
         BufferedReader reader = new BufferedReader(new InputStreamReader(geocodeOri.openStream()));
         String line;
         while ((line = reader.readLine()) != null){
            int colon = 0;
            colon = line.indexOf(":");
            if(line.contains("formatted_address")){
               loc = line.substring(colon + 2);
            }
            if(line.contains("lat"))
               lat = line.substring(colon + 2, line.lastIndexOf(",") - 1);
            if(line.contains("lng")) 
               lng = line.substring(colon + 2);
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
      return(new GeoPt(lt, ln)); 
   }

   @Override
   public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
      Ride ride;
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService(); 
      // TODO: cast this to a long
      String key = req.getParameter("Key");
      long keynum = Long.parseLong(key);
      String name = req.getParameter("name");
      String email = req.getParameter("email"); 
      String origin = req.getParameter("origin");
      String dest = req.getParameter("dest");
      String depart = req.getParameter("depart");
      String arrive = req.getParameter("arrive");
      String driving = req.getParameter("drive");
      List<Ride> rides = ObjectifyService.ofy()
         .load()
         .type(Ride.class)
         .list(); 
      for(int i = 0; i < rides.size(); ++i){
         if(rides.get(i).id == keynum){
            ride = rides.get(i);
            break;
         }
      }
      // Transaction code on the ride goes here
      Transaction txn = datastore.beginTransaction();
      try {
         Key ridekey = KeyFactory.createKey("Ride", keynum);
         Entity tom = datastore.get(ridekey);
         tom.setProperty("name", name);
         tom.setProperty("email", email);
         tom.setProperty("depart", depart); 
         tom.setProperty("arrive", arrive);
         tom.setProperty("drive", driving);
         GeoPt st = geocode(origin);
         origin = loc;

         GeoPt end = geocode(dest);
         dest = loc;
         tom.setProperty("origin", origin);
         tom.setProperty("destination", dest);
         tom.setProperty("start", st);
         tom.setProperty("end", end);
         datastore.put(tom);
         txn.commit();
      }
      catch(EntityNotFoundException e){
      }
      finally {
         if(txn.isActive()) {
            txn.rollback();
         }
      }
      resp.sendRedirect("/rideshare.jsp");
   }
}
