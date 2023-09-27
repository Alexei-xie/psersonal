package com.itself.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itself.user.entity.UserPO;
import com.itself.user.mapper.UserMapper;
import com.itself.user.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author xxw
 * @Date 2022/07/09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPO> implements UserService {

    @Resource
    private UserMapper localUserMapper;


    @Override
    public List<UserPO> listAll() {
        return localUserMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public Page<UserPO> listPage(int pageNum, int pageSize) {
        return localUserMapper.selectPage(new Page<>(pageNum, pageSize),new QueryWrapper<>());
    }
}
