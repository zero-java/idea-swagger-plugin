package com.yazuo.xiaoya.swagger.entity;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiShortNamesCache;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

/**
 *文件类
 * Created by scvzerng on 2017/6/23.
 */
public class FileClass {
    /**
     * 文件拥有的所有类
     */
    private List<Class> classes;
    private Project project;
    private JavaPsiFacade javaPsiFacade;
    private Set<PsiClass> psiClasses;
    /**
     * java文件
     */
    public FileClass(PsiJavaFile psiFile, Project project) {
        this.project = project;
        this.javaPsiFacade = JavaPsiFacade.getInstance(project);
        psiClasses = new HashSet<>();
        psiClasses.addAll(Arrays.asList(psiFile.getClasses()));
        psiClasses.addAll(psiClasses.stream()
                .map(PsiClass::getAllInnerClasses).flatMap(Arrays::stream).collect(toList()));
        new HashSet<>(psiClasses).forEach(psiClass -> psiClasses.addAll(getFieldClasses(psiClass)));
        this.classes = psiClasses.stream().distinct().map(psiClass -> new Class(psiClass,(PsiJavaFile) psiClass.getContainingFile())).collect(toList());
    }

    public List<Class> getClasses() {
        return classes;
    }

    public void setClasses(List<Class> classes) {
        this.classes = classes;
    }



    private Set<PsiClass> getFieldClasses(PsiClass psiClass){

        Set<PsiClass> psiClassSet = new HashSet<>();
        psiClassSet.add(psiClass);
        stream(psiClass.getAllFields()).map(PsiField::getTypeElement)
                .map(PsiTypeElement::getInnermostComponentReferenceElement)
                .filter(Objects::nonNull)
                .map(psiJavaCodeReferenceElement -> {
                    if(psiJavaCodeReferenceElement.getTypeParameters().length==0){
                        return new String[]{psiJavaCodeReferenceElement.getCanonicalText()};
                    }else{
                        List<String> paramsTypes = stream(psiJavaCodeReferenceElement.getTypeParameters()).map(PsiType::getCanonicalText).collect(toList());
                        String[] typeStringArray = new String[paramsTypes.size()];
                        return paramsTypes.toArray(typeStringArray);
                    }
                }).flatMap(Arrays::stream)
                .filter(typeString->!typeString.startsWith("java."))
                .map(clazz->javaPsiFacade.findClass(clazz,GlobalSearchScope.projectScope(project)))
                .filter(Objects::nonNull)
                .filter(clazz->!clazz.getText().contains("enum"))
                .forEach(clazz -> {
                    if(psiClasses.contains(clazz)) return ;
                    psiClassSet.addAll(getFieldClasses(clazz));
                    psiClassSet.addAll(stream(clazz.getAllInnerClasses()).collect(toList()));
                });
        return psiClassSet;
    }


    /**
     * 对java文件拥有的类进行处理
     * @param classConsumer 类消费者
     * @return
     */
    public FileClass process(Consumer<Class> classConsumer){
        if(this.classes!=null&&this.classes.size()>0){
            this.classes.forEach(classConsumer);
        }
        return this;
    }

}
