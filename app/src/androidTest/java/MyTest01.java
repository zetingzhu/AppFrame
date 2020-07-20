import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.rule.ActivityTestRule;
import android.text.TextUtils;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import frame.zzt.com.appframe.R;
import frame.zzt.com.appframe.ui.Fragment.DemoInfo;
import frame.zzt.com.appframe.ui.login.LoginActivity3;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;

/**
 * Created by zeting
 * Date 18/12/26.
 */
public class MyTest01 {
    private String mStringToBetyped;

    /**
        1.首先创建一个@Rule ActivityTestRule用来指明被测试的Activity;
        2.可以使用@Before注解来做测试前的准备工作。
        3.测试方法写在@Test
     */

    @Rule
    public ActivityTestRule<LoginActivity3> mActivityRule = new ActivityTestRule<>( LoginActivity3.class );

    @Before
    public void initString(){
        mStringToBetyped = "click";
    }

    @Test
    public void clickTest() throws InterruptedException {
//        Thread.sleep(1000);
//        onView(withText(mStringToBetyped)).perform(click());
//        Thread.sleep(1000);

        Thread.sleep(1000);
        // 在emaile中输入字符，关闭键盘
        onView(withId(R.id.email)).perform(ViewActions.typeText("13797745363") , ViewActions.closeSoftKeyboard()) ;
        // 在emaile中输入字符，关闭键盘
        onView(withId(R.id.password)).perform(ViewActions.typeText("123456") , ViewActions.closeSoftKeyboard()) ;
        // 点击登录按钮
        onView(withId(R.id.button1)) .perform(click()) ;

        Thread.sleep(1000);

        // 查找list 是否显示
//        onView(allOf( withText("") , isDisplayed() )) ;
//        onData(allOf( is(instanceOf(DemoInfo.class)),  is("item: 12") ))  .perform(click());
        onData(searchMainItemWithName("滑动解锁动画"))  .perform(click());

    }

    public static Matcher<Object> searchMainItemWithName(final String name) {
        return new BoundedMatcher<Object, DemoInfo>(DemoInfo.class) {
            @Override
            protected boolean matchesSafely(DemoInfo item) {
                int id1 = item.title ;
                String title = "" ;
                System.out.println("名称：" + title );
                return item != null
                        && !TextUtils.isEmpty( title )
                        && title.equals(name);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("SalesOpp has Name: " + name);
            }
        };
    }


}
