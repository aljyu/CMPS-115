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
public class Keys {
   @Id public String type;
   public String value;
   Keys(){
   }
   Keys(String name, String key){
      type = name;
      value = key;
   }
}
