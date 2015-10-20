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
   @Id public String riderEmail;
   @Index public String startLocation;
   @Index public String endLocation;
   @Index public String departureTime;
    
   // add more fields later
   // default needed for Objectify 
   public Ride(){
   }
   public Ride(String email, String start, String end, String time){
      riderEmail = email;
      startLocation = start;
      endLocation = end;
      departureTime = time;
   }
}
