package com.yazuo.xiaoya.swagger.handler;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiImportList;
import com.yazuo.xiaoya.swagger.entity.Class;

import static com.yazuo.xiaoya.swagger.constanst.Constants.SWAGGER_PREFIX;

/**
 * swagger注解实现
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Year: 2017-2017/6/24-23:02
 * Project:idea-swagger-plugin
 * Package:com.yazuo.xiaoya.swagger.handler
 * To change this template use File | Settings | File Templates.
 */

public abstract class SwaggerAnnotationHandler extends AbstractHandler {
    public SwaggerAnnotationHandler(Project project, Class clazz) {
        super(project, clazz);
        checkImports();
    }

    /**
     * 检查是否导入了必须的包
     */
    private void checkImports(){
        PsiImportList importList = clazz.getJavaFile().getImportList();
        if(!hasPackageImport(importList)){
            importList.add( elementFactory.createImportStatementOnDemand(SWAGGER_PREFIX));
        }
    }


    private boolean hasPackageImport(PsiImportList importList){
        return importList.findOnDemandImportStatement(SWAGGER_PREFIX)!=null;
    }
}
