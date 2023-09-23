package com.itself.genericity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 泛型
 * @Author xxw
 * @Date 2022/03/27
 */
public class Generic<T> {

    private T obj;

    Random random = new Random();

    List<T> list = new ArrayList<T>();

    public void addObject(T obj){
        list.add(obj);
    }

    public T getObject(){
        obj = list.get(random.nextInt(list.size()));
        return obj;
    }

}
