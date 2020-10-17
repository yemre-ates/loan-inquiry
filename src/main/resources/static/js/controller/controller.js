app.controller('homeController', ['$scope', '$http', '$routeParams','$route','$modal',
    function($scope, $http, $routeParams,$route,$location,$modal){
    $scope.headingTitle = "Welcome myProject .";
}]);

app.controller('loanInquiryController', ['$scope', '$http', '$routeParams','$route','$modal',
    function($scope, $http, $routeParams,$route,$location,$modal){
    $scope.headingTitle = "Loan Inquiry";
    $scope.modalMessage = "";
    $scope.modal = "";
    $scope.msisdnPattern = "(999) 999 99 99";
    $scope.tcknPattern = "99999999999";
    $scope.birthYearPattern = "9999";
    
    $scope.jsonObj =
    	{
    		"tckn":"",
    		"name":"",
    		"income":"",
    		"msisdn":"",
    		"birthYear":""
    	};
	
	 $scope.inquiry = function(){ 
		 var incm = $scope.jsonObj.income.match(/^\d+$/);    
		 
		 if($scope.jsonObj.tckn == "" || $scope.jsonObj.name == "" || $scope.jsonObj.income == "" || $scope.jsonObj.msisdn == "" || $scope.jsonObj.birthYear == "" ){
			 getPopup("alert alert-danger","Please fill in blank or missing fields");
		 }
		 else if (incm !== null){	
			     var url = '/loanInquiry';
				 var method = 'POST';
				 var json = JSON.stringify($scope.jsonObj);	
				 $http({
				  method: method,
				  url: url,
				  data:json,
				  headers: {				    
				  'Content-Type':'application/json'
				  }
				 }).success(function(data) {						 
					 console.log(data);
					 if(data.loanStatus === "NOK"){
						 getPopup("alert alert-danger",data.message);	
					 }else{
						 getPopup("alert alert-success",data.message +" Your limit is: " + data.limit );	
					 }
				 }).error(function(data, status, headers, config) {
					 console.log(data);
					 getPopup("alert alert-danger","Error from Inquiry service - " + data);				  
				 });
				 
		 }else {
			 getPopup("alert alert-danger","You should enter Number format into Monthly Income field.");	
		 }
		
	 }  
	 
	 getPopup = function (modal,message){
		 $scope.modal = modal;
		 $scope.modalMessage = message; 
		 jQuery('#infoModal').modal();		 
	 }
	 
}]);



