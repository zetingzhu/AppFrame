  signingConfigs {
        releaseSign {
            keyAlias 'key0'
            keyPassword 'android'
            storeFile file('../dev/ktingKey.jks')
            storePassword 'android'
        }
    }



1. 多渠道打包jar
walle-cli-all.jar
https://github.com/Meituan-Dianping/walle

2.命令使用方法
https://github.com/Meituan-Dianping/walle/blob/master/walle-cli/README.md

3.多渠道打包命令
java -jar walle-cli-all.jar batch -f  D:\ZZTAndroid\Android_Work\AppFrame\dev\channel  D:\ZZTAndroid\Android_Work\AppFrame\app\build\outputs\a
pk\debug\app-debug.apk D:\ZZTAndroid\Android_Work\AppFrame\dev\outapk

4.curl 命令使用
http://www.ruanyifeng.com/blog/2019/09/curl-reference.html

