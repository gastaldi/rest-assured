package io.restassured.internal.common.assertion;

/**
 * A {@link PathFragmentEscaper} that escapes the path fragment with <code>getAt('<fragment>')</code>
 */
public abstract class GetAtPathFragmentEscaper implements PathFragmentEscaper {
  @Override
  public String escape(String pathFragment) {
    return ((String) ("getAt('" + pathFragment + "')"));
  }

}
