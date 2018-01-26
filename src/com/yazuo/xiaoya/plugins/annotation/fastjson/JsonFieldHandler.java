package com.yazuo.xiaoya.plugins.annotation.fastjson;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.yazuo.xiaoya.plugins.annotation.AbstractHandler;
import com.yazuo.xiaoya.plugins.constanst.Constants;
import com.yazuo.xiaoya.plugins.entity.Class;
import com.yazuo.xiaoya.plugins.entity.Field;
import com.yazuo.xiaoya.plugins.utils.AnnotationUtil;
import com.yazuo.xiaoya.plugins.utils.StringUtils;

import java.util.*;

import static com.yazuo.xiaoya.plugins.constanst.Constants.FAST_JSON_PREFIX;

/**
 * @JSONField 处理
 * Created by scvzerng on 2017/7/6.
 */
public class JsonFieldHandler extends AbstractHandler {
    public static final String[] filter = {"com.alipay.api.internal.mapping.ApiField","com.alipay.api.internal.mapping.ApiListField"};
    private String format;
    public static final String DATE = Date.class.getName();
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
            PsiModifierList modifierList = psiField.getModifierList();
            Arrays.stream(filter).map(modifierList::findAnnotation).filter(Objects::nonNull).forEach(PsiElement::delete);
            String alternateNames = psiField.getName();
            String name = StringUtils.humpToUnderLine(alternateNames);
            String type = psiField.getType().getCanonicalText();
            if(name.equals(alternateNames)&&!type.contains(DATE)) return ;
            Map<String,String> params = new HashMap<>();
            params.put("name",name);
            params.put("alternateNames",alternateNames);
            if(type.contains(DATE)&&format!=null){
                params.put("format",format);

            }
            PsiAnnotation annotation = elementFactory.createAnnotationFromText(AnnotationUtil.createAnnotationText("JSONField",params),clazz.getPsiClass());
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
