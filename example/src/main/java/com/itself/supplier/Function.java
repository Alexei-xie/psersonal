package com.itself.supplier;

/**
 * @Author xxw
 * @Date 2023/04/14
 */
@FunctionalInterface
public interface Function<T,R> {

    /**
     * Function函数的表现形式为接收一个参数，并返回一个值
     * Supplier、Consumer和Runnable可以看作Function的一种特殊表现形式
     * @param t
     * @return
     */
    R apply(T t);
}
