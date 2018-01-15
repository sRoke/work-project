class Controller {

    constructor() {

        var vm = this;
        vm.menus = [
            {
                name: 'home',
                iconLiga: "home",
                iconClass: "ag-home",
                text: "生意",
                state: "main.brandApp.home"
            },
            // {
            //     name: 'manageList',
            //     iconLiga: "purchase",
            //     iconClass: "ag-truck-o",
            //     text: "进",
            //     state: "main.brandApp.manageList"
            // },
            // {
            //     name: 'sales',
            //     iconLiga: "fileboard",
            //     iconClass: "ag-fileboard",
            //     text: "销",
            //     state: "main.brandApp.sales.salesMain"
            // },
            // {
            //     name: 'stock',
            //     iconLiga: "grid-system",
            //     iconClass: "ag-grid-system",
            //     text: "存",
            //     state: "main.brandApp.stock"
            // },
            // {
            //     name: 'report',
            //     iconLiga: "showcase",
            //     iconClass: "ag-showcase",
            //     text: "报表",
            //     state: "main.brandApp.report.reportMain"
            // },
            // {
            //     name: 'shelves',
            //     iconLiga: "shelf",
            //     iconClass: "ag-shelf",
            //     text: "货架",
            //     state: "main.brandApp.shelves"
            // },
            // {
            //     name: 'cart',
            //     iconLiga: "cart",
            //     iconClass: "ag-cart",
            //     text: "购物车",
            //     state: "main.brandApp.cart"
            // },
            {
                name: 'center',
                iconLiga: "profile",
                iconClass: "ag-profile",
                text: "我的",
                state: "main.brandApp.center.main"
            }
        ];

    }

}

Controller.$inject = [];

export default Controller;
