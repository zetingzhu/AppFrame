package frame.zzt.com.appframe.networkopt;

import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;

import androidx.annotation.RequiresApi;

import android.util.Log;

/**
 * Created by zeting
 * Date 19/7/23.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class NetworkCallbackImpl extends ConnectivityManager.NetworkCallback {

    private static final String TAG = NetworkCallbackImpl.class.getSimpleName();

    public NetworkCallbackImpl() {
        super();
    }


    /**
     * 在网络失去连接的时候回调，但是如果是一个生硬的断开，他可能不回调
     * 实践中在网络连接正常的情况下，丢失数据会有回调
     */
    @Override
    public void onLosing(Network network, int maxMsToLive) {
        super.onLosing(network, maxMsToLive);
        Log.i(TAG, " - onLosing - >>> " + network.toString() + " - max :" + maxMsToLive);
    }

    /**
     * 网络可用的回调
     */
    @Override
    public void onAvailable(Network network) {
        super.onAvailable(network);
        Log.i(TAG, " - onAvailable - >>> 网络已经连接 :" + network.toString());
    }

    /**
     * 网络丢失的回调
     */
    @Override
    public void onLost(Network network) {
        super.onLost(network);
        Log.i(TAG, " - onLost - >>> 网络已经中断 :" + network.toString());
    }

    /**
     * 按照官方的字面意思是，当我们的网络的某个能力发生了变化回调，那么也就是说可能会回调多次
     */
    @Override
    public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities);
        Log.i(TAG, " - onCapabilitiesChanged - >>>  :" + network.toString() + " - " + networkCapabilities.toString());
        if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i(TAG, " - onCapabilitiesChanged - >>> 网络已经发生改变 WIFI");
            } else {
                Log.i(TAG, " - onCapabilitiesChanged - >>> 网络已经发生改变 其他网络");
            }
        }
    }


    /**
     * 当建立网络连接时，回调连接的属性
     */
    @Override
    public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
        super.onLinkPropertiesChanged(network, linkProperties);
        Log.i(TAG, " - onLinkPropertiesChanged - >>>  :" + network.toString() + " - " + linkProperties.toString());
    }
}
