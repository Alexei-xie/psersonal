package com.itself.template;

/**
 * 模板方法设计模式
 *  优点：
 *      1.封装了不变的部分，扩展可变部分
 *      2.提取了公共的部分代码，便于代码复用
 *      3.通过增加子类可以很好的扩展算法，符合开闭原则
 *  缺点：
 *      1.对于不同的实现都需要增加一个子类，这会导致系统的类增多
 *      2.模板方法设计更加抽象，增加了系统实现的复杂度
 *      3.通过子类方法影响父类方法，这导致一种反向的控制结构，提高了代码阅读的难度
 *      4.基于继承关系自身的特点，父类添加新的抽象方法，所有子类都需要同步修改一遍
 *  案例：
 *      Spring中的AbstractApplicationContext中的refresh()就是模板方法模式
 * @Author xxw
 * @Date 2023/07/31
 */
public class Demo {
    public static void main(String[] args) {
        AbstractTemplate template = new AchieveTemplate();
        template.templateMethod();
    }
}
