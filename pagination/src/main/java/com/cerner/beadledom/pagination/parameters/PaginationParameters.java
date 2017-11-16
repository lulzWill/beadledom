package com.cerner.beadledom.pagination.parameters;

import javax.validation.Valid;
import javax.ws.rs.QueryParam;

/**
 * Query parameters for an endpoint with offset based pagination.
 *
 * <p>This class can be added with the @BeanParam annotation to add the query parameters needed
 * to support offset based pagination. Allows for injection of default values for limit and offset.
 * *NOTE* This will not work with configured field names.
 *
 * @author Will Pruyn
 * @since 2.7
 */
public class PaginationParameters {
  @Valid
  @QueryParam("limit")
  private LimitParameter limit;

  @Valid
  @QueryParam("offset")
  private OffsetParameter offset;

  public Integer getLimit() {
    return limit != null ? limit.getValue() : LimitParameter.getDefaultLimit();
  }

  public Long getOffset() {
    return offset != null ? offset.getValue() : OffsetParameter.getDefaultOffset();
  }
}