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
	List<Ride> inputlist;
	
	Ridelist(List<Ride> list){
		inputlist = list;
	}
	
	public List<Ride> sortDepart(int length, int time){
		List<Ride> rlist = new List<Ride>[inputlist.length()];
		int start;
		int end;
		
		return rlist.sublist(start,end);
	}
}