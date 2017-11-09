package com.cerner.beadledom.pagination.parameters;

import javax.ws.rs.QueryParam;

/**
 * Query parameters for an endpoint with offset based pagination.
 *
 * <p>This class can be added with the @BeanParam annotation to add the query parameters needed
 * to support offset based pagination. Allows for injection of default values for limit and offset.
 *
 * @author Will Pruyn
 * @since 2.7
 */
public class PaginationParameters {
  @QueryParam("limit")
  private Integer limit;

  @QueryParam("offset")
  private Long offset;

  public Integer getLimit() {
    return limit != null ? limit : LimitParameter.DEFAULT_LIMIT;
  }

  public Long getOffset() {
    return offset != null ? offset : OffsetParameter.DEFAULT_OFFSET;
  }
}