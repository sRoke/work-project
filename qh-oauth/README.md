


# 参考
[demo@炮灰](https://github.com/zengchw/github.repo/tree/master/java/spring-boot/my-oauth2)



# 说明

## qh-wap-front
OAuth 登录认证服务器。


# 域名规划

鉴于微信对于域名有各种限制，为避免麻烦，放弃各个工程使用独立子域名的想法。
所有工程均放到 `kingsilk.net` 域名下面，通过不同路径加以区分。规划如下：

| path                  | project               |
|-----------------------|-----------------------|
| `/common/**`          | qh-common-wap-front   |
| `/common/rs/**`       | qh-common-wap         |
| `/common/admin/**`    | qh-common-admin-front |
| `/common/admin/rs/**` | qh-common-admin       |
| `/oauth/**`           | qh-oauth-wap-front    |
| `/oauth/rs/**`        | qh-oauth-wap          |
| `/oauth/admin/**`     | qh-oauth-admin-front  |
| `/oauth/admin/rs/**`  | qh-oauth-admin        |
| `/pay/**`             | qh-pay-wap-front      |
| `/pay/rs/**`          | qh-pay-wap            |
| `/pay/admin/**`       | qh-pay-admin-front    |
| `/pay/admin/rs/**`    | qh-pay-admin          |
| `/agency/**`          | qh-agency-wap-front   |
| `/agency/rs/**`       | qh-agency-wap         |
| `/agency/admin/**`    | qh-agency-admin-front |
| `/agencyadmin/rs/**`  | qh-agency-admin       |
| `/yun/**`             | qh-yun-wap-front      |
| `/yun/rs/**`          | qh-yun-wap            |
| `/yun/admin/**`       | qh-yun-admin-front    |
| `/yun/admin/rs/**`    | qh-yun-admin          |


# 开发、测试环境线上映射
适用于：
1. 开发环境的宽带网络不能提供80端口访问
2. 或者测试不需要使用微信支付的相关功能

|domain         | path                  | local path                        | memo              |
|---------------|-----------------------|-----------------------------------|-------------------|
| kingsilk.net  | `/oauth/`             | `/oauth/?_ddnsPort=1xx00`         |仅仅提供 index.html |        
| kingsilk.net  | `/oauth/**`           | `/oauth/local/1xx00/**`           |返回css,js,font,image等|    
| kingsilk.net  | `/oauth/rs/`          | `/oauth/admin/local/1xx00/rs/**`  |qh-oauth-wap 的 API|    
| kingsilk.net  | `/oauth/admin/`       | `/oauth/admin/?_ddnsPort=1xx00`   |仅仅提供 index.html |        
| kingsilk.net  | `/oauth/admin/**`     | `/oauth/admin/local/1xx00/**`     |返回css,js,font,image等|    
| kingsilk.net  | `/oauth/admin/rs/**`  | `/oauth/admin/local/1xx00/rs/**`  |qh-oauth-admin 的 API   |                     

其他工程的示例请举一反三。


# 本工程配置

- 参考的nginx配置

    在kingsilk.net.conf中追加如下location
    ```txt
    location /oauth {
    
            access_log  logs/kingsilk.net_oauth.access.log;
            error_log   logs/kingsilk.net_oauth.error.log;      
            alias   /home/zcw/work/git-repo/qh-oauth/qh-oauth-wap-front/build/;
    
            #############################################wap
            location  /oauth/local/16600 { 
              add_header Cache-Control no-cache;      
              alias   /home/zcw/work/git-repo/qh-oauth/qh-oauth-wap-front/build/;
            }
          
            location /oauth/local/16600/rs {
              proxy_pass              http://localhost:10080;
              proxy_set_header        Host            $host;
              proxy_set_header        X-Forwarded-For $proxy_add_x_forwarded_for;
              proxy_set_header        X-Forwarded-Proto $scheme;
            }
        }
    ```

- ssh隧道

    新建shell脚本，添加以下内容
    ```txt
    #!/usr/bin/expect
    
    set password ddns2017
    spawn ssh ddns@pub-prod11.kingsilk.net -C -N -g -R localhost:16600:localhost:80 -o ExitOnForwardFailure=yes -o ServerAliveInterval=60
    expect "password" {send "$password\r"}
    interact        # 取回控制权
    ```

- qh-oauth-wap 运行

    ```sh
    $ ./gradlew qh-oauth-wap:bootRun -Dspring.profiles.active=base,dev
    ```

- qh-pay-wap-front运行
    ```sh
    $ cd qh-oauth-wap-front
    $ npm run webpack -- --env.dev
    ```
