import conf from "../../conf";
import store from "store";
authServiceFactory.$inject = ['$http', 'loginService', '$injector'];

function authServiceFactory($http, loginService, $injector) {

    function update(a) {
        a = ["SA"];
        var ssSideNavSections = $injector.get('ssSideNavSections');
        var sections = ssSideNavSections.sections;
        if (a.indexOf("SA") > -1) {
            for (var i = 0; i < sections.length; i++) {
                $injector.get('ssSideNavSections').sections[i].show = true;
                var pages = sections[i].pages;
                // 二级菜单
                for (var j = 0; j < pages.length; j++) {
                    $injector.get('ssSideNavSections').sections[i].pages[j].show = true;
                }
            }
            return;
        }
        console.log('sections===========', sections);
        //更新menu
        // for (var i = 0; i < sections.length; i++) {
        //     /*console.log(sections[i].name);
        //      console.log(sections[i].pages);*/
        //     //检查每一项的权限
        //     var flag = false;
        //
        //     for (var auth = 0; auth < sections[i].authorities.length; auth++) {
        //         if (a.indexOf(sections[i].authorities[auth]) > -1) {
        //             flag = true;
        //             break;
        //         }
        //     }
        //     if (!flag) {
        //         // $injector.get('ssSideNavSections').sections[i].hidden = !$injector.get('ssSideNavSections').sections[i].hidden;
        //         $injector.get('ssSideNavSections').sections[i].show = false;
        //     }else {
        //         $injector.get('ssSideNavSections').sections[i].show = true;
        //     }
        // }


        //更新menu_list
        // 一级菜单
        for (var i = 0; i < sections.length; i++) {
            var pages = sections[i].pages;

            // 二级菜单
            for (var j = 0; j < pages.length; j++) {
                // var flag = false;

                // 二级菜单所需的权限列表检查每一项的权限

                for (var auth = 0; auth < pages[j].authorities.length; auth++) {
                    if (a.indexOf(pages[j].authorities[auth]) > -1) {
                        // flag = true;
                        $injector.get('ssSideNavSections').sections[i].show = true;
                        $injector.get('ssSideNavSections').sections[i].pages[j].show = true;
                        break;
                    }
                }
                // if (!flag) {
                //     // $injector.get('ssSideNavSections').sections[i].pages[j].hidden = !$injector.get('ssSideNavSections').sections[i].pages[j].hidden;
                //     $injector.get('ssSideNavSections').sections[i].pages[j].show = false;
                // }else {
                //     $injector.get('ssSideNavSections').sections[i].pages[j].show = true;
                // }
            }
        }
    }


    function setAuthorities(brandAppId) {
        store.set(conf.authorSet, null);
        update('[]');
        // $http({
        //     method: "GET",
        //     url: conf.apiPath + "/authorities",
        //
        //     headers: {
        //         'Authorization': 'Bearer ' + loginService.getAccessToken(),
        //         'brandApp-Id': brandAppId
        //     },
        //     data: {}
        // }).then(function successCallback(data) {
        //     if (data.data.data) {
        //
        //         store.set(conf.authorSet, data.data.data);
        //
        //
        //         var startTime = new Date();
        //         console.log('------------------------11111', startTime, startTime.getMilliseconds());
        //         update(data.data.data);
        //         var endTime = new Date();
        //         console.log('------------------------22222', endTime, endTime.getMilliseconds());
        //
        //     } else {
        //         store.set(conf.authorSet, null);
        //         update('[]')
        //     }
        // }, function errorCallback(response) {
        //     store.set(conf.authorSet, null);
        //     update('[]');
        //     if (response.status == '401') {
        //         jso.wipeTokens();
        //         loginService.setAccessToken(null);
        //         loginService.setbrandAppId(null);
        //         $http({
        //             method: "POST",
        //             url: "https:" + conf.oauthPath + "/logout",
        //             headers: {},
        //             params: {},
        //             withCredentials: true,
        //         }).success(function (resp) {
        //             // console.log('data', resp);
        //             location.reload();
        //         }, function (resp) {
        //             console.log('ERR', resp);
        //         });
        //     }
        // });
    }

    let getAuthorities = () => {
        store.get(conf.authorSet);
        return store.get(conf.authorSet);
    };


    function hasAuthor(author) {
        var auth = store.get(conf.authorSet);
        if (!auth) {
            auth = []
        }
        if (auth.indexOf(author) > -1 || auth.indexOf('SA') > -1) {
            return true
        } else {
            return false
        }


        // for (var i = 0; i < auth.length; i++) {
        //     if (auth[i] == author ) {
        //         return true
        //     }
        // }
        // return false
    }


    return {
        hasAuthor: hasAuthor,
        setAuthorities: setAuthorities,
        getAuthorities: getAuthorities,
        isUpdateApp: true,
        pages: {item: 0}
    };

}

export default  authServiceFactory;


