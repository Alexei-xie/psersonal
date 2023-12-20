package com.itself.redis;

import org.springframework.stereotype.Service;

/**
 *
 * @Author duJi
 */
@Service("bbb")
public class BserviceImpl implements RedisRouteService{
    @Override
    public void route() {
        System.out.println("b service impl------");
    }
}
