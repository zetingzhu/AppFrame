1. 嘿嘿
把自己平时使用的功能整理起来


dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile 'com.android.support:appcompat-v7:25.3.1'
    compile 'com.android.support:design:25.3.1'
    testCompile 'junit:junit:4.12'

    compile 'com.google.code.gson:gson:2.8.5'
    // yanzhenjie/AndPermission 运行权限管理库
    compile 'com.yanzhenjie:permission:1.1.2'
    // rxjava
    compile 'io.reactivex.rxjava2:rxandroid:2.0.1'
    compile 'io.reactivex.rxjava2:rxjava:2.1.5'
    // okhttp 网络请求
    compile 'com.squareup.okhttp3:okhttp:3.11.0'
    compile 'com.squareup.retrofit2:retrofit:2.4.0'
    //ConverterFactory的String依赖包
    compile 'com.squareup.retrofit2:converter-scalars:2.3.0'
    //ConverterFactory的Gson依赖包
    compile 'com.squareup.retrofit2:converter-gson:2.3.0'
    //CallAdapterFactory的Rx依赖包
    compile 'com.squareup.retrofit2:adapter-rxjava2:2.3.0'
    // butterknife 注解框架
    compile 'com.jakewharton:butterknife:8.8.1'
    annotationProcessor 'com.jakewharton:butterknife-compiler:8.8.1'
    // 数据库框架
    compile 'org.litepal.android:core:2.0.0'
    // greendao 数据库框架
    compile 'org.greenrobot:greendao:3.2.0'
    // greendao 使用加密数据库时需要添加
    compile 'net.zetetic:android-database-sqlcipher:3.5.7'
    // 蓝牙ble框架
    compile 'com.clj.fastble:FastBleLib:2.3.2'
    compile 'com.inuker.bluetooth:library:1.4.0'
}