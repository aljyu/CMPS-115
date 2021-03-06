package com.rideshare;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.Key;
import com.google.appengine.api.datastore.GeoPt;
import java.lang.String;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Entity
public class Ride implements java.io.Serializable{
   @Id public Long id;
   @Index public String name;
   @Index public String email;
   @Index public String origin;
   @Index public String destination;
   @Index public String depart;
   @Index public String arrive;
   @Index public Date ridedate;
   @Index public GeoPt start;
   @Index public GeoPt end;
   @Index public boolean drive;
   @Index public boolean mo;
   @Index public boolean tu;
   @Index public boolean we;
   @Index public boolean th;
   @Index public boolean fr;
   @Index public boolean sa;
   @Index public boolean su;
   @Index public int seats;
    
   // add more fields later
   // default needed for Objectify 
   public Ride(){
   }
   public Ride(String fullname, String riderEmail, 
               String orig, String dest, 
               String departure, String arrival, Date date,
               GeoPt st, GeoPt en, boolean driver, 
               boolean sun, boolean mon, 
               boolean tue, boolean wed, 
               boolean thu, boolean fri, 
               boolean sat, int seat){
        Random r = new Random();
        id = r.nextLong();
	name = fullname;
	email = riderEmail;
	origin = orig;
	destination = dest;
	depart = departure;
	arrive = arrival;
        ridedate = date;
        start = st;
        end = en;
	drive = driver;
	su = sun;
	mo = mon;
	tu = tue;
	we = wed;
	th = thu;
	fr = fri;
	sa = sat;
	seats = seat;
   }

	public String getName(){
		return name;
	}
	
	public void setName(String nName){
		name = nName;
	}
}
