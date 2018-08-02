package com.productsapp.domain.service.dto;

import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
public class PageDTO<T> {
  private final long total;
  private final List<T> content;
  private final int page;
  private final int pageSize;

  public <R> PageDTO(Page<R> page, Function<R, T> mapper){
    this.total = page.getTotalElements();
    this.content = page.getContent().stream().map(mapper).collect(Collectors.toList());
    this.page = page.getNumber();
    this.pageSize = page.getSize();
  }

  public long getTotal() {
    return total;
  }

  public List<T> getContent() {
    return Collections.unmodifiableList(content);
  }

  public int getPage() {
    return page;
  }

  public int getPageSize() {
    return pageSize;
  }

  public boolean hasContent(){
    return content != null && content.size() > 0;
  }
}
