package com.itself.user.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.util.ListUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itself.user.entity.UserPO;
import com.itself.user.mapper.UserMapper;
import com.itself.user.service.UserService;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @Author xxw
 * @Date 2022/07/09
 */
@Slf4j
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

    @Override
    public List<String> importData(InputStream inputStream) {
        List<String> errorMessages = new ArrayList<>();

        try {
            EasyExcel.read(inputStream, UserPO.class, new AnalysisEventListener<UserPO>() {
                // 单次缓存的数据量
                private final int BATCH_COUNT = 1000;
                // 临时存储
                private List<UserPO> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
                @Override
                public void invoke(UserPO user, AnalysisContext context) {
                    if (Objects.isNull(user)){
                        return;
                    }
                    // 自定义对个别字段进行特殊校验，返回具体报错信息
                    String errorMsg = "";
                    if (StringUtils.isNoneBlank(errorMsg)) {
                        String str = "第"+context.readRowHolder().getRowIndex()+"行导入失败， 错误信息："+errorMsg;
                        errorMessages.add(str);
                    }
                    cachedDataList.add(user);
                    // 数据量较大，则走分批处理
                    if (cachedDataList.size() >= BATCH_COUNT) {
                        saveBatch(cachedDataList);
                        // 存储完成清理 list
                        cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
                    }
                }

                /**
                 * 数据量不够大，走此处逻辑
                 */
                @Override
                public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                    saveBatch(cachedDataList);
                }
            }).sheet().doRead();
        } catch (ExcelDataConvertException e) {
            log.error("cell={}, msg={}",e.getCellData().getStringValue(), e.getMessage(), e);
            // 此处可以将异常包装出来返回给前端，例如返回一个集合
            String errorMsg = "第"+e.getRowIndex()+"行导入失败， 错误信息："+e.getMessage();
            errorMessages.add(errorMsg);
        }
        return errorMessages;
    }
}
