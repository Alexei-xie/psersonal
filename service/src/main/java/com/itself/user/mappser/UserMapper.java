package com.itself.user.mappser;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itself.user.entity.UserPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author xxw
 * @Date 2022/07/09
 */
@Mapper
public interface UserMapper extends BaseMapper<UserPO> {

}
