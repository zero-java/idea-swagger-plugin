package com.yazuo.xiaoya.swagger;

import com.intellij.lang.Language;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;

import java.util.Arrays;
import java.util.function.Function;

/**
 *
 * Created by scvzerng on 2017/6/20.
 */
public class GenerateSwaggerModelAction extends AnAction {
    private final String SWAGGER_PREFIX = "io.swagger.annotations";
    private final String SPRING_PREFIX = "org.springframework.web.bind.annotation.";
    private Project project;
    private PsiClass psiClass;
    private PsiJavaFile psiJavaFile;
    private PsiElementFactory elementFactory;
    private Editor editor;
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        PsiFile psiFile = anActionEvent.getData(DataKeys.PSI_FILE);
        if(psiFile instanceof PsiJavaFile){
           FileClass fileClass = new FileClass((PsiJavaFile) psiFile);
           fileClass.process(clazz->{

           });
            System.out.println(fileClass.getClasses());
        }
//            DataContext dataContext = anActionEvent.getDataContext();
//            this.project = CommonDataKeys.PROJECT.getData(dataContext);
//            this.psiJavaFile = (PsiJavaFile) anActionEvent.getData(LangDataKeys.PSI_FILE);
//            this.editor = anActionEvent.getData(PlatformDataKeys.EDITOR);
//            checkLanguage();
//            new WriteCommandAction.Simple(project,psiJavaFile){
//                @Override
//                protected void run() throws Throwable {
//                    addClassAnnotation();
//                    checkImports();
//                    checkFields();
//                    checkMethods();
//                }
//            }.execute();
//        }catch (Exception e){
//            Messages.showErrorDialog(e.getMessage(),"错误信息");
//        }
    }

    private void checkMethods(){
        //    @ApiOperation(value = "创建口碑活动",notes = "创建口碑活动",response =CountryExample.class, httpMethod = "POST",responseContainer = "Map")

        PsiMethod[] methods = this.psiClass.getMethods();
        Arrays.stream(methods).filter(this::findRestMethod).map(ApiOperation::new).forEach(apiOperation -> {
           apiOperation.getComment().addAfter(this.elementFactory.createAnnotationFromText(apiOperation.toString(),psiClass),apiOperation.getComment());
        });
    }
   class ApiOperation {
        private String httpMethod;
        private String returnType;
        private String doc;
        private PsiComment comment;
        public ApiOperation(PsiMethod psiMethod){
            PsiComment comment = psiMethod.getDocComment();
            this.comment = comment;
            this.doc = this.text(comment);
            PsiAnnotation annotation = getHttpAnnotation(psiMethod);
            String annotationText = annotation.getText();
            this.httpMethod =annotationText.substring(1,annotationText.indexOf("Mapping")).toUpperCase();
            this.returnType = psiMethod.getReturnType().getPresentableText();
        }
       public String getHttpMethod() {
           return httpMethod;
       }

       public void setHttpMethod(String httpMethod) {
           this.httpMethod = httpMethod;
       }

       public PsiComment getComment() {
           return comment;
       }
       private String text(PsiComment comment){
            if(comment==null) return "";

            return comment.getText();
       }
       @Override
       public String toString() {
           return String.format("@ApiOperation(value = \"%s\",notes = \"%s\",response =%s.class, httpMethod = \"%s\")",doc,doc,returnType,httpMethod);
       }
   }
    private boolean findRestMethod(PsiMethod method){

        return method.getModifierList().findAnnotation(SPRING_PREFIX+"GetMapping")!=null
                ||method.getModifierList().findAnnotation(SPRING_PREFIX+"PutMapping")!=null||
        method.getModifierList().findAnnotation(SPRING_PREFIX+"PostMapping")!=null||
        method.getModifierList().findAnnotation(SPRING_PREFIX+"DeleteMapping")!=null;
    }

    private PsiAnnotation getHttpAnnotation(PsiMethod method){
        PsiAnnotation annotation = method.getModifierList().findAnnotation(SPRING_PREFIX+"GetMapping");
        if(annotation==null) annotation = method.getModifierList().findAnnotation(SPRING_PREFIX+"PutMapping");
        if(annotation==null) annotation = method.getModifierList().findAnnotation(SPRING_PREFIX+"PostMapping");
        if(annotation==null) annotation = method.getModifierList().findAnnotation(SPRING_PREFIX+"DeleteMapping");
      return annotation;
    }
    private void checkFields() {
        if(!isRestController(psiClass.getModifierList())){
            PsiField[] psiFields = this.psiClass.getAllFields();
            Arrays.stream(psiFields)
                    .filter(psiField -> !psiField.getText().contains("@ApiModelProperty"))
                    .forEach(psiField -> {
                        PsiComment comment = psiField.getDocComment();
                        addAnnotation(comment,psiField,doc->"@ApiModelProperty(notes=\""+doc+"\")");
                    });
        }
    }
    private String getText(PsiComment comment){
        if(comment==null) return "";
        String trimmed = comment.getText();
        trimmed = trimmed.replaceAll("\\n","").replaceAll("\\*","").replaceFirst("/","").replace(" ","");
        trimmed = trimmed.substring(0,trimmed.lastIndexOf("/")).trim();
        return trimmed;
    }
    private void checkLanguage(){
        boolean isJava = psiJavaFile.getLanguage().is(Language.findLanguageByID("JAVA"));
        if(!isJava) throw new RuntimeException("不是java文件");
        this.elementFactory = JavaPsiFacade.getElementFactory(this.project);
        if(editor==null) throw new RuntimeException("empty editor");
        int offset = editor.getCaretModel().getOffset();
        PsiElement element = psiJavaFile.findElementAt(offset);
        if(element==null) throw new RuntimeException("请在类内部操作");
        this.psiClass = PsiTreeUtil.getParentOfType(element, PsiClass.class);

    }
    private void addClassAnnotation(){
        PsiModifierList modifierList = this.psiClass.getModifierList();
        PsiComment comment = this.psiClass.getDocComment();
        if(modifierList.findAnnotation(SWAGGER_PREFIX+".ApiModel")==null){
            if(!isRestController(modifierList)){
                addAnnotation(comment,psiClass,doc->"@ApiModel(description=\""+doc+"\")");
            }
        }
        if(isRestController(modifierList)){
            if(modifierList.findAnnotation(SWAGGER_PREFIX+".Api")==null){
                addAnnotation(comment,psiClass,doc->"@Api(description=\""+doc+"\")");
            }
        }

    }

    private boolean isRestController(PsiModifierList modifierList){
        return modifierList.findAnnotation(SPRING_PREFIX+"RestController")!=null;
    }

    private void addAnnotation(PsiComment comment, PsiElement element, Function<String,String> function){
       String annotationText = function.apply(getText(comment));

       if(comment==null) annotationText = annotationText.substring(0,annotationText.indexOf("("));

       PsiAnnotation annotation = this.elementFactory.createAnnotationFromText(annotationText,psiClass);
       if(comment!=null){
           comment.addAfter(annotation,comment);
       }else{
           element.getParent().addBefore(annotation,element);
       }
    }
    private void checkImports(){
        PsiImportList importList = psiJavaFile.getImportList();
       if(!hasClassImport(importList)&&!hasPackageImport(importList)){
           importList.add( elementFactory.createImportStatementOnDemand(SWAGGER_PREFIX));
       }
    }

    private boolean hasClassImport(PsiImportList importList){
       return importList.findSingleClassImportStatement(SWAGGER_PREFIX +".ApiModel")!=null || importList.findSingleClassImportStatement(SWAGGER_PREFIX +".ApiModelProperty")!=null;
    }
    private boolean hasPackageImport(PsiImportList importList){
        return importList.findOnDemandImportStatement(SWAGGER_PREFIX)!=null;
    }
}
