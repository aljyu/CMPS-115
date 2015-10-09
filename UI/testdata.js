angular.module('myApp', []).controller('namesCtrl', function($scope) {
    $scope.names = [
        {origin: 'downtown', dest: 'crown',  drive: 'd', depart: '13:30', arrive: '14:00', name:'Jani King', email: 'jkking@ucsc.edu'},
        {origin: 'uptown',   dest: 'cowell', drive: 'r', depart: '14:30', arrive: '15:00', name:'Hege Bush', email: 'hcbush@ucsc.edu'},
        {origin: 'midtown',  dest: 'porter', drive: 'd', depart: '9:00',  arrive: '9:30',  name:'Kai Lewis', email: 'kmlewis@ucsc.edu'}
    ];
});