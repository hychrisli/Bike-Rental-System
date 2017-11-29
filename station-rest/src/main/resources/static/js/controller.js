'use strict';

var app = angular.module('station', [ 'ngResource' ]);

app.factory("CheckoutService", function($resource) {
	return $resource("station/checkout");
});

app.controller('station-ctrl', function($scope, $log, CheckoutService) {
	$scope.checkout = function() {
		var req = {
			"user_id" : $scope.userId
		};
		console.log(req);
		var svc = new CheckoutService(req);
		svc.$save(function(res){
			console.log(res)
			console.log(res.is_success)
		})
		//var res = CheckoutService.save(req);
		//console.log(res);
	};
});