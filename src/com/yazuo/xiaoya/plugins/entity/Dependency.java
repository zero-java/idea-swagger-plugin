package com.yazuo.xiaoya.plugins.entity;

import com.intellij.psi.PsiElement;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Year: 2017-2017/7/25-23:10
 * Project:idea-annotation-plugin
 * Package:com.yazuo.xiaoya.plugins.entity
 * To change this template use File | Settings | File Templates.
 */

public class Dependency {
    private PsiElement groupId;
    private PsiElement artifactId;
    private PsiElement version;

    public PsiElement getGroupId() {
        return groupId;
    }

    public void setGroupId(PsiElement groupId) {
        this.groupId = groupId;
    }

    public PsiElement getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(PsiElement artifactId) {
        this.artifactId = artifactId;
    }

    public PsiElement getVersion() {
        return version;
    }

    public void setVersion(PsiElement version) {
        this.version = version;
    }
}
