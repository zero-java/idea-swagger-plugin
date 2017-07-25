package com.yazuo.xiaoya.plugins.xml.pom;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiPlainText;
import com.intellij.psi.impl.source.xml.XmlFileImpl;
import com.intellij.psi.impl.source.xml.XmlTagImpl;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.xml.XmlFile;
import com.yazuo.xiaoya.plugins.entity.Dependency;
import com.yazuo.xiaoya.plugins.entity.PomEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

/**
 *
 * 统一修改maven版本号
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Year: 2017-2017/7/25-19:44
 * Project:idea-annotation-plugin
 * Package:com.yazuo.xiaoya.plugins.xml
 * To change this template use File | Settings | File Templates.
 */

public class PomVersionChange extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Project project = anActionEvent.getProject();
        String version = Messages.showInputDialog("请输入版本号","修改依赖版本", AllIcons.Actions.Edit);
        if(version==null||version.trim().equals("")) return;
        List<XmlFile> files = Arrays
                .stream(FilenameIndex.getFilesByName(project,"pom.xml", GlobalSearchScope.projectScope(project)))
                .filter(psiFile -> psiFile instanceof XmlFileImpl)
                .map(psiFile -> (XmlFile)psiFile).collect(toList());
        Editor editor = anActionEvent.getData(CommonDataKeys.EDITOR);
        SelectionModel selectionModel = editor.getSelectionModel();
        String selectedText = selectionModel.getSelectedText();
        WriteCommandAction.runWriteCommandAction(project,()-> files.stream()
                .map(XmlFile::getRootTag)
                .map(PomEntity::new)
                .flatMap(pomEntity -> pomEntity.getDependencies().stream())
                .filter(dependency -> dependency.getArtifactId().getText().contains(selectedText))
                .map(Dependency::getVersion)
                .filter(Objects::nonNull)
                .map(xmlTag->((XmlTagImpl) xmlTag).getValue())
                .forEach(versionTagValue-> versionTagValue.setText(version)));




    }

}
