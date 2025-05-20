package online.shenjian.cloud.common.utils;

public class HexUtils {

    /**
     * 字节数组转16进制字符串工具方法
     *
     * @param bytes
     * @return
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
            // 每16字节换行（可选）
            if (sb.length() % (16 * 3) == 0) sb.append("\n");
        }
        return sb.toString();
    }
}
