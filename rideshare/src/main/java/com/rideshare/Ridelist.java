package com.rideshare;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.Key;
import com.google.appengine.api.search.GeoPoint;

import java.lang.String;
import java.util.Date;
import java.util.List;
import java.util.*;
import java.lang.Math;

@Entity
public class Ridelist{
	//class variables
	List<Ride> inputlist;

	
	//constructor 
	Ridelist(List<Ride> list){
		inputlist = list;
	}
	
	private double findDist(GeoPoint orig, GeoPoint dest){
		double dist;
		int R = 6371000; //Radius of the earth in meters
		double latO = Math.toRadians(orig.getLatitude());
		double latD = Math.toRadians(dest.getLatitude());
		double lonO = Math.toRadians(orig.getLongitude());
		double lonD = Math.toRadians(dest.getLongitude());
		double latDiff = latD - latO;
		double lonDiff = lonD - latO;
		double a = Math.sin(latDiff/2) * Math.sin(latDiff/2) + 
		           Math.cos(latO) * Math.cos(latD) * Math.sin(lonDiff/2) * Math.sin(lonDiff/2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		
		dist = R * c;
		
		return dist;
	}
	
	//Sort: This function should take in a list of DoubleRides (simple class 
	//consisting of an double and a ride, and sort the rides in ascending 
	//order by their associated doubles, and return just the list of rides 
	//in the correct order. 
	private List<Ride> sort(List<DoubleRide> iRide) {
		List<Ride> rList = new ArrayList<Ride>(); //[this.inputlist.size()]
		List<DoubleRide> sorted = logsort(iRide);
		for(int i = 0; i < sorted.size(); i++){
			rList.add(sorted.get(i).ride);
		}
		return rList;
	}
	
	private List<DoubleRide> logsort(List<DoubleRide> rides){
		List<DoubleRide> ret = new ArrayList<DoubleRide>();//[rides.size()]
		if(rides.size() <= 1){
			return rides;
		} else {
			int size = rides.size();
			List<DoubleRide> left = logsort(rides.subList(0,size/2-1));
			List<DoubleRide> right = logsort(rides.subList(size/2,size-1));
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

	//sortDepart: this function uses sort to sort a list by the departure
	//time closest to the input time
	public List<Ride> sortDepart(double length, double time){
		List<DoubleRide> irlist = new ArrayList<DoubleRide>();//[this.inputlist.size()]
		//first, go through the list and compute values
		for (int i=0; i< this.inputlist.size(); i++) {
			double val = Math.abs(this.inputlist.get(i).depart - time);
			DoubleRide ir = new DoubleRide(val, this.inputlist.get(i));
			irlist.add(ir);
		}
		//then, sort the list and return it
		return sort(irlist);
	}

	//sortArrive: this function uses sort to sort a list by the arrival
	//time closest to the input time
	public List<Ride> sortArrive(double length, double time){
		List<DoubleRide> irlist = new ArrayList<DoubleRide>();//[this.inputlist.size()]
		//first, go through the list and compute values
		for (int i=0; i< this.inputlist.size(); i++) {
			double val = Math.abs(this.inputlist.get(i).arrive - time);
			DoubleRide ir = new DoubleRide(val, this.inputlist.get(i));
			irlist.add(ir);
		}
		//then, sort the list and retur it
		return sort(irlist);
	}


}