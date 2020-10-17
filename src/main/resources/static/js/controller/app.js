var app = angular.module('app', ['ngRoute','ngResource','ui.bootstrap.tpls', 'ui.bootstrap.modal','ui.utils']);
app.config(function($routeProvider){
    $routeProvider
        .when('/home',{
            templateUrl: '/views/home.html',
            controller: 'homeController'
        }).when('/loanInquiry',{
            templateUrl: '/views/loanInquiry.html',
            controller: 'loanInquiryController'
        })
        .otherwise(
            { redirectTo: '/home'}
        );
});




