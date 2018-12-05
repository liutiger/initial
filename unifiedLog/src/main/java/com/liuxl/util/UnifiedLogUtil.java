package com.liuxl.util;

import com.liuxl.constant.CharacterConstant;
import com.liuxl.enumType.ErrorCodeEnum;
import com.liuxl.exception.CommonException;
import com.liuxl.utils.StringUtil;
import org.apache.log4j.spi.ThrowableInformation;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created with IntelliJ IDEA.
 * Description: 日志记录的地址、ip
 *
 * @author liuxl
 * @date 2018/12/5
 */
public class UnifiedLogUtil {

    private final static String MAC_LOG_FILE_PATH = "/tmp/log/unifiedLog/";
    private final static String LINUX_LOG_FILE_PATH = "/var/log/unifiedLog/";
    private final static String WINDOWS_LOG_FILE_PATH = "C:/log/unifiedLog/";

    private static String hostIp = "";

    private static String getLocalAddressIp() {
        String localHostIp = "";
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface networkInterface = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddress = networkInterface.getInetAddresses(); enumIpAddress.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddress.nextElement();
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()) {
                        localHostIp = inetAddress.getHostAddress();
                    }

                }
            }
        } catch (SocketException e) {
            throw new CommonException(e, ErrorCodeEnum.NETWORK_INTERFACE_ERROR);
        }
        return localHostIp;
    }

    public static String getHostIp() {

        if (StringUtil.isNotBlank(hostIp)) {
            return hostIp;
        }

        hostIp = getLocalAddressIp();

        return hostIp;
    }

    public static String getLogFilePath() {
        if (isLinux()) {
            return LINUX_LOG_FILE_PATH;
        }
        if (isMac()) {
            return MAC_LOG_FILE_PATH;
        }
        if (isWindows()) {
            return WINDOWS_LOG_FILE_PATH;
        }
        return LINUX_LOG_FILE_PATH;
    }

    public static boolean isLinux() {
        return !isWindows() && !isMac();
    }

    public static boolean isWindows() {
        String os = System.getProperty("os.name");
        return os.toLowerCase().startsWith("win");
    }

    public static boolean isMac() {
        String os = System.getProperty("os.name");
        return os.toLowerCase().startsWith("mac");
    }

    public static String getExceptionMsg(Throwable e, String separator) {
        if (e == null) {
            return "";
        }
        separator = CharacterConstant.N;
        StringBuilder stringBuilder = new StringBuilder();
        ThrowableInformation throwableInfo = new ThrowableInformation(e);

        String[] s = throwableInfo.getThrowableStrRep();
        if (s != null) {
            int len = s.length;
            for (int i = 0; i < len; i++) {
                stringBuilder.append(s[i]);
                stringBuilder.append(separator);
            }
        }
        return stringBuilder.toString();
    }

}
