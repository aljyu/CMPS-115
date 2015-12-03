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
public class CleanupServlet extends HttpServlet {
   
   @Override
   public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService(); 
      String key = req.getParameter("Key");
      long idnum = Long.parseLong(key);
      Ride ride;
      List<Ride> rides = ObjectifyService.ofy()
         .load()
         .type(Ride.class)
         .list(); 
      for (int i = 0; i < rides.size(); ++ i){
         Transaction txn = datastore.beginTransaction();
         try {
            Key ridekey = KeyFactory.createKey("Ride", idnum);
            datastore.delete(ridekey);
            txn.commit();
         }finally {
            if(txn.isActive()) {
               txn.rollback();
            }
         }
      }
      resp.sendRedirect("/rideshare.jsp");
   }
}
