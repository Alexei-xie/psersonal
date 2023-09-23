package com.itself.supplier;

/**
 * @Author xxw
 * @Date 2023/04/14
 */


@FunctionalInterface
public interface Consumer<T> {
    /**
     * Consumer接收一个参数，没有返回值
     * @param t
     */
    void accept(T t);
}
