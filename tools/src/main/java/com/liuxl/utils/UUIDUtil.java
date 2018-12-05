package com.liuxl.utils;

import com.liuxl.constant.CharacterConstant;
import org.apache.commons.lang.StringUtils;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * Description:UUID 生成器
 *
 * @author liuxl
 * @date 2018/12/5
 */
public class UUIDUtil {

    /**
     * 方法作用：生成UUID <br/>
     *
     * @param isFormat 是否带横线格式<br/>
     * @return
     */
    
    public static String getUUID(boolean isFormat) {
        if (isFormat) {
            return UUID.randomUUID().toString();
        } else {
            return UUID.randomUUID().toString().replaceAll(CharacterConstant.LINE, StringUtils.EMPTY);
        }
    }
}
