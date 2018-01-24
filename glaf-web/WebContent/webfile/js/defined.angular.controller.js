var angularApp = angular.module("angularApp", []);

var $post = function($scope, $http, options) {
	return $http.post(options.url, options.data).success(function(response) {
		$scope.response = response || {};
	});
};

function officeCtrl($scope, $http){
	
	$scope.rows = [{name : 'klaus'},{name : 'fc'}];
}

function customersCtrl($scope, $http) {

	var options = {
		url : contextPath + '/mx/form/data/gridData',
		Query : function() {
			return {
				rid : "22e9a433e2f440f1ba125eee65021180",
				params : JSON.stringify({
					col1449467024417 : $scope.name || ''
				})
			};
		},
		pagination : true,
		pageSize : 10
	};

	ngGrid($scope, $http, options);

}

function ngGrid($scope, $http, options) {

	$scope.response = {}, options = options || {
		data : {}
	};
	$scope.$watch('response', function() {
		$scope.rows = $scope.response.data;
		$scope.total = $scope.response.total;
	});

	$scope.search = function() {
		options.data = (options.Query ? options.Query() : options.data) || {};
		options.data.page = $scope.page;
		options.data.pageSize = $scope.pageSize;
		$post($scope, $http, options);
	};

	if (options.pagination) {
		Paging($scope, $http, options, $scope.search);
	} else {
		$scope.search();
	}

}

function Paging($scope, $http, options, fn) {

	$scope.page = options.page || 1;
	$scope.pageSize = options.pageSize || 10;

	$scope.total = 0;
	$scope.totalPage = 0;

	$scope.paging = {
		first : function() {
			if ($scope.page == 1) {
				if (fn)
					fn();
			}
			$scope.page = 1;
		},
		prev : function() {
			if ($scope.page > 1)
				--$scope.page;
		},
		next : function() {
			if ($scope.page < $scope.totalPage)
				++$scope.page;
		},
		last : function() {
			$scope.page = $scope.totalPage;
		}
	};

	$scope.$watch('total', function() {
		$scope.totalPage = Math.ceil($scope.total / $scope.pageSize);
	});

	$scope.$watch('page', function() {
		if (fn)
			fn();
	});

}
