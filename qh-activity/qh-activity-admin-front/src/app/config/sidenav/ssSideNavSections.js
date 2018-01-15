export default  [
    {
        name: '营销管理',
        type: 'toggle',
        // authorities: ['BRANDAPP_A', 'SA'],
        icon: {
            mdFontSet: "ks-admin-font",
            mdFontIcon: "ks-manage-s"
        },
        pages: [
            {
                name: '投票活动',
                state: 'main.brandApp.marketing.vote',
                authorities: ['BRANDAPP_A', 'SA', 'BRANDCOM_R'],
            },
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
            {
                name: '公众号设置',
                state: 'main.brandApp.publicNumber',
                authorities: ['SA'],
            },
        ]
    }

];
