
# 说明
1. 该目录中的文件仅仅用以演示，大家本地使用时，最好另外复制一份，不要直接修改，比如端口号，路径等。


# docker 安装 nginx

```bash
# 安装 nginx
mkdir -p ~/tmp/my-nginx/conf/conf.d
mkdir -p ~/tmp/my-nginx/logs
mkdir -p ~/tmp/my-nginx/html

docker pull nginx:1.11.8

docker run -d --name my-nginx nginx:1.11.8
docker cp my-nginx:/etc/nginx/nginx.conf ~/tmp/my-nginx/conf/nginx.conf
docker cp my-nginx:/etc/nginx/conf.d ~/tmp/my-nginx/conf/
docker cp my-nginx:/usr/share/nginx/html ~/tmp/my-nginx/
docker stop my-nginx
docker rm my-nginx

docker run \
    --name my-nginx \
    -d \
    -p 80:80 \
    -p 443:443 \
    -v ~/tmp/my-nginx/conf/nginx.conf:/etc/nginx/nginx.conf:ro \
    -v ~/tmp/my-nginx/conf/conf.d:/etc/nginx/conf.d:ro \
    -v ~/tmp/my-nginx/html/:/usr/share/nginx/html:ro \
    -v ~/tmp/my-nginx/logs/:/etc/nginx/logs:rw \
    nginx:1.11.8

docker exec -it my-nginx bash
```

# 修改配置文件

```bash
cp login.kingsilk.net.conf ~/tmp/my-nginx/conf/conf.d

# 1. 修改配置文件中 14100 为你自己IP地址的对应的端口号，比如 16000。其中 41， 60 为自己IP地址的后两位。
# 2. 修改其中静态文件的路径为自己本地实际的路径。

docker restart my-nginx
docker ps
docker logs my-nginx

net.kingsilk.qh.agency.admin
# 测试本地 spring-boot/tomcat 启动状况
curl net.kingsilk.qh.agency.admin \
-H 'Host: agency.kingsilk.net'   \
-H 'Accept: */*' \
-v

# 测试本地 nginx 反向代理状况 (注意：端口为80端口)
curl net.kingsilk.qh.agency.admin \
-H 'Host: agency.kingsilk.net' \
-H 'Accept: */*' \
-v

# 测试线上 nginx 反向代理状况 (注意：协议为 https协议，域名为线上域名)
curl net.kingsilk.qh.agency.admin \
-H 'Accept: */*' \
-v

net.kingsilk.qh.agency.admin
# 测试本地 spring-boot/tomcat 启动状况
curl 'http://localhost:10060/local/14100/rs/' \
-H 'Host: agency.kingsilk.net'   \
-H 'Accept: */*' \
-v

# 测试本地 nginx 反向代理状况 (注意：端口为80端口)
curl 'http://localhost/local/14100/rs/' \
-H 'Host: agency.kingsilk.net' \
-H 'Accept: */*' \
-v

# 测试线上 nginx 反向代理状况 (注意：协议为 https协议，域名为线上域名)
curl 'https://agency.kingsilk.net/local/14100/rs/' \
-H 'Accept: */*' \
-v

```