package com.itself.page;

import com.itself.utils.ApplicationContextProvider;
import com.itself.utils.baseutils.StrUtil;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.SqlSessionTemplate;

@Slf4j
public class MybatisUtil {
    /**
     * 获取实体类对应的mybatis mapper的resultMap映射对象
     *
     * @param clazz
     * @return
     */
    private static ResultMap getBaseResultMap(Class<?> clazz) {
        SqlSessionTemplate sqlSessionTemplate = ApplicationContextProvider.getBean(SqlSessionTemplate.class);
        assert sqlSessionTemplate != null;
        final Configuration configuration = sqlSessionTemplate.getConfiguration();
        Collection<String> resultMapNames = configuration.getResultMapNames();
        List<ResultMap> resultMaps = resultMapNames.stream()
                .filter(name -> name.contains("."))
                .map(configuration::getResultMap)
                .filter(resultMap -> Objects.equals(resultMap.getType(), clazz))
                .sorted(Comparator.comparing(resultMap -> resultMap.getPropertyResultMappings().size()))
                .collect(Collectors.toList());
        Collections.reverse(resultMaps);
        if(!resultMaps.isEmpty()){
            for(ResultMap resultMap:resultMaps){
                Class<?> type = resultMap.getType();
                if(Objects.equals(type, clazz)){
                    return resultMap;
                }
            }
        }
        return null;
    }

    /**
     * 根据实体类的属性名获取对应的数据表的字段列名
     *
     * @param property
     * @param clazz
     * @return
     */
    public static String propertyToColumn(String property, Class<?> clazz) {
        ResultMap resultMap = getBaseResultMap(clazz);
        if (resultMap != null) {
            for (ResultMapping resultMapping : resultMap.getPropertyResultMappings()) {
                if (property.equals(resultMapping.getProperty())) {
                    property = resultMapping.getColumn();
                    return property;
                }
            }
        }
        // 找不到resultMap就转下划线处理
        String column = StrUtil.toUnderlineCase(property);
        log.debug("没有查询到Mapper中的ResultMap：" + property + "字段映射信息!将使用驼峰命名法转换下划线属性名:" + column);
        return column;
    }
}