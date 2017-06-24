package com.yazuo.xiaoya.swagger.utils;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiWhiteSpace;

import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Year: 2017-2017/6/24-23:15
 * Project:idea-swagger-plugin
 * Package:com.yazuo.xiaoya.swagger.utils
 * To change this template use File | Settings | File Templates.
 */

public class CommonUtils {
    /**
     * 过滤掉所有空元素
     * @param element
     * @return
     */
    public static boolean filterWhiteSpace(PsiElement element){
        return !(Objects.equals(element.getText().trim(), "")||element instanceof PsiWhiteSpace) ;
    }
}
