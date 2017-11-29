'use strict';

var app = angular.module('station', [ 'ngResource' ]);

app.factory('StationService', function($resource) {
	return $resource("station/:id");
});

app.factory('CheckoutService', function($resource) {
	return $resource("station/checkout");
});

app.factory('CheckinService', function($resource) {
	return $resource('station/checkin');
});

app.controller('station-ctrl', function($scope, StationService) {
	var ids = StationService.get(function() {
		console.log(ids);
		console.log(ids.station_ids);
		$scope.stationIds = ids.station_ids;
	});

	$scope.onClick = function() {
		var station = StationService.get({
			id : $scope.selId
		}, function() {
			console.log(station);
			$scope.stationName = "Station: " + station.name;
			$scope.availBikes = "Available bikes: " + station.availBikes;
		});
	};
});

app.controller('checkout-ctrl', function($scope, CheckoutService) {
	$scope.checkout = function() {
		var req = {
			"user_id" : $scope.userId,
			"station_id": $scope.selId
		};
		console.log(req);
		var svc = new CheckoutService(req);
		svc.$save(function(res) {
			console.log(res)
			console.log(res.is_success)
			if (res.is_success == false) {
				$scope.result = "Checkout Unsuccessful";
			} else {
				$scope.result = "Successful checkout! Bike ID: " + res.bike_id;
			}

		})
		// var res = CheckoutService.save(req);
		// console.log(res);
	};
});

app.controller('checkin-ctrl', function($scope, CheckinService) {
	$scope.checkin = function() {
		var req = {
			"bike_id" : $scope.bikeId,
			"station_id": $scope.selId
		};
		console.log(req);
		var svc = new CheckinService(req);
		svc.$save(function(res) {
			console.log(res)
			console.log(res.checked_in)
			if (res.checked_in == false) {
				$scope.result = "Checkin Unsuccessful";
			} else {
				$scope.result = "Successfully checked in! Grand Total: $" + res.grand_total;
			}

		});
	};
});