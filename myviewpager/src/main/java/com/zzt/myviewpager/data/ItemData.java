package com.zzt.myviewpager.data;

/**
 * @author: zeting
 * @date: 2020/1/15
 * 展示的数据
 */
public class ItemData {
    String content;
    Long page;

    public ItemData(String content, Long page) {
        this.content = content;
        this.page = page;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }
}
