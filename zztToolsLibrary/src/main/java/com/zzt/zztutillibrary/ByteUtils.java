package com.zzt.zztutillibrary;

import java.io.ByteArrayOutputStream;

/**
 * byte 数据处理工具类
 *  自己在做蓝牙数据读取，串口数据读取的时候，各种处理byte数据，再次记录一下，
 *  方便下次使用
 * Created by zeting
 * Date 2019-08-09.
 */
public class ByteUtils {

    public static byte[] shortToByteArrayBig(short s) {
        byte[] b = new byte[2];
        b[0] = (byte) ((s & 0xff00) >> 8);
        b[1] = (byte) (s & 0x00ff);
        return b;
    }

    public static byte[] shortToByteArrayLittel(short s) {
        byte[] b = new byte[2];
        b[0] = (byte) (s & 0x00ff);
        b[1] = (byte) ((s & 0xff00) >> 8);
        return b;
    }

    /**
     * int转byte[] 已大端模式
     *
     * @param a
     * @return
     */
    public static byte[] intToBytesBig(int a) {
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
    public static byte[] intToBytesLittel(int a) {
        return new byte[]{
                (byte) (a & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 24) & 0xFF)
        };
    }


    /**
     * byte 转 int 小端模式
     * @param src
     * @return
     */
    public static int bytesToIntLittel(byte[] src ) {
        int value;
        value = (int) ((src[0] & 0xFF)
                | ((src[1] & 0xFF)<<8)
                | ((src[2] & 0xFF)<<16)
                | ((src[3] & 0xFF)<<24));
        return value;
    }


    /**
     * byte 转 int 大端模式
     * @param src
     * @return
     */
    public static int bytesToIntBig(byte[] src ) {
        int value;
        value = (int) ( ((src[0] & 0xFF)<<24)
                |((src[1] & 0xFF)<<16)
                |((src[2] & 0xFF)<<8)
                |(src[3] & 0xFF));
        return value;
    }


    /**
     *  使用1字节就可以表示数字
     */
    public static String numToHex8(int num ) {
        return String.format("%02x", num ) ;//2表示需要两个16进行数
    }

    /**需要使用2字节表示b
     *
     * @param num
     * @return
     */
    public static String numToHex16(int num ) {
        return String.format("%04x", num ) ;
    }

    /**需要使用4字节表示b
     *
     * @param num
     * @return
     */
    public static String numToHex32(int num ) {
        return String.format("%08x", num ) ;
    }


    /**
     * 将byte转换为一个长度为8的byte数组，数组每个值代表bit
     */
    public static byte[] ByteToBitArray(byte b) {
        byte[] array = new byte[8];
        for (int i = 7; i >= 0 ; i--) {
            array[i] = (byte)(b & 1);
            b = (byte) (b >> 1);
        }
        return array;
    }

    /**
     * 将byte 拆分成一个字符串
     * @param by
     * @return
     */
    public static String  ByteToString (byte by){
        StringBuffer sb = new StringBuffer();
        sb.append((by>>7)&0x1)
                .append((by>>6)&0x1)
                .append((by>>5)&0x1)
                .append((by>>4)&0x1)
                .append((by>>3)&0x1)
                .append((by>>2)&0x1)
                .append((by>>1)&0x1)
                .append((by>>0)&0x1);
        return sb.toString();
    }

    /**
     * 判断是不是空格
     * @param s
     * @return
     */
    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 将bit数组换行成字符串
     */
    public static String BytesToHexString(byte[] bytes) {
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

    /**
     * 字节数组转换成对应的16进制表示的字符串 大写有空格表示
     *
     * @param src
     * @return
     */
    public static String BytesToHexStrUpper(byte[] src) {
        StringBuilder builder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return "";
        }
        char[] buffer = new char[2];
        for (int i = 0; i < src.length; i++) {
            buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
            buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
            builder.append(buffer);
            builder.append(" ");
        }
        return builder.toString().toUpperCase();
    }

    private static final char hexDigits[] =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * Bytes to hex string.
     * <p>e.g. bytes2HexString(new byte[] { 0, (byte) 0xa8 }) returns "00A8"</p>
     *
     * @param bytes The bytes.
     * @return hex string
     */
    public static String BytesToHexStringT(final byte[] bytes) {
        if (bytes == null) return "";
        int len = bytes.length;
        if (len <= 0) return "";
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = hexDigits[bytes[i] >> 4 & 0x0f];
            ret[j++] = hexDigits[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

    /**
     * Hex string to bytes.
     * <p>e.g. hexString2Bytes("00A8") returns { 0, (byte) 0xA8 }</p>
     *
     * @param hexString The hex string.
     * @return the bytes
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (isSpace(hexString)) return null;
        int len = hexString.length();
        if (len % 2 != 0) {
            hexString = "0" + hexString;
            len = len + 1;
        }
        char[] hexBytes = hexString.toUpperCase().toCharArray();
        byte[] ret = new byte[len >> 1];
        for (int i = 0; i < len; i += 2) {
            ret[i >> 1] = (byte) (hexToInt(hexBytes[i]) << 4 | hexToInt(hexBytes[i + 1]));
        }
        return ret;
    }


    /**
     *
     * @param hexChar
     * @return
     */
    private static int hexToInt(final char hexChar) {
        if (hexChar >= '0' && hexChar <= '9') {
            return hexChar - '0';
        } else if (hexChar >= 'A' && hexChar <= 'F') {
            return hexChar - 'A' + 10;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * 将 hex 转换成 byte 数组
     * @param inHex
     * @return
     */
    public static byte[] hexStringToByteArray(String inHex) {
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
     * 将多个参数组合成一个byte[]
     * 现在支持String、Byte、Short、Integer、Long、byte[]
     * <b>注意：Byte、Short、Integer、Long都会被当做byte处理</b>
     *
     * @param args
     *            可以包括byte、int、short、long、byte[]、String
     * @return byte数组结果
     */
    public static byte[] combine(Object... args) {
        if (args == null || args.length == 0) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
        for (int i = 0; i < args.length; i++) {
            if (args[i] == null) {
                continue;
            }
            try {
                if (args[i] instanceof String) {
                    String hex = (String) args[i];
                    byte[] bytearr = hexStringToByteArray(hex);
                    if (bytearr != null) {
                        baos.write(bytearr);
                    }
                } else if (args[i] instanceof byte[]) {
                    byte[] ba = (byte[]) args[i];
                    baos.write(ba);
                } else if (args[i] instanceof Byte) {
                    Byte b = (Byte) args[i];
                    int num = b & 0xff;
                    baos.write(num);
                } else if (args[i] instanceof Short) {
                    Short s = (Short) args[i];
                    int num = s & 0xff;
                    baos.write(num);
                } else if (args[i] instanceof Integer) {
                    Integer s = (Integer) args[i];
                    int num = s & 0xff;
                    baos.write(num);
                } else if (args[i] instanceof Long) {
                    Long s = (Long) args[i];
                    int num = (int) (s & 0xff);
                    baos.write(num);
                } else {
                    System.out.println("不被支持的参数[" + i + "]");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return baos.toByteArray();
    }

    /**
     * 把十进制数字转换成足位的十六进制字符串,并补全空位
     *
     * @param num
     * @return
     */
    public static String DecimalToFitHexString(long num) {
        String hex = Long.toHexString(num).toUpperCase();
        if (hex.length() % 2 != 0) {
            return "0" + hex;
        }
        return hex.toUpperCase();
    }

}
