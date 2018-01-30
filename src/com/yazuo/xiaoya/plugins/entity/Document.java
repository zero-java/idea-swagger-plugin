package com.yazuo.xiaoya.plugins.entity;

import com.intellij.psi.*;
import com.intellij.psi.impl.source.javadoc.PsiDocCommentImpl;
import com.intellij.psi.impl.source.javadoc.PsiDocTokenImpl;
import com.yazuo.xiaoya.plugins.utils.AnnotationUtil;
import com.yazuo.xiaoya.plugins.utils.CommonUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

/**
 *注释实体
 * Created by scvzerng on 2017/6/23.
 */
public class Document  {
    /**
     * 注释
     */
    private Optional<PsiDocCommentImpl> document;
    /**
     * 注释描述 非注释标签的都属于描述
     */
    private List<PsiDocTokenImpl> descriptions;
    /**
     * param标签列表
     */
    private List<Tag> params;
    /**
     * return 标签
     */
    private Optional<Tag> returnTag;
    /**
     * 注释依附的元素
     */
    private PsiModifierListOwner owner;
    public List<PsiDocTokenImpl> getDescriptions() {
        return descriptions;
    }

    /**
     * 返回所有注解描述文本
     * @return
     */
    public String getDescriptionText(){
        StringBuilder text = new StringBuilder();
        if(descriptions!=null&&descriptions.size()>0){
            descriptions.stream().map(PsiElement::getText).forEach(text::append);
        }
        return text.toString();
    }

    /**
     * 返回注释描述的第一行
     * @return
     */
    public String firstLine(){
        if(descriptions!=null&&descriptions.size()>0){
            return descriptions.get(0).getText();
        }
        return "";
    }
    public void getParams(Consumer<Stream<Tag>> consumer){
        consumer.accept(params.stream());
    }
    public List<Tag> getParams() {
        return params;
    }

    public Optional<Tag> getReturnTag() {
        return returnTag;
    }

    public Document(PsiComment document,PsiModifierListOwner owner) {
        if(document instanceof PsiDocCommentImpl){
            this.document = Optional.of((PsiDocCommentImpl) document);
            this.document.ifPresent(doc->{
                this.descriptions =stream(doc.getDescriptionElements())
                        .filter(CommonUtils::filterWhiteSpace)
                        .filter(psiElement->psiElement instanceof PsiDocTokenImpl)
                        .map(psiElement->(PsiDocTokenImpl) psiElement)
                        .collect(toList());
                this.params = stream(doc.getTags())
                        .filter(tag-> {
                            if(Objects.equals(tag.getName(), "return")) {
                                returnTag = Optional.of(new Tag(tag));
                                return false;
                            }
                            return true;
                        }).filter(tag-> Objects.equals(tag.getName(), "param"))
                        .map(Tag::new)
                        .collect(toList());
            });


        }else{
            this.document = Optional.ofNullable(null);
        }

        this.owner = owner;




    }

    public Optional<PsiDocCommentImpl> getDocument() {
        return document;
    }


    public PsiModifierListOwner getOwner() {
        return owner;
    }

    /**
     * 对注释依附的元素添加注解
     * 如果有注释则把注解添加在注释下方
     * @param annotation 需要依附的注解
     */
    public void addAnnotation(PsiAnnotation annotation) {
        if(AnnotationUtil.hasAnnotation(this.owner.getModifierList(),annotation)) return;
        if(!this.document.isPresent()){
            this.owner.getParent().addBefore(annotation,this.owner);
        }
        this.document.ifPresent(doc-> doc.addAfter(annotation,doc));

    }

}
