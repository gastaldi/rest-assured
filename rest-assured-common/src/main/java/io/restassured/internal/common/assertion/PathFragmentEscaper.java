package io.restassured.internal.common.assertion;

/**
 * Escapes a path fragment if required
 */
public interface PathFragmentEscaper {
    /**
     * @param pathFragment The path fragment to escape
     * @return <code>true</code> if the path fragment should be escaped, <code>false</code> otherwise
     */
    boolean shouldEscape(String pathFragment);

    /**
     * @param pathFragment The path fragment to escape
     * @return The escaped path fragment
     */
    String escape(String pathFragment);
}
