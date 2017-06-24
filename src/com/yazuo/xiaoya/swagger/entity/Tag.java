package com.yazuo.xiaoya.swagger.entity;

import com.intellij.psi.PsiElement;
import com.intellij.psi.javadoc.PsiDocTag;
import com.yazuo.xiaoya.swagger.utils.CommonUtils;

import static java.util.Arrays.stream;

/**
 * 对注释标签的封装
 *          标签名   值    描述
 *  example:@param test  it is a test
 * Created by scvzerng on 2017/6/23.
 */
public class Tag {
    /**
     * 标签名称
     */
    private String name;
    /**
     * 标签值
     */
    private String value;
    /**
     * 标签描述
     */
    private String description;
    public Tag(PsiDocTag tag) {
        this.name = tag.getName();
        this.value = tag.getValueElement()==null?null:tag.getValueElement().getText();
        StringBuilder des = new StringBuilder();
        stream(tag.getDataElements())
                .filter(CommonUtils::filterWhiteSpace).map(PsiElement::getText)
                .forEach(des::append);
        this.description = des.toString();
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
