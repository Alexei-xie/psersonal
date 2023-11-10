package com.itself.test.meituan;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @Author duJi
 * @Date 2023/11/10
 */
public class TagDTO implements Serializable {
    private Integer tagId;
    private String tagName;
    //情感值 1=正向，0=中性，-1=负向
    private Integer affection;
    //标签关联数量
    private Integer relationAmount;

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Integer getAffection() {
        return affection;
    }

    public void setAffection(Integer affection) {
        this.affection = affection;
    }

    public Integer getRelationAmount() {
        return relationAmount;
    }

    public void setRelationAmount(Integer relationAmount) {
        this.relationAmount = relationAmount;
    }
}

class TestService{
    /**
     * 目标：输入一个标签列表，长度大于10，进行以下操作
     * 1.按照标签情感值倒序排列，优先展示正向标签，最后展示负向标签，同一情感登记的标签按关联数量进行倒序排序
     * 2.如果前六个标签词均为正向标签，则将排序最靠前的负向标签提权到前六位展示，此时前六位按照关联数量排序，不再按情感值排序，原本第6位及之后的词顺延至下一位展示
     * 3.返回排序后标签id（tagId）为1001和1002对应的次序加和（假设输入的标签列表一定会包含着两个标签id）
     * @param tagList
     * @return
     */
    public int tagOrder(List<TagDTO> tagList){
        // 按照标签情感值和关联数量排序
        Collections.sort(tagList, new TagComparator());

        // 判断前六个标签是否为正向标签
        boolean allForwardFlag = true;
        for (int i = 0; i < Math.min(tagList.size(), 6); i++) {
            if (tagList.get(i).getAffection() < 1) { //非1
                allForwardFlag = false;
                break;
            }
        }
        // 如果前六个标签均为正向标签，则提权最靠前的负向标签
        if (allForwardFlag) {
            int negativeIndex = -1;
            //从第7位开始
            for (int i = 6; i < tagList.size(); i++) {
                if (tagList.get(i).getAffection() < 0) { //取-1
                    negativeIndex = i;
                    break;
                }
            }
            if (negativeIndex >= 0) { //说明找到第六位后的第一个负标签的索引位置
                // 利用swap方法替换两个索引位置的数据，将负向标签提前到第六位
                Collections.swap(tagList, negativeIndex, 5);
            }
        }
        // 计算标签id为1001和1002对应的次序加和
        int sum = 0;
        for (int i = 0; i < tagList.size(); i++) {
            TagDTO tag = tagList.get(i);
            if (tag.getTagId() == 1001 || tag.getTagId() == 1002) {//tagId为主键就不需要多余考虑
                sum += (i + 1);
            }
        }
        return sum;
    }
    /**
     * 标签比较器，用于排序
     */
    private static class TagComparator implements Comparator<TagDTO> {
        @Override
        public int compare(TagDTO tag1, TagDTO tag2) {
            // 情感值倒序排列
            int affectionComparison = tag2.getAffection().compareTo(tag1.getAffection());
            if (affectionComparison != 0) { //情感值不相等
                return affectionComparison;
            }
            // 情感值相等按照 登记的标签按关联数量倒序排序
            return tag2.getRelationAmount().compareTo(tag1.getRelationAmount());
        }
    }
}



