package com.yazuo.xiaoya.swagger.handler;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.yazuo.xiaoya.swagger.constanst.Constants;
import com.yazuo.xiaoya.swagger.entity.Class;
import com.yazuo.xiaoya.swagger.entity.Field;

import static com.yazuo.xiaoya.swagger.constanst.Constants.SWAGGER_PREFIX;

/**
 * ApiModel的注解实现通过解析注释自动添加类和字段级别的注解
 * Created by scvzerng on 2017/6/23.
 */
public class ApiModelHandler extends SwaggerAnnotationHandler {

    public ApiModelHandler(Project project,Class clazz) {
        super(project,clazz);
    }

    @Override
    public void addMethodAnnotation() {
      //nothing to do
    }

    @Override
    public void addFieldAnnotation() {
        this.clazz.getFields().stream().filter(this::isNotExist).map(Field::getDocument).forEach(doc->{
            String annotationText = doc.getDescriptionText();
            PsiAnnotation annotation = elementFactory.createAnnotationFromText("@ApiModelProperty"+(annotationText==""?"":String.format("(notes=\"%s\")",annotationText)),clazz.getPsiClass());
            doc.addAnnotation(annotation);
        });
    }

    @Override
    public void addClassAnnotation() {
        String annotationText = getAnnotationText(clazz);
        if(annotations.findAnnotation(SWAGGER_PREFIX+".ApiModel")==null){
            PsiAnnotation annotation = elementFactory.createAnnotationFromText("@ApiModel"+(annotationText==""?"":String.format("(description=\"%s\")",annotationText)),clazz.getPsiClass());
            this.clazz.getDocument().addAnnotation(annotation);

        }
    }

    private String getAnnotationText(Class clazz){
        return clazz.getDocument().firstLine();
    }
    private boolean isNotExist(Field field){
        return field.getField().getModifierList().findAnnotation(Constants.SWAGGER_PREFIX+".ApiModelProperty")==null;
    }

}
