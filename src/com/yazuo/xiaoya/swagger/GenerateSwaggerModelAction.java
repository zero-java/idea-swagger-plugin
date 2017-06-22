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
    private final String IMPORT_PACKAGE = "io.swagger.annotations";
    private Project project;
    private PsiClass psiClass;
    private PsiJavaFile psiJavaFile;
    private PsiElementFactory elementFactory;
    private Editor editor;
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        try {
            DataContext dataContext = anActionEvent.getDataContext();
            this.project = CommonDataKeys.PROJECT.getData(dataContext);
            this.psiJavaFile = (PsiJavaFile) anActionEvent.getData(LangDataKeys.PSI_FILE);
            this.editor = anActionEvent.getData(PlatformDataKeys.EDITOR);
            checkLanguage();
            new WriteCommandAction.Simple(project,psiJavaFile){
                @Override
                protected void run() throws Throwable {
                    addClassAnnotation();
                    checkImports();
                    checkFields();
                    checkMethods();
                }
            }.execute();
        }catch (Exception e){
            Messages.showErrorDialog(e.getMessage(),"错误信息");
        }
    }

    private void checkMethods(){
        PsiMethod[] methods = this.psiClass.getMethods();
    }
    private void checkFields() {
        PsiField[] psiFields = this.psiClass.getAllFields();
        Arrays.stream(psiFields)
                .filter(psiField -> !psiField.getText().contains("@ApiModelProperty"))
                .forEach(psiField -> {
                    PsiComment comment = psiField.getDocComment();
                    addAnnotation(comment,psiField,doc->"@ApiModelProperty(notes=\""+doc+"\")");
                });
        ;
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
        if(!this.psiClass.getText().contains("@ApiModel")){
            PsiComment comment = this.psiClass.getDocComment();
            addAnnotation(comment,psiClass,doc->"@ApiModel(description=\""+doc+"\")");
        }

        if(this.psiClass.getText().contains("@RestController")){
            PsiComment comment = this.psiClass.getDocComment();
            addAnnotation(comment,psiClass,doc->"@Api(description=\""+doc+"\")");
        }
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
           importList.add( elementFactory.createImportStatementOnDemand(IMPORT_PACKAGE));
       }
    }

    private boolean hasClassImport(PsiImportList importList){
       return importList.findSingleClassImportStatement(IMPORT_PACKAGE+".ApiModel")!=null || importList.findSingleClassImportStatement(IMPORT_PACKAGE+".ApiModelProperty")!=null;
    }
    private boolean hasPackageImport(PsiImportList importList){
        return importList.findOnDemandImportStatement(IMPORT_PACKAGE)!=null;
    }
}
