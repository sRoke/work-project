
# 更新流程
1. 字体文件有更新的都通知言一，由她负责更新到 `/qh-wap-ui/iconmoon-ks-admin-font`。

1. 每次应当从 `/qh-wap-ui/iconmoon-ks-admin-font` 目录下获取最新(git pull)，并将ks-iconfont.zip 解压, 
    将内容迁移到 `qh-shop/qh-shop-admin-front/src/app/ks-admin-font` 下面。
    注意:不要删除本文件(README.md) 以及 md-icon-ks.html

1. 将迁移后的字体文件重命名，并追加上日期编号(比如: ks-iconfont_20160620_2.eot) ，以防止缓存。并修改 sytle.css 文件中字体的URL.

1. style.css 文件中追加 
    
    ```
    .ks-icon {
       /* 原有内容省略 */
        width: 1em;
        height: 1em;
        font-size: 24px;
    }
    ```
    注意头部引用路径修改
    
1. 直接重新运行项目


1. 在项目中通过以下方式使用

    ```
     <!-- 推荐第一种 -->
     <md-icon md-font-set="ks-icon" md-font-icon="{{cssName}}"></md-icon>

     <md-icon md-font-set="ks-icon" >{{liga}}/md-icon>
    ```







1.对应目录下/data0/work/git-repo/gitlab/kingsilk/qh-wap-ui/iconmoon-ks-admin-font(后台图标)
/data0/work/git-repo/gitlab/kingsilk/qh-wap-ui/iconmoon/生意参谋    (移动端图标)
拉取最新图标库，将其解压，整体复制到ks-admin-front目录下，
2,改动fonts--下的文件日期版本，，，，将原来的删除
3,看style.css看其中的src是否和fonts文件下的文件向对应，如果不对应的话就手动改
4，将如下内容放进ks-admin-front中
        width: 1em;
        height: 1em;
        font-size: 24px;
 5,编译运行项目       
        