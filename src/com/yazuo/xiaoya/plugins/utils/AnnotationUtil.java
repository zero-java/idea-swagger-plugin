package com.yazuo.xiaoya.plugins.utils;

import com.intellij.psi.*;
import com.yazuo.xiaoya.plugins.entity.Method;

import java.util.Map;

import static com.yazuo.xiaoya.plugins.constanst.Constants.SPRING_PREFIX;

/**
 *
 * Created by scvzerng on 2017/6/23.
 */
public class AnnotationUtil {
    public static final String PREFIX = "@";
    public static final String PARAM_PREFIX = "(";
    public static final String PARAM_SUFFIX = ")";
    public static final String PARAM_SPLITER = ",";

    public static String createAnnotationText(String annotation, Map<String,String> params){
        StringBuilder builder = new StringBuilder(PREFIX+annotation);

        if(params==null||params.size()==0) return builder.toString();
        builder.append(PARAM_PREFIX);
        params.forEach((k,v)-> builder.append(k).append("=").append("\"").append(v).append("\"").append(PARAM_SPLITER));
        builder.delete(builder.lastIndexOf(PARAM_SPLITER),builder.length())
        .append(PARAM_SUFFIX);
        return builder.toString();
    }
    public static String createAnnotationText(String annotation){
        return createAnnotationText(annotation,null);
    }

    /**
     * 给指定元素添加注解
     * @param element 需要添加注解的元素
     * @param annotation 被添加的注解
     */
    @Deprecated
    public static void addAnnotation(PsiElement element,PsiAnnotation annotation){

            element.getParent().addBefore(annotation,element);
    }

    /**
     * 判断元素是否拥有某个注解
     * @param modifierList 注解集合
     * @param annotation 需要检测的注解
     * @return
     */
    public static boolean hasAnnotation(PsiModifierList modifierList,PsiAnnotation annotation){
        return modifierList.findAnnotation(annotation.getQualifiedName())!=null;
    }


    /**
     * 获取 spring mvc 的mapping注解
     * @param method 需要检测的方法
     * @return
     */
    public static PsiAnnotation getHttpAnnotation(PsiMethod method){
        PsiAnnotation annotation = method.getModifierList().findAnnotation(SPRING_PREFIX+"GetMapping");
        if(annotation==null) annotation = method.getModifierList().findAnnotation(SPRING_PREFIX+"PutMapping");
        if(annotation==null) annotation = method.getModifierList().findAnnotation(SPRING_PREFIX+"PostMapping");
        if(annotation==null) annotation = method.getModifierList().findAnnotation(SPRING_PREFIX+"DeleteMapping");
        return annotation;
    }

    /**
     * 判断是否是一个RestController
     * @param owner 需要检测的元素
     * @return
     */
    public static boolean isRestController(PsiModifierListOwner owner){
        return owner.getModifierList().findAnnotation(SPRING_PREFIX+"RestController")!=null;
    }

    /**
     * 判断方法是否为一个rest action
     * @param method 需要检测的方法
     * @return
     */
    public static boolean isRest(Method method){
        return getHttpAnnotation(method.getMethod())!=null;
    }



}
