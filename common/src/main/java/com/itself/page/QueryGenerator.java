package com.itself.page;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itself.utils.SqlInjectionUtil;
import com.itself.utils.baseutils.ArrayUtil;
import com.itself.utils.baseutils.StrUtil;
import com.itself.utils.baseutils.TimeUtil;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author: duJi
 * @Date: 2024-05-07
 **/
@Slf4j
public class QueryGenerator {
    /**
     * 排序方式
     */
    private static final String ORDER_TYPE = "order_type";
    private static final String ORDER_TYPE_ASC = "ASC";
    /**
     * 排序列
     */
    private static final String ORDER_COLUMN = "order_columns";
    /**
     * 页面带有规则值查询，空格作为分隔符
     */
    private static final String QUERY_SEPARATE_KEYWORD = " ";
    private static final String BEGIN = "_begin";
    private static final String END = "_end";
    private static final String RANGE = "_range";
    private static final String STAR = "*";
    private static final String COMMA = ",";
    private static final String NOT_EQUAL = "!";
    private static final String IN = "in ";
    private static final String NOT_IN = "not_in ";
    private static final String SQL_RULES = "USE_SQL_RULES ";
    private static final String DOT = ".";

    /**
     * 获取查询条件构造器QueryWrapper实例 通用查询条件已被封装完成
     *
     * @param searchObj    查询实体
     * @param parameterMap request.getParameterMap()
     * @return QueryWrapper实例
     */
    public static <T> QueryWrapper<T> initQueryWrapper(T searchObj, Map<String, String[]> parameterMap) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<T>();
        buildQueryWrapper(queryWrapper, searchObj, parameterMap);
        return queryWrapper;
    }
    /**
     * 组装Mybatis Plus 查询条件
     * <p>使用此方法 需要有如下几点注意:
     * <br>1.使用QueryWrapper 而非LambdaQueryWrapper;
     * <br>2.实例化QueryWrapper时不可将实体传入参数
     * <br>错误示例:如QueryWrapper<Demo> queryWrapper = new QueryWrapper<Demo>(demo);
     * <br>正确示例:QueryWrapper<Demo> queryWrapper = new QueryWrapper<Demo>();
     * <br>3.也可以不使用这个方法直接调用 {@link #initQueryWrapper}直接获取实例
     */
    public static void buildQueryWrapper(QueryWrapper<?> queryWrapper, Object searchObj, Map<String, String[]> parameterMap) {
        Class<?> aClass = searchObj.getClass();
        Field[] fields = aClass.getFields();;
        Class entityClass = searchObj.getClass();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            String columnName = getColumnName(field);
            String type = field.getType().getTypeName();
            try {
                if (judgedIsUselessField(name) || !PropertyUtils.isReadable(searchObj, name)) {
                    continue;
                }

                // 处理json类型字段的查询
                if (!parameterMap.isEmpty()) {
                    String jsonColumnName = MybatisUtil.propertyToColumn(name, entityClass);
                    Map<String, String[]> jsonParameterMap = parameterMap.entrySet().stream()
                            .filter(map -> map.getKey().startsWith(name + DOT))
                            .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
                    buildJsonQueryWrapper(queryWrapper, jsonParameterMap, jsonColumnName, name);

                    String beginKey = name + BEGIN;
                    if (parameterMap.containsKey(beginKey)) {
                        String beginValue = parameterMap.get(beginKey)[0].trim();
                        addQueryByRule(queryWrapper, columnName, type, beginValue, QueryRuleEnum.GE, entityClass);
                    }

                    String endKey = name + END;
                    if (parameterMap.containsKey(endKey)) {
                        String endValue = parameterMap.get(name + END)[0].trim();
                        addQueryByRule(queryWrapper, columnName, type, endValue, QueryRuleEnum.LE, entityClass);
                    }

                    // 添加判断是否有区间值
                    String rangeKey = name + RANGE;
                    if (parameterMap.containsKey(rangeKey)) {
                        String rangeValue = parameterMap.get(name + RANGE)[0].trim();
                        String[] params = rangeValue.split(",");
                        String beginParam = params[0];
                        String endParam = params[1];
                        addQueryByRule(queryWrapper, columnName, type, StrUtil.subPre(beginParam, beginParam.indexOf(BEGIN)), QueryRuleEnum.GE, entityClass);
                        addQueryByRule(queryWrapper, columnName, type, StrUtil.subPre(endParam, endParam.indexOf(END)), QueryRuleEnum.LE, entityClass);
                    }
                }

                //判断单值  参数带不同标识字符串 走不同的查询
                Object value = field.get(searchObj);
                if (null != value && value.toString().startsWith(COMMA) && value.toString().endsWith(COMMA)) {
                    String multiLikeval = value.toString().replace(",,", COMMA);
                    String[] vals = multiLikeval.substring(1, multiLikeval.length()).split(COMMA);
                    if (vals.length > 1) {
                        queryWrapper.and(j -> {
                            j = j.like(columnName, vals[0]);
                            for (int k = 1; k < vals.length; k++) {
                                j = j.or().like(columnName, vals[k]);
                            }
                        });
                    } else {
                        queryWrapper.and(j -> j.like(columnName, vals[0]));
                    }
                } else {
                    //根据参数值带什么关键字符串判断走什么类型的查询
                    QueryRuleEnum rule = convert2Rule(value);
                    value = replaceValue(rule, value);
                    addEasyQuery(queryWrapper, columnName, rule, value, searchObj.getClass());
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        doMultiFieldsOrder(queryWrapper, parameterMap, searchObj.getClass());
    }
    public static String getColumnName(Field field) {
        String columnName = StrUtil.toUnderlineCase(field.getName());
        if (field.getAnnotation(TableField.class) != null) {
            columnName = field.getAnnotation(TableField.class).value();
        }
        return columnName;
    }
    private static boolean judgedIsUselessField(String name) {
        return "class".equals(name) || "ids".equals(name)
                || "page".equals(name) || "rows".equals(name)
                || "sort".equals(name) || "order".equals(name)
                || "version".equals(name);
    }

    private static void buildJsonQueryWrapper(QueryWrapper<?> queryWrapper, Map<String, String[]> jsonParameterMap, String jsonColumnName, String name) {
        jsonParameterMap.forEach((key, value) -> {
            String jsonKey = StrUtil.replace(key, name, "$");
            String initValue = value[0];
            if (initValue.startsWith(STAR) || initValue.endsWith(STAR)) {
                String jsonColumn = jsonColumnName + " ->> '" + jsonKey + "'";
                String queryParam = StrUtil.replace(initValue, "*", "").trim();
                if (initValue.startsWith(STAR) && initValue.endsWith(STAR)) {
                    queryWrapper.like(jsonColumn, queryParam);
                } else if (initValue.startsWith(STAR) && !initValue.endsWith(STAR)) {
                    queryWrapper.likeLeft(jsonColumn, queryParam);
                } else {
                    queryWrapper.likeRight(jsonColumn, queryParam);
                }
            } else if (initValue.contains(BEGIN) && initValue.contains(END)) {
                String relJsonKey = StrUtil.replace(jsonKey, RANGE, "");
                String jsonColumn = jsonColumnName + " ->> '" + relJsonKey + "'";
                String[] params = StrUtil.splitToArray(initValue, COMMA);
                String beginParam = StrUtil.replace(params[0], BEGIN, "").trim();
                String endParam = StrUtil.replace(params[1], END, "").trim();
                if (isDateStr(endParam)) {
                    queryWrapper.le(jsonColumn, TimeUtil.getEndOfDay(endParam))
                            .ge(jsonColumn, TimeUtil.getBeginOfDay(beginParam));
                } else {
                    queryWrapper.le(jsonColumn, endParam)
                            .ge(jsonColumn, beginParam);
                }
            } else if (initValue.contains(NOT_EQUAL)) {
                String jsonColumn = jsonColumnName + " ->> '" + jsonKey + "'";
                String queryParam = StrUtil.replace(initValue, NOT_EQUAL, "").trim();
                queryWrapper.ne(jsonColumn, queryParam);
            } else if (initValue.contains(QueryRuleEnum.GE.getValue())) {
                String jsonColumn = jsonColumnName + " ->> '" + jsonKey + "'";
                String queryParam = StrUtil.replace(initValue, QueryRuleEnum.GE.getValue(), "").trim();
                if (isDateStr(queryParam)) {
                    queryWrapper.ge(jsonColumn, TimeUtil.getBeginOfDay(queryParam));
                } else {
                    queryWrapper.ge(jsonColumn, queryParam);
                }
            } else if (initValue.contains(QueryRuleEnum.LE.getValue())) {
                String jsonColumn = jsonColumnName + " ->> '" + jsonKey + "'";
                String queryParam = StrUtil.replace(initValue, QueryRuleEnum.LE.getValue(), "").trim();
                if (isDateStr(queryParam)) {
                    queryWrapper.le(jsonColumn, TimeUtil.getEndOfDay(queryParam));
                } else {
                    queryWrapper.le(jsonColumn, queryParam);
                }
            } else if (initValue.startsWith(IN)) {
                String jsonColumn = jsonColumnName + " ->> '" + jsonKey + "'";
                String queryValue = StrUtil.replace(initValue, IN, "").trim();
                List<String> list = StrUtil.split(queryValue, ",");
                queryWrapper.in(jsonColumn, list);
            } else {
                String jsonColumn = jsonColumnName + " ->> '" + jsonKey + "'";
                queryWrapper.eq(jsonColumn, initValue);
            }
        });
    }
    public static void addQueryByRule(QueryWrapper<?> queryWrapper, String name, String type, String value, QueryRuleEnum rule, Class entityClass) throws ParseException {
        if (StringUtils.isNotBlank(value)) {
            Object temp;
            switch (type) {
                case "class java.lang.Integer":
                case "java.lang.Integer":
                    temp = Integer.parseInt(value);
                    break;
                case "class java.math.BigDecimal":
                case "java.math.BigDecimal":
                    temp = new BigDecimal(value);
                    break;
                case "class java.lang.Short":
                case "java.lang.Short":
                    temp = Short.parseShort(value);
                    break;
                case "class java.lang.Long":
                case "java.lang.Long":
                    temp = Long.parseLong(value);
                    break;
                case "class java.lang.Float":
                case "java.lang.Float":
                    temp = Float.parseFloat(value);
                    break;
                case "class java.lang.Double":
                case "java.lang.Double":
                    temp = Double.parseDouble(value);
                    break;
                case "class java.util.Date":
                case "java.util.Date":
                    temp = getDateQueryByRule(value, rule);
                    break;
                default:
                    temp = value;
                    break;
            }
            addEasyQuery(queryWrapper, name, rule, temp, entityClass);
        }
    }
    /**
     * 获取日期类型的值
     *
     * @param value
     * @param rule
     * @return
     * @throws ParseException
     */
    public static Date getDateQueryByRule(String value, QueryRuleEnum rule) throws ParseException {
        Date date = null;
        if (value.length() == 10) {
            if (rule == QueryRuleEnum.GE) {
                //比较大于
                date = TimeUtil.formatToDate(TimeUtil.getBeginOfDay(value));
            } else if (rule == QueryRuleEnum.LE) {
                //比较小于
                date = TimeUtil.formatToDate(TimeUtil.getEndOfDay(value));
            }
            //TODO 日期类型比较特殊 可能oracle下不一定好使
        }
        if (date == null) {
            date = TimeUtil.localDateToDate(TimeUtil.formatDateTime(value));
        }
        return date;
    }
    /**
     * 根据所传的值 转化成对应的比较方式
     * 支持><= like in !
     *
     * @param value
     * @return
     */
    public static QueryRuleEnum convert2Rule(Object value) {
        // 避免空数据
        if (value == null) {
            return null;
        }
        String val = (value + "").toString().trim();
        if (val.length() == 0) {
            return null;
        }
        QueryRuleEnum rule = null;

        // step 2 .>= =<
        if (rule == null && val.length() >= 3) {
            if (QUERY_SEPARATE_KEYWORD.equals(val.substring(2, 3))) {
                rule = QueryRuleEnum.getByValue(val.substring(0, 2));
            }
        }
        // step 1 .> <
        if (rule == null && val.length() >= 2) {
            if (QUERY_SEPARATE_KEYWORD.equals(val.substring(1, 2))) {
                rule = QueryRuleEnum.getByValue(val.substring(0, 1));
            }
        }

        // step 3 like
        if (rule == null && val.contains(STAR)) {
            if (val.startsWith(STAR) && val.endsWith(STAR)) {
                rule = QueryRuleEnum.LIKE;
            } else if (val.startsWith(STAR)) {
                rule = QueryRuleEnum.LEFT_LIKE;
            } else if (val.endsWith(STAR)) {
                rule = QueryRuleEnum.RIGHT_LIKE;
            }
        }
        if (rule == null && val.startsWith(IN)) {
            rule = QueryRuleEnum.IN;
        }
        if (rule == null && val.startsWith(NOT_IN)) {
            rule = QueryRuleEnum.NOT_IN;
        }
        // step 5 !=
        if (rule == null && val.startsWith(NOT_EQUAL)) {
            rule = QueryRuleEnum.NE;
        }
        return rule != null ? rule : QueryRuleEnum.EQ;
    }
    /**
     * 替换掉关键字字符
     *
     * @param rule
     * @param value
     * @return
     */
    public static Object replaceValue(QueryRuleEnum rule, Object value) {
        if (rule == null) {
            return null;
        }
        if (!(value instanceof String)) {
            return value;
        }
        String val = (value + "").trim();
        if (rule == QueryRuleEnum.LIKE) {
            value = val.substring(1, val.length() - 1);
        } else if (rule == QueryRuleEnum.LEFT_LIKE || rule == QueryRuleEnum.NE) {
            value = val.substring(1);
        } else if (rule == QueryRuleEnum.RIGHT_LIKE) {
            value = val.substring(0, val.length() - 1);
        } else if (rule == QueryRuleEnum.IN) {
            value = val.substring(3).split(",");
        } else if (rule == QueryRuleEnum.NOT_IN) {
            value = val.substring(7).split(",");
        } else {
            if (val.startsWith(rule.getValue())) {
                //TODO 此处逻辑应该注释掉-> 如果查询内容中带有查询匹配规则符号，就会被截取的（比如：>=您好）
                value = val.replaceFirst(rule.getValue(), "");
            } else if (val.startsWith(rule.getCondition() + QUERY_SEPARATE_KEYWORD)) {
                value = val.replaceFirst(rule.getCondition() + QUERY_SEPARATE_KEYWORD, "").trim();
            }
        }
        return value;
    }

    /**
     * 根据规则走不同的查询
     *
     * @param queryWrapper QueryWrapper
     * @param name         字段名字
     * @param rule         查询规则
     * @param value        查询条件值
     */
    public static void addEasyQuery(QueryWrapper<?> queryWrapper, String name, QueryRuleEnum rule, Object value, Class entityClass) {
        if (value == null || rule == null || StrUtil.isEmpty(value.toString())) {
            return;
        }
        name = MybatisUtil.propertyToColumn(name, entityClass);
        log.debug("--查询规则-->" + name + " " + rule.getValue() + " " + value);
        switch (rule) {
            case GT:
                queryWrapper.gt(name, value);
                break;
            case GE:
                queryWrapper.ge(name, value);
                break;
            case LT:
                queryWrapper.lt(name, value);
                break;
            case LE:
                queryWrapper.le(name, value);
                break;
            case EQ:
                queryWrapper.eq(name, value);
                break;
            case NE:
                queryWrapper.ne(name, value);
                break;
            case IN:
                if (value instanceof String) {
                    queryWrapper.in(name, (Object[]) value.toString().split(","));
                } else if (value instanceof String[]) {
                    queryWrapper.in(name, (Object[]) value);
                } else {
                    queryWrapper.in(name, value);
                }
                break;
            case NOT_IN:
                if (value instanceof String) {
                    queryWrapper.notIn(name, (Object[]) value.toString().split(","));
                } else if (value instanceof String[]) {
                    queryWrapper.notIn(name, (Object[]) value);
                } else {
                    queryWrapper.notIn(name, value);
                }
                break;
            case LIKE:
                queryWrapper.like(name, value);
                break;
            case LEFT_LIKE:
                queryWrapper.likeLeft(name, value);
                break;
            case RIGHT_LIKE:
                queryWrapper.likeRight(name, value);
                break;
            default:
                log.info("--查询规则未匹配到---");
                break;
        }
    }

    /**
     * 多字段排序
     */
    public static void doMultiFieldsOrder(QueryWrapper<?> queryWrapper, Map<String, String[]> parameterMap, Class entityClass) {
        String[] columns = null;
        String[] properties = null;
        String order = null;
        if (parameterMap != null && parameterMap.containsKey(ORDER_COLUMN)) {
            properties = parameterMap.get(ORDER_COLUMN);
            if (ArrayUtil.isNotEmpty(properties)) {
                columns = Stream.of(properties).map(property -> {
                    if (property.contains(DOT)) {
                        String subPre = StrUtil.subBefore(property, ".", false);
                        String columnName = MybatisUtil.propertyToColumn(subPre, entityClass);
                        String subAfter = StrUtil.subAfter(property, ".", false);
                        return "JSON_EXTRACT(" + columnName + ",\"$." + subAfter + "\")";
                    } else {
                        return MybatisUtil.propertyToColumn(property, entityClass);
                    }
                }).toArray(String[]::new);
            }
        }
        if (parameterMap != null && parameterMap.containsKey(ORDER_TYPE)) {
            order = parameterMap.get(ORDER_TYPE)[0];
        }

        log.debug("排序规则>>列:" + columns + ",排序方式:" + order);
        if (ArrayUtil.isNotEmpty(columns) && StrUtil.isNotEmpty(order)) {
            //SQL注入check
            SqlInjectionUtil.filterContent(columns);
            if (order.toUpperCase().contains(ORDER_TYPE_ASC)) {
                queryWrapper.orderByAsc(Arrays.asList(columns));
            } else {
                queryWrapper.orderByDesc(Arrays.asList(columns));
            }
        }
    }

    private static boolean isDateStr(String str) {
        // yyyy-MM-dd 已考虑平年闰年
        String regex = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$";
        return str.matches(regex);
    }

}
