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
public class DoubleRide{
	double value;
	Ride ride;

	DoubleRide(double val, Ride r) {
		value=val;
		ride = r;
	}
	
	Ride getRide(){
		return ride;
	}
	
	double getValue(){
		return value;
	}
	
}
