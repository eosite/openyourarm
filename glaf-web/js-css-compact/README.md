使用说明
1、安装 nodejs 
   下载地址: https://nodejs.org/en/  请下载LTS版本

2、安装 grunt 
   // npm 需要在 命令行下执行

   //先安装淘宝镜像 避免下载缓慢
   npm install -g cnpm --registry=https://registry.npm.taobao.org
   //使用cnpm命令 安装grunt
   cnpm install -g  grunt-cli
3、cmd 进入当前目录 
   cnpm install

4、执行打包编译
   npm run build(编译.bat) 或者  npm run watch(监听.bat)

目录结构
    |-node_modules // nodejs模块文件夹
    |-Gruntfile.js //grunt配置文件夹
    |-package.json // npm 包管理资源文件

/**
**  Gruntfile.js 文件内容
**  
**  cssFiles:[]  
**  jsFiles:[]
**  extendFiles:[]  如果有新的控件js请添加到此数组里面 既 原来的bootstrap.extend.all.js
**  
**
*/


