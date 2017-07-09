package com.yazuo.xiaoya.swagger.actions;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.yazuo.xiaoya.swagger.entity.FileClass;
import com.yazuo.xiaoya.swagger.handler.AnnotationHandler;
import com.yazuo.xiaoya.swagger.handler.ApiHandler;
import com.yazuo.xiaoya.swagger.handler.ApiModelHandler;
import com.yazuo.xiaoya.swagger.utils.AnnotationUtil;
/**
 * 自动根据注释生成swagger注解
 * Created by scvzerng on 2017/6/20.
 */
public class GenerateSwaggerModelAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        DataContext dataContext = anActionEvent.getDataContext();
        PsiFile psiFile = anActionEvent.getData(DataKeys.PSI_FILE);
        Project project = CommonDataKeys.PROJECT.getData(dataContext);
        FileClass fileClass = new FileClass((PsiJavaFile) psiFile,project);
        new WriteCommandAction.Simple(project,psiFile){
            @Override
            protected void run() throws Throwable {
                    fileClass.process(clazz->{
                        AnnotationHandler handler = AnnotationUtil.isRestController(clazz.getPsiClass())?new ApiHandler(project,clazz): new ApiModelHandler(project,clazz);
                        handler.addClassAnnotation();
                        handler.addFieldAnnotation();
                        handler.addMethodAnnotation();
                    });
            }
        }.execute();
    }

}
