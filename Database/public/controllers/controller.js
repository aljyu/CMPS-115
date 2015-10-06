var myApp = angular.module('myApp', []);
myApp.controller('AppCtrl', ['$scope', '$http', function($scope, $http) {
	
	console.log("Hello World from controller");

	person1 = {
	Name: 'Tim',
	Email: 'tim@gmail.com',
	Seats: '(111) 111-1111'
	}

}]);


var contactlist = [person1];
$scope.contactlist = contactlist;

}