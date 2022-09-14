package io.restassured.internal.common.assertion;

/**
 * A {@link PathFragmentEscaper} that escapes the path fragment with quotes
 */
public abstract class EndToEndQuoteFragmentEscaper implements PathFragmentEscaper {
  @Override
  public String escape(String pathFragment) {
    return "'" + pathFragment + "'";
  }

}
