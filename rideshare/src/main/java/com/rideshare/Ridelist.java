package com.rideshare;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.Key;

import java.lang.String;
import java.util.Date;
import java.util.List;

import Ride;

@Entity
public class Ridelist{
	//class variables
	List<Ride> inputlist;

	
	//constructor 
	Ridelist(List<Ride> list){
		inputlist = list;
	}

	//Sort: This function should take in a list of IntRides (simple class 
	//consisting of an int and a ride, and sort the rides in ascending 
	//order by their associated ints, and return just the list of rides 
	//in the correct order. 
	private List<ride> sort(List<IntRide> l) {
		List<ride> rl = new List<ride>[inputlist.length()];
		return rl;
	}

	//sortDepart: this function uses sort to sort a list by the departure
	//time closest to the input time
	public List<Ride> sortDepart(int length, int time){
		List<IntRide> irlist = new List<IntRide>[inputlist.length()];
		//first, go through the list and compute values
		for (i=0; i< inputlist.length(); i++) {
			int val = Math.abs(inputlist[i].depart - time);
			IntRide ir = new IntRide(val, inputlist[i]);
			irlist[i]= ir;
		}
		//then, sort the list
		List<Ride> rlist = sort(irlist);
		return rlist.sublist(0,length);
	}

	//sortArrive: this function uses sort to sort a list by the arrival
	//time closest to the input time
	public List<Ride> sortArrive(int length, int time){
		List<IntRide> irlist = new List<IntRide>[inputlist.length()];
		//first, go through the list and compute values
		for (i=0; i< inputlist.length(); i++) {
			int val = Math.abs(inputlist[i].arrive - time);
			IntRide ir = new IntRide(val, inputlist[i]);
			irlist[i]= ir;
		}
		//then, sort the list
		List<Ride> rlist = sort(irlist);
		return rlist.sublist(0,length);
	}


}