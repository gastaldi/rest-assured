package io.restassured.internal.common.assertion;

public interface Assertion {
    /**
     * @param actual The actual value
     * @param expected The expected value
     * @param <T> The type of the actual and expected value
     * @return <code>true</code> if the assertion is satisfied, <code>false</code> otherwise
     */
    Object getResult(Object object, Object config);

    /**
     * @return The description of the assertion
     */
    String description();
}
