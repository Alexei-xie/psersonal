package com.itself.redis.queue;

import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * @Author duJi
 */
@Service("aaa")
public class AserviceImpl implements RedisRouteService , Serializable {
    @Override
    public void route() {
        System.out.println("a service impl-----");
    }


    public static void main(String[] args) {
        System.out.println(String.valueOf(new AserviceImpl()));
    }
}
