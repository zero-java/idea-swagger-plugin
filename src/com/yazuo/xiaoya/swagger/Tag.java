package com.yazuo.xiaoya.swagger;

import com.intellij.psi.PsiElement;
import com.intellij.psi.javadoc.PsiDocTag;

import static java.util.Arrays.stream;

/**
 *
 * Created by scvzerng on 2017/6/23.
 */
public class Tag {
    private String name;
    private String value;
    private String description;
    public Tag(PsiDocTag tag) {
        this.name = tag.getName();
        this.value = tag.getValueElement()==null?null:tag.getValueElement().getText();
        StringBuilder des = new StringBuilder();
        stream(tag.getDataElements())
                .filter(DocumentUtil::filterWhiteSpace).map(PsiElement::getText)
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
