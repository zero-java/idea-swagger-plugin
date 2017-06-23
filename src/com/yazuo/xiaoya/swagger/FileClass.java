package com.yazuo.xiaoya.swagger;

import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

/**
 *
 * Created by scvzerng on 2017/6/23.
 */
public class FileClass {
    private List<Class> classes;
    private PsiJavaFile javaFile;
    public FileClass(PsiJavaFile psiFile) {
        this.javaFile = psiFile;
        this.classes = stream(psiFile.getClasses()).map(Class::new).collect(Collectors.toList());
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
    public void process(Consumer<Class> classConsumer){
        if(this.classes!=null&&this.classes.size()>0){
            this.classes.forEach(classConsumer);
        }
    }
}
