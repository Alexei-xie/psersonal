package com.itself.supplier.demo;

import org.apache.commons.lang3.StringUtils;

import java.util.function.Consumer;

/**
 * function函数校验工具类demo
 * @Author xxw
 * @Date 2023/04/14
 */
public class FunctionUtil {

    public static void main(String[] args) {
        isTrue(false).throwMessage("错误信息");
        isTrueOrFalse(true).trueOrFalseHandle(() -> {
            System.out.println("true");
        }, () -> {
            System.out.println("false");
        });
        isBlankOrNotBlank("1").presentOrElseHandle(System.out::println,()-> System.out.println("error"));
    }

    public static ThrowExceptionFunction isTrue(Boolean flag) {
        return message -> {
            if (flag) {
                throw new RuntimeException(message);
            }else {
                System.out.println("11");
            }
        };
    }

    public static BranchHandler isTrueOrFalse(Boolean flag) {
        return (trueHandle, falseHandle) -> {
            if (flag) {
                trueHandle.run();
            } else {
                falseHandle.run();
            }
        };
    }

    public static PresentOrElseHandler<?> isBlankOrNotBlank(String str){
        return (consumer,runnable)->{
            if (StringUtils.isNotBlank(str)){
                consumer.accept(str);
            }else {
                runnable.run();
            }
        };
    }
}
@FunctionalInterface
interface ThrowExceptionFunction {
    /**
     * 抛出异常信息
     * @param message
     */
    void throwMessage(String message);
}

/**
 * 分支处理接口
 **/
@FunctionalInterface
interface BranchHandler {

    /**
     * 分支操作
     * @param trueHandle 为true时要进行的操作
     * @param falseHandle 为false时要进行的操作
     * @return void
     **/
    void trueOrFalseHandle(Runnable trueHandle, Runnable falseHandle);

}

/**
 * 空值与非空值分支处理
 */
@FunctionalInterface
interface PresentOrElseHandler<T extends Object> {

    /**
     * 值不为空时执行消费操作
     * 值为空时执行其他的操作
     * @param action 值不为空时，执行的消费操作
     * @param emptyAction 值为空时，执行的操作
     * @return void
     **/
    void presentOrElseHandle(Consumer<? super T> action, Runnable emptyAction);

}

