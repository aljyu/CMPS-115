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
	public Ridelist(List<Ride> list){
		inputlist = list;
	}
	
	public double testFindDist(){
		//List<double> l = new ArrayList<double>();
		double l = 0.0;
		//l = findDist(createGP(50,03,59,-05,-42,-53),createGP(58,38,38,-03,-04,-12));
		//l = findDist(createGP(50,03,59,-05,-42,-53),createGP(50,03,59,-05,-42,-54));
		l = findDist(createGP(50,03,59,-05,-42,-53),createGP(40,44,55,-73,-59,-11));
		return l;
	}
	
	private GeoPoint createGP(double d1, double m1, double s1, double d2, double m2, double s2){
		double lat = createRad(d1,m1,s1);
		double lon = createRad(d2,m2,s2);
		GeoPoint g = new GeoPoint(lat,lon);
		return g;
	}
	
	private double createRad(double d, double m, double s){
		double mn = m/60;
		double sn = s/3600;
		return d + mn + sn;
	}
	
	private double findDist(GeoPoint orig, GeoPoint dest){
		double dist;
		int R = 6371; //Radius of the earth in meters
		double latO = Math.toRadians(orig.getLatitude());
		double latD = Math.toRadians(dest.getLatitude());
		double lonO = Math.toRadians(orig.getLongitude());
		double lonD = Math.toRadians(dest.getLongitude());
		double latDiff = latD - latO;
		double lonDiff = lonD - lonO;
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
			List<DoubleRide> left = logsort(rides.subList(0,size/2));
			List<DoubleRide> right = logsort(rides.subList(size/2,size));
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

	public double convertTime(String time) {
		double hour = 0;
		double minutes = 0;
        int index = time.indexOf(':');
        if(index == -1) hour = Integer.parseInt(time);
        else{
            hour = Integer.parseInt(time.substring(0, index));
            if(index == time.length() - 1) {
                minutes = Integer.parseInt(time.substring(index + 1));
            }
        }
        double hours = hour + minutes/60;
        return hours;
	}
	
	//sortDepart: this function uses sort to sort a list by the departure
	//time closest to the input time
    public List<Ride> sortDepart(double length, String time){
		double inputtime = convertTime(time);
        List<DoubleRide> irlist = new ArrayList<DoubleRide>();//[this.inputlist.size()]
        //first, go through the list and compute values
        for (int i=0; i< this.inputlist.size(); i++) {
            String depart = this.inputlist.get(i).depart;
            double hours = this.convertTime(depart);
            double val = Math.abs(hours - inputtime);
            DoubleRide ir = new DoubleRide(val, this.inputlist.get(i));
            irlist.add(ir);
        }
        //then, sort the list and return it
        return sort(irlist);
    }

    //sortArrive: this function uses sort to sort a list by the arrival
    //time closest to the input time
    public List<Ride> sortArrive(double length, String time){
		double inputtime = this.convertTime(time);
        int hour = 0;
        int minutes = 0;
        List<DoubleRide> irlist = new ArrayList<DoubleRide>();//[this.inputlist.size()]
        //first, go through the list and compute values
        for (int i=0; i< this.inputlist.size(); i++) {
            String arrive = this.inputlist.get(i).arrive;
            double hours = this.convertTime(arrive);
            double val = Math.abs(hours - inputtime);
            DoubleRide ir = new DoubleRide(val, this.inputlist.get(i));
            irlist.add(ir);
        }
        //then, sort the list and return it	
        return sort(irlist);
    }

    //filterByDrive: takes in a list of rides and returns a list of rides 
    //that match the boolean
    public List<Ride> filterByDrive(List<Ride> l, boolean b) {
    	List<Ride> rl = new List<Ride>[];
    	for (int i = 0; i < l.length; i++) {
    		if (l[i].drive = b) {
    			rl.add(l[i]);
    		}
    	}
    	return rl;
    }
	
	//filterByEmail: takes in a list of rides and returns a list of rides 
    //that match the email
    public List<Ride> filterByEmail(List<Ride> l, String e) {
    	List<Ride> rl = new List<Ride>[];
    	for (int i = 0; i < l.length; i++) {
    		if (l[i].email.equals(e)) {
    			rl.add(l[i]);
    		}
    	}
    	return rl;
    }


	/*public List<Ride> sortDistance(){
		
	}*/
	
}
