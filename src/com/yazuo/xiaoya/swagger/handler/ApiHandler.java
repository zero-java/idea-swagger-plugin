package com.yazuo.xiaoya.swagger.handler;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiElementFactory;
import com.yazuo.xiaoya.swagger.entity.ApiOperation;
import com.yazuo.xiaoya.swagger.entity.Class;
import com.yazuo.xiaoya.swagger.entity.Document;
import com.yazuo.xiaoya.swagger.entity.Method;
import com.yazuo.xiaoya.swagger.utils.AnnotationUtil;

import static com.yazuo.xiaoya.swagger.constanst.Constants.SWAGGER_PREFIX;

/**
 * Api的实现可以自动扫描注释和spring mvc的注解来生成简单的swagger注解
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Year: 2017-2017/6/24-19:41
 * Project:idea-swagger-plugin
 * Package:com.yazuo.xiaoya.swagger
 * To change this template use File | Settings | File Templates.
 */

public class ApiHandler extends SwaggerAnnotationHandler {
    public ApiHandler(Project project, Class clazz) {
        super(project, clazz);
    }
    class HttpMethod{
        private ApiOperation apiOperation;
        private Method method;

        public HttpMethod(Method method) {
            this.method = method;
            this.apiOperation = new ApiOperation(method);
        }

        public ApiOperation getApiOperation() {
            return apiOperation;
        }

        public void setApiOperation(ApiOperation apiOperation) {
            this.apiOperation = apiOperation;
        }

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
        }
    }
    @Override
    public void addMethodAnnotation() {
             this.clazz.getMethods().stream()
                     .filter(AnnotationUtil::isRest)
                     .filter(this::isNotExist)
                     .map(HttpMethod::new).
                     forEach(httpMethod->{
                         Document document = httpMethod.getMethod().getDocument();
                         PsiAnnotation annotation = elementFactory.createAnnotationFromText(httpMethod.getApiOperation().toString(),clazz.getPsiClass());
                         document.addAnnotation(annotation);
     });
    }


    @Override
    public void addFieldAnnotation() {
        //nothing to do
    }


    @Override
    public void addClassAnnotation() {
        if(AnnotationUtil.isRestController(this.clazz.getPsiClass())&&isNotExist(this.clazz)){

            PsiAnnotation api = elementFactory.createAnnotationFromText("@Api"+(clazz.getDocument().firstLine()==""?"":String.format("(description=\"%s\")",clazz.getDocument().firstLine())),clazz.getPsiClass());
           clazz.getDocument().addAnnotation(api);
        }
    }

    private boolean isNotExist(Method method){
        return method.getMethod().getModifierList().findAnnotation(SWAGGER_PREFIX+".ApiOperation")==null;
    }
    private boolean isNotExist(Class clazz){
        return clazz.getPsiClass().getModifierList().findAnnotation(SWAGGER_PREFIX+".Api")==null;
    }


}
