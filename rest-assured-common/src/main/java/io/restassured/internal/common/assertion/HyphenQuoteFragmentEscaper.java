package io.restassured.internal.common.assertion;

/**
 * A {@link PathFragmentEscaper} that is specific to path fragments that consists of one or more hyphens
 */
public abstract class HyphenQuoteFragmentEscaper implements PathFragmentEscaper {
    private static final String INDEX_START_CHAR = "[";
    private static final String INDEX_END_CHAR = "]";

    @Override
    public String escape(String pathFragment) {
        int indexOfStart;
        // Check if this path fragment contains reads an index from a collection (for example some-list[0])
        // If this is the case we should escape "some-list" but leave the index lookup ([0]) outside, i.e. 'some-list'[0]
        if (pathFragment.trim().endsWith(INDEX_END_CHAR) && (indexOfStart = pathFragment.indexOf(INDEX_START_CHAR)) > 1
                && pathFragment.indexOf(INDEX_END_CHAR) > indexOfStart) {

            String toEscape = substringBeforeLast(pathFragment, INDEX_START_CHAR);
            String indexLookup = INDEX_START_CHAR + substringAfterLast(pathFragment, INDEX_START_CHAR);
            return doEscape(toEscape) + indexLookup;
        } else {
            return doEscape(pathFragment);
        }
    }

    private String doEscape(String str) {
        return "'" + str + "'";
    }

    public static String substringBeforeLast(final String str, final String separator) {
        if (str.isEmpty() || separator.isEmpty()) {
            return str;
        }
        final int pos = str.lastIndexOf(separator);
        if (pos == -1) {
            return str;
        }
        return str.substring(0, pos);
    }

    public static String substringAfterLast(final String str, final String separator) {
        if (str.isEmpty()) {
            return str;
        }
        if (separator.isEmpty()) {
            return "" ;
        }
        final int pos = str.lastIndexOf(separator);
        if (pos == -1 || pos == str.length() - separator.length()) {
            return "";
        }
        return str.substring(pos + separator.length());
    }
}
