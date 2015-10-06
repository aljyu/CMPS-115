var myApp = angular.module("myApp", []);
myApp.controller("AppCtrl", ["$scope", "$http", function($scope, $http) {
	console.log("Hello World from the controller");
	
	var refresh = function() {
		$http.get("/contactlist").success(function(response) {
			//console.log will tell us if we succcessfully got the data we requested
			console.log("I got the data I requested");
			//Puts the data into our HTML file
			$scope.contactlist = response;
			$scope.contact = "";
		});
	};

	refresh();

	$scope.addContact = function() {
		$http.post('/contactlist', $scope.contact).success(function(response){
				console.log(response);
				refresh();
			});
	};

	$scope.remove = function(id) {
  		console.log(id);
  		$http.delete('/contactlist/' + id).success(function(response) {
    	refresh();
  		});
	};


}]);