/**
 * 注册表，登记了哪些 state 要作为tab 打开。第一个是主页。
 *
 * {
 *      title           : "",       // 标签页的标题
 *      matchStates     : "",       // 匹配的状态, FIXME 当前使用的状态的前缀，是否可以换成正则表达式？
 *      uiView          : "",       // 要追加的 ui-view 的名称
 *      curState        : "",       // 该标签页的内容会在多个状态之间切换时，当前实际要显示的状态。
 *      disableClose    : false,    // 是否禁止关闭。
 * }
 */
export default [
    {
        title: "首页",
        matchStates: "main.brandApp.home",
        uiView: "home",
        curState: "main.brandApp.home",
        disableClose: true
    },
    {
        title: "应用信息",
        matchStates: "main.brandApp.application.applicationList",
        uiView: "applicationList",
        curState: "main.brandApp.application.applicationList"
    },
    {
        title: "应用信息编辑",
        matchStates: "main.brandApp.application.informationEdit",
        uiView: "informationEdit",
        curState: "main.brandApp.application.informationEdit"
    },
    {
        title: "应用角色",
        matchStates: "main.brandApp.application.appUserList",
        uiView: "appUserList",
        curState: "main.brandApp.application.appUserList"
    },
    {
        title: "应用角色新增",
        matchStates: "main.brandApp.application.appUserAdd",
        uiView: "appUserAdd",
        curState: "main.brandApp.application.appUserAdd"
    },
    {
        title: "应用角色查看",
        matchStates: "main.brandApp.application.appUserView",
        uiView: "appUserView",
        curState: "main.brandApp.application.appUserView"
    },

    {
        title: "商家信息",
        matchStates: "main.brandApp.business.businessList",
        uiView: "businessList",
        curState: "main.brandApp.business.businessList"
    },
    {
        title: "商家信息新增",
        matchStates: "main.brandApp.business.businessAdd",
        uiView: "businessAdd",
        curState: "main.brandApp.business.businessAdd"
    },
    {
        title: "商家信息编辑",
        matchStates: "main.brandApp.business.businessEdit",
        uiView: "businessEdit",
        curState: "main.brandApp.business.businessEdit"
    },
    {
        title: "商家应用",
        matchStates: "main.brandApp.business.busApplication",
        uiView: "busApplication",
        curState: "main.brandApp.business.busApplication"
    },
    {
        title: "新增应用",
        matchStates: "main.brandApp.business.busAppAdd",
        uiView: "busAppAdd",
        curState: "main.brandApp.business.busAppAdd"
    },
    {
        title: "应用查看",
        matchStates: "main.brandApp.business.busAppView",
        uiView: "busAppView",
        curState: "main.brandApp.business.busAppView"
    },

];
