package com.itself.mapstruct.convert;

import com.itself.mapstruct.bean.BeanDto;
import com.itself.mapstruct.bean.BeanPo;
import java.util.List;
import java.util.stream.Collectors;
import org.assertj.core.util.Lists;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.mockito.internal.util.collections.Sets;

/**
 * @Author: duJi
 * @Date: 2024-01-19
 **/
@Mapper
public interface BeanConvert{
    BeanConvert INSTANCE = Mappers.getMapper( BeanConvert.class );

    BeanDto poToDto(BeanPo po);
    List<BeanDto> poToDto(List<BeanPo> pos);


    @Mappings({
            @Mapping(source = "time", target = "newTime"),//不同的字段名称可以通过这种方式来进行自动映射
            @Mapping(source = "set", target = "set1"),//不同的字段名称可以通过这种方式来进行自动映射
            @Mapping(target = "age",qualifiedByName = "intToString"),//利用自定义方法来对不同类型的字段进行转换映射
            @Mapping(source = "list", target = "list1",qualifiedByName = "intToStringOnList")//利用自定义方法来对不同类型的字段集合进行转换映射
    })
    BeanPo dtoToPo(BeanDto dto);

    @Named("intToString")
    static String  intToString(Integer num){
        return String.valueOf(num);
    }
    @Named("intToStringOnList")
    static List<String> intToString(List<Integer> nums){
        return nums.stream().map(String::valueOf).collect(Collectors.toList());
    }


    static void main(String[] args) {
        BeanDto dto = new BeanDto().setName("dto").setAge(1).setTime("2022").setList(Lists.newArrayList(1,2)).setSet(Sets.newSet("2"));
        BeanPo beanPo = BeanConvert.INSTANCE.dtoToPo(dto);
        System.out.println(beanPo);
    }

}
