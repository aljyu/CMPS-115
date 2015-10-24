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
   @Id public String email;
   @Index public String name;
   @Index public String origin;
   @Index public String destination;
   @Index public int depart;
   @Index public int arrive;
   @Index public boolean drive;
    
   // add more fields later
   // default needed for Objectify 
   public Ride(){
   }
   public Ride(String name, String email, String origin, String dest, int depart, int arrive, boolean drive){
	name = name;
	email = email;
	origin = origin;
	dest = dest;
	depart = depart;
	arrive = arrive;
	drive = drive;
   }	
}
