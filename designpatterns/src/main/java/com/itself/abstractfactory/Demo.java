package com.itself.abstractfactory;

/**
 * 抽象产品 A
 */
interface ProductA {
    void operationA();
}

/**
 * 具体产品 A1
 */
class ConcreteProductA1 implements ProductA {
    @Override
    public void operationA() {
        System.out.println("AAAAA11111 operationA");
    }
}

/**
 * 抽象产品 B
 */
interface ProductB {
    void operationB();
}

/**
 * 具体产品 B1
 */
class ConcreteProductB1 implements ProductB {
    @Override
    public void operationB() {
        System.out.println("BBBBBB111111 operationB");
    }
}

/**
 * 抽象工厂
 */
interface AbstractFactory {
    ProductA createProductA();
    ProductB createProductB();
}

/**
 * 具体工厂 1
 */
class ConcreteFactory1 implements AbstractFactory {
    @Override
    public ProductA createProductA() {
        return new ConcreteProductA1();
    }

    @Override
    public ProductB createProductB() {
        return new ConcreteProductB1();
    }
}

/**
 * 抽象工厂demo
 */
public class Demo {
    public static void main(String[] args) {
        AbstractFactory factory = new ConcreteFactory1();
        ProductA productA = factory.createProductA();
        ProductB productB = factory.createProductB();
        productA.operationA();
        productB.operationB();
    }
}