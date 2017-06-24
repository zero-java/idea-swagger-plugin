package com.yazuo.xiaoya.swagger.entity;

import com.intellij.psi.*;

import java.util.*;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

/**
 * 类
 * Created by scvzerng on 2017/6/23.
 */
public class Class {
    /**
     * 类
     */
    private PsiClass psiClass;
    /**
     * 类注释
     */
    private Document document;
    /**
     * 类方法
     */
    private List<Method> methods;
    /**
     * 类字段
     */
    private List<Field> fields;
    /**
     * 类的java文件
     */
    private PsiJavaFile javaFile;
    public Class(PsiClass psiClass,PsiJavaFile javaFile) {

        this.psiClass = psiClass;
        this.javaFile = javaFile;
        this.document = new Document(psiClass.getDocComment(),psiClass);
        this.methods = stream(this.psiClass.getMethods()).map(Method::new).collect(toList());
        this.fields = stream(this.psiClass.getAllFields()).map(Field::new).collect(toList());
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



    public PsiJavaFile getJavaFile() {
        return javaFile;
    }

    public Document getDocument() {
        return document;
    }
}
