package com.yazuo.xiaoya.swagger;

import com.intellij.psi.*;
import com.intellij.psi.impl.source.javadoc.PsiDocCommentImpl;
import com.intellij.psi.impl.source.javadoc.PsiDocTokenImpl;
import com.intellij.psi.javadoc.PsiDocTag;

import java.util.*;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

/**
 *
 * Created by scvzerng on 2017/6/23.
 */
public class Class implements AnnotationOperator {
    private PsiClass psiClass;
    private Document document;
    private List<Method> methods;
    private List<Field> fields;
    private PsiModifierList annotations;
    public Class(PsiClass psiClass) {
        this.psiClass = psiClass;
        this.document = new Document(psiClass.getDocComment());
        this.methods = stream(this.psiClass.getMethods()).map(Method::new).collect(toList());
        this.fields = stream(this.psiClass.getAllFields()).map(Field::new).collect(toList());
        this.annotations = psiClass.getModifierList();
    }

    class Field implements AnnotationOperator{
        private PsiField field;
        private Document document;

        public Field(PsiField field) {
            this.field = field;
            this.document = new Document(field.getDocComment());
        }

        public PsiField getField() {
            return field;
        }

        public Document getDocument() {
            return document;
        }


        @Override
        public void addAnnotation(PsiAnnotation annotation) {
            if(AnnotationUtil.hasAnnotation(field.getModifierList(),annotation)) return;
             AnnotationUtil.addAnnotation(field,document.getDocument(),annotation);

        }
    }


    public PsiClass getPsiClass() {
        return psiClass;
    }

    public List<Method> getMethods() {
        return methods;
    }

    public List<Field> getFields() {
        return fields;
    }


    public void addAnnotation(PsiAnnotation annotation){
        if(AnnotationUtil.hasAnnotation(annotations,annotation)) return;
        AnnotationUtil.addAnnotation(psiClass,document.getDocument(),annotation);
    }

    public Document getDocument() {
        return document;
    }
}
