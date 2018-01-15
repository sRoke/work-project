# 郝太太进货系统/郝太太进货系统管理后台

- nginx参考配置

    ```txt
    server {
        listen *:80;
        server_name agency.kingsilk.net; 
        root html;
        index  index.html;
    
        access_log  logs/agency.kingsilk.net.access.log;
        error_log   logs/agency.kingsilk.net.error.log;
    
        #############################################wap
        location  /local/16600 { 
          add_header Cache-Control no-cache;      
          alias   /home/zcw/work/git-repo/qh-agency/qh-agency-wap-front/build/;
        }
      
        location /local/16600/rs {
          proxy_pass              http://localhost:10060;
          proxy_set_header        Host            $host;
          proxy_set_header        X-Forwarded-For $proxy_add_x_forwarded_for;
        }
    
        ############################################admin
        location  /admin/local/16600 { 
          add_header Cache-Control no-cache;      
          alias   /home/zcw/work/git-repo/qh-agency/qh-agency-admin-front/build/;
        }
      
        location /admin/local/16600/rs {
          proxy_pass              http://localhost:10070;
          proxy_set_header        Host            $host;
          proxy_set_header        X-Forwarded-For $proxy_add_x_forwarded_for;
        }
    }
    ```
    
- 运行

    - qh-agency-wap
    
    ```sh
    $ ./gradlew qh-agency-wap:bootRun -Dspring.profiles.active=base,dev
    ```
    
    - qh-agency-admin

    ```sh
    $ ./gradlew qh-agency-admin:bootRun -Dspring.profiles.active=base,dev
    ```
    
    - qh-agency-wap-front
    
    ```sh
    $ cd qh-agency-wap-front
    $ npm run webpack -- --env.env=dev --watch
    ```
    
    - qh-agency-admin-front

   ```sh
   $ cd qh-agency-admin-front
   $ npm run webpack -- --env.env=dev --watch
   ```
   
- systemctl 配置文件示例 ``

    - /data0/app/qh-agency/qh-agency-admin/start.sh

        ```bash
        #!/bin/bash
        . /etc/profile.d/test13.sh
        
        /usr/local/java/jdk1.8.0_45/bin/java \
            -jar /data0/app/qh-agency/qh-agency-admin/upload/*.jar \
            --spring.profiles.active=base,test13 \
            > /data0/app/qh-agency/qh-agency-admin/defalut.log
        ```
    
    - /usr/lib/systemd/system/qh-agency-admin.service
    
        ```ini
        [Unit]
        Description=qh-agency-admin
        After=network.target
        
        [Service]
        Type=simple
        
        #User=qh
        ExecStart=/data0/app/qh-agency/qh-agency-admin/start.sh
        
        LimitFSIZE=infinity
        LimitCPU=infinity
        LimitAS=infinity
        LimitNOFILE=64000
        LimitRSS=infinity
        LimitNPROC=64000
        
        [Install]
        WantedBy=multi-user.target
        ```
    
    - 常用 systemctl 命令:
    
        ```bash
        systemctl daemon-reload
        systemctl enable    qh-agency-admin
        systemctl status    qh-agency-admin
        systemctl start     qh-agency-admin
        systemctl stop      qh-agency-admin
        systemctl restart   qh-agency-admin
        ```