export default  [
    {
        name: '门店管理',
        type: 'toggle',
        // authorities: ['STAFF_A', 'SA','BASESET_A'],
        icon: {
            mdFontSet: "ks-admin-font",
            mdFontIcon: "ks-user"
        },
        pages: [
            {
                name: '门店信息',
                state: 'main.brandApp.store',
                authorities: ['SA'],
            },
            {
                name: '计费设置',
                state: 'main.brandApp.setBuyPrice',
                authorities: ['SA'],
            }
        ]
    },
    {
        name: '系统设置',
        type: 'toggle',
        // authorities: ['STAFF_A', 'SA','BASESET_A'],
        icon: {
            mdFontSet: "ks-admin-font",
            mdFontIcon: "ks-cog"
        },
        pages: [
            {
                name: '公众号设置',
                state: 'main.brandApp.publicNumber',
                authorities: ['SA'],
            },
            {
                name: '支付设置',
                state: 'main.brandApp.paySetting',
                authorities: ['BASESET_A', 'SA','STATISTICS_U','STATISTICS_R','PARAMETER_R','PARAMETER_U','REFUNDADDR_R','REFUNDADDR_U'],
            }
        ]
    }

];
