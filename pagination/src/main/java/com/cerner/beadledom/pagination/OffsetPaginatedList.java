package com.cerner.beadledom.pagination;

import com.google.auto.value.AutoValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Offset based pagination list.
 *
 * @author Will Pruyn
 * @since 2.7
 */
@AutoValue
public abstract class OffsetPaginatedList<T> {
  /**
   * Returns a list of items for the current page.
   */
  public abstract List<T> items();

  /**
   * Returns the {@link OffsetPaginatedListMetadata} for the {@link OffsetPaginatedList}; null if
   * not provided.
   */
  public abstract OffsetPaginatedListMetadata metadata();

  /**
   * Creates a builder for {@link OffsetPaginatedList}.
   *
   * @return instance of {@link OffsetPaginatedList.Builder}
   */
  public static <T> OffsetPaginatedList.Builder<T> builder() {
    return new AutoValue_OffsetPaginatedList.Builder<T>()
        .items(new ArrayList<>()).metadata(null, "offset", null, "limit", null, null);
  }

  @AutoValue.Builder
  public abstract static class Builder<T> {

    public abstract OffsetPaginatedList.Builder<T> items(List<T> items);

    public abstract OffsetPaginatedList.Builder<T> metadata(OffsetPaginatedListMetadata metadata);

    /**
     * Convenience method that allows metadata to be set without having to manually build a
     * {@link OffsetPaginatedListMetadata} object.
     *
     * @return The {@link OffsetPaginatedList.Builder<T>} being used
     */
    public OffsetPaginatedList.Builder<T> metadata(
        Long offset, String offsetFieldName, Integer limit, String limitFieldName,
        Long totalResults, Boolean hasMore) {
      return metadata(OffsetPaginatedListMetadata.builder()
          .offset(offset)
          .offsetFieldName(offsetFieldName)
          .limit(limit)
          .limitFieldName(limitFieldName)
          .totalResults(totalResults)
          .hasMore(hasMore)
          .build()
      );
    }

    public abstract OffsetPaginatedList<T> build();
  }
}
