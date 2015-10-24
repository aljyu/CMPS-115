package com.rideshare;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.Key;

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
//   @Index public int depart;
//   @Index public int arrive;
   @Index public boolean drive;
    
   // add more fields later
   // default needed for Objectify 
   public Ride(){
   }
   public Ride(String fullname, String riderEmail, 
               String orig, String dest, String departure, String arrival, boolean driver){
	name = fullname;
	email = riderEmail;
	origin = orig;
	destination = dest;
	depart = departure;
	arrive = arrival;
	drive = driver;
   }	
}
