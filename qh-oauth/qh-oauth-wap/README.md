

## 运行

```bash
./gradlew -Dspring.profiles.active=base,dev :qh-oauth-wap:bootRun 

java -Dspring.profiles.active=base,dev -jar xxx.jar
./gradlew -Dspring.profiles.active=base,dev :qh-platform-server:bootRun

http://localhost:10080/oauth/rs/webjars/swagger-ui/3.0.19/index.html
http://localhost:10080/oauth/rs/api/swagger.json
http://localhost:10080/oauth/rs/api/swagger.yaml


# 本地 - tomcat
curl -v \
    -H 'X-Requested-With: XMLHttpRequest' \
    http://localhost:10080/oauth/local/14100/rs/api/s/user/info

# 本地 - nginx
curl -v \
    -H 'X-Requested-With: XMLHttpRequest' \
    -H 'Host: kingsilk.net' \
    http://localhost/oauth/local/14100/rs/api/s/user/info

# 线上 - nginx
curl -v -H 'X-Requested-With: XMLHttpRequest' \
    https://kingsilk.net/oauth/local/14100/rs/api/s/user/info

浏览器访问:
线上: 
    授权: https://kingsilk.net/oauth/rs/oauth/authorize
    退出: https://kingsilk.net/oauth/rs/logout
本地: 
    授权: https://kingsilk.net/oauth/local/14100/rs/oauth/authorize
    退出: https://kingsilk.net/oauth/local/14100/rs/logout
```

## 生成 JWT 校验签名的 公钥、私钥对儿

```bash
keytool -genkeypair \
    -alias qh-oauth-wap-jwt \
    -keyalg RSA \
    -keysize 2048 \
    -sigalg SHA256withRSA \
    -dname "CN=kingsilk.net, OU=R & D department, O=\"HangZhou Kingsilk Net Tech Co., Ltd.\", L=Hangzhou, S=Zhejiang, C=CN" \
    -validity 3650 \
    -keypass '(*Gs!Hhh' \
    -keystore qh-oauth-wap-jwt.jks \
    -storepass '(*Gs!Hhh' \
    -storetype JKS
```

# SSH 隧道

```bash
# 下面的 16000 改为与自己IP对应的端口号
ssh user@pub-prod11.kingsilk.net -C -N -g \
    -R localhost:16000:localhost:80 \
    -o ExitOnForwardFailure=yes \
    -o ServerAliveInterval=60
```

# nginx 配置

```
server {
    listen *:80;
    server_name dev.test.me login.kingsilk.net;
    root html;
    index  index.html index.htm;

    access_log  conf.d/logs/login.kingsilk.net.access.log main;
    error_log   conf.d/logs/login.kingsilk.net.error.log;

    location  /local/16500/ { 
        add_header Cache-Control no-cache;
        alias     /home/yanfq/work/repo/kingsilk/qh-oauth/qh-oauth-wap-front/build/;
    }   

    location /local/16500/api/ { 
         proxy_pass              http://192.168.0.65:10080;

        proxy_set_header        Host            $host;
        #proxy_set_header        X-Real-IP       $remote_addr;
        proxy_set_header        X-Forwarded-For $proxy_add_x_forwarded_for;
        #proxy_set_header        X-Forwarded-Proto $scheme;
    }
}
```


## 以 client 模式获取AT

```bash
# 命令行获取AT。注意，这里配合了 Http Basic 认证来认证 client
curl http://MY_CLIENT:secret@localhost:10080/oauth/token \
    -d grant_type=client_credentials \
    -d scope=LOGIN \
    --trace-ascii /dev/stdout 
    
响应如为：{
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiTVlfUlNDIl0sInNjb3BlIjpbInJlYWQiXSwiZXhwIjoxNDgyMDA0MTk1LCJhdXRob3JpdGllcyI6WyJST0xFX0NMSUVOVCJdLCJqdGkiOiIzYWY3NDVlZS1jYWIyLTRjNzctYjdmNi1jOWU4NDNhYzEzZDkiLCJjbGllbnRfaWQiOiJNWV9DTElFTlQifQ.T4Lq5vkNPNE6tDjCIf8NtPjzV6T15pU3WaFoHqnCtv8", 
    "token_type": "bearer", 
    "expires_in": 43199, 
    "scope": "read", 
    "jti": "3af745ee-cab2-4c77-b7f6-c9e843ac13d9"
}
```