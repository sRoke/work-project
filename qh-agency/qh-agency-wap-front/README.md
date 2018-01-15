

```bash
# 安装 webpack
# npm i webpack -g      //如果有错，，版本@2.2.0
# npm i webpack

# 安装依赖包
#npm i
yarn

# 运行 webpack
npm run webpack -- --env.env=dev        // --watch
```


# 上传

```bash
PROJECT_NAME=qh-agency-admin-front

uploadEnv(){
    ENV=$1
    rm -fr build/*
    npm run webpack -- --env.env=$ENV
    tar zcvf target/${PROJECT_NAME}-${ENV}.tar.gz -C build/ .
}

uploadEnvs(){
    npm install
    rm -fr build target
    mkdir target
    
    for i in "$@"; do 
        uploadEnv "$i"
    done
}

uploadEnvs test13 test12 test14 prod
 
./gradlew 
 
```
# 使用mock.js模拟api

1.npm install mockjs --save

2.在使用的js里面引入
   import Mock from "mockjs"
    
3.创建数据  
```
 Mock.mock('http://g.cnaaaaaaaaaaaaaaaa', {
                'name': '[@name](/user/name)()',
                'age|1-100': 100,
                'color': '[@color](/user/color)',
                'aaaaa': 'cccccc'
            });

```

# 使用scss的变量 /app/scss/_variables.scss

 **
    在每个css中引入  @import "../../../../scss/index.scss";
    即可
 **
 
# 关于weui.js应用 github地址(https://github.com/weui/weui)
   **
     项目中新增加了weui.js 
     
     1.拉下代码之后 先 npm install
        若是新建的  先  1.npm install weui --save
                       2.npm install weui.js
     2.安装好之后 在顶层的index.js 中import 'weui' 
     3.然后在需要用到的controller.js中import weui from 'weui.js'
     #### 具体例子请看项目中的weuiTest  
     #### 目录结构 src/app/states/main/company/weuiTest
   **
   
#关于登录验证 
    **
    //判断是否授权-通用方法-依赖
            loginService.loginCtl(true,$location.absUrl());
            
    需要登录验证在页面js最开始写上述js 注意依赖
    **