
# 更新流程
1. 字体文件有更新的都通知小卉，由她负责更新到 `qh-wap-ui/iconmoon`。

1. 每次应当从 `qh-wap-ui/iconmoon` 目录下获取最新(git pull)，并将ks-iconfont.zip 解压, 
    将内容迁移到 `qh-wap-front/src/lib/ks-iconfont/` 下面。
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
    
1. 重新执行 `gulp lib.less` 并提交更新。
        在工程根目录下
1. 修改 `qh-wap-front/src/demo.html` 中引用的 lib-min-*.css 的路径


1. 在项目中通过以下方式使用

    ```
     <!-- 推荐第一种 -->
     <md-icon md-font-set="ks-icon" md-font-icon="{{cssName}}"></md-icon>

     <md-icon md-font-set="ks-icon" >{{liga}}/md-icon>
    ```