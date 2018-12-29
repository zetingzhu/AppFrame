package frame.zzt.com.appframe.UI.login;

import org.junit.Test;

/**
 * Created by zeting
 * Date 18/12/25.
 */
public class LoginActivity31Test {

    private static final String PACKAGE_ESPRESSOTEST = "frame.zzt.com.appframe";

    @Test
    public void login01() throws Exception {
//        //初始化一个UiDevice对象
//        UiDevice mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
//        // 点击home键，回到home界面
//        mDevice.pressHome();
//        String launcherPackage = mDevice.getLauncherPackageName();
//        assertThat(launcherPackage,notNullValue());
//        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)),3);
//
//        // 启动espressotest App
//        Context context = InstrumentationRegistry.getContext();
//        Intent intent = context.getPackageManager().getLaunchIntentForPackage(PACKAGE_ESPRESSOTEST);
//        // 清除以前的实例
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        context.startActivity(intent);
//
//        // 等待应用程序启动
//        mDevice.wait(Until.hasObject(By.pkg(PACKAGE_ESPRESSOTEST).depth(0)),3);
//        //通过id找到输入框一
//        UiObject edt1 = mDevice.findObject(new UiSelector().resourceId(PACKAGE_ESPRESSOTEST + ":id/email").className(AutoCompleteTextView.class));
//        //往里面输入字符2
//        edt1.setText("13797745363");
//        //通过id找到输入框二
//        UiObject edt2 = mDevice.findObject(new UiSelector().resourceId(PACKAGE_ESPRESSOTEST + ".UI.login:id/password").className(EditText.class));
//        //往里面输入字符5
//        edt2.setText("123456");
//        //通过文本"计算"找到按钮
//        UiObject btn = mDevice.findObject(new UiSelector().text("Login 0").className(Button.class));
//        //执行点击事件，计算结果
//        btn.click();

    }

    @Test
    public void login02() throws Exception {

    }

    @Test
    public void login03() throws Exception {

    }

}