package com.itself.annotation.demo;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 *  自定义注解测试合集
 * @Author xxw
 * @Date 2022/10/17
 */
@ClassAnnotation(name = "AnnotationDemoClass",version = "1.0")
public class AnnotationDemo {

    @ConstructorAnnotation(constructionName = "constructor",remark = "满参构造方法")
    public AnnotationDemo(String description) {
        this.description = description;
    }

    @FieldAnnotation(name = "description",value = "介绍")
    private String description;

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @MethodAnnotation(name = "sayHelloMethod",type = MethodTypeEnum.TYPE2)
    public void sayHello(){
        System.out.println("hello");
    }

}

/**
 * 类注解测试
 */
class TesClassAnnotationPrintln{
    public static void main(String[] args) {
        Class<AnnotationDemo> aClass = AnnotationDemo.class;
        //因为注解是作用于类上，所以可以通过isAnnotationPresent来判断是否有指定注解的类
        if (aClass.isAnnotationPresent(ClassAnnotation.class)){
            System.out.println("=== this is a class with annotation classAnnotation ===");
            //通过getAnnotation获取注解对象
            ClassAnnotation annotation = aClass.getAnnotation(ClassAnnotation.class);
            System.out.println(Optional.ofNullable(annotation).flatMap(e->Optional.ofNullable("ClassName : "+e.name())).orElse("we have not get the classAnnotation"));
            System.out.println(Optional.ofNullable(annotation).flatMap(e->Optional.ofNullable("ClassVersion :" +e.version())).orElse("we have not get the classAnnotation"));
            /*if (null != annotation){
                //获取类注解里的属性值
                System.out.println("ClassName : "+ annotation.name());
                System.out.println("ClassVersion :" + annotation.version());
            }else {
                System.out.println("we have not get the classAnnotation");
            }*/
        }else{
            System.out.println("This is not the class that with ClassAnnotation");
        }
    }
}

/**
 * 字段注解测试
 */
class TestFieldAnnotationPrintln{
    public static void main(String[] args) {
        Field[] fields = AnnotationDemo.class.getDeclaredFields();
        for (Field field : fields) {
            if(field.isAnnotationPresent(FieldAnnotation.class)) {
                FieldAnnotation annotation = field.getAnnotation(FieldAnnotation.class);
                System.out.println(annotation.name()+"\t"+annotation.value());
            }
        }
    }
}

/**
 * 构造方法注解测试
 */
class TestConstructorAnnotationPrintln{
    public static void main(String[] args) {
        Constructor<?>[] constructors = AnnotationDemo.class.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            if(constructor.isAnnotationPresent(ConstructorAnnotation.class)) {
                ConstructorAnnotation annotation = constructor.getAnnotation(ConstructorAnnotation.class);
                System.out.println(annotation.constructionName()+"\t"+annotation.remark());
            }
        }
    }
}

/**
 * 方法注解测试
 */
class TestMethodAnnotationPrintln{
    public static void main(String[] args) {
        Method[] methods = AnnotationDemo.class.getDeclaredMethods();
        for (Method method : methods) {
            if(method.isAnnotationPresent(MethodAnnotation.class)) {
                MethodAnnotation annotation = method.getAnnotation(MethodAnnotation.class);
                System.out.println(annotation.name()+"\t"+annotation.type());
            }
        }
    }
}
