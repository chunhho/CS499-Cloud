(function() {
    'use strict';

    angular
        .module('cs499A2App')
        .controller('RoleController', RoleController);

    RoleController.$inject = ['$scope', '$state', 'Role'];

    function RoleController ($scope, $state, Role) {
        var vm = this;

        vm.roles = [];

        loadAll();

        function loadAll() {
            Role.query(function(result) {
                vm.roles = result;
                vm.searchQuery = null;
            });
        }
    }
})();
