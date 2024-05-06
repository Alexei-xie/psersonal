package com.itself.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.util.ListUtils;
import com.itself.user.entity.UserPO;
import com.itself.user.service.UserService;
import com.itself.utils.ExcelUtils;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * 监听器只能通过new的方式来创建，不能交由spring进行单例模式管理
 * @Author: duJi
 * @Date: 2024-05-06
 **/
@Slf4j
public class UserImportListener  extends AnalysisEventListener<UserPO> {
    /**
     * 单次缓存的数据量
     */
    private static final int BATCH_COUNT = 1000;
    /**
     * 临时存储数据集
     */
    private List<UserPO> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    private UserService userService;
    /**
     * 错误信息返回结果集
     */
    private List<String> validateMsgList;
    public UserImportListener(UserService userService, List<String> validateMsgList) {
        this.userService = userService;
        this.validateMsgList = validateMsgList;
    }
    @Override
    public void invoke(UserPO data, AnalysisContext analysisContext) {
        try {
            //校验是否当前行所有数据都是空
            if (ExcelUtils.isLineNullValue(data)) {
                return;
            }
            cachedDataList.add(data);
            // 数据量较大，则走分批处理
            if (cachedDataList.size() >= BATCH_COUNT) {
                userService.saveBatch(cachedDataList);
                cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
            }
        }catch (ExcelDataConvertException e) {
            log.error(String.format("cell={}, msg={}",e.getCellData().getStringValue(), e.getMessage()), e);
            String errorMsg = String.format("第%d行导入失败， 错误信息：%s", e.getRowIndex(), e.getMessage());
            validateMsgList.add(errorMsg);
        }
    }
    /**
     * 数据量不够大，走此处逻辑
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        try {
            userService.saveBatch(cachedDataList);
        } catch (Exception e) {
            log.error("用户管理导入失败， 错误信息：{}",e.getMessage());
            String errorMsg = String.format("用户管理导入失败， 错误信息：%s", e.getMessage());
            validateMsgList.add(errorMsg);
        }
    }
}
