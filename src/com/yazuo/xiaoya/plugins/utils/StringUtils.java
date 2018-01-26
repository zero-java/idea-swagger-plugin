package com.yazuo.xiaoya.plugins.utils;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Year: 2017-2017/7/10-22:00
 * Project:idea-plugins-plugin
 * Package:com.yazuo.xiaoya.plugins.handler
 * To change this template use File | Settings | File Templates.
 */

public class StringUtils {
    /**
     * 驼峰转下划线
     * @param hump
     * @return
     */
    public static final String humpToUnderLine(String hump){
        return hump.replaceAll("([A-Z])","_$1").toLowerCase();
    }
}
