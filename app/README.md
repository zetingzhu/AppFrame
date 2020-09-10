组件化实现的几个步骤

1. 修改当前项目是模块还是库
if (isModule.toBoolean()) {//如果是组件模式---可以单独运行
    apply plugin: 'com.android.application'
} else {//集成模式，不能单独运行
    apply plugin: 'com.android.library'
}

2. 对当前项目的 applicationId 判断
    defaultConfig {
            ......
            if (isModule.toBoolean()) {
                applicationId "com.zzt.headerrecycleview"
            }
             ......
 }
 
 3.添加路由，
  //ARouter添加
    defaultConfig {
         ......
         javaCompileOptions {
             annotationProcessorOptions {
                 arguments = [AROUTER_MODULE_NAME: project.getName()]
             }
         }
         ......
    }
         
4. 统一java    
    // java8 支持
android {
    ......
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
    ......
}    
    
5.修改处理 AndroidManifest.xml 文件
    // 多渠道资源文件修改
android {
        ......
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
      ......  
}
    
6.修改项目导入库为api 模式 并且添加ARouter添加

//ARouter添加
    annotationProcessor 'com.alibaba:arouter-compiler:1.2.2'

7.实现 myapp 继承 BaseApplication

8.如果项目里面有kotlin 代码，需要导入kotlin 依赖

product 的 Gradle配置
apply plugin: 'com.alibaba.arouter'

    dependencies {
        // Arouter 版本配置
        def arouter_register_version = "1.0.2"
        classpath "com.alibaba:arouter-register:$arouter_register_version"
    }
Module 的 gradle 配置
apply plugin: 'kotlin-kapt'
android {
kapt {
    arguments {
        arg("moduleName", project.getName())
    }
}
}
注意点： 必须用 kapt ""
dependencies {
 
    // ARouter 包
    api 'com.alibaba:arouter-api:1.5.0'
    kapt 'com.alibaba:arouter-compiler:1.2.2'

}

问题
1.每个model 里面布局，资源文件，不能相同
2.kotlit 和Java 应用方式不同

排查点:
1.清理本地缓存,然后重新编译(大部分情况都是缓存的问题)
2.App层 是否引入了对应的module
3.检查build->generated->source->apt(kapt)是否生成了对应的映射
4.检查每个gradle 中是否正确配置了 相应库的 引用.
5.检查 moduleName 是否正确配置
5.检查 不同module 中 activity 或者 fragment 的path 或者 group 是否有错误
