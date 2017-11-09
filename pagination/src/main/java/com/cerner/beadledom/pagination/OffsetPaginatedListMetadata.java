package com.cerner.beadledom.pagination;

import com.google.auto.value.AutoValue;

import javax.annotation.Nullable;

/**
 * Metadata for an offset based pagination list.
 *
 * @author Will Pruyn
 * @since 2.7
 */
@AutoValue
public abstract class OffsetPaginatedListMetadata {
  /**
   * Returns true if there are additional items on another page; false if not; may be null if
   * {@code totalResults} is set instead. This value will be derived if not provided when
   * {@code totalResults}, {@code offset}, and {@code limit} are set.
   */
  @Nullable
  public abstract Boolean hasMore();

  /**
   * Returns the total number of items across all pages; null if total is unknown.
   */
  @Nullable
  public abstract Long totalResults();

  /**
   * Returns the page offset that was used to create the current page; null if not set.
   */
  @Nullable
  public abstract Long offset();

  /**
   * Returns the name of the page offset field that was used to create the current page;
   * null if not set.
   */
  @Nullable
  public abstract String offsetFieldName();

  /**
   * Returns the page limit that was used to create the current page; null if not set.
   */
  @Nullable
  public abstract Integer limit();

  /**
   * Returns the name of the page limit field that was used to create the current page;
   * null if not set.
   */
  @Nullable
  public abstract String limitFieldName();

  /**
   * Creates a builder for {@link OffsetPaginatedListMetadata}.
   *
   * @return instance of {@link OffsetPaginatedListMetadata.Builder}
   */
  public static OffsetPaginatedListMetadata.Builder builder() {
    return new AutoValue_OffsetPaginatedListMetadata.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract OffsetPaginatedListMetadata.Builder hasMore(Boolean hasMore);

    public abstract OffsetPaginatedListMetadata.Builder totalResults(Long totalResults);

    public abstract OffsetPaginatedListMetadata.Builder offset(Long offset);

    public abstract OffsetPaginatedListMetadata.Builder offsetFieldName(String offsetFieldName);

    public abstract OffsetPaginatedListMetadata.Builder limit(Integer limit);

    public abstract OffsetPaginatedListMetadata.Builder limitFieldName(String limitFieldName);

    @Nullable
    abstract Boolean hasMore();

    @Nullable
    abstract Long totalResults();

    @Nullable
    abstract Long offset();

    abstract String offsetFieldName();

    @Nullable
    abstract Integer limit();

    abstract String limitFieldName();

    abstract OffsetPaginatedListMetadata autoBuild();

    public OffsetPaginatedListMetadata build() {
      if (limit() != null && offset() != null && totalResults() != null && hasMore() == null) {
        hasMore(totalResults() > limit() + offset());
      }

      if(offsetFieldName() == null) {
        offsetFieldName("offset");
      }

      if(limitFieldName() == null) {
        limitFieldName("limit");
      }
      return autoBuild();
    }
  }
}
