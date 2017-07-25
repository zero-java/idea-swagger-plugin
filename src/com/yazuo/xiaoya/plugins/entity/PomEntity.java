package com.yazuo.xiaoya.plugins.entity;

import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlTag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Year: 2017-2017/7/25-23:10
 * Project:idea-annotation-plugin
 * Package:com.yazuo.xiaoya.plugins.entity
 * To change this template use File | Settings | File Templates.
 */

public class PomEntity {
    private PsiElement root;
    private List<Dependency> dependencies;
    private XmlTag parent ;
    private List<XmlTag> self;
    public PomEntity(XmlTag root) {
        this.root = root;
        // add dependencies
        dependencies = Arrays.stream(root.getSubTags())
                .filter(xmlTag->{
                    if(xmlTag.getLocalName().equals("parent")) {
                        this.parent = xmlTag;
                        return false;
                    }
                    return true;

                })
                .filter(xmlTag -> {
                    if(xmlTag.getLocalName().equals("version")||xmlTag.getLocalName().equals("artifactId")||xmlTag.getLocalName().equals("groupId")){
                        if(this.self==null) this.self = new ArrayList<>();
                        self.add(xmlTag);
                        return false;
                    }
                    return true;
                })
                .filter(xmlTag -> xmlTag.getLocalName().equals("dependencies"))
                .flatMap(dependencies->Arrays.stream(dependencies.getSubTags()))
                .map(dependencyTag-> convertDependency(dependencyTag.getSubTags())).collect(toList());
        // add parent
        if(parent!=null){
            dependencies.add(convertDependency(parent.getSubTags()));
        }
        // add self
        if(self.size()>1){
            XmlTag[] selfArray = new XmlTag[self.size()];
            dependencies.add(convertDependency(self.toArray(selfArray)));
        }
    }

    public PsiElement getRoot() {
        return root;
    }

    public List<Dependency> getDependencies() {
        return dependencies;
    }

    private Dependency convertDependency(XmlTag[] tags){
        Dependency dependency = new Dependency();
        for(XmlTag tag : tags){
            if(tag.getLocalName().equals("groupId")){
                dependency.setGroupId(tag);
            }
            if(tag.getLocalName().equals("version")){
                dependency.setVersion(tag);
            }
            if(tag.getLocalName().equals("artifactId")){
                dependency.setArtifactId(tag);
            }
        }
        return dependency;
    }
}
