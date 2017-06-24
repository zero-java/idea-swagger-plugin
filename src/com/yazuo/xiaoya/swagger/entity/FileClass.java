package com.yazuo.xiaoya.swagger.entity;

import com.intellij.psi.PsiJavaFile;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

/**
 *文件类
 * Created by scvzerng on 2017/6/23.
 */
public class FileClass {
    /**
     * 文件拥有的所有类
     */
    private List<Class> classes;
    /**
     * java文件
     */
    private PsiJavaFile javaFile;
    public FileClass(PsiJavaFile psiFile) {
        this.javaFile = psiFile;
        this.classes = stream(psiFile.getClasses()).map(psiClass -> new Class(psiClass,javaFile)).collect(Collectors.toList());

    }

    public List<Class> getClasses() {
        return classes;
    }

    public void setClasses(List<Class> classes) {
        this.classes = classes;
    }

    public PsiJavaFile getJavaFile() {
        return javaFile;
    }

    public void setJavaFile(PsiJavaFile javaFile) {
        this.javaFile = javaFile;
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
