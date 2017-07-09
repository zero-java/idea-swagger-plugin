package com.yazuo.xiaoya.swagger.actions;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.yazuo.xiaoya.swagger.entity.FileClass;
import com.yazuo.xiaoya.swagger.handler.AnnotationHandler;
import com.yazuo.xiaoya.swagger.handler.ApiHandler;
import com.yazuo.xiaoya.swagger.handler.ApiModelHandler;
import com.yazuo.xiaoya.swagger.handler.JsonFieldHandler;
import com.yazuo.xiaoya.swagger.utils.AnnotationUtil;

/**
 * Created by scvzerng on 2017/7/6.
 */
public class FastJsonSwaggerModelAction extends AnAction {
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
                    AnnotationHandler handler = new JsonFieldHandler(project,clazz);
                    handler.addFieldAnnotation();
                });
            }
        }.execute();
    }
}
