package frame.zzt.com.appframe.UI.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import frame.zzt.com.appframe.R;
import frame.zzt.com.appframe.UI.Fragment.FirstFragment;
import frame.zzt.com.appframe.UI.Fragment.SecondFragment;
import frame.zzt.com.appframe.widget.MyFragmentTabHost;

/**
 * Created by allen on 18/8/7.
 */

public class HomeActivity extends FragmentActivity {
    private static final String TAG = HomeActivity.class.getSimpleName();
    /**
     * 菜单项标签：车辆状况
     */
    private static final String MENU_ITEM_TAG_DETAILS = "details";
    /**
     * 菜单项标签：服务中心
     */
    private static final String MENU_ITEM_TAG_SERVICE = "service";
    /**
     * 菜单项标签：设置
     */
    private static final String MENU_ITEM_TAG_SETTING = "setting";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        initView();

        initFragment(savedInstanceState);
    }


    /**
     * 初始化Fragment
     *
     * @param savedInstanceState
     */
    private void initFragment(Bundle savedInstanceState) {

        //如果之前已经存在Fragment，那是应用被杀死后重新创建，需移除原有的Fragment，否则地图不能操作
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null && fragments.size() > 0) {
            Log.d(TAG, "fragments.size = " + fragments.size());
            for (Fragment f : fragments) {
                if (f == null) {
                    continue;
                }
                Log.d(TAG, "remove exist fragment:" + f.toString());
                getSupportFragmentManager().beginTransaction().remove(f).commit();
            }
        }

    }//end initFragment

    private void initView() {

        //构建菜单项
        List<MenuItem> menuItemList = new ArrayList<MenuItem>();

        //车辆状况
        MenuItem item = new MenuItem();
        item.setTag(MENU_ITEM_TAG_DETAILS);
        item.setTitle(getString(R.string.home_menu_vehicle));
        item.setIcon(R.drawable.home_menu_vehicle_selector);
        item.setFragmentClass(FirstFragment.class);
        menuItemList.add(item);

        //服务中心
        item = new MenuItem();
        item.setTag(MENU_ITEM_TAG_SERVICE);
        item.setTitle(getString(R.string.home_menu_service));
        item.setIcon(R.drawable.home_menu_service_selector);
        item.setFragmentClass(SecondFragment.class);
        menuItemList.add(item);

        item = new MenuItem();
        item.setTag(MENU_ITEM_TAG_SETTING);
        item.setTitle(getString(R.string.home_menu_setting));
        item.setIcon(R.drawable.home_menu_setting_selector);
        item.setFragmentClass(FirstFragment.class);
        menuItemList.add(item);

        //实例化TabHost对象，得到TabHost
        MyFragmentTabHost tabHost = (MyFragmentTabHost) findViewById(android.R.id.tabhost);
        //需保存状态的菜单项
        tabHost.setSaveStateTabByTags(new String[]{MENU_ITEM_TAG_DETAILS, MENU_ITEM_TAG_SERVICE});
        tabHost.setup(this, getSupportFragmentManager(), R.id.home_frame);

        for (MenuItem menuItem : menuItemList) {
            View menuItemView = getMenuItemView(menuItem);
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec taSpec = tabHost.newTabSpec(menuItem.getTag()).setIndicator(menuItemView);

            //将Tab按钮添加进Tab选项卡中
            tabHost.addTab(taSpec, menuItem.getFragmentClass(), null);
        }
        //去除分割竖线
        tabHost.getTabWidget().setDividerDrawable(null);

    }

    /**
     * 获取菜单项View
     *
     * @param menuItem
     * @return
     */
    private View getMenuItemView(MenuItem menuItem) {
        View view = getLayoutInflater().inflate(R.layout.home_menu_item, null);

        ImageView ivIcon = (ImageView) view.findViewById(R.id.home_menu_item_ivIcon);
        ivIcon.setImageResource(menuItem.getIcon());

        TextView tvTitle = (TextView) view.findViewById(R.id.home_menu_item_tvTitle);
        tvTitle.setText(menuItem.getTitle());

        return view;
    }

    /**
     * 菜单项属性
     */
    private class MenuItem {
        /**
         * 菜单项图标
         */
        private int icon;
        /**
         * 菜单项标题
         */
        private String title;

        /**
         * 菜单项标签
         */
        private String tag;
        /**
         * 菜单项标签
         */
        private Class<?> fragmentClass;


        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public Class<?> getFragmentClass() {
            return fragmentClass;
        }

        public void setFragmentClass(Class<?> fragmentClass) {
            this.fragmentClass = fragmentClass;
        }
    }
}
