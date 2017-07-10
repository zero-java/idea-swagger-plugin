package com.yazuo.xiaoya.swagger.handler;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.yazuo.xiaoya.swagger.constanst.Constants;
import com.yazuo.xiaoya.swagger.entity.Class;
import com.yazuo.xiaoya.swagger.entity.Field;

import java.util.Optional;

import static com.yazuo.xiaoya.swagger.constanst.Constants.FAST_JSON_PREFIX;
import static com.yazuo.xiaoya.swagger.constanst.Constants.SWAGGER_PREFIX;

/**
 * Created by scvzerng on 2017/7/6.
 */
public class JsonFieldHandler extends AbstractHandler {
    private String format;
    public JsonFieldHandler(Project project, Class clazz) {
        super(project, clazz);
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    protected void checkImports() {
        PsiImportList importList = clazz.getJavaFile().getImportList();
        if(!hasPackageImport(importList)){
            importList.add( elementFactory.createImportStatementOnDemand(FAST_JSON_PREFIX));
        }
    }

    @Override
    public void addMethodAnnotation() {

    }

    @Override
    public void addFieldAnnotation() {
        this.clazz.getFields().stream().filter(this::isNotExist).forEach(field->{
            PsiField psiField = field.getField();
            Optional.ofNullable(field.getField().getModifierList().findAnnotation("com.alipay.api.internal.mapping.ApiField")).ifPresent(PsiElement::delete);
            Optional.ofNullable(field.getField().getModifierList().findAnnotation("com.alipay.api.internal.mapping.ApiListField")).ifPresent(PsiElement::delete);
            String alternateNames = psiField.getName();
            String name = alternateNames.replaceAll("([A-Z])","_$1").toLowerCase();
            String type = psiField.getType().getCanonicalText();
            if(name.equals(alternateNames)&&!type.contains("java.util.Date")) return ;
            PsiAnnotation annotation = null;
            if(type.contains("java.util.Date")){
                annotation  = elementFactory.createAnnotationFromText("@JSONField(name=\""+name+"\",alternateNames=\""+alternateNames+"\""+(format==null?"":",format=\""+format+"\"")+")",clazz.getPsiClass());
            }else{
                annotation  = elementFactory.createAnnotationFromText("@JSONField(name=\""+name+"\",alternateNames=\""+alternateNames+"\")",clazz.getPsiClass());

            }
            field.getDocument().addAnnotation(annotation);
        });
    }
    private boolean isNotExist(Field field){
        return field.getField().getModifierList().findAnnotation(Constants.SWAGGER_PREFIX+".JSONField")==null;
    }
    private boolean hasPackageImport(PsiImportList importList){
        return importList.findOnDemandImportStatement(FAST_JSON_PREFIX)!=null;
    }

    @Override
    public void addClassAnnotation() {

    }
}
