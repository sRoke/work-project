sideNavServiceFactory.$inject = ['$injector'];
function sideNavServiceFactory($injector) {

    return {
        update: update
    };

    // -------------------------------

    /**
     * 根据用户权限刷新菜单列表
     *
     * @param a  当前用户的权限列表
     */
    function update(a) {

        if (a.indexOf("SUPER_ADMIN") > -1) {
            return;
        }

        //a.splice(0,1);
        /*for(var i=0;i<a.length;i++){
         console.log(":"+a[i]);
         }*/

        var ssSideNavSections = $injector.get('ssSideNavSections');
        var sections = ssSideNavSections.sections;
        //更新menu
        for (var i = 0; i < sections.length; i++) {
            /*console.log(sections[i].name);
             console.log(sections[i].pages);*/
            //检查每一项的权限
            var flag = false;
            for (var auth = 0; auth < sections[i].authorities.length; auth++) {
                if (a.indexOf(sections[i].authorities[auth]) > -1) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                $injector.get('ssSideNavSections').sections[i].hidden = !$injector.get('ssSideNavSections').sections[i].hidden;
            }

        }
        //更新menu_list
        for (var i = 0; i < sections.length; i++) {
            var pages = sections[i].pages;
            for (var j = 0; j < pages.length; j++) {
                var flag = false;
                //检查每一项的权限
                for (var auth = 0; auth < pages[j].authorities.length; auth++) {
                    if (a.indexOf(pages[j].authorities[auth]) > -1) {
                        flag = true;
                        break;
                    }
                }

                if (!flag) {
                    $injector.get('ssSideNavSections').sections[i].pages[j].hidden = !$injector.get('ssSideNavSections').sections[i].pages[j].hidden;
                }
            }
        }
    }
}


export default sideNavServiceFactory;
