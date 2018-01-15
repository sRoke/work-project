class Controller {

    constructor() {

        var vm = this;
        vm.menus = [
            {
                name: 'home',
                iconLiga: "home",
                iconClass: "ag-home",
                text: "生意",
                state: "main.voteApp.home"
            },
            // {
            //     name: 'manageList',
            //     iconLiga: "purchase",
            //     iconClass: "ag-truck-o",
            //     text: "进",
            //     state: "main.voteApp.manageList"
            // },
            // {
            //     name: 'sales',
            //     iconLiga: "fileboard",
            //     iconClass: "ag-fileboard",
            //     text: "销",
            //     state: "main.voteApp.sales.salesMain"
            // },
            // {
            //     name: 'stock',
            //     iconLiga: "grid-system",
            //     iconClass: "ag-grid-system",
            //     text: "存",
            //     state: "main.voteApp.stock"
            // },
            // {
            //     name: 'report',
            //     iconLiga: "showcase",
            //     iconClass: "ag-showcase",
            //     text: "报表",
            //     state: "main.voteApp.report.reportMain"
            // },
            // {
            //     name: 'shelves',
            //     iconLiga: "shelf",
            //     iconClass: "ag-shelf",
            //     text: "货架",
            //     state: "main.voteApp.shelves"
            // },
            // {
            //     name: 'record',
            //     iconLiga: "work-o",
            //     iconClass: "ag-work-o",
            //     text: "参谋",
            //     state: "main.voteApp.record"
            // },
            {
                name: 'center',
                iconLiga: "profile",
                iconClass: "ag-profile",
                text: "我的",
                state: "main.voteApp.center.main"
            }
        ];

    }

}

Controller.$inject = [];

export default Controller;
