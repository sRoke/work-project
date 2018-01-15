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
    // {
    //     title: "绑定手机号",
    //     matchStates: "main.bindPhone",
    //     uiView: "bindPhone",
    //     curState: "main.bindPhone"
    // },

    {
        title: "订单中心",
        matchStates: "main.brandApp.order",
        uiView: "order",
        curState: "main.brandApp.order"
    },
    {
        title: "订单详情",
        matchStates: "main.brandApp.ordDetail",
        uiView: "ordDetail",
        curState: "main.brandApp.ordDetail"
    },
    {
        title: "退款订单",
        matchStates: "main.brandApp.refund",
        uiView: "refund",
        curState: "main.brandApp.refund"
    },
    {
        title: "退款详情",
        matchStates: "main.brandApp.refDetail",
        uiView: "refDetail",
        curState: "main.brandApp.refDetail"
    },
    {
        title: "员工管理",
        matchStates: "main.brandApp.user",
        uiView: "user",
        curState: "main.brandApp.user"
    },
    {
        title: "员工新增",
        matchStates: "main.brandApp.usAdd",
        uiView: "usAdd",
        curState: "main.brandApp.usAdd"
    },
    {
        title: "员工编辑",
        matchStates: "main.brandApp.usEdit",
        uiView: "usEdit",
        curState: "main.brandApp.usEdit"
    },
    {
        title: "角色管理",
        matchStates: "main.brandApp.role",
        uiView: "role",
        curState: "main.brandApp.role"
    },
    {
        title: "角色管理",
        matchStates: "main.brandApp.roAdd",
        uiView: "roAdd",
        curState: "main.brandApp.roAdd"
    },
    {
        title: "渠道账号",
        matchStates: "main.brandApp.member",
        uiView: "member",
        curState: "main.brandApp.member"
    },
    {
        title: "查看会员",
        matchStates: "main.brandApp.mbAdd",
        uiView: "mbAdd",
        curState: "main.brandApp.mbAdd"
    },
    {
        title: "属性管理",
        matchStates: "main.brandApp.attribute",
        uiView: "attribute",
        curState: "main.brandApp.attribute"
    },
    {
        title: " 属性管理",
        matchStates: "main.brandApp.attAdd",
        uiView: "attAdd",
        curState: "main.brandApp.attAdd"
    },
    {
        title: "分类管理",
        matchStates: "main.brandApp.classify",
        uiView: "classify",
        curState: "main.brandApp.classify"
    },
    {
        title: "商品列表",
        matchStates: "main.brandApp.goods",
        uiView: "goods",
        curState: "main.brandApp.goods"
    },
    {
        title: "商品发布",
        matchStates: "main.brandApp.gooAdd",
        uiView: "gooAdd",
        curState: "main.brandApp.gooAdd"
    },
    {
        title: "商品发布",
        matchStates: "main.brandApp.goodAdd",
        uiView: "goodAdd",
        curState: "main.brandApp.goodAdd"
    },
    {
        title: "商品编辑",
        matchStates: "main.brandApp.goodEdit",
        uiView: "goodEdit",
        curState: "main.brandApp.goodEdit"
    },
    {
        title: "商品查看",
        matchStates: "main.brandApp.gooSee",
        uiView: "gooSee",
        curState: "main.brandApp.gooSee"
    },
    {
        title: "组织管理",
        matchStates: "main.brandApp.org",
        uiView: "org",
        curState: "main.brandApp.org.list"
    },
    {
        title: "系统设置",
        matchStates: "main.brandApp.sysConf",
        uiView: "sysConf",
        curState: "main.brandApp.sysConf"
    },
    {
        title: "意见反馈",
        matchStates: "main.brandApp.complain",
        uiView: "complain",
        curState: "main.brandApp.complain"
    },

    {
        title: "七牛",
        matchStates: "main.brandApp.qiniu",
        uiView: "qiniu",
        curState: "main.brandApp.qiniu"
    },
    {
        title: "实验页面",
        matchStates: "main.brandApp.example",
        uiView: "example",
        curState: "main.brandApp.example"
    },
    {
        title: "退款管理",
        matchStates: "main.brandApp.manageRefund",
        uiView: "manageRefund",
        curState: "main.brandApp.manageRefund"
    },
    {
        title: "渠道审核",
        matchStates: "main.brandApp.partnerApply",
        uiView: "partnerApply",
        curState: "main.brandApp.partnerApply"
    },
    {
        title: "渠道信息",
        matchStates: "main.brandApp.partnerManage",
        uiView: "partnerManage",
        curState: "main.brandApp.partnerManage"
    },
    {
        title: "审核详情",
        matchStates: "main.brandApp.review",
        uiView: "review",
        curState: "main.brandApp.review"
    },
    {
        title: "渠道信息查看",
        matchStates: "main.brandApp.manageLook",
        uiView: "manageLook",
        curState: "main.brandApp.manageLook"
    },
    {
        title: "发布授权",
        matchStates: "main.brandApp.publish",
        uiView: "publish",
        curState: "main.brandApp.publish"
    },
    {
        title: "发货管理",
        matchStates: "main.brandApp.sendOrder",
        uiView: "sendOrder",
        curState: "main.brandApp.sendOrder"
    },
    {
        title: "发货详情",
        matchStates: "main.brandApp.sendDetail",
        uiView: "sendDetail",
        curState: "main.brandApp.sendDetail"
    },
    {
        title: "会员编辑",
        matchStates: "main.brandApp.memEdit",
        uiView: "memEdit",
        curState: "main.brandApp.memEdit"
    },
    {
        title: "退货管理",
        matchStates: "main.brandApp.rfGoods",
        uiView: "rfGoods",
        curState: "main.brandApp.rfGoods"
    },
    {
        title: "提现管理",
        matchStates: "main.brandApp.assetManage",
        uiView: "assetManage",
        curState: "main.brandApp.assetManage"
    },
    {
        title: "账户流水",
        matchStates: "main.brandApp.transactionLog",
        uiView: "transactionLog",
        curState: "main.brandApp.transactionLog"
    },
    {
        title: "渠道授权",
        matchStates: "main.brandApp.authorization",
        uiView: "authorization",
        curState: "main.brandApp.authorization"
    },
    {
        title: "基础设置",
        matchStates: "main.brandApp.basicSetting",
        uiView: "basicSetting",
        curState: "main.brandApp.basicSetting"
    },
    {
        title: "支付设置",
        matchStates: "main.brandApp.paySetting",
        uiView: "paySetting",
        curState: "main.brandApp.paySetting"
    },
    {
        title: "查看授权书",
        matchStates: "main.brandApp.lookAuthorization",
        uiView: "lookAuthorization",
        curState: "main.brandApp.lookAuthorization"
    },
    {
        title: "公众号设置",
        matchStates: "main.brandApp.publicNumber",
        uiView: "publicNumber",
        curState: "main.brandApp.publicNumber"
    },
];
