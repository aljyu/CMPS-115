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

@Entity
public class Ride {
   @Id public String name;
   @Index public String email;
   @Index public String origin;
   @Index public String destination;
   @Index public String depart;
   @Index public String arrive;
   @Index public GeoPt start;
   @Index public GeoPt end;
//   @Index public int depart;
//   @Index public int arrive;
   @Index public boolean drive;
   @Index public boolean mo;
   @Index public boolean tu;
   @Index public boolean we;
   @Index public boolean th;
   @Index public boolean fr;
   @Index public boolean sa;
   @Index public boolean su;
    
   // add more fields later
   // default needed for Objectify 
   public Ride(){
   }
   public Ride(String fullname, String riderEmail, 
               String orig, String dest, String departure, String arrival, 
               GeoPt st, GeoPt en, boolean driver, 
               boolean sun, boolean mon, boolean tue, boolean wed, boolean thu, boolean fri, boolean sat){
	name = fullname;
	email = riderEmail;
	origin = orig;
	destination = dest;
	depart = departure;
	arrive = arrival;
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
   }	
}
