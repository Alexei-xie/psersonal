package com.itself.factory;

/**
 * 抽象产品
 */
interface Product {
	void operation();
}

/**
 * 具体产品 A
 */
class ConcreteProductA implements Product {
	@Override
	public void operation() {
		System.out.println("AAAAAAAA operation");
	}
}

/**
 * 具体产品 B
 */
class ConcreteProductB implements Product {
	@Override
	public void operation() {
		System.out.println("BBBBBBB operation");
	}
}

/**
 * 抽象创建者
 */
interface Creator {
	Product createProduct();
}

/**
 * 具体创建者 A
 */
class ConcreteCreatorA implements Creator {
	@Override
	public Product createProduct() {
		return new ConcreteProductA();
	}
}

/**
 * 具体创建者 B
 */
class ConcreteCreatorB implements Creator {
	@Override
	public Product createProduct() {
		return new ConcreteProductB();
	}
}


/**
 * 工厂模式demo
 */
public class Demo {
	public static void main(String[] args) {
		Creator creator = new ConcreteCreatorA();
		Product product = creator.createProduct();
		product.operation();
	}
}