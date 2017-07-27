package com.yazuo.xiaoya.plugins.entity;

import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.xml.XmlTagImpl;
import com.intellij.psi.xml.XmlTag;
import com.yazuo.xiaoya.plugins.utils.ReflectUtil;

import java.util.ArrayList;
import static java.util.Arrays.stream;
import java.util.List;
import java.util.Optional;
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
    private XmlTag packaging;
    private PsiElement root;
    private List<Dependency> dependencies;
    private XmlTag parent ;
    private List<XmlTag> self;
    private Dependency selfDependency;
    private Dependency parentDependency;
    public PomEntity(XmlTag root) {
        this.root = root;
        // add dependencies
        dependencies = stream(root.getSubTags())
                .filter(isNotParent())
                .filter(isNotSelf())
                .filter(isNotPacking())
                .filter(isDependencies())
                .flatMap(dependencies->stream(dependencies.getSubTags()))
                .map(dependencyTag-> stream(dependencyTag.getSubTags()).collect(ReflectUtil.toBean(Dependency::new, XmlTag::getLocalName))).collect(toList());
        // add parent
        if(parent!=null){
            this.parentDependency = stream(parent.getSubTags()).collect(ReflectUtil.toBean(Dependency::new, XmlTag::getLocalName));
            this.parentDependency.setTag(Dependency.PARENT);
            dependencies.add(this.parentDependency);
        }
        // add self
        if(self.size()>0){
            XmlTag[] selfArray = new XmlTag[self.size()];
            this.selfDependency = stream(this.self.toArray(selfArray)).collect(ReflectUtil.toBean(Dependency::new, XmlTag::getLocalName));
            this.selfDependency.setTag(Dependency.SELF);
            dependencies.add(this.selfDependency);
        }
    }

    public Optional<Dependency> getSelfDependency() {
        return Optional.ofNullable(selfDependency);
    }

    public Optional<Dependency> getParentDependency() {
        return Optional.ofNullable(parentDependency);
    }

    public PsiElement getRoot() {
        return root;
    }

    public List<Dependency> getDependencies() {
        return dependencies;
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
    private Predicate<XmlTag> isNotPacking(){
        return xmlTag -> {
            if(xmlTag.getLocalName().equals("packaging")){
                this.packaging = xmlTag;
                return false;
            }else{
                return true;
            }
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

    public boolean isNotPom(){
        return packaging == null || !packaging.getValue().getText().equals("pom");
    }
}
