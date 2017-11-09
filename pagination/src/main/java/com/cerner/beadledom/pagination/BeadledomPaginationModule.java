package com.cerner.beadledom.pagination;

import com.cerner.beadledom.pagination.parameters.LimitParameter;
import com.cerner.beadledom.pagination.parameters.OffsetParameter;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import javax.inject.Singleton;

/**
 * @author Will Pruyn
 * @since 2.7
 */
public class BeadledomPaginationModule extends AbstractModule {
  private final Integer defaultLimit;
  private final Long defaultOffset;

  public BeadledomPaginationModule() {
    this.defaultLimit = 20;
    this.defaultOffset = 0L;
  }

  public BeadledomPaginationModule(Integer defaultLimit, Long defaultOffset) {
    this.defaultLimit = defaultLimit != null ? defaultLimit : 20;
    this.defaultOffset = defaultOffset != null ? defaultOffset : 0L;
  }

  @Override
  protected void configure() {
    requestStaticInjection(LimitParameter.class);
    requestStaticInjection(OffsetParameter.class);

    bind(OffsetPaginatedListLinksWriterInterceptor.class);
  }

  @Provides
  @Singleton
  @com.google.inject.name.Named("defaultOffset")
  Long provideDefaultOffset() {
    return defaultOffset;
  }

  @Provides
  @Singleton
  @com.google.inject.name.Named("defaultLimit")
  Integer provideDefaultLimit() {
    return defaultLimit;
  }
}
