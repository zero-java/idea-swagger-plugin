package com.yazuo.xiaoya.swagger;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiWhiteSpace;

import java.util.Objects;

/**
 *
 * Created by scvzerng on 2017/6/23.
 */
public class DocumentUtil {
    public static boolean filterWhiteSpace(PsiElement element){
        return !(Objects.equals(element.getText().trim(), "")||element instanceof PsiWhiteSpace) ;
    }
}
