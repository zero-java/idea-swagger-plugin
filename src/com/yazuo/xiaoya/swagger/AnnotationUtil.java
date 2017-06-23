package com.yazuo.xiaoya.swagger;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiModifierList;
import com.intellij.psi.javadoc.PsiDocComment;

/**
 * Created by scvzerng on 2017/6/23.
 */
public class AnnotationUtil {
    public static void addAnnotation(PsiElement element,PsiDocComment docComment, PsiAnnotation annotation){
        if(docComment!=null){
            docComment.addAfter(annotation,docComment);
        }else{
            element.getParent().addBefore(annotation,element);
        }
    }

    public static boolean hasAnnotation(PsiModifierList modifierList,PsiAnnotation annotation){
        return modifierList.findAnnotation(annotation.getQualifiedName())!=null;
    }
}
