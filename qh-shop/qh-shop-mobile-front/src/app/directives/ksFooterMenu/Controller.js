class Controller {

    constructor() {

        var vm = this;
        vm.menus = [

            {
                name: 'home',
                iconLiga: "home",
                iconClass: "ag-home",
                text: "首页",
                state: "main.brandApp.store.home"
            },
            {
                name: 'manage-buy',
                iconLiga: "manage-buy",
                iconClass: "ag-manage-buy",
                text: "购物车",
                state: "main.brandApp.store.cart"
            },
            {
                name: 'center',
                iconLiga: "profile",
                iconClass: "ag-profile",
                text: "我的",
                state: "main.brandApp.store.personalCenter.centerHome"
            }
        ];

    }

}

Controller.$inject = [];

export default Controller;
