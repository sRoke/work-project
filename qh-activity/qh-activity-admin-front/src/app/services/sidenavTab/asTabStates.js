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
        title: "投票活动",
        matchStates: "main.brandApp.marketing.vote",
        uiView: "vote",
        curState: "main.brandApp.marketing.vote"
    },
    {
        title: "作品列表",
        matchStates: "main.brandApp.marketing.worksList",
        uiView: "worksList",
        curState: "main.brandApp.marketing.worksList"
    },
    {
        title: "投票记录",
        matchStates: "main.brandApp.marketing.recordVote",
        uiView: "recordVote",
        curState: "main.brandApp.marketing.recordVote"
    },
    {
        title: "新增活动",
        matchStates: "main.brandApp.marketing.creatVote",
        uiView: "creatVote",
        curState: "main.brandApp.marketing.creatVote"
    },
    {
        title: "公众号设置",
        matchStates: "main.brandApp.publicNumber",
        uiView: "publicNumber",
        curState: "main.brandApp.publicNumber"
    },
];
