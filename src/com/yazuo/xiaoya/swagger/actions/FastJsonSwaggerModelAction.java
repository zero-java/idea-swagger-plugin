package com.yazuo.xiaoya.swagger.actions;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.yazuo.xiaoya.swagger.entity.FileClass;
import com.yazuo.xiaoya.swagger.handler.AnnotationHandler;
import com.yazuo.xiaoya.swagger.handler.ApiHandler;
import com.yazuo.xiaoya.swagger.handler.ApiModelHandler;
import com.yazuo.xiaoya.swagger.handler.JsonFieldHandler;
import com.yazuo.xiaoya.swagger.utils.AnnotationUtil;

import static com.intellij.icons.AllIcons.Icon;

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
            String formatter = Messages.showInputDialog(project,"输入日期格式化字符串","日期格式化", AllIcons.Toolbar.Folders);

            @Override
            protected void run() throws Throwable {
                fileClass.process(clazz->{
                    JsonFieldHandler handler = new JsonFieldHandler(project,clazz);
                    handler.setFormat(formatter);
                    handler.addFieldAnnotation();
                });
            }
        }.execute();
    }
}
