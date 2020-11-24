package com.example.viewautosizelayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.zzt.zztutillibrary.ByteUtils;

import java.lang.reflect.Field;
import java.util.HashMap;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AutoSizeActivity extends AppCompatActivity {

    private static final String TAG = AutoSizeActivity.class.getSimpleName();


    HashMap<String, String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testMap();
        logBaseMapFun();
        testByte();
        finish();
    }

    private void testByte() {
        int to1 = 123456;
        LogE(to1);
        LogE(to1, " >> 1", to1 >> 1);
        LogE(to1, " << 1", to1 << 1);

        int to7 = 12345;
        LogE(to7);
        LogE(to7, " >> 1", to7 >> 1);
        LogE(to7, " << 1", to7 << 1);

        int to5 = -1234;
        LogE(to5);
        LogE(to5, " >> 10", to5 >> 10);
        LogE(to5, " << 10", to5 << 10);

        int to3 = -12345678;
        LogE(to3);
        int to4 = to3 >>> 10;
        LogE(to3, " >>> 10", to4);

        int to6 = 12345678;
        LogE(to6);
        LogE(to6, " >>> 10", to6 >>> 10);


        Object key = "qqqww";
        int h = key.hashCode();
        Log.w(TAG, "hashCode    值 ：" + h + "  -  " + ByteUtils.toBinaryString(h));
        Log.w(TAG, "hashCode 位移值 ：" + (h >>> 16) + "  -  " + ByteUtils.toBinaryString(h >>> 16));
        Log.w(TAG, "hashCode 位移值 ：" + (h ^ (h >>> 16)) + "  -  " + ByteUtils.toBinaryString(h ^ (h >>> 16)));
        /***
         hashCode    值 ：107836657   -  0000 0110 0110 1101 0111 0100 1111 0001
         hashCode 位移值 ：1645       -  0000 0000 0000 0000 0000 0110 0110 1101
         hashCode 位移值 ：107836060  -  0000 0110 0110 1101 0111 0010 1001 1100
         **/

    }

    private void LogE(int i) {
        Log.e(TAG, "\n\t");
        LogE("", i);
    }

    private void LogE(String tag, int i) {
        LogE(-1, tag, i);
    }

    private void LogE(int res, String tag, int i) {
        Log.e(TAG, (res == -1 ? "" : res) + tag + " Int 【 " + i + " 】 转二进制字符串：" + ByteUtils.toBinaryString(i));
    }


    private void testMap() {
//        getThreshold();
        // 16*0.75 = 12
        map = new HashMap<>(5);
        getMapThreshold(map);
        for (int i = 1; i <= 10; i++) {
            if (i == 12) {
                map.put("key_" + i, "扩容 Value_" + i);
            } else if (i == 24) {
                map.put("key_" + i, "扩容 Value_" + i);
            } else if (i == 48) {
                map.put("key_" + i, "扩容 Value_" + i);
            } else {
                map.put("key_" + i, "Value_" + i);
            }
        }
        // 获取当前map节点容量
        int getMpaLength = getMapThreshold(map);


        /****************取值****************/
        String getKey = "key_1";
        String getValue = map.get(getKey);
        Log.d(TAG, "取出来的值：" + getValue);

        try {
            Class clazz = HashMap.class;
            Field field = clazz.getDeclaredField("table");
            field.setAccessible(true);
            Object table = field.get(map);

            Log.d(TAG, "取出来的值：" + table);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

//        hash(getKey) ;
//        if ((tab = table) != null && (n = tab.length) > 0 &&
//                (first = tab[(n - 1) & hash]) != null) {

        /***删除数据**/
        map.remove(getKey);
    }

    /**
     * 获取当前map的容量
     *
     * @param hashMap
     */
    public int getMapThreshold(HashMap<String, String> hashMap) {
        try {
            Class clazz = HashMap.class;
            Field field = clazz.getDeclaredField("threshold");
            field.setAccessible(true);
            Log.d(TAG, "当前map的最大阀值" + field.get(hashMap));
            return (int) field.get(hashMap);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取扩容大小
     */
    public void getThreshold() {
        try {
            HashMap<String, Integer> hashMap = new HashMap<>();
            Class clazz = HashMap.class;
            // threshold是hashmap对象里的一个私有变量，若hashmap的size超过该数值，则扩容。这是通过反射获取该值
            Field field = clazz.getDeclaredField("threshold");
            //setAccessible设置为true可以开启对似有变量的访问
            field.setAccessible(true);
            int threshold = 0;
            for (int i = 0; i < 1000; i++) {
                hashMap.put(String.valueOf(i), 0);
                if ((int) field.get(hashMap) != threshold) {
                    threshold = (int) field.get(hashMap);
                    // 默认的负载因子是0.75,也就是说实际容量是/0.75
                    Log.d(TAG, "当前数据触发了扩容：" + i + " -最大阀值：" + threshold + " -扩容后可添加容量 " + ((int) field.get(hashMap) / 0.75));
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * hashmap 中的基本方法测试
     */
    public void logBaseMapFun() {

        Log.d(TAG, "输入值：" + enterTenToTwo(1));
        int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16
        Log.d(TAG, "位运算：" + DEFAULT_INITIAL_CAPACITY);
        Log.d(TAG, "输入值：" + enterTenToTwo(DEFAULT_INITIAL_CAPACITY));

        int a = -5;
        Log.d(TAG, "输入值 a ：" + enterTenToTwo(a));
        int pa = a << 4; // aka 16
        Log.d(TAG, "位运算 a ：" + pa);
        Log.d(TAG, "输入值 a ：" + enterTenToTwo(pa));
        int pa1 = a >> 7; // aka 16
        Log.d(TAG, "位运算 a1 ：" + pa1);
        Log.d(TAG, "输入值 a1 ：" + enterTenToTwo(pa1));
        int pa2 = a >>> 7; // aka 16
        Log.d(TAG, "位运算 a2 ：" + pa2);
        Log.d(TAG, "输入值 a2 ：" + enterTenToTwo(pa2));

        int MAXIMUM_CAPACITY = 1 << 30;
        Log.d(TAG, "位运算 最大容量：" + MAXIMUM_CAPACITY);
        Log.d(TAG, "输入值 最大容量：" + enterTenToTwo(MAXIMUM_CAPACITY));

        log2Base(2, 0);
        log2Base(2, 1);
        log2Base(2, 2);
        log2Base(2, 3);
        log2Base(2, 4);
        log2Base(2, 5);
        log2Base(2, 6);
        log2Base(2, 7);
        log2Base(2, 8);
        log2Base(2, 9);
        log2Base(2, 10);
        log2Base(2, 30);
        Log.d(TAG, "输入值 15：" + enterTenToTwo(15));

        int size = tableSizeFor(17);
        Log.d(TAG, "扩容机制 结果 ：" + size);

        getHash("key_1");
        /**
         * 开始计算hash值 ：101944913 -   110000100111000111001010001
         * 开始计算hash值 ：1555 -                        11000010011
         * 计算得到的hash值 ：101943362 - 110000100111000100001000010
         */
//        getHash("key_2");
//        getHash(3456);
//        getHash("String");
//        getHash("String");


    }

    public void getHash(Object obj) {
        int getHash = hash(obj);
        Log.d(TAG, "计算得到的hash值 ：" + getHash + " - " + enterTenToTwo(getHash));


    }

    int hash(Object key) {
        int h;
//        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
        h = key.hashCode();
        Log.d(TAG, "开始计算hash值 ：" + h + " - " + enterTenToTwo(h));
        Log.d(TAG, "开始计算hash值2 ：" + h + " - " + enterTenToTwo2(h));
        Log.d(TAG, "开始计算hash值3 ：" + h + " - " + enterTenToTwo3(h));
        Log.d(TAG, "开始计算hash值 ：" + (h >>> 16) + " - " + enterTenToTwo((h >>> 16)));
        return (key == null) ? 0 : h ^ (h >>> 16);
    }

    public void log2Base(int b, int e) {
        Log.d(TAG, "输入值 " + b + " " + e + "：" + Math.pow(b, e) + " = " + enterTenToTwo(Double.valueOf(Math.pow(b, e)).intValue()));
    }

    /**
     * a |= b 等价于 a = a|b
     * a &= b 等价于 a = a&b
     * a ^= b 等价于 a = a^b
     * |=：两个二进制对应位都为0时，结果等于0，否则结果等于1； 两个位只要有一个为1，那么结果就是1，否则就为0
     * &=：两个二进制的对应位都为1时，结果为1，否则结果等于0； 两个操作数中位都为1，结果才为1，否则结果为0
     * ^=：两个二进制的对应位相同，结果为0，否则结果为1。     两个操作数的位中，相同则结果为0，不同则结果为1
     * ~ 如果位为0，结果是1，如果位为1，结果是0
     *
     * @param cap
     * @return
     */
    int tableSizeFor(int cap) {
        int MAXIMUM_CAPACITY = 1 << 30;
        Log.d(TAG, "扩容机制 ：" + cap + " =二进制：" + enterTenToTwo(cap));
        int n = cap - 1;
        Log.d(TAG, "扩容机制 ：" + n + " =二进制：" + enterTenToTwo(n) + " =n >>> 1：" + enterTenToTwo(n >>> 1));
        n |= n >>> 1;
        Log.d(TAG, "扩容机制 1：" + n + " =二进制：" + enterTenToTwo(n) + " =n >>> 2：" + enterTenToTwo(n >>> 2));
        n |= n >>> 2;
        Log.d(TAG, "扩容机制 2：" + n + " =二进制：" + enterTenToTwo(n) + " =n >>> 4：" + enterTenToTwo(n >>> 4));
        n |= n >>> 4;
        Log.d(TAG, "扩容机制 4：" + n + " =二进制：" + enterTenToTwo(n) + " =n >>> 5：" + enterTenToTwo(n >>> 5));
        n |= n >>> 8;
        Log.d(TAG, "扩容机制 8：" + n + " =二进制：" + enterTenToTwo(n) + " =n >>> 16：" + enterTenToTwo(n >>> 16));
        n |= n >>> 16;
        Log.d(TAG, "扩容机制 16：" + n + " =二进制：" + enterTenToTwo(n) + " =n >>> 16：" + enterTenToTwo(n >>> 16));
        Log.d(TAG, "扩容机制 最后结果：" + (n + 1) + " 二进制输出：" + enterTenToTwo(n + 1));
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }


    public String enterTenToTwo(Integer input) {
        String result = Integer.toBinaryString(input);//十进dao制转二进制
        return result;
    }


    public String enterTenToTwo2(Integer input) {
        String result = toUnsignedString0(input, 1);
        return result;
    }


    public static String enterTenToTwo3(int num) {
        char[] chs = new char[Integer.SIZE];
        for (int i = 0; i < Integer.SIZE; i++) {
            chs[Integer.SIZE - 1 - i] = (char) ((num >> i & 1) + '0');
        }
        return new String(chs);
    }

    public void binaryToDecimal(int n) {
        for (int i = 31; i >= 0; i--)
            System.out.print(n >>> i & 1);
    }


    public static String toBinaryString(int i) {
        return toUnsignedString0(i, 1);
    }


    private static String toUnsignedString0(int val, int shift) {
        int mag = Integer.SIZE;
        int chars = Math.max(((mag + (shift - 1)) / shift), 1);
        char[] buf = new char[chars];
        formatUnsignedInt(val, shift, buf, 0, chars);
        return new String(buf);
    }

    static int formatUnsignedInt(int val, int shift, char[] buf, int offset, int len) {
        int charPos = len;
        int radix = 1 << shift;
        int mask = radix - 1;
        do {
            buf[offset + --charPos] = digits[val & mask];
            val >>>= shift;
        } while (charPos > 0);

        return charPos;
    }

    final static char[] digits = {
            '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b',
            'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z'
    };

}