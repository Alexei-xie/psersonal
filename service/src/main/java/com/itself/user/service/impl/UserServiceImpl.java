package com.itself.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itself.user.entity.UserPO;
import com.itself.user.mappser.UserMapper;
import com.itself.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author xxw
 * @Date 2022/07/09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,UserPO> implements UserService{

    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }





    @Override
    public List<UserPO> listAll() {
        return userMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public Page<UserPO> listPage(int pageNum, int pageSize) {
        return userMapper.selectPage(new Page<>(pageNum, pageSize),new QueryWrapper<>());
    }
}
