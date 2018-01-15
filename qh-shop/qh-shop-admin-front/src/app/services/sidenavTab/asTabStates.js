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
    // {
    //     title: "公司",
    //     matchStates: "main.brandApp",
    //     uiView: "brandApp",
    //     curState: "main.brandApp"
    // },
    {
        title: "首页",
        matchStates: "main.brandApp.home",
        uiView: "home",
        curState: "main.brandApp.home",
        disableClose: true
    },
    {
        title: "渠道授权",
        matchStates: "main.brandApp.authorization",
        uiView: "authorization",
        curState: "main.brandApp.authorization"
    },
    {
        title: "支付设置",
        matchStates: "main.brandApp.paySetting",
        uiView: "paySetting",
        curState: "main.brandApp.paySetting"
    },
    {
        title: "公众号设置",
        matchStates: "main.brandApp.publicNumber",
        uiView: "publicNumber",
        curState: "main.brandApp.publicNumber"
    },
    {
        title: "门店信息",
        matchStates: "main.brandApp.store",
        uiView: "store",
        curState: "main.brandApp.store"
    },
    {
        title: "门店新增",
        matchStates: "main.brandApp.addStore",
        uiView: "addStore",
        curState: "main.brandApp.addStore"
    },
    {
        title: "门店编辑",
        matchStates: "main.brandApp.editStore",
        uiView: "editStore",
        curState: "main.brandApp.editStore"
    },
    {
        title: "基础数据",
        matchStates: "main.brandApp.basicData",
        uiView: "basicData",
        curState: "main.brandApp.basicData"
    },
    {
        title: "商品详细",
        matchStates: "main.brandApp.gooSee",
        uiView: "gooSee",
        curState: "main.brandApp.gooSee"
    },
    {
        title: "计费设置",
        matchStates: "main.brandApp.setBuyPrice",
        uiView: "setBuyPrice",
        curState: "main.brandApp.setBuyPrice"
    },

];
