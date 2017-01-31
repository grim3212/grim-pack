var module = angular.module('Main', ['ui.router', 'ui.bootstrap', 'ngTouch', 'ngAnimate']);

module.run(['$rootScope', '$state', '$stateParams', function ($rootScope, $state, $stateParams) {
    $rootScope.$state = $state;
    $rootScope.$stateParams = $stateParams;
    $rootScope.superTitle = 'Default';
}]);

module.service('partService', function () {
    var grimpackParts = {
        "Core": {
            name: "Grim Core",
            underTitle: "The core part containing shared classes and the instruction manual"
        },
        "Cuisine": {
            name: "Grim Cuisine",
            underTitle: "Cuisine contains food and health related items"
        },
        "Decor": {
            name: "Grim Decor",
            underTitle: "Decor is for decorating and eye candy"
        },
        "Industry": {
            name: "Grim Industry",
            underTitle: "Industry allows for more complex machinery or items"
        },
        "Tools": {
            name: "Grim Tools",
            underTitle: "Tools adds in many different weapons and tools"
        },
        "Util": {
            name: "Grim Util",
            underTitle: "Util provides small utilities like automatically replacing items or FusRoDah!"
        },
        "World": {
            name: "Grim World",
            underTitle: "World contains special world generation and mobs"
        }
    };

    this.getGrimPackParts = function () {
        return grimpackParts;
    };

    this.getPartFromName = function (partName) {
      angular.forEach(grimpackParts, function (value, key) {
            if (key == partName)
                return value;
        });

        return grimpackParts[0];
    };
});

module.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

    'use strict';

    $urlRouterProvider.otherwise('/home');

    $stateProvider
        .state('Home', {
            url: '/home',
            templateUrl: 'pages/home.html',
            controller: 'HomeCtrl'
        })
        .state('About', {
            url: '/about',
            templateUrl: 'pages/about.html',
            controller: 'AboutCtrl'
        })
        .state('Parts', {
            abstract: true,
            url: '/parts/:partName',
            templateUrl: 'pages/parts.html',
            controller: 'PartsCtrl'
        })
        .state('Parts.page', {
            url: '',
            views: {
                'info@Parts': {
                    templateUrl: function (stateParams) {
                        var fileName = stateParams.partName.replace(/[\s]/g, '').toLowerCase();
                        return 'pages/parts/' + fileName + 'Info.html';
                    },
                    controller: 'PartsInfoCtrl'
                }
            }
        });
}]);

module.controller('MainCtrl', ['$scope', '$uibModal', '$location', 'partService', function ($scope, $uibModal, $location, partService) {
    $scope.title = "Grim Pack";

    $scope.grimpackParts = partService.getGrimPackParts();

    $scope.scrollTo = function (id) {
        smoothScroll(document.getElementById(id));
    };
}]);

module.controller('HomeCtrl', ['$rootScope', '$scope', function ($rootScope, $scope) {
    $rootScope.superTitle = 'Home';
}]);

module.controller('AlertModalCtrl', ['$scope', '$uibModalInstance', '$sce', 'data', function ($scope, $uibModalInstance, $sce, data) {
    $scope.trust = $sce.trustAsHtml;
    $scope.data = data;
    $scope.close = function () {
        $uibModalInstance.close($scope.data);
    };
}]);

module.controller('AboutCtrl', ['$rootScope', '$scope', function ($rootScope, $scope) {
    $rootScope.superTitle = 'About';
    $scope.underTitle = "Learn how the hosting works!";
}]);

module.controller('PartsCtrl', ['$rootScope', '$scope', '$stateParams', 'partService', function ($rootScope, $scope, $stateParams, partService) {
    $scope.name = $stateParams.partName;
    $rootScope.superTitle = $scope.name;
    $scope.currentPart = partService.getPartFromName($scope.name);
}]);

module.controller('PartsInfoCtrl', ['$scope', 'partService', function ($scope, partService) {
}]);
