package com.yazuo.xiaoya.swagger;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;

import java.util.function.Function;

/**
 *
 * Created by scvzerng on 2017/6/23.
 */
public abstract class AbstractHandler implements AnnotationHandler {
    protected Project project;
    protected PsiElementFactory elementFactory;
    protected Class clazz;
    protected PsiModifierList annotations;
    public AbstractHandler(Project project,Class clazz) {
        this.project = project;
        this.elementFactory = JavaPsiFacade.getElementFactory(this.project);
        this.clazz = clazz;
        this.annotations = this.clazz.getPsiClass().getModifierList();
    }

    protected abstract void addMethodAnnotation(PsiElementFactory factory);
    protected abstract void addFieldAnnotation(PsiElementFactory factory);
    protected abstract void addClassAnnotation(PsiElementFactory factory);
}
