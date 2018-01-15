export default  [
    // {
    //     name: '人员管理',
    //     type: 'toggle',
    //     icon: {
    //         mdFontSet: "ks-admin-font",
    //         mdFontIcon: "ks-user"
    //     },
    //     pages: [
    //         {
    //             name: '员工管理',
    //             state: 'main.brandApp.user'
    //         }
    //         // {
    //         //     name: '角色管理',
    //         //     state: 'main.brandApp.role'
    //         // }
    //     ]
    // },
    {
        name: '渠道管理',
        type: 'toggle',
        // authorities: ['PARTNERCHECK_A', 'SA','PARTNERLICENSE_A','PARTNERINFO_A'],
        icon: {
            mdFontSet: "ks-admin-font",
            mdFontIcon: "ks-users"
        },
        pages: [
            {
                name: '渠道审核',
                state: 'main.brandApp.partnerApply',
                authorities: ['PARTNERCHECK_A', 'SA','PARTNERCHECK_R','PARTNERCHECK_U'],
            },
            {
                name: '渠道信息',
                state: 'main.brandApp.partnerManage',
                authorities: ['PARTNERLICENSE_A', 'SA','PARTNERLICENSE_U','PARTNERLICENSE_R','PARTNERLICENSE_D'],
            },
            {
                name: '渠道账号',
                state: 'main.brandApp.member',
                authorities: ['PARTNERINFO_A', 'SA','PARTNERINFO_U','PARTNERINFO_R','PARTNERINFO_D'],
            }
            // {
            //     name: '渠道授权',
            //     state: 'main.brandApp.authorization'
            // }
        ]
    },
    {
        name: '商品管理',
        type: 'toggle',
        // authorities: ['ITEM_PROP_A', 'SA','CATEGORY_A','ITEM_A'],
        icon: {
            mdFontSet: "ks-admin-font",
            mdFontIcon: "ks-goods"
        },
        pages: [
            {
                name: '属性管理',
                state: 'main.brandApp.attribute',
                authorities: ['ITEM_PROP_A', 'SA','ITEM_PROP_C','ITEM_PROP_U','ITEM_PROP_R','ITEM_PROP_D'],
            },
            {
                name: '分类管理',
                state: 'main.brandApp.classify',
                authorities: ['CATEGORY_A','SA','CATEGORY_C','CATEGORY_U','CATEGORY_R','CATEGORY_D'],
            },
            {
                name: '商品发布',
                state: 'main.brandApp.goods',
                authorities: ['ITEM_A', 'SA','ITEM_C','ITEM_U','ITEM_R','ITEM_R'],
            }
        ]
    },
    {
        name: '订单管理',
        type: 'toggle',
        // authorities: ['REFUND_A', 'SA','ORDER_A','DELIVERINVOICE_A','REFUNDMONEY_A'],
        icon: {
            mdFontSet: "ks-admin-font",
            mdFontIcon: "ks-order"
        },
        pages: [
            {
                name: '订单中心',
                state: 'main.brandApp.order',
                authorities: ['ORDER_A', 'SA','ORDER_C','ORDER_R','ORDER_E','ORDER_Q','ORDER_J','ORDER_Y'],
            },
            // {
            //     name: '退款订单',
            //     state: 'main.brandApp.refund'
            // },
            {
                name: '退货管理',
                state: 'main.brandApp.rfGoods',
                authorities: ['REFUND_A', 'SA','REFUND_C','REFUND_R','REFUND_J'],
            },
            {
                name: '发货管理',
                state: 'main.brandApp.sendOrder',
                authorities: ['DELIVERINVOICE_A','SA','DELIVERINVOICE_C','DELIVERINVOICE_U','DELIVERINVOICE_R'],
            },
            {
                name: '退款管理',
                state: 'main.brandApp.manageRefund',
                authorities: ['REFUNDMONEY_A', 'SA','REFUNDMONEY_C','REFUNDMONEY_U','REFUNDMONEY_R'],
            }

        ]
    },
    {
        name: '资产管理',
        type: 'toggle',
        // authorities: ['WITHDRAWCASH_A', 'SA','ACCOUNTLOG_A'],
        icon: {
            mdFontSet: "ks-admin-font",
            mdFontIcon: "ks-asset"
        },
        pages: [
            {
                name: '提现管理',
                state: 'main.brandApp.assetManage',
                authorities: ['WITHDRAWCASH_A', 'SA','WITHDRAWCASH_R','WITHDRAWCASH_U','WITHDRAWCASH_J'],
            },
            {
                name: '账户流水',
                state: 'main.brandApp.transactionLog',
                authorities: ['ACCOUNTLOG_A', 'SA','ACCOUNTLOG_R'],
            }
        ]
    },
    {
        name: '系统管理',
        type: 'toggle',
        // authorities: ['STAFF_A', 'SA','BASESET_A'],
        icon: {
            mdFontSet: "ks-admin-font",
            mdFontIcon: "ks-cog"
        },
        pages: [
            // {
            //     name: '短信管理',
            //     state: 'main.message'
            // },
            // {
            //     name: '登录日志',
            //     state: 'main.org'
            // },
            // {
            //     name: '系统设置',
            //     state: 'main.brandApp.sysConf'
            // },
            // {
            //     name: '1111',
            //     state: 'main.brandApp.checkPartner'
            // },
            {
                name: '公众号设置',
                state: 'main.brandApp.publicNumber',
                authorities: ['SA'],
            },
            {
                name: '基础设置',
                state: 'main.brandApp.basicSetting',
                authorities: ['BASESET_A', 'SA','STATISTICS_U','STATISTICS_R','PARAMETER_R','PARAMETER_U','REFUNDADDR_R','REFUNDADDR_U'],
            },
            {
                name: '支付设置',
                state: 'main.brandApp.paySetting',
                authorities: ['BASESET_A', 'SA','STATISTICS_U','STATISTICS_R','PARAMETER_R','PARAMETER_U','REFUNDADDR_R','REFUNDADDR_U'],
            },
            {
                name: '员工管理',
                state: 'main.brandApp.user',
                authorities: ['STAFF_A', 'SA','STAFF_C','STAFF_U','STAFF_R','STAFF_D'],
            }
        ]
    }

];
