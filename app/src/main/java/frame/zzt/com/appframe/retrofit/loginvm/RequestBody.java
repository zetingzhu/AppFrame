package frame.zzt.com.appframe.retrofit.loginvm;

import android.content.Context;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

/**
 * @author: zeting
 * @date: 2020/12/7
 */
public class RequestBody {
    public static final String PARAM_KEY_VALUE = "Chfq5NLG0PtAkbqciKYfAz";
    public static final String PARAM_TIME = "t";//时间毫秒
    public static final String PARAM_DEVICEID = "deviceId";//设备唯一标示
    public static final String PARAM_SOURCEID = "sourceId";//app source
    public static final String PARAM_DEVICETYPE = "device";//设备类型：0=PC，1=安卓，2=ISO，3=微信
    public static final String PARAM_VERSION = "v";
    public static final String PARAM_LANGUAGE = "language";
    public static final String PARAM_AUTH = "auth";//auth认证
    public static final String PARAM_EXCHANGEID = "exchangeId";

    private static HashMap<String, String> addBaseBody() {
        HashMap<String, String> map = new LinkedHashMap<String, String>();
        map.put(PARAM_TIME, System.currentTimeMillis() + "");
        map.put(PARAM_DEVICEID, "1");
        map.put(PARAM_SOURCEID, "10");
        map.put(PARAM_DEVICETYPE, "1");
        map.put(PARAM_VERSION, "1.2.2.32");
        map.put(PARAM_LANGUAGE, "zh-CN");
        map.put("market", "debug");
        map.put(PARAM_EXCHANGEID, "7");
        map.put("timeZoneOffset", String.valueOf(TimeZone.getDefault().getRawOffset() / 1000));
        map.put("remoteLoginTips", "1");
        return map;
    }

    public static HashMap<String, String> getParamMap(Map<String, String> parmMap) {
        //最开始需要公共的参数,map必须要有顺
        HashMap<String, String> map = addBaseBody();
        //接口额外的参数
        if (parmMap != null && parmMap.size() > 0) {
            map.putAll(parmMap);
        }
        //最后加上auth 验证
        map.put(PARAM_AUTH, getAuth(map));
        return map;
    }


    /**
     * map 顺序MD5
     */
    private static String getAuth(Map<String, String> map) {
        Map<String, String> authMap = new TreeMap<>();
        authMap.putAll(map);
        StringBuffer stringBuffer = new StringBuffer();
        Iterator<String> iterator = authMap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = authMap.get(key);
            if (!value.isEmpty()) {
                stringBuffer.append(value);
            }
        }
        return md5(stringBuffer.toString() + PARAM_KEY_VALUE);
    }

    private static String md5(String str) {
        StringBuffer buf = new StringBuffer("");
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte b[] = md.digest();
            int i;
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return buf.toString();
    }
}
