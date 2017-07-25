package com.yazuo.xiaoya.plugins.entity;

import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlTag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

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
    public static final String TAG_VERSION = "version";
    public static final String TAG_GROUP_ID = "groupId";
    public static final String TAG_ARTIFACT_ID = "artifactId";
    public static final String TAG_DEPENDENCIES = "dependencies";
    public static final String TAG_PARENT = "parent";
    private PsiElement root;
    private List<Dependency> dependencies;
    private XmlTag parent ;
    private List<XmlTag> self;
    public PomEntity(XmlTag root) {
        this.root = root;
        // add dependencies
        dependencies = Arrays.stream(root.getSubTags())
                .filter(isNotParent())
                .filter(isNotSelf())
                .filter(isDependencies())
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
            if(tag.getLocalName().equals(TAG_GROUP_ID)){
                dependency.setGroupId(tag);
            }
            if(tag.getLocalName().equals(TAG_VERSION)){
                dependency.setVersion(tag);
            }
            if(tag.getLocalName().equals(TAG_ARTIFACT_ID)){
                dependency.setArtifactId(tag);
            }
        }
        return dependency;
    }

    /**
     * 过滤掉parent标签
     * @return
     */
    private Predicate<XmlTag> isNotParent(){
        return xmlTag->{
            if(xmlTag.getLocalName().equals(TAG_PARENT)) {
                this.parent = xmlTag;
                return false;
            }
            return true;

        };
    }

    /**
     * 过滤掉自身
     * @return
     */
    private Predicate<XmlTag> isNotSelf(){
        return xmlTag -> {
            if(xmlTag.getLocalName().equals(TAG_VERSION)||xmlTag.getLocalName().equals(TAG_ARTIFACT_ID)||xmlTag.getLocalName().equals(TAG_GROUP_ID)){
                if(this.self==null) this.self = new ArrayList<>();
                self.add(xmlTag);
                return false;
            }
            return true;
        };
    }

    /**
     * 过滤通过所有dependency节点
     * @return
     */
    private Predicate<XmlTag> isDependencies(){
        return xmlTag -> xmlTag.getLocalName().equals(TAG_DEPENDENCIES);
    }
}
