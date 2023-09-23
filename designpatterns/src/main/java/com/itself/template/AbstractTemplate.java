package com.itself.template;

/**
 * @Author xxw
 * @Date 2023/07/31
 */
public abstract class AbstractTemplate {
    /**
     * 模板方法
     *  为了防止恶意操作，一般情况下模板方法都加上final关键词
     *  一下的方法调用顺序构成算法骨架
     */
    public final void templateMethod(){
        System.out.println("this is a template method !");
        abstractMethod();
        if (hockMethod()){
            publicMethod();
        }
        scalableMethod();
    }


    /**
     * 这是一个公共的方法
     */
    public void publicMethod(){
        System.out.println("super:this is a public method !");
    }

    /**
     * 这是一个可扩展的方法由子类去实现改写
     */
    public void scalableMethod(){
        System.out.println("super: this is a scalable method !");
    }

    /**
     * 钩子函数
     */
    public Boolean hockMethod(){
        return true;
    }

    /**
     * 抽象方法
     */
    public abstract void abstractMethod();



}
