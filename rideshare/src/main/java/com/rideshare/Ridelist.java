package com.rideshare;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.Key;
import com.google.appengine.api.datastore.GeoPt;

import java.lang.String;
import java.util.*;
import java.lang.Math;

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

@Entity
public class Ridelist implements java.io.Serializable{
	//class variables
	List<Ride> inputlist;

	
	//constructor
	public Ridelist(){
	}
	
	public Ridelist(List<Ride> list){
		inputlist = list;
	}
	
	public List<Ride> getInputlist(){
		return inputlist;
	}
	
	public void setInputlist(List<Ride> ilist){
		inputlist = ilist;
	}
	
	public float testFindDist(){
		//List<float> l = new ArrayList<float>();
		float l = 0;
		//l = findDist(createGP(50,03,59,-05,-42,-53),createGP(58,38,38,-03,-04,-12));
		//l = findDist(createGP(50,03,59,-05,-42,-53),createGP(50,03,59,-05,-42,-54));
		l = findDist(createGP(50,03,59,-05,-42,-53),createGP(40,44,55,-73,-59,-11));
		return l;
	}
	
	private GeoPt createGP(float d1, float m1, float s1, float d2, float m2, float s2){
		float lat = createRad(d1,m1,s1);
		float lon = createRad(d2,m2,s2);
		GeoPt g = new GeoPt(lat,lon);
		return g;
	}
	
	private float createRad(float d, float m, float s){
		float mn = m/60;
		float sn = s/3600;
		return d + mn + sn;
	}
	
	private float findDist(GeoPt orig, GeoPt dest){
		if(orig == null || orig == new GeoPt(0, 0) || dest == null || dest == new GeoPt(0, 0))return 0;
                float dist;
		int R = 6371; //Radius of the earth in meters
		float latO = (float) Math.toRadians(orig.getLatitude());
		float latD = (float) Math.toRadians(dest.getLatitude());
		float lonO = (float) Math.toRadians(orig.getLongitude());
		float lonD = (float) Math.toRadians(dest.getLongitude());
		float latDiff = latD - latO;
		float lonDiff = lonD - lonO;
		float a = (float) (Math.sin(latDiff/2) * Math.sin(latDiff/2) + 
		           Math.cos(latO) * Math.cos(latD) * Math.sin(lonDiff/2) * Math.sin(lonDiff/2));
		float c = (float) (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)));
		
		dist = R * c;
		
		return dist;
	}
	
	//Sort: This function should take in a list of FloatRides (simple class 
	//consisting of an float and a ride, and sort the rides in ascending 
	//order by their associated floats, and return just the list of rides 
	//in the correct order. 
	public List<Ride> sort(List<floatRide> iRide) {
		List<Ride> rList = new ArrayList<Ride>(); //[this.inputlist.size()]
		List<floatRide> sorted = logsort(iRide);
		for(int i = 0; i < sorted.size(); i++){
			rList.add(sorted.get(i).ride);
		}
		return rList;
	}
	
	private List<floatRide> logsort(List<floatRide> rides){
		List<floatRide> ret = new ArrayList<floatRide>();//[rides.size()]
		if(rides.size() <= 1){
			return rides;
		} else {
			int size = rides.size();
			List<floatRide> left = logsort(rides.subList(0,size/2));
			List<floatRide> right = logsort(rides.subList(size/2,size));
			int l = 0;
			int r = 0;
			while(l < left.size() && r < right.size()){
				if(left.get(l).value < right.get(r).value){
					ret.add(left.get(l));
					l++;
				}else{
					ret.add(right.get(r));
					r++;
				}
			}
			for(;l < left.size();l++){
				ret.add(left.get(l));
			}
			for(;r < right.size();r++){
				ret.add(right.get(r));
			}
			return ret;
		}
	}

	public GeoPt convertString(String location) {
	       String finalLoc;
	       String lat = "0", lng = "0";
	       location = location.replaceAll(" ", "%20");
	       List<Keys> listkey = ObjectifyService.ofy().load().type(Keys.class).list();
               String geokey = null;
               for(int i = 0; i < listkey.size(); ++i){
                  if(listkey.get(i).type.compareToIgnoreCase("Server") == 0)geokey = listkey.get(i).value;
              }
              try {
	         String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + location + "key="+geokey; 
	         URL geocodeOri = new URL(url);
	         BufferedReader reader = new BufferedReader(new InputStreamReader(geocodeOri.openStream()));
	         String line;
	         while ((line = reader.readLine()) != null){
	            int colon = 0;
	            colon = line.indexOf(":");
	            if(line.contains("formatted_address")){
	               finalLoc = line.substring(colon + 2);
	            }
	            if(line.contains("lat")) lat = line.substring(colon + 2, line.lastIndexOf(",") - 1);
	            if(line.contains("lng")) lng = line.substring(colon + 2);
	         }   
	      } catch (MalformedURLException e) {
	      // ...
	      } catch (IOException e) {
	      // ...
	      } 
	      float lt = Float.parseFloat(lat);
	      float ln = Float.parseFloat(lng);
	      GeoPt loc = new GeoPt(lt, ln); 
	      return loc;
	}

	public List<Ride> sortOrigin(List<Ride> ilist, GeoPt location) {
	List<floatRide> irlist = new ArrayList<floatRide>();
        //first, go through the list and compute values
        for (int i=0; i< ilist.size(); i++) {
            GeoPt istart = ilist.get(i).start;
            float val = this.findDist(istart, location);
            floatRide ir = new floatRide(val, ilist.get(i));
            irlist.add(ir);
        }
        //then, sort the list and return it
        return sort(irlist);
	}

	public List<Ride> originRadius(List<Ride> l, GeoPt origin, String radius){
		int b = Integer.parseInt(radius);
		List<Ride> rl = new ArrayList<Ride>();
    	for (int i = 0; i < l.size(); i++) {
    		if (this.findDist(l.get(i).start, origin) < b) {
    		   rl.add(l.get(i));
    		}
    	}
    	return rl;
	}

	public List<Ride> sortDest(List<Ride> ilist, GeoPt location) {
	List<floatRide> irlist = new ArrayList<floatRide>();
        //first, go through the list and compute values
        for (int i=0; i< ilist.size(); i++) {
            GeoPt istart = ilist.get(i).end;
            float val = this.findDist(istart, location);
            floatRide ir = new floatRide(val, ilist.get(i));
            irlist.add(ir);
        }
        //then, sort the list and return it
        return sort(irlist);
	}

	public List<Ride> destRadius(List<Ride> l, GeoPt dest, String radius){
		int b = Integer.parseInt(radius);
		List<Ride> rl = new ArrayList<Ride>();
    	for (int i = 0; i < l.size(); i++) {
    		if (this.findDist(l.get(i).end, dest) < b) {
    			rl.add(l.get(i));
    		}
    	}
    	return rl;
	}

	public float convertTime(String time) {
		float hour = 0;
		float minutes = 0;
		if (time == null) {
			return 0;
		}
        int index = time.indexOf(':');
        if(index == -1) hour = Integer.parseInt(time);
        else{
            hour = Integer.parseInt(time.substring(0, index));
            if(index == time.length() - 1) {
                minutes = Integer.parseInt(time.substring(index + 1));
            }
        }
        float hours = hour + minutes/60;
        return hours;
	}
	
	//sortDepart: this function uses sort to sort a list by the departure
	//time closest to the input time
    public List<Ride> sortDepart(float length, String time){
		float inputtime = convertTime(time);
        List<floatRide> irlist = new ArrayList<floatRide>();//[this.inputlist.size()]
        //first, go through the list and compute values
        for (int i=0; i< this.inputlist.size(); i++) {
            String depart = this.inputlist.get(i).depart;
            float hours = this.convertTime(depart);
            float val = Math.abs(hours - inputtime);
            floatRide ir = new floatRide(val, this.inputlist.get(i));
            irlist.add(ir);
        }
        //then, sort the list and return it
        return sort(irlist);
    }

    //sortArrive: this function uses sort to sort a list by the arrival
    //time closest to the input time
    public List<Ride> sortArrive(float length, String time){
		float inputtime = this.convertTime(time);
        int hour = 0;
        int minutes = 0;
        List<floatRide> irlist = new ArrayList<floatRide>();//[this.inputlist.size()]
        //first, go through the list and compute values
        for (int i=0; i< this.inputlist.size(); i++) {
            String arrive = this.inputlist.get(i).arrive;
            float hours = this.convertTime(arrive);
            float val = Math.abs(hours - inputtime);
            floatRide ir = new floatRide(val, this.inputlist.get(i));
            irlist.add(ir);
        }
        //then, sort the list and return it	
        return sort(irlist);
    }

    //filterByDrive: takes in a list of rides and returns a list of rides 
    //that match the boolean
    public List<Ride> filterByDrive(List<Ride> l, boolean b) {
    	List<Ride> rl = new ArrayList<Ride>();
    	for (int i = 0; i < l.size(); i++) {
    		if (l.get(i).drive == b) {
    			rl.add(l.get(i));
    		}
    	}
    	return rl;
    }
	
	//filterByEmail: takes in a list of rides and returns a list of rides 
    //that match the email
    public List<Ride> filterByEmail(List<Ride> l, String e) {
    	List<Ride> rl = new ArrayList<Ride>();
    	for (int i = 0; i < l.size(); i++) {
    		if (l.get(i).email.equals(e)) {
    			rl.add(l.get(i));
    		}
    	}
    	return rl;
    }

    //filterByDays: takes in a list of rides and returns a list of rides 
    //that match the days listed in boolean form
    public List<Ride> filterByDays(List<Ride> l, boolean su, boolean mo, boolean tu, boolean we, boolean th, boolean fr, boolean sa) {
    	List<Ride> rl = new ArrayList<Ride>();
    	for (int i = 0; i < l.size(); i++) {
    		if (su) {
	    		if (l.get(i).su) {
	    			rl.add(l.get(i));
	    		}
    		}
    		if (mo) {
	    		if (l.get(i).mo) {
	    			rl.add(l.get(i));
	    		}
    		}
    		if (tu) {
	    		if (l.get(i).tu) {
	    			rl.add(l.get(i));
	    		}
    		}
    		if (we) {
	    		if (l.get(i).we) {
	    			rl.add(l.get(i));
	    		}
    		}
    		if (th) {
	    		if (l.get(i).th) {
	    			rl.add(l.get(i));
	    		}
    		}
    		if (fr) {
	    		if (l.get(i).fr) {
	    			rl.add(l.get(i));
	    		}
    		}
    		if (sa) {
	    		if (l.get(i).sa) {
	    			rl.add(l.get(i));
	    		}
    		}
    	}
    	return rl;
    }

    //filterByDepart: takes in a list of rides and returns a list of rides 
    //that leave after the departure time
    public List<Ride> filterByDepart(List<Ride> l, String inputtime) {
    	List<Ride> rl = new ArrayList<Ride>();
    	for (int i = 0; i < l.size(); i++) {
    		if (this.convertTime(l.get(i).depart) >= this.convertTime(inputtime)) {
    			rl.add(l.get(i));
    		}
    	}
    	return rl;
    }

    //filterByArrive: takes in a list of rides and returns a list of rides 
    //that arrive before the arrival time
    public List<Ride> filterByArrive(List<Ride> l, String inputtime) {
    	List<Ride> rl = new ArrayList<Ride>();
    	for (int i = 0; i < l.size(); i++) {
    		if (this.convertTime(l.get(i).arrive) <= this.convertTime(inputtime)) {
    			rl.add(l.get(i));
    		}
    	}
    	return rl;
    }
    public List<Ride> filterBySeatsMore(List<Ride> l, String inputseats){
		List <Ride> rl = new ArrayList<Ride>();
		int seats = Integer.parseInt(inputseats);
		for (int i = 0; i < l.size(); i++) {
    		if (l.get(i).seats >= seats) {
    			rl.add(l.get(i));
    		}
    	}
    	return rl;
	}

	public List<Ride> filterBySeatsLess(List<Ride> l, String inputseats){
		List <Ride> rl = new ArrayList<Ride>();
		int seats = Integer.parseInt(inputseats);
		for (int i = 0; i < l.size(); i++) {
    		if (l.get(i).seats <= seats) {
    			rl.add(l.get(i));
    		}
    	}
    	return rl;
	}

}
