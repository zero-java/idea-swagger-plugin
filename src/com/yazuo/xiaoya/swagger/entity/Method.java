package com.yazuo.xiaoya.swagger.entity;

import com.intellij.psi.PsiMethod;

/**
 *类方法
 * Created by scvzerng on 2017/6/23.
 */
public class Method {
    /**
     * 类方法
     */
    private PsiMethod method;
    /**
     * 类方法注释
     */
    private Document document;
    public Method(PsiMethod method) {
        this.method = method;
        this.document = new Document(method.getDocComment(),method);
    }

    public PsiMethod getMethod() {
        return method;
    }

    public Document getDocument() {
        return document;
    }

}
