package com.itself.supplier.demo;

/**
 * @Author xxw
 * @Date 2023/08/14
 */
public class SupplierDemo {
    public static void main(String[] args) {
        for (int i = 2; i <= 3; i++) {
            testExec(i,()-> System.out.println("333"));
            testGet(i,()->{
                System.out.println("==333==");
                return 2;
            });
        }

    }

    public static <T> void testExec(int count, Supplier supplier) {
        if (count > 2) {
            supplier.exec();
            supplier.print();//输出默认方法
        } else {
            System.out.println("222");
        }
    }
    public static <T> void testGet(int count, SupplierGet<T> supplier) {
        if (count > 2) {
            T t = supplier.get();
            System.out.println(t);
        } else {
            System.out.println("222");
        }
    }
}

@FunctionalInterface
interface Supplier {

    void exec();

    /**
     * 可添加默认的方法用于扩展
     */
    default void print(){
        System.out.println("result");
    }
}
@FunctionalInterface
interface SupplierGet<T> {

    T get();

}