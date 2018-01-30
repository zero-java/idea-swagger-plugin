package com.yazuo.xiaoya.plugins.annotation;


/**
 *注解操作接口
 * Created by scvzerng on 2017/6/23.
 */
public interface AnnotationHandler {
     /**
      * 添加方法注解
      */
     void addMethodAnnotation();

     /**
      * 添加字段注解
      */
     void addFieldAnnotation();

     /**
      * 添加类注解
      */
     void addClassAnnotation();

}
