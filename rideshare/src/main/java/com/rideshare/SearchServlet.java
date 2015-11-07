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
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
			List<Ride> filter2 = allrides.filterByDepart(filtered, depart);
			filtered=filter2;
		}
		//fifth, if user entered an arrival time, filter by arrive
		if (arrive.length() > 0) {
			List<Ride> filter2 = allrides.filterByArrive(filtered, arrive);
			filtered=filter2;
		}
		//sixth, if user entered number of seats, filter by seats avaliable
		if(seats.length() > 0){
			List<Ride> filter2 = allrides.filterBySeats(filtered, seats);
			filtered=filter2;
		}
    //seventh, if user entered a origin radius
    if ((origin.length() > 0) && (originrad.length()>0)) {
      List<Ride> filter2 = allrides.originRadius(filtered, origin, originrad);
      filtered=filter2;
    }

    //eigthth, if user entered a origin radius
    if ((dest.length() > 0) && (destrad.length()>0)) {
      List<Ride> filter2 = allrides.destRadius(filtered, dest, destrad);
      filtered=filter2;
    }

    //Now, sort according to user priority
    if (((origin.length() > 0) && (prio.equals("origin"))) || ((!(dest.length()>0)) && (prio.equals("both")))) {
      List<Ride> so = allrides.sortOrigin(filtered, origin);
      filtered=so;
    }

    else if (((dest.length() > 0) &&(prio.equals("destination"))) || ((!(origin.length()>0)) && (prio.equals("both")))) {
        List<Ride> sd = allrides.sortDest(filtered, dest);
        filtered=sd;
      }

    //optimize for both
    else if ((dest.length()>0) && (origin.length()>0)){
        //go thru so and sd, adding up respective indices from filtered
        List<floatRide> irlist = new ArrayList<floatRide>();
        List<Ride> so = allrides.sortOrigin(filtered, origin);
        List<Ride> sd = allrides.sortDest(filtered, dest);
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

		PrintWriter writer = resp.getWriter();
	  
		String htmlResp = "<html><b>Start</b>";
		for(Ride ride : filtered){
			htmlResp += "<p>";
			//htmlResp += "<b>";
			htmlResp += "Contact Email: " + ride.email + ", ";
			htmlResp += "Origin: " + ride.origin.replaceAll("%20", " ") + ", ";
			htmlResp += "Destination: " + ride.destination.replaceAll("%20", " ") + ", ";
			htmlResp += "Departure Time: " + ride.depart + ", ";
			htmlResp += "Arrival Time: " + ride.arrive + ", ";
			//htmlResp += "Days of the week offered: " + ride.su + ", " + ride.mo + ", " + ride.tu + ", " + ride.we + ", " + ride.th + ", " + ride.fr + ", " + ride.sa;
			htmlResp += "Seats Avaliable: " + ride.seats;
			htmlResp += "Driver? " + ride.drive;
			//htmlResp += "</b>";"
			htmlResp += "</p>";
		}
		htmlResp += "</html>";
	  
		writer.println(htmlResp);

      //resp.sendRedirect("/searchreturn.jsp");
   }
}