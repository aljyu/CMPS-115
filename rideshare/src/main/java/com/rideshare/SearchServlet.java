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
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Properties;
import java.lang.Iterable;
import java.util.Iterator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;
import com.googlecode.objectify.ObjectifyService;

public class SearchServlet extends HttpServlet {

   @Override
   public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
      
      List<Ride> rides = ObjectifyService.ofy()
         .load()
         .type(Ride.class)
         .order("-depart")
         .list();

      String email = req.getParameter("email"); 
      String origin = req.getParameter("origin");
      String originrad = req.getParameter("originrad");
      String dest = req.getParameter("dest");
      String destrad = req.getParameter("destrad");
      String depart = req.getParameter("depart");
      String arrive = req.getParameter("arrive");
      String datestr = req.getParameter("date");
      
      try{ 
         Date date = new Date(datestr);
      }catch(IllegalArgumentException e){
         // don't need to do anything its not an error
      }
      List<Ride> finalrides = new ArrayList<Ride>(); 
      if(datestr != null || !(datestr.isEmpty())){ 
         for(Ride ride: rides){
            if(date.equals(ride.ridedate)){
               finalrides.add(ride);
            }
         } 
         rides = finalrides;
      }
      String driving = req.getParameter("drive");
      boolean drive = false;
      if (driving.equals("true")) {
         drive = true;
      }
      String weekdays[]= req.getParameterValues("weekday");
      boolean su = false;
      boolean mo = false;
      boolean tu = false;
      boolean we = false;
      boolean th = false;
      boolean fr = false;
      boolean sa = false;
      String prio = req.getParameter("prio");
      String seats = req.getParameter("seats");
      boolean b = true;

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

	  
	Ridelist allrides = new Ridelist(rides);
	//first, always filter by driver or rider
	List<Ride> filtered = allrides.filterByDrive(rides, drive);
	//second, if user entered an email, filter by email
	if (email.length() > 0) {
		List<Ride> filter2 = allrides.filterByEmail(filtered, email);
		filtered=filter2;
	}
	//third, if user checked any recurring boxes, filter by days
	if ((weekdays != null) && (weekdays.length > 0)) {
		List<Ride> filter2 = allrides.filterByDays(filtered, su, mo, tu, we, th, fr, sa);
		filtered = filter2;
	}
	//fourth, if user entered a departure time, filter by depart
 	if (depart.length() > 0) {
           String time = depart;
           int hour = 0;
           int minutes = 0;
           if(time.matches(".*[a-zA-Z]+.*") == true) {
              b = false;
              System.out.println("Invalid Characters");
           }
           int index = time.indexOf(':');
           if(index != -1) {
              hour = Integer.parseInt(time.substring(0, index));  
              if(index != time.length() - 3) {
                 b = false;
                 System.out.println("There is a colon at the wrong spot for depart");
              }else { 
                 minutes = Integer.parseInt(time.substring(index+1));
              }
         }else {
            hour = Integer.parseInt(time);
         }
         if ((hour>23) || (minutes>59)) {
            b= false;
            System.out.println("Time out of range");
         }
         if (b){
             List<Ride> filter2 = allrides.filterByDepart(filtered, depart);
  	     filtered=filter2;
         }
      }
      //fifth, if user entered an arrival time, filter by arrive
      if (arrive.length() > 0) {
         String time = arrive;
         int hour = 0;
         int minutes = 0;
         if(time.matches(".*[a-zA-Z]+.*") == true) {
            b = false;
            System.out.println("Invalid Characters");
         }
         int index = time.indexOf(':');
         if(index != -1) {
              hour = Integer.parseInt(time.substring(0, index));
              if(index != time.length() - 3) {
                b = false;
                System.out.println("There is a colon at the wrong spot for arrive");}
                else { minutes = Integer.parseInt(time.substring(index+1));}
         }
         else {hour = Integer.parseInt(time);}
         if ((hour>23) || (minutes>59)) {
            b= false;
            System.out.println("Time out of range");
         }
         if (b){
            List<Ride> filter2 = allrides.filterByArrive(filtered, arrive);
            filtered=filter2;
         }
     }
		//sixth, if user entered number of seats, filter by seats avaliable
      if(seats.length() > 0){
      	if (drive) {
        	List<Ride> filter2 = allrides.filterBySeatsMore(filtered, seats);
        	filtered=filter2;}
      	else {
		List<Ride> filter2 = allrides.filterBySeatsLess(filtered, seats);
          	filtered=filter2;
        }
    }
    //check origin and destination are valid
    GeoPt origPt = null; 
    if(origin.length() > 0 && origin != null) {
      String status ="";
      String lat = "0", lng = "0";
      String origins = origin.replaceAll(" ", "%20");
      String geokey = null;
      List<Keys> listkey = ObjectifyService.ofy().load().type(Keys.class).list();
      for(int i = 0; i < listkey.size(); ++i){
         if(listkey.get(i).type.compareToIgnoreCase("Server") == 0)geokey = listkey.get(i).value;
      }
      try {
         String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + origins + "key="+geokey; 
         URL geocodeOri = new URL(url);
         BufferedReader reader = new BufferedReader(new InputStreamReader(geocodeOri.openStream()));
         String line;
         while ((line = reader.readLine()) != null){
            int colon = 0;
            colon = line.indexOf(":");
            if(line.contains("status")){
               status = line.substring(colon + 2);
            }
	    if(line.contains("lat")) lat = line.substring(colon + 2, line.lastIndexOf(",") - 1);
            if(line.contains("lng")) lng = line.substring(colon + 2);
         }
         float lt = Float.parseFloat(lat);
         float ln = Float.parseFloat(lng); 
         origPt = new GeoPt(lt, ln);
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
      }
    } 
    GeoPt destpt = null;
    if(dest.length() > 0 || dest == null) {
      String status = "";
      String dests = dest.replaceAll(" ", "%20");
      String dlat = "0", dlng = "0";
      List<Keys> listkey = ObjectifyService.ofy().load().type(Keys.class).list();
      String geokey = null;
      for(int i = 0; i < listkey.size(); ++i){
         if(listkey.get(i).type.compareToIgnoreCase("Server") == 0)geokey = listkey.get(i).value;
      }
      try {
         String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + dests + "key="+geokey; 
         URL geocodeOri = new URL(url);
         BufferedReader reader = new BufferedReader(new InputStreamReader(geocodeOri.openStream()));
         String line;
         while ((line = reader.readLine()) != null){
            int colon = 0;
            colon = line.indexOf(":");
            if(line.contains("status")){
               status = line.substring(colon + 2);
            }
            if(line.contains("lat")) dlat = line.substring(colon + 2, line.lastIndexOf(",") - 1);
            if(line.contains("lng")) dlng = line.substring(colon + 2);
         }
         float dlt = Float.parseFloat(dlat);
         float dln = Float.parseFloat(dlng);
         destpt = new GeoPt(dlt, dln);     
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
      }
    }
    //check for valid radii
    if (originrad.length() > 0 && originrad != null) {
      if(originrad.matches(".*[a-zA-Z]+.*") == true) {
        b = false;
        System.out.println("Invalid Characters in originrad");
      }
    }
    if (destrad.length() > 0 && destrad != null) {
      if(destrad.matches(".*[a-zA-Z]+.*") == true) {
        b = false;
        System.out.println("Invalid Characters in destrad");
      }
    }
    if (!b) {
      resp.sendRedirect("/searcherror.jsp");
    }
    else {
	//seventh, if user entered a origin radius and a origin
	if ((origin.length() > 0) && (originrad.length() > 0)) {
		List<Ride> filter2 = allrides.originRadius(filtered, origPt, originrad);
		filtered=filter2;
	} 
	//eigthth, if user entered a destination radius
	if ((dest.length() > 0) && (destrad.length()>0)) {
		List<Ride> filter2 = allrides.destRadius(filtered, destpt, destrad);
		filtered=filter2;
	}

	//Now, sort according to user priority
	if (((origin.length() > 0) && (prio.equals("origin"))) || ((!(dest.length()>0)) && (prio.equals("both")))) {
		List<Ride> so = allrides.sortOrigin(filtered, origPt);
		filtered=so;
	}

	else if (((dest.length() > 0) &&(prio.equals("destination"))) || ((!(origin.length()>0)) && (prio.equals("both")))) {
		List<Ride> sd = allrides.sortDest(filtered, destpt);
		filtered=sd;
	}

	//optimize for both
	else if ((dest.length()>0) && (origin.length()>0)){
		//go thru so and sd, adding up respective indices from filtered
		List<floatRide> irlist = new ArrayList<floatRide>();
		List<Ride> so = allrides.sortOrigin(filtered, origPt);
		List<Ride> sd = allrides.sortDest(filtered, destpt);
		//first, go through the list and compute values
		for (int i=0; i< filtered.size(); i++) {
			int loc1 = so.indexOf(filtered.get(i));
			int loc2 = sd.indexOf(filtered.get(i));
			float val = loc1+loc2;
			floatRide ir = new floatRide(val, filtered.get(i));
			irlist.add(ir);
		}
		//then, sort the list and return it
		List<Ride> filter2 = allrides.sort(irlist);
		filtered =filter2;
	}
	Ridelist rlist = new Ridelist(filtered);
	//System.out.println("First");
	req.setAttribute("departs", depart);
	req.setAttribute("resultRides", rlist);
	//System.out.println("Fourth");
	String url = "/searchreturn.jsp";
	//ServletContext sc = this.getServletContext();
	//System.out.println("Second");
	RequestDispatcher rd = req.getRequestDispatcher(url);
	//System.out.println("Third");
	try{
		rd.forward(req, resp);
	}catch(ServletException e){
		//...
		//System.out.println("First error");
		System.exit(127);
	}catch(IOException e){
		//...
		//System.out.println("Second error");
		System.exit(127);
	}
	//System.out.println("Fifth");
	//resp.sendRedirect("/searchreturn.jsp");
	//System.out.println("Sixth");
   }
}
}
