package com.yazuo.xiaoya.swagger.handler;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.yazuo.xiaoya.swagger.entity.Class;
import com.yazuo.xiaoya.swagger.handler.AnnotationHandler;

/**
 *
 * Created by scvzerng on 2017/6/23.
 */
public abstract class AbstractHandler implements AnnotationHandler {
    /**
     * 工程项目
     */
    protected Project project;
    /**
     * 元素构建器
     */
    protected PsiElementFactory elementFactory;
    /**
     * 对应的java类
     */
    protected Class clazz;
    /**
     * 注解信息
     */
    protected PsiModifierList annotations;
    public AbstractHandler(Project project,Class clazz) {
        this.project = project;
        this.elementFactory = JavaPsiFacade.getElementFactory(this.project);
        this.clazz = clazz;
        this.annotations = this.clazz.getPsiClass().getModifierList();
        checkImports();
    }

    protected abstract void checkImports();
}
