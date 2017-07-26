package com.yazuo.xiaoya.plugins.utils;

import com.intellij.psi.xml.XmlTag;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * Created by scvzerng on 2017/7/26.
 */
public class ReflectUtil {
    public static<T> void setValue(T entity, Supplier<String> key,Supplier<Object> value){
        try {
            Field field = entity.getClass().getDeclaredField(key.get());
            field.setAccessible(true);
            field.set(entity,value.get());
            field.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
           throw new RuntimeException(e);
        }
    }

    public static<T,I> Collector<I, T, T> toBean(Supplier<T> supplier,Function<I,String> function){
        return new Collector<I,T,T>(){
            @Override
            public Supplier<T> supplier() {
                return supplier;
            }

            @Override
            public BiConsumer<T, I> accumulator() {
                return (t,in)->{
                    try {
                        Field field = t.getClass().getDeclaredField(function.apply(in));
                         field.setAccessible(true);
                         field.set(t,in);
                         field.setAccessible(false);
                    } catch (NoSuchFieldException |IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }


                };
            }

            @Override
            public BinaryOperator<T> combiner() {
                return (t,o)->t;
            }

            @Override
            public Function<T, T> finisher() {
                return (t)->t;
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Collections.unmodifiableSet(EnumSet.of(Characteristics.UNORDERED));
            }
        };
    }

}
