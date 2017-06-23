package com.yazuo.xiaoya.swagger;

import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.impl.source.javadoc.PsiDocCommentImpl;
import com.intellij.psi.impl.source.javadoc.PsiDocTokenImpl;
import com.intellij.psi.javadoc.PsiDocTag;

import java.util.List;
import java.util.Objects;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

/**
 *
 * Created by scvzerng on 2017/6/23.
 */
public class Document{
    public static final String COMMENT_DATA = "DOC_COMMENT_DATA";
    private PsiDocCommentImpl document;
    private List<PsiDocTokenImpl> descriptions;
    private List<Tag> params;
    private Tag returnTag;
    public List<PsiDocTokenImpl> getDescriptions() {
        return descriptions;
    }
    public String getDescriptionText(){
        StringBuilder text = new StringBuilder();
        descriptions.stream().map(PsiElement::getText).forEach(text::append);
        return text.toString();
    }

    public String firstLine(){
        if(descriptions!=null&&descriptions.size()>0){
            return descriptions.get(0).getText();
        }
        return "";
    }
    public List<Tag> getParams() {
        return params;
    }

    public Tag getReturnTag() {
        return returnTag;
    }

    public Document(PsiComment document) {
        if(document instanceof PsiDocCommentImpl){
            this.document = (PsiDocCommentImpl) document;
            this.descriptions =stream(this.document.getDescriptionElements())
                    .filter(DocumentUtil::filterWhiteSpace)
                    .filter(psiElement->psiElement instanceof PsiDocTokenImpl)
                    .map(psiElement->(PsiDocTokenImpl) psiElement)
                    .collect(toList());
            this.params = stream(this.document.getTags())
                    .filter(tag-> {
                        if(Objects.equals(tag.getName(), "return")) {
                            returnTag = new Tag(tag);
                            return false;
                        }
                        return true;
                    }).filter(tag-> Objects.equals(tag.getName(), "param"))
                    .map(Tag::new)
                    .collect(toList());

        }



    }

    public PsiDocCommentImpl getDocument() {
        return document;
    }
}
