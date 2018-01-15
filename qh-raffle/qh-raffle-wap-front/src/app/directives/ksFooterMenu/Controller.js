class Controller {

    constructor() {

        var vm = this;
        vm.menus = [
            {
                name: 'home',
                iconLiga: "home",
                iconClass: "ag-home",
                text: "生意",
                state: "main.raffleApp.home"
            },
            // {
            //     name: 'manageList',
            //     iconLiga: "purchase",
            //     iconClass: "ag-truck-o",
            //     text: "进",
            //     state: "main.raffleApp.manageList"
            // },
            // {
            //     name: 'sales',
            //     iconLiga: "fileboard",
            //     iconClass: "ag-fileboard",
            //     text: "销",
            //     state: "main.raffleApp.sales.salesMain"
            // },
            // {
            //     name: 'stock',
            //     iconLiga: "grid-system",
            //     iconClass: "ag-grid-system",
            //     text: "存",
            //     state: "main.raffleApp.stock"
            // },
            // {
            //     name: 'report',
            //     iconLiga: "showcase",
            //     iconClass: "ag-showcase",
            //     text: "报表",
            //     state: "main.raffleApp.report.reportMain"
            // },
            // {
            //     name: 'shelves',
            //     iconLiga: "shelf",
            //     iconClass: "ag-shelf",
            //     text: "货架",
            //     state: "main.raffleApp.shelves"
            // },
            // {
            //     name: 'record',
            //     iconLiga: "work-o",
            //     iconClass: "ag-work-o",
            //     text: "参谋",
            //     state: "main.raffleApp.record"
            // },
            {
                name: 'center',
                iconLiga: "profile",
                iconClass: "ag-profile",
                text: "我的",
                state: "main.raffleApp.center.main"
            }
        ];

    }

}

Controller.$inject = [];

export default Controller;
