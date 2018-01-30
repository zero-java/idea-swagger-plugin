package com.yazuo.xiaoya.plugins.annotation.swagger;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiImportList;
import com.yazuo.xiaoya.plugins.annotation.AbstractHandler;
import com.yazuo.xiaoya.plugins.entity.Class;

import static com.yazuo.xiaoya.plugins.constanst.Constants.SWAGGER_PREFIX;

/**
 * swagger注解实现
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Year: 2017-2017/6/24-23:02
 * Project:idea-plugins-plugin
 * Package:com.yazuo.xiaoya.plugins.handler
 * To change this template use File | Settings | File Templates.
 */

public abstract class SwaggerAnnotationHandler extends AbstractHandler {
    public SwaggerAnnotationHandler(Project project, Class clazz) {
        super(project, clazz);
    }

    /**
     * 检查是否导入了必须的包
     */
    protected void checkImports(){
        PsiImportList importList = clazz.getJavaFile().getImportList();
        if(!hasPackageImport(importList)){
            importList.add( elementFactory.createImportStatementOnDemand(SWAGGER_PREFIX));
        }
    }


    private boolean hasPackageImport(PsiImportList importList){
        return importList.findOnDemandImportStatement(SWAGGER_PREFIX)!=null;
    }
}
