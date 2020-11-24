package frame.zzt.com.appframe;

import org.junit.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;


import frame.zzt.com.appframe.bluetooth.MD5Util;
import frame.zzt.com.appframe.util.ByteUtil;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);

        byte[] byte0 = "1".getBytes();

        Integer i01 = 20180717;
        byte by02 = i01.byteValue();
        byte[] by03 = ByteUtil.intToByteArrayBig(i01);

        byte by01 = ByteUtil.intToByte(22222);
        byte by04 = 0x14;
        byte by05 = 20;

        int int01 = ByteUtil.byteToInt(by01);

        byte byte1 = ByteUtil.bitToByte("00010010");

        String byStr = ByteUtil.getBit(by01);
//        String byStr = ByteUtil.getBit(by02);
        String byStr01 = ByteUtil.getBit(by03[0]);
        String byStr02 = ByteUtil.getBit(by03[1]);
        String byStr03 = ByteUtil.getBit(by03[2]);
        String byStr04 = ByteUtil.getBit(by03[3]);

        System.out.println("---- byStr：" + byStr);
        System.out.println("---- byStr1：" + ByteUtil.toBinaryString(22222));

        System.out.println("----获取信号值：" + getProgressRssi(-70));

        int max = 85;
        int min = 70;

        Random random = new Random();
        int result2 = random.nextInt(max);
        System.out.println("----随机数：" + result2);
        int result3 = (max - min + 1);
        System.out.println("----随机数：" + result3);
        int result1 = random.nextInt(max) % (max - min + 1);
        System.out.println("----随机数：" + result1);
        int result = random.nextInt(max) % (max - min + 1) + min;

        System.out.println("----随机数：" + result);


        int iii = 21;
        byte[] bb = ByteUtil.intToBytes(iii);
        byte[] bb1 = ByteUtil.intToByteArrayBig(iii);
        byte[] bb2 = ByteUtil.intToBytes2(iii);
        byte[] bb3 = ByteUtil.intToByteHex(iii);
        System.out.println("----是什么 ：" + ByteUtil.intToBytes(iii));


        //定义一个十进制值
        // 357550110481790
//        int valueTen = 110481790;
        int valueTen = 102100002;
        //将其转换为十六进制并输出
        String strHex = Integer.toHexString(valueTen);
        System.out.println(valueTen + " [十进制]---->[十六进制] " + strHex);
        //将十六进制格式化输出
        String strHex2 = String.format("%04x", valueTen);
        System.out.println(valueTen + " [十进制]---->[十六进制] " + strHex2);


        //定义一个十六进制值
        String strHex3 = "0015";
        //将十六进制转化成十进制
        int valueTen2 = Integer.parseInt(strHex3, 16);
        System.out.println(strHex3 + " [十六进制]---->[十进制] " + valueTen2);

        //可以在声明十进制时，自动完成十六进制到十进制的转换
        int valueHex = 0x0015;
        System.out.println("int valueHex = 0x00001322 --> " + valueHex);


        // 十六进制转数组
        byte[] bbbbb = ByteUtil.hexToByteArray(strHex3);

        String sssss = ByteUtil.bytesToHex2(bbbbb);

        byte[] bbbbb1 = ByteUtil.intToByteArrayBig(21);


        //将其转换为十六进制并输出
        String strHex4 = ByteUtil.numToHex8(valueTen);
        System.out.println(valueTen + " [十进制]---->[十六进制] " + strHex4);
        String strHex5 = ByteUtil.numToHex16(valueTen);
        System.out.println(valueTen + " [十进制]---->[十六进制] " + strHex5);
        String strHex6 = ByteUtil.numToHex32(valueTen);
        System.out.println(valueTen + " [十进制]---->[十六进制] " + strHex6);

        System.out.println(valueTen + " [十进制]---->[二进制] " + Integer.toBinaryString(valueTen));


        byte[] keyId = ByteUtil.intToByteArrayBig(valueTen);
        String strHex7 = ByteUtil.bytesToHex2(keyId);
        System.out.println(valueTen + " [十进制]---->[十六进制] " + strHex7);

        System.out.println("--结束线--");


        int val = getGpsGsm(30);

        System.out.println("--val:" + val);

//        String autoCode = autoGenericCode( "111" , 15 ) ;
//        System.out.println("补充后的code : " + autoCode );


        String str = "a141b40301028000000000000615EC21585f4b66786a6f4136772a41716e2850502478487451547a";
        byte[] byteMd5L = ByteUtil.hexStr2bytes(str);
//        byte[] byteMd5L = ByteUtil.hexToByteArray(str);
        // md5 加密后的byte
        byte[] md5DataDigest = MD5Util.getMD5Byte(byteMd5L);
        System.out.println("没有验证md5的时候 - 加密后：" + ByteUtil.bytesToHex2(md5DataDigest));


        int vvv1 = 23;
        System.out.println(valueTen + " [byte]---->[十六] " + ByteUtil.bytesToHex2(ByteUtil.intToBytes(vvv1)));
        System.out.println(valueTen + " [十进制]---->[二进制] " + ByteUtil.toBinaryString(vvv1));
        System.out.println(valueTen + " [十进制]---->[八进制] " + ByteUtil.toOctalString(vvv1));
        System.out.println(valueTen + " [十进制]---->[十六进制] " + ByteUtil.toHexString(vvv1));


        System.out.println("--结束线--");
    }

    public static String autoGenericCode(String code, int num) {
        String result = "";
        // 保留num的位数
        // 0 代表前面补充0
        // num 代表长度为4
        // d 代表参数为正数型
        result = String.format("%0" + num + "s", code);
        return result;
    }


    public int getGpsGsm(int val) {
        BigDecimal b1 = new BigDecimal(val);
        BigDecimal b2 = new BigDecimal(24);
        BigDecimal res = b1.divide(b2, 0, BigDecimal.ROUND_HALF_UP);
        Double resDb = res.doubleValue();
        System.out.println("--res:" + res);
        System.out.println("--resDb:" + resDb);
        int ii = (int) Math.ceil(resDb);
        System.out.println("--ii:" + ii);

        DecimalFormat df = new DecimalFormat("######0");
        DecimalFormat df1 = new DecimalFormat("#.00");

        System.out.println("--df:" + df.format(resDb));
        System.out.println("--df1:" + df1.format(resDb));
        BigDecimal bd = new BigDecimal(resDb).setScale(0, BigDecimal.ROUND_HALF_UP);
        return resDb.intValue(); // 四舍五入，保留2位小数
    }

    public int getProgressRssi(int rssi) {
        return Math.round(Math.abs(div(rssi, 120).floatValue()) * 100);
    }

    // 除法。
    public BigDecimal div(int v1, int v2) {
        BigDecimal b1 = new BigDecimal(Integer.toString(v1));
        BigDecimal b2 = new BigDecimal(Integer.toString(v2));
        return b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP); // 四舍五入，保留2位小数
    }
}