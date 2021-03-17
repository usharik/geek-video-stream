(function () {
    'use strict';

    angular.module('app')
        .config(config);

    function config($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.when('', '/');
        $urlRouterProvider.otherwise('/');

        $stateProvider
            .state('root', {
                abstract: true,
                url: '/',
                data: {
                    title: 'Home'
                },
                views: {
                    'navbar': {
                        templateUrl: 'navbar/navbar.html',
                        controller: 'NavbarController',
                        controllerAs: 'NC'
                    },
                    'content': {
                        template: 'depends on step'
                    },
                    'footer': {
                        templateUrl: 'footer/footer.html',
                        controller: 'FooterController',
                        controllerAs: 'FC'
                    }
                }
            })
            .state('root.gallery', {
                url: '',
                data: {
                    title: 'Gallery'
                },
                views: {
                    'content@': {
                        templateUrl: 'gallery/gallery.html',
                        controller: 'GalleryController',
                        controllerAs: 'GC'
                    }
                }
            })
            .state('root.view', {
                url: 'view/:id',
                data: {
                    title: 'View'
                },
                views: {
                    'content@': {
                        templateUrl: 'view/view.html',
                        controller: 'GalleryController',
                        controllerAs: 'GC'
                    }
                }
            })
            .state('root.upload', {
                url: 'upload',
                data: {
                    title: 'Upload'
                },
                views: {
                    'content@': {
                        templateUrl: 'upload/upload.html',
                        controller: 'UploadController',
                        controllerAs: 'UC'
                    }
                }
            });
    }
})();