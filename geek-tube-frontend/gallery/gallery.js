(function () {
    'use strict';

    angular.module('app.gallery', [])
        .controller('GalleryController', GalleryController);

    let previews = [
        {
            id: 1,
            description: `First video`,
            imageUrl: `https://loremflickr.com/213/106`
        },
        {
            id: 2,
            description: `Second video`,
            imageUrl: `https://loremflickr.com/213/106`
        },
        {
            id: 3,
            description: `Third video`,
            imageUrl: `https://loremflickr.com/213/106`
        },
        {
            id: 5,
            description: `Fifth video`,
            imageUrl: `https://loremflickr.com/213/106`
        },
        {
            id: 6,
            description: `Sixth video`,
            imageUrl: `https://loremflickr.com/213/106`
        },
        {
            id: 7,
            description: `Seventh video`,
            imageUrl: `https://loremflickr.com/213/106`
        },
        {
            id: 8,
            description: `Eighth video`,
            imageUrl: `https://loremflickr.com/213/106`
        },
    ]

    function GalleryController($scope) {
        $scope.previews = previews;
    }

})();