export default  [
    {
        name: '应用管理',
        type: 'toggle',
        // authorities: ['APPLICATION_A', 'SA'],
        icon: {
            mdFontSet: "ks-admin-font",
            mdFontIcon: "ks-category",
        },
        pages: [
            {
                name: '应用信息',
                state: 'main.brandApp.application.applicationList',
                authorities: ['APPLICATION_A', 'SA','APPLICATION_R'],
            },
        ]
    },{
        name: '商家管理',
        type: 'toggle',
        // authorities: ['BRANDAPP_A', 'SA'],
        icon: {
            mdFontSet: "ks-admin-font",
            mdFontIcon: "ks-manage-s"
        },
        pages: [
            {
                name: '商家信息',
                state: 'main.brandApp.business.businessList',
                authorities: ['BRANDAPP_A', 'SA','BRANDCOM_R'],
            },
        ]
    }

];
