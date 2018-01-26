package com.yazuo.xiaoya.plugins.entity;

import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlTag;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Year: 2017-2017/7/25-23:10
 * Project:idea-annotation-plugin
 * Package:com.yazuo.xiaoya.plugins.entity
 * To change this template use File | Settings | File Templates.
 */

public class Dependency {
    public static final int PARENT = 1;
    public static final int SELF = 2;
    public static final int DEPENDENCY = 0;
    private XmlTag groupId;
    private XmlTag artifactId;
    private XmlTag version;
    private int tag = DEPENDENCY;

    public XmlTag getGroupId() {
        return groupId;
    }


    public XmlTag getArtifactId() {
        return artifactId;
    }


    public XmlTag getVersion() {
        return version;
    }

    public void setGroupId(XmlTag groupId) {
        this.groupId = groupId;
    }

    public void setArtifactId(XmlTag artifactId) {
        this.artifactId = artifactId;
    }

    public void setVersion(XmlTag version) {
        this.version = version;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public boolean isParent(){
        return tag==PARENT;
    }
    public boolean isSelf(){
        return tag==SELF;
    }
    public boolean isDeendency(){
        return tag==DEPENDENCY;
    }
}
