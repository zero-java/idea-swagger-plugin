package com.yazuo.xiaoya.swagger;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiMethod;

/**
 *
 * Created by scvzerng on 2017/6/23.
 */
public class Method implements AnnotationOperator{
    private PsiMethod method;
    private Document document;

    public Method(PsiMethod method) {
        this.method = method;
        this.document = new Document(method.getDocComment());
    }

    public PsiMethod getMethod() {
        return method;
    }

    public Document getDocument() {
        return document;
    }

    @Override
    public void addAnnotation(PsiAnnotation annotation) {
        if(AnnotationUtil.hasAnnotation(method.getModifierList(),annotation)) return;
        AnnotationUtil.addAnnotation(method,document.getDocument(),annotation);
    }
}
