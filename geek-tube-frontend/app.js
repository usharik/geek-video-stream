(function () {
    'use strict';

    angular
        .module('app', [
            'ui.router',
            'app.navbar',
            'app.gallery',
            'app.view',
            'app.upload',
            'app.footer'
        ])
        .constant('config', {
            'backendUrl': 'http://localhost:8080'
        });
})();
