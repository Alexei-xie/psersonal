package com.itself.supplier;

/**
 * @Author xxw
 * @Date 2023/04/14
 */

@FunctionalInterface
public interface Supplier<T> {

    /**
     * Supplier并且 只包含一个抽象方法的接口是函数式接口。
     * 函数式接口主要分为Supplier供给型函数、Consumer消费型函数、Runnable无参无返回型函数和Function有参有返回型函数。
     * @return
     */
    T get();
}
