组件化实现的几个步骤

1. 修改当前项目是模块还是库
if (isModule.toBoolean()) {//如果是组件模式---可以单独运行
    apply plugin: 'com.android.application'
} else {//集成模式，不能单独运行
    apply plugin: 'com.android.library'
}

2. 对当前项目的 applicationId 判断
 if (isModule.toBoolean()) {
            applicationId "com.zzt.headerrecycleview"
 }
 
 3.添加路由，
  //ARouter添加
         javaCompileOptions {
             annotationProcessorOptions {
                 arguments = [AROUTER_MODULE_NAME: project.getName()]
             }
         }
         
         
4. 统一java    
    // java8 支持
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
    
    
5.修改处理 AndroidManifest.xml 文件
    // 多渠道资源文件修改
    sourceSets {
        main {
            if (isModule.toBoolean()) {//组件模式下
                manifest.srcFile 'src/main/AndroidManifest.xml'
            } else {//集成模式下
                manifest.srcFile 'src/main/library/AndroidManifest.xml'
                //集成开发模式下排除debug文件夹中的所有Java文件
                java {
                    exclude 'debug/**'
                }
            }
        }
    }    
    
6.修改项目导入库为api 模式 并且添加ARouter添加

//ARouter添加
    annotationProcessor 'com.alibaba:arouter-compiler:1.2.2'

7.实现 myapp 继承 BaseApplication