(function () {
    'use strict';

    angular.module('app.upload', [])
        .controller('UploadController', UploadController);

    function UploadController($scope, $http, config) {
        $scope.submit = function () {
            let fd = new FormData(document.forms.uploadForm);
            $http({
                url: config.backendUrl + "/upload",
                method: 'POST',
                data: fd,
                headers: { 'Content-Type': undefined},
                transformRequest: angular.identity
            })
        }
    }
})();