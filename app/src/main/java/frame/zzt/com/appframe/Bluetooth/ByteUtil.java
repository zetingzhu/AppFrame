package frame.zzt.com.appframe.Bluetooth;

/**
 * Created by zeting
 * Date 18/12/5.
 */

public class ByteUtil {

    /**
     * 将int类型的数据转换为byte数组
     * @param n int数据
     * @return 生成的byte数组
     */
    public static byte[] intToBytes(int n){
        String s = String.valueOf(n);
        return s.getBytes();
    }

    /**
     * 将byte数组转换为int数据
     * @param b 字节数组
     * @return 生成的int数据
     */
    public static int bytesToInt(byte[] b){
        String s = new String(b);
        return Integer.parseInt(s);
    }

    /**
     * 将int类型的数据转换为byte数组
     * 原理:将int数据中的四个byte取出,分别存储
     * @param n int数据
     * @return 生成的byte数组
     */
    public static byte[] intToBytes2(int n){
        byte[] b = new byte[4];
        for(int i = 0;i < 4;i++){
            b[i] = (byte)(n >> (24 - i * 8));
        }
        return b;
    }

    /**
     * 将byte数组转换为int数据
     * @param b 字节数组
     * @return 生成的int数据
     */
    public static int byteToInt2(byte[] b){
        return (((int)b[0]) << 24) + (((int)b[1]) << 16) + (((int)b[2]) << 8) + b[3];
    }


    public static String bytesToHex2(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() < 2) {
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static byte[] hexToByteArray(String inHex) {
        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % 2 == 1) {
            //奇数
            hexlen++;
            result = new byte[(hexlen / 2)];
            inHex = "0" + inHex;
        } else {
            //偶数
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexlen; i += 2) {
            result[j] = hexToByte(inHex.substring(i, i + 2));
            j++;
        }
        return result;
    }

    /**
     * Hex字符串转byte
     *
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte
     */
    public static byte hexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }


    /**
     * 字符串转换成为16进制(无需Unicode编码)
     * @param str
     * @return
     */
    public static String str2HexStr(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
            // sb.append(' ');
        }
        return sb.toString().trim();
    }

    /**
     * 16进制直接转换成为字符串(无需Unicode解码)
     * @param hexStr
     * @return
     */
    public static String hexStr2Str(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }

    /**
     * int转byte[] 已大端模式
     *
     * @param a
     * @return
     */
    public static byte[] intToByteArrayBig(int a) {
        return new byte[]{
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)
        };
    }

    /**
     * int转byte[] 已小端模式
     *
     * @param a
     * @return
     */
    public static byte[] intToByteArrayLittel(int a) {
        return new byte[]{
                (byte) (a & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 24) & 0xFF)
        };
    }


    /**
     * 数组转换成十六进制，在转换成int
     * @return
     */
    public static Integer byteHexToInt(byte[] bb){

        String sssss =  ByteUtil.bytesToHex2(bb);

        //将十六进制转化成十进制
        int valueTen = Integer.parseInt(sssss,16);
        return valueTen ;
    }

    /**
     *  将int 数据装换成byte 2位的是需求种的数组
     * @return
     */
    public static byte[] intToByteHex(int in){

        String strHex2 = String.format("%04x",in);
        String strHex3 = String.format("%08x",in);

        // 十六进制转数组
        byte[] intByte = ByteUtil.hexToByteArray(strHex2);
        byte[] intByte1 = ByteUtil.hexToByteArray(strHex3);

        return intByte ;
    }


    //使用1字节就可以表示b
    public static String numToHex8(int b) {
        return String.format("%02x", b);//2表示需要两个16进行数
    }
    //需要使用2字节表示b
    public static String numToHex16(int b) {
        return String.format("%04x", b);
    }
    //需要使用4字节表示b
    public static String numToHex32(int b) {
        return String.format("%08x", b);
    }

}