package com.liuxl.enumType;

/**
 * Created with IntelliJ IDEA.
 * Description:请求类型
 *
 * @author liuxl
 * @date 2018/12/5
 */
public enum RequsetTypeEnum {
    HTTP("0", "http"), SERVICE("1", "service");

    private String type;

    private String desc;

    RequsetTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    RequsetTypeEnum(String type) {
        this.type = type;
    }

    public static RequsetTypeEnum getRequsetTypeEnum(String type) {
        for (RequsetTypeEnum x : RequsetTypeEnum.values()) {
            if (x.getType().equals(type)) {
                return x;
            }
        }
        return null;
    }
}
