package com.yazuo.xiaoya.plugins.entity;

import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlTag;

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

    public PomEntity(XmlTag root) {
        this.root = root;

        dependencies = Arrays.stream(root.getSubTags())
                .filter(xmlTag -> xmlTag.getLocalName().equals("dependencies"))
                .flatMap(dependencies->Arrays.stream(dependencies.getSubTags()))
                .map(dependencyTag->{
                    XmlTag[] tags = dependencyTag.getSubTags();
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
                }).collect(toList());
        Arrays.stream(root.getSubTags()).filter(parent->parent.getLocalName().equals("parent")).map(parentTag->{
            XmlTag[] tags = parentTag.getSubTags();
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
        }).findAny().ifPresent(dependency -> dependencies.add(dependency));
        Dependency dependency = new Dependency();
        Arrays.stream(root.getSubTags())
                .filter(xmlTag -> xmlTag.getLocalName().equals("version")||xmlTag.getLocalName().equals("artifactId")||xmlTag.getLocalName().equals("groupId"))
                .collect(toList()).stream().forEach(tag->{
            if(tag.getLocalName().equals("groupId")){
                dependency.setGroupId(tag);
            }
            if(tag.getLocalName().equals("version")){
                dependency.setVersion(tag);
            }
            if(tag.getLocalName().equals("artifactId")){
                dependency.setArtifactId(tag);
            }
        });
        if(dependency.getVersion()!=null){
            dependencies.add(dependency);
        }
    }

    public PsiElement getRoot() {
        return root;
    }

    public List<Dependency> getDependencies() {
        return dependencies;
    }
}
