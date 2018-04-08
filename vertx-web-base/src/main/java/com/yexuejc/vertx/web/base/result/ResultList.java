package com.yexuejc.vertx.web.base.result;

import io.vertx.core.json.Json;

/**
 * response响应集合消息
 *
 * @ClassName: ResultList
 * @Description:
 * @author: maxf
 * @date: 2018/4/8 14:23
 */
public class ResultList<T> extends ResultOb {
    private int total;     //共多少条
    private int count;     //每页多少条
    private int pagetotal;     //共多少页
    private boolean hasNextPage; //有下一页
    private boolean hasPrePage; //有上一页
    private int nopage;  //第几页

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPagetotal() {
        return pagetotal;
    }

    public void setPagetotal(int pagetotal) {
        this.pagetotal = pagetotal;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public boolean isHasPrePage() {
        return hasPrePage;
    }

    public void setHasPrePage(boolean hasPrePage) {
        this.hasPrePage = hasPrePage;
    }

    public int getNopage() {
        return nopage;
    }

    public void setNopage(int nopage) {
        this.nopage = nopage;
    }

    public ResultList(Paginator p) {
        total = p.getTotalCount();     //共多少条
        count = p.getLimit();     //每页多少条
        pagetotal = p.getTotalPages();     //共多少页
        nopage = p.getPage();  //第几页
        hasNextPage = p.isHasNextPage(); //有下一页
        hasPrePage = p.isHasPrePage(); //有上一页
    }

    @Override
    public String toString() {
        return Json.encode(this);
    }
}
