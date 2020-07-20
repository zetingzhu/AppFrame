package frame.zzt.com.appframe.widgetview;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import frame.zzt.com.appframe.R;
import frame.zzt.com.appframe.ui.home.HomeActivity;

/**
 *
 *
 1.先声明 Widget 的一些属性。在 res 新建 xml 文件夹，创建 appwidget-provider 标签的 xml 文件。
 2.创建桌面要显示的布局。 在 layout 创建 app_widget.xml。
 3.然后来管理 Widget 状态。实现一个继承 AppWidgetProvider 的类。
 4.最后在 AndroidManifest.xml 里，将 AppWidgetProvider类 和 xml属性 注册到一块。
 5.通常我们会加一个 Service 来控制 Widget 的更新时间，后面再讲为什么。
 *
 */
public class NewAppWidget extends AppWidgetProvider {

    private static final String TAG = NewAppWidget.class.getSimpleName();

    // 更新 widget 的广播对应的action
    private final String ACTION_UPDATE_ALL = "com.lyl.widget.UPDATE_ALL";
    // 保存 widget 的id的HashSet，每新建一个 widget 都会为该 widget 分配一个 id。
    private static Set idsSet = new HashSet();

    public static int mIndex;

    /**
     * 接收窗口小部件点击时发送的广播
     */
    @Override
    public void onReceive(final Context context, Intent intent) {
        super.onReceive(context, intent);
        final String action = intent.getAction();

        if (ACTION_UPDATE_ALL.equals(action)) {
            // “更新”广播
            updateAllAppWidgets(context, AppWidgetManager.getInstance(context), idsSet);
        } else if (intent.hasCategory(Intent.CATEGORY_ALTERNATIVE)) {
            // “按钮点击”广播
            mIndex = 0;
            updateAllAppWidgets(context, AppWidgetManager.getInstance(context), idsSet);
        }
    }

    // 更新所有的 widget
    private void updateAllAppWidgets(Context context, AppWidgetManager appWidgetManager, Set set) {
        // widget 的id
        int appID;
        // 迭代器，用于遍历所有保存的widget的id
        Iterator it = set.iterator();

        // 要显示的那个数字，每更新一次 + 1
        mIndex++; // TODO:可以在这里做更多的逻辑操作，比如：数据处理、网络请求等。然后去显示数据

        while (it.hasNext()) {
            appID = ((Integer) it.next()).intValue();

            // 获取 example_appwidget.xml 对应的RemoteViews
            RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

            // 设置显示数字
            remoteView.setTextViewText(R.id.widget_txt, String.valueOf(mIndex));

            // 设置点击按钮对应的PendingIntent：即点击按钮时，发送广播。
            remoteView.setOnClickPendingIntent(R.id.widget_btn_reset, getResetPendingIntent(context));
            remoteView.setOnClickPendingIntent(R.id.widget_btn_open, getOpenPendingIntent(context));

            // 更新 widget
            appWidgetManager.updateAppWidget(appID, remoteView);
        }
    }

    /**
     * 获取 重置数字的广播
     */
    private PendingIntent getResetPendingIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, NewAppWidget.class);
        intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        return pi;
    }

    /**
     * 获取 打开 MainActivity 的 PendingIntent
     */
    private PendingIntent getOpenPendingIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, HomeActivity.class);
        intent.putExtra("main", "这句话是我从桌面点开传过去的。");
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
        return pi;
    }

//    public void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
//
//        CharSequence widgetText = context.getString(R.string.appwidget_text);
//        // Construct the RemoteViews object
//        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
//        views.setTextViewText(R.id.widget_txt, widgetText);
//
//        // Instruct the widget manager to update the widget
//        appWidgetManager.updateAppWidget(appWidgetId, views);
//    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        // There may be multiple widgets active, so update all of them
        // 每次 widget 被创建时，对应的将widget的id添加到set中
        for (int appWidgetId : appWidgetIds) {
            idsSet.add(Integer.valueOf(appWidgetId));
            updateAllAppWidgets(context, appWidgetManager, idsSet);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        // 在第一个 widget 被创建时，开启服务
//        Intent intent = new Intent(context, WidgetService.class);
//        context.startService(intent);
        Log.d(TAG ,  "开始添加 Widget" ) ;
//        Toast.makeText(context, "开始计数", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        // 在最后一个 widget 被删除时，终止服务
        Intent intent = new Intent(context, WidgetService.class);
        context.stopService(intent);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // 当 widget 被删除时，对应的删除set中保存的widget的id
        for (int appWidgetId : appWidgetIds) {
            idsSet.remove(Integer.valueOf(appWidgetId));
        }
        super.onDeleted(context, appWidgetIds);
    }
}

