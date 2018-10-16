package com.liuxl.utils;

import org.apache.commons.lang.StringUtils;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author liuxl
 * @date 2018/10/16
 */
public class FileUtil {
    public static String getFileExtName(File file) {
        if (file.exists()) {
            String fileName = file.getName();
            if (StringUtils.isNotBlank(fileName)) {
                if (fileName.endsWith(".xlsx")) {
                    //Excel 2003
                    return ".xlsx";
                } else if (fileName.endsWith(".xls")) {
                    //Excel 2007/2010
                    return ".xls";
                }
            }
        }
        return "";
    }
}