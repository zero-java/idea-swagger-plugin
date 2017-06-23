package com.yazuo.xiaoya.swagger;

import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiElementFactory;

import java.util.Objects;

/**
 *
 * Created by scvzerng on 2017/6/23.
 */
public class ApiModelHandler extends AbstractHandler {

    public ApiModelHandler(Project project,Class clazz) {
        super(project,clazz);
    }

    @Override
    protected void addMethodAnnotation(PsiElementFactory factory) {

    }

    @Override
    protected void addFieldAnnotation(PsiElementFactory factory) {

    }

    @Override
    protected void addClassAnnotation(PsiElementFactory factory) {
        String annotationText = getAnnotationText(clazz);
        PsiAnnotation annotation = elementFactory.createAnnotationFromText("@ApiModel"+annotationText==""?"":String.format("(description=\"%s\")",annotationText),clazz.getPsiClass());
        clazz.addAnnotation(annotation);
    }


    @Override
    public void handler(Class clazz) {
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(this.project);
       addClassAnnotation(elementFactory);
    }

    private String getAnnotationText(Class clazz){
        return clazz.getDocument().firstLine();
    }


}
