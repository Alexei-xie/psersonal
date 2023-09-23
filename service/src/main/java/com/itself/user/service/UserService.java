package com.itself.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itself.user.entity.UserPO;

import java.util.List;

/**
 * @Author xxw
 * @Date 2022/07/09
 */

public interface UserService extends IService<UserPO> {
    /**
     * 查询所有数据
     */
    List<UserPO> listAll();

    /**
     * 分页查询数据
     * @param pageNum  从当前页索引开始
     * @param pageSize  每页大小
     */
    Page<UserPO> listPage(int pageNum, int pageSize);
}
