package com.yazuo.xiaoya.plugins.entity;

import com.intellij.psi.PsiField;

/**
 * 对类字段的封装
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Year: 2017-2017/6/24-12:51
 * Project:idea-plugins-plugin
 * Package:com.yazuo.xiaoya.plugins
 * To change this template use File | Settings | File Templates.
 */

public class Field {
    /**
     * 类字段
     */
    private PsiField field;
    /**
     * 字段注释
     */
    private Document document;

    public Field(PsiField field) {
        this.field = field;
        this.document = new Document(field.getDocComment(),field);
    }

    public PsiField getField() {
        return field;
    }

    public Document getDocument() {
        return document;
    }


}
