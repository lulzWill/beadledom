package com.cerner.beadledom.pagination.parameters;

import com.cerner.beadledom.pagination.parameters.exceptions.InvalidParameterException;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Pattern;

/**
 * Represent the limit parameter used for pagination.
 *
 * <p>The validation rules needed for a limit parameter are contained here
 * so that the same rules may be applied consistently across all paged
 * resources.
 *
 * @author Will Pruyn
 * @since 2.7
 */
@ApiModel
public class LimitParameter extends AbstractParameter<Integer> {

  @ApiModelProperty(value = "Total number of items to return in the response.", dataType = "int",
      allowableValues = "range[0, 100]")
  @Pattern(regexp = "^[1-9][0-9]?$|^100$", message = "limit must be an integer between 1 and 100")
  private final String limit;

  @Inject
  @Named("defaultLimit")
  public static Integer DEFAULT_LIMIT;

  @Inject
  @Named("defaultLimitFieldName")
  public static String DEFAULT_LIMIT_FIELD_NAME;

  /**
   * Creates an instance of {@link LimitParameter}.
   *
   * @param param the limit value from a request
   */
  public LimitParameter(String param) {
    super(param, DEFAULT_LIMIT_FIELD_NAME);
    this.limit = param;
  }

  /**
   * Creates an instance of {@link LimitParameter} with a non-default field name.
   *
   * @param param the limit value from a request
   * @param paramFieldName the name of the limit field being
   */
  public LimitParameter(String param, String paramFieldName) {
    super(param, paramFieldName);
    this.limit = param;
  }

  @Override
  protected Integer parse(String param) throws InvalidParameterException {
    Integer limit;
    try {
      limit = Integer.parseInt(this.limit);
    } catch (NumberFormatException e) {
      throw InvalidParameterException.create(
          "Invalid type for '" + this.getParameterFieldName() + "': " + this.limit
              + " - int is required.");
    }

    if (limit < 0 || limit > 100) {
      throw InvalidParameterException.create(
          "Invalid value for '" + this.getParameterFieldName() + "': " + this.limit
              + "  - value between 0 and 100 is required.");
    }

    return limit;
  }
}
