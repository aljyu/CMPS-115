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
import java.util.List;

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
      String dest = req.getParameter("dest"); 
      String depart = req.getParameter("depart"); 
      String arrive = req.getParameter("arrive"); 
	  
	  //String url = "/searchreturn.jsp";
	  //ServletContext sc = getServletContext();
	  //RequestDispatcher rd = sc.getRequestDispatcher(url);
	  
	  //String email = req.setParameter("email");
      //String origin = req.setParameter("origin"); 
      //String dest = req.setParameter("dest"); 
      //String depart = req.setParameter("depart"); 
      //String arrive = req.setParameter("arrive");

	  //req.setAttribute("depart", depart);
	  //rd.forward(req, resp);
	  
	  
      //System.out.println (depart);
	  
		Ridelist rl = new Ridelist(rides);
		List<Ride> rides2 = rl.sortDepart(5,depart);
		PrintWriter writer = resp.getWriter();
	  
		String htmlResp = "<html><b>Start</b>";
		for(Ride ride : rides2){
			htmlResp += "<p>";
			//htmlResp += "<b>";
			htmlResp += ride.email + ", " + ride.depart;
			//htmlResp += "</b>";"
			htmlResp += "</p>";
		}
		htmlResp += "</html>";
	  
		writer.println(htmlResp);

      //resp.sendRedirect("/searchreturn.jsp");
   }
}