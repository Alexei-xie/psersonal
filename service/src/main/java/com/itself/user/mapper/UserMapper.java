package com.itself.user.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itself.user.entity.UserPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Author xxw
 * @Date 2022/07/09
 */
@Mapper
public interface UserMapper extends BaseMapper<UserPO> {

    @Select("select * from user ${ew.customSqlSegment}")
    Page<UserPO> queryPageData(Page<UserPO> page,@Param(Constants.WRAPPER) Wrapper<UserPO> queryWrapper);
}
