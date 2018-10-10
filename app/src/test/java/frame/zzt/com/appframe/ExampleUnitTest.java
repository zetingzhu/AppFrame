package frame.zzt.com.appframe;

import org.junit.Test;

import java.math.BigDecimal;

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