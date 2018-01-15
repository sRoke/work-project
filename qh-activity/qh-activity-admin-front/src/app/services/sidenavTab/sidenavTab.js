import asTabStates from "./asTabStates";
import "angular";
let $rootScope, $log, $state, $stateParams, $timeout;

class sidenavTab {

    constructor(_$rootScope, _$log, _$state, _$stateParams, _$timeout) {
        $rootScope = _$rootScope;
        $log = _$log;
        $state = _$state;
        $stateParams = _$stateParams;
        $timeout = _$timeout;

        this.tabs = [];
        this.tabIndex = 0;   // 当前选中的 tab 的索引
    }

    // 当前选中的 tab 的 uiView
    get tabUiView() {
        return this.tabs[this.tabIndex].uiView;
    }

    set tabUiView(newVal) {
        if (this.tabs[this.tabIndex].uiView === newVal) {
            return;
        }
        let idx = this.tabs.findIndex((ele) => {
            ele.uiView === newVal
        });
        if (idx < 0) {
            // FIXME <0 ?
        }
        this.tabIndex = idx;
    }

    findTab(state) {
        let stateName = state;
        if (typeof(state) === "object") {
            stateName = state.name;
        }
        // 找到模板
        return asTabStates.find((ele) => {
            return stateName.startsWith(ele.matchStates);
        });
    }

    getOpenedTabIndex(tab) {
        return this.tabs.indexOf(tab);
    }

    openTab(tab) {
        this.tabs.push(tab);
    }


    // 只有 打开tab/跳转到对应状态 时才调用。
    focusTab(tabOrIdx) {
        let tabIndex;
        if (typeof tabOrIdx === "undefined") {
            tabIndex = this.tabs.length - 1;
        } else if (typeof (tabOrIdx) === "object") {
            tabIndex = this.getOpenedTabIndex(tabOrIdx);
        } else if (typeof tabOrIdx === "number") {
            tabIndex = tabOrIdx;
        }

        if (tabIndex < 0) {
            return;
        }

        this.tabIndex = tabIndex;
    }

    openOrFocusTab(toState, fromParams, toParams) {
        let tab = this.findTab(toState);
        if (!tab) {
            return;
        }
        tab.curState = toState.name ? toState.name : toState;

        //将params插入到tab中,以在前端做ui-sref跳转的时候用,需要copy,防止参数指定发生变更
        tab.tParams = angular.copy(toParams);
        tab.fParams = angular.copy(fromParams);
        // tab.tParams = toParams;
        // tab.fParams = fromParams;
        let tabIdx = this.getOpenedTabIndex(tab);
        if (tabIdx < 0) {
            this.openTab(tab);
            tabIdx = this.tabs.length - 1;
        }
        this.focusTab(tabIdx);
    }

    closeTab(tab) {


        let idx = this.tabs.indexOf(tab);
        if (idx < 0) {
            return;
        }

        let curIdx = this.tabIndex;

        // 删除
        this.tabs.splice(idx, 1);

        // 要关闭的 tab 在 focus tab 前。
        if (idx < curIdx) {
            this.focusTab(curIdx - 1);

            // 要关闭的 tab 是当前 focus tab
        } else if (idx === curIdx) {


            // 默认打开关闭标签页后面的，或前面的（仅当要关闭的标签页是最后一个时）
            let tabIdx = this.tabs.length > idx ? idx : this.tabs.length - 1;

            if (tabIdx >= 0) {
                this.focusTab(tabIdx);
                $state.go(this.tabs[tabIdx].curState);
            }

            // 要关闭的 tab 在 focus tab 之后
        } else {
            return;
        }
    }
}
sidenavTab.$inject = ['$rootScope', '$log', '$state', '$stateParams', '$timeout'];


export default sidenavTab;
