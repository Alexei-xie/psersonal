package com.itself.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itself.user.entity.UserPO;

import java.io.InputStream;
import java.util.List;

/**
 * @Author xxw
 * @Date 2022/07/09
 */

public interface UserService extends IService<UserPO> {

    Page<UserPO> queryPageData(Integer pageSize, Integer pageNum, UserPO userPO);

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

    /**
     * 单sheet页导入数据
     * @param inputStream 文件流
     * @return 错误信息
     */
    List<String> importData(InputStream inputStream);

    /**
     * 多sheet页导入
     * @param inputStream 文件流
     * @return 错误信息
     */
    List<String> importDataSheets(InputStream inputStream);

}
