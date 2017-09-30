var app = angular.module('demo', []);

app.controller('Game', function ($scope, $http) {
    $scope.listGames = function () {
        $http.get("/list")
            .then(function (response) {
                $scope.games = response.data;
            });
    };
    $scope.newGame = function () {
        $http.post("/prepare").then(function () {
            $scope.listGames();
        });
    };
    $scope.addPlayer = function (gameId, index) {
        var n = document.getElementById("playerName" + index).value
        $http.post("/game/" + gameId + "/players?name=" + n).then(function () {
            $scope.listGames();
        });
    };

    $scope.listGames();
});