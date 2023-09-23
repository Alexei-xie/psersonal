package com.itself.template;

/**
 * @Author xxw
 * @Date 2023/07/31
 */
public class AchieveTemplate extends AbstractTemplate{
    @Override
    public void abstractMethod() {
        System.out.println("achieve:this is achieve method !");
    }

    @Override
    public void publicMethod() {
        super.publicMethod();
        System.out.println("achieve:this is a public method !");
    }

    @Override
    public void scalableMethod() {
        super.scalableMethod();
        System.out.println("achieve:this is a scalable method !");
    }

    @Override
    public Boolean hockMethod() {
        return super.hockMethod();
    }
}
