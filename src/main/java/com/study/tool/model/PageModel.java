package com.study.tool.model;

import java.util.List;

public class PageModel<T> {

  private Integer pageNum;
  private Integer pageSize;
  private Integer tatalPageNum;
  private Long totalNum;
  private List<T> data;

  public Integer getPageNum() {
    if(pageNum == null){
      pageNum = 1;
    }
    return pageNum;
  }

  public void setPageNum(Integer pageNum) {
    this.pageNum = pageNum;
  }

  public Integer getPageSize() {
    if(pageSize == null){
      pageSize = 20;
    }
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Integer getTatalPageNum() {
    return tatalPageNum;
  }

  public void setTatalPageNum(Integer tatalPageNum) {
    this.tatalPageNum = tatalPageNum;
  }

  public Long getTotalNum() {
    return totalNum;
  }

  public void setTotalNum(Long totalNum) {
    this.totalNum = totalNum;
  }

  public List<T> getData() {
    return data;
  }

  public void setData(List<T> data) {
    this.data = data;
  }
}
