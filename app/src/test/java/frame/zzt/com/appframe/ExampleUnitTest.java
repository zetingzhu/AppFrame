package frame.zzt.com.appframe;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Random;

import frame.zzt.com.appframe.Bluetooth.ByteUtil;

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

    System.out.println("----获取信号值：" + getProgressRssi(-70) );

        int max = 85;
        int min = 70;

        Random random = new Random();
        int result2 = random.nextInt(max)  ;
        System.out.println("----随机数：" + result2 );
        int result3 =  (max - min + 1) ;
        System.out.println("----随机数：" + result3 );
        int result1 = random.nextInt(max) % (max - min + 1) ;
        System.out.println("----随机数：" + result1 );
        int result = random.nextInt(max) % (max - min + 1) + min;

        System.out.println("----随机数：" + result );


        int iii = 21 ;
        byte[] bb = ByteUtil.intToBytes(iii) ;
        byte[] bb1 = ByteUtil.intToByteArrayBig(iii) ;
        byte[] bb2 = ByteUtil.intToBytes2(iii) ;
        byte[] bb3 = ByteUtil.intToByteHex(iii) ;
        System.out.println("----是什么 ：" + ByteUtil.intToBytes(iii));




        //定义一个十进制值
        // 357550110481790
        int valueTen = 110481790;
        //将其转换为十六进制并输出
        String strHex = Integer.toHexString(valueTen);
        System.out.println(valueTen + " [十进制]---->[十六进制] " + strHex);
        //将十六进制格式化输出
        String strHex2 = String.format("%04x",valueTen);
        System.out.println(valueTen + " [十进制]---->[十六进制] " + strHex2);


        //定义一个十六进制值
        String strHex3 = "0015";
        //将十六进制转化成十进制
        int valueTen2 = Integer.parseInt(strHex3,16);
        System.out.println(strHex3 + " [十六进制]---->[十进制] " + valueTen2);

        //可以在声明十进制时，自动完成十六进制到十进制的转换
        int valueHex = 0x0015;
        System.out.println("int valueHex = 0x00001322 --> " + valueHex);


        // 十六进制转数组
        byte[] bbbbb = ByteUtil.hexToByteArray(strHex3);

        String sssss =  ByteUtil.bytesToHex2(bbbbb);

        byte[] bbbbb1 = ByteUtil.intToByteArrayBig(21);


        //将其转换为十六进制并输出
        String strHex4 = ByteUtil.numToHex8(valueTen);
        System.out.println(valueTen + " [十进制]---->[十六进制] " + strHex4);
        String strHex5 = ByteUtil.numToHex16(valueTen);
        System.out.println(valueTen + " [十进制]---->[十六进制] " + strHex5);
        String strHex6 = ByteUtil.numToHex32(valueTen);
        System.out.println(valueTen + " [十进制]---->[十六进制] " + strHex6);

        System.out.println(valueTen + " [十进制]---->[十六进制] " + Integer.toBinaryString(valueTen)) ;

        System.out.println("--结束线--");

}


    public int getProgressRssi( int rssi){
        return Math.round( Math.abs(  div(rssi , 120).floatValue() ) * 100)  ;
    }

    // 除法。
    public BigDecimal div(int v1, int v2){
        BigDecimal b1 = new BigDecimal(Integer.toString(v1));
        BigDecimal b2 = new BigDecimal(Integer.toString(v2));
        return b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP); // 四舍五入，保留2位小数
    }
}