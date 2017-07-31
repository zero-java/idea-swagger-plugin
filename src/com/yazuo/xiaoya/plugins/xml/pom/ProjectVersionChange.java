package com.yazuo.xiaoya.plugins.xml.pom;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.impl.source.xml.XmlFileImpl;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.yazuo.xiaoya.plugins.entity.Dependency;
import com.yazuo.xiaoya.plugins.entity.PomEntity;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

/**
 *
 *
 * Created by scvzerng on 2017/7/26.
 */
public class ProjectVersionChange extends AnAction {
    private static  Set<String> filter = new HashSet<>();
    static {
        filter.add("xiaoYa");
        filter.add("test");
        filter.add("pom_parent");
        filter.add("component");
        filter.add("common-parent");
        filter.add("build");
        filter.add("alipay-sdk");
    }
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {

        Project project = anActionEvent.getProject();
        String version = Messages.showInputDialog("请输入版本号","修改依赖版本", AllIcons.Actions.Edit);
        if(version==null||version.trim().equals("")) return;
        List<XmlFile> files =
                stream(FilenameIndex.getFilesByName(project,"pom.xml", GlobalSearchScope.projectScope(project)))
                .filter(psiFile -> psiFile instanceof XmlFileImpl)
                .map(psiFile -> (XmlFile)psiFile).collect(toList());
        WriteCommandAction.runWriteCommandAction(project,()-> files.stream()
                .map(XmlFile::getRootTag)
                .map(PomEntity::new)
                .flatMap(getAllDependency())
                .filter(isNotParent())
                .filter(isXiaoya())
                .filter(skip())
                .map(checkVersion(version))
                .map(Dependency::getVersion)
                .filter(Objects::nonNull)
                .map(XmlTag::getValue)
                .forEach(versionTagValue-> versionTagValue.setText(version)));


    }

    private Predicate<Dependency> isXiaoya(){
        return dependency-> dependency.getGroupId()==null||dependency.getGroupId().getText().contains("com.yazuo.xiaoya");
    }
    private Predicate<Dependency> isNotParent(){
        return dependency -> !dependency.isParent();
    }

    private Predicate<Dependency> skip(){
        return dependency -> !filter.contains(dependency.getArtifactId().getText().trim().replaceAll("<artifactId>","")
                .replaceAll("</artifactId>","").trim());
    }

    private Function<Dependency,Dependency> checkVersion(final String version){
        return dependency -> {
            if(dependency.getVersion()==null){
                XmlTag parent = dependency.getArtifactId().getParentTag();
                XmlTag versionTag = parent.createChildTag("version",parent.getNamespace(),version,false);
                parent.addSubTag(versionTag,false);
                dependency.setVersion(versionTag);
            }
            return dependency;
        };
    }

    private Function<PomEntity,Stream<Dependency>> getAllDependency(){
        return pomEntity -> {
            if(pomEntity.isNotPom()) return pomEntity.getDependencies().stream();
            return pomEntity.getOnlyDependencies().stream();
        };
    }

}
