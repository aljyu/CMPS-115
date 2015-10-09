var Db = require('mongodb').Db,
    MongoClient = require('mongodb').MongoClient,
    Server = require('mongodb').Server,
    ReplSetServers = require('mongodb').ReplSetServers,
    ObjectID = require('mongodb').ObjectID,
    Binary = require('mongodb').Binary,
    GridStore = require('mongodb').GridStore,
    Grid = require('mongodb').Grid,
    Code = require('mongodb').Code,
    BSON = require('mongodb').pure().BSON,
    assert = require('assert');

//TODO: replace this with our actual database
var db = new Db('test', new Server('localhost', 27017));
// Establish connection to db
db.open(function(err, db) {

      // Retrieve all the documents in the collection ( replace 'collection' with whatever'searchby' is )
      collection.find().toArray(function(err, documents) {
        	if searchby.equals('origin') {
			//need to use Google maps somehow - I'm calling it 'maps' though I'm sure it will 
			//be more complicated than that
			var distance;
			var sorted; //=new array?
			for element in array:
				distance = maps.distance(element.origin, searchterm);
				//insert element into sorted using some search algorithm based on minimizing distance
				//return first x(how many?) elements of sorted
		}
		else if searchby.equals('dest') {
			var distance;
			var sorted; //=new array?
			for element in array:
				distance = maps.distance(element.dest, searchterm);
				//insert element into sorted using some search algorithm based on minimizing distance
				//return first x(how many?) elements of sorted
		}
		else if searchby.equals('depart') {
			var timediff;
			var sorted; //=new array?
			for element in array:
				//note: this assumes a 24 hour clock and does NOT take into account proximity 
				//of 24:00 and 1:00
				timediff = abs(element.depart - searchterm);
				//insert element into sorted using some search algorithm based on minimizing timediff
				//return first x(how many?) elements of sorted
		}

		else if searchby.equals('arrive') {
			var timediff;
			var sorted; //=new array?
			for element in array:
				//note: this assumes a 24 hour clock and does NOT take into account proximity 
				//of 24:00 and 1:00
				timediff = abs(element.arrive - searchterm);
				//insert element into sorted using some search algorithm based on minimizing timediff
				//return first x(how many?) elements of sorted
		}
		else if searchby.equals('drive') {
			var sorted;
			if searchterm.equals('true') {
				for element in array:
					if (element.drive.equals('true') {
						//add element to sorted
					}
			}
			else {
				for element in array:
					if (element.drive.equals('false') {
						//add element to sorted
					}
			}
			//return first x(how many?) elements of sorted
		}
		else {error:invalid searchterm}
	

        db.close();
      });
   
});