<?php

$m = new MongoClient();

// select database
$db = $m->comedy;

// select a collection
$collection = $db->cartoons;

// fix this to include all elements
// adds a record
$document = array ( "name" => "($POST));
$collection->insert($document);

// prints the database for debugging
$cursor = $collection->find();
foreach ($cursor as $document) {
   echo $document["title"] . "\n";
}

?>

