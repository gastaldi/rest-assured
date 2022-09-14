package io.restassured.internal.common.assertion;

public class AssertionSupport {
    private static final char CLOSURE_START_FRAGMENT = '{';
    private static final char CLOSURE_END_FRAGMENT = '}';
    private static final char LIST_GETTER_FRAGMENT = '(';
    private static final char LIST_INDEX_START_FRAGMENT = '[';
    private static final char LIST_INDEX_END_FRAGMENT = ']';
    private static final char SPACE = ' ';

    public static String escapePath(String key, PathFragmentEscaper... pathFragmentEscapers) {
        String[] pathFragments = key.split("(?<=\\')");
        for (int i = 0; i < pathFragments.length; i++) {
            String pathFragment = pathFragments[i];
            if (!pathFragment.endsWith("'") || pathFragment.contains("**")) {
                String[] dotFragments = pathFragment.split("\\.");
                for (int j = 0; j < dotFragments.length; j++) {
                    for (PathFragmentEscaper escaper : pathFragmentEscapers) {
                        if (escaper.shouldEscape(dotFragments[j])) {
                            dotFragments[j] = escaper.escape(dotFragments[j]);
                            break;
                        }
                    }
                }
                pathFragments[i] = String.join(".", dotFragments);
            }
        }
        return String.join("", pathFragments);
    }

    public static HyphenQuoteFragmentEscaper hyphen() {
        return new HyphenQuoteFragmentEscaper() {
            @Override
            public boolean shouldEscape(String pathFragment) {
                return !pathFragment.startsWith("'") && !pathFragment.endsWith("'") && pathFragment.contains("-")
                        && !containsAny(pathFragment, CLOSURE_START_FRAGMENT, CLOSURE_END_FRAGMENT, LIST_GETTER_FRAGMENT);
            }

        };
    }

    public static GetAtPathFragmentEscaper properties() {
        return new GetAtPathFragmentEscaper() {
            @Override
            public boolean shouldEscape(String pathFragment) {
                return !pathFragment.startsWith("'") && !pathFragment.endsWith("'") && pathFragment.contains("properties")
                        && !containsAny(pathFragment, CLOSURE_START_FRAGMENT, CLOSURE_END_FRAGMENT, LIST_GETTER_FRAGMENT,
                                LIST_INDEX_START_FRAGMENT, SPACE, LIST_INDEX_END_FRAGMENT);
            }

        };
    }

    public static GetAtPathFragmentEscaper classKeyword() {
        return new GetAtPathFragmentEscaper() {
            @Override
            public boolean shouldEscape(String pathFragment) {
                return !pathFragment.startsWith("'") && !pathFragment.endsWith("'") && pathFragment.contains("class")
                        && !containsAny(pathFragment, CLOSURE_START_FRAGMENT, CLOSURE_END_FRAGMENT, LIST_GETTER_FRAGMENT,
                                LIST_INDEX_START_FRAGMENT, SPACE, LIST_INDEX_END_FRAGMENT);
            }

        };
    }

    public static EndToEndQuoteFragmentEscaper attributeGetter() {
        return new EndToEndQuoteFragmentEscaper() {
            @Override public boolean shouldEscape(String pathFragment) {
                return pathFragment.startsWith("@") && !pathFragment.endsWith("'") && !containsAny(pathFragment,CLOSURE_START_FRAGMENT, CLOSURE_END_FRAGMENT, SPACE);
            }

        };
    }

    public static EndToEndQuoteFragmentEscaper doubleStar() {
        return new EndToEndQuoteFragmentEscaper() {
            @Override public boolean shouldEscape(String pathFragment) {
                return pathFragment.equals("**");
            }

        };
    }

    public static HyphenQuoteFragmentEscaper colon() {
        return new HyphenQuoteFragmentEscaper() {
            @Override public boolean shouldEscape(String pathFragment) {
                return !pathFragment.startsWith("'") && !pathFragment.endsWith("'") && pathFragment.contains(":")
                        && !containsAny(pathFragment, CLOSURE_START_FRAGMENT, CLOSURE_END_FRAGMENT, LIST_GETTER_FRAGMENT);
            }

        };
    }

    public static EndToEndQuoteFragmentEscaper integer() {
        return new EndToEndQuoteFragmentEscaper() {
            @Override public boolean shouldEscape(String pathFragment) {
                return (startsWithDigit(pathFragment) || isNumeric(pathFragment)) && !containsAny(pathFragment,CLOSURE_START_FRAGMENT, CLOSURE_END_FRAGMENT, SPACE,
                                        LIST_GETTER_FRAGMENT,
                                        LIST_INDEX_START_FRAGMENT, LIST_INDEX_END_FRAGMENT);
            }

        };
    }

    private static boolean startsWithDigit(String pathFragment) {
        if (pathFragment.isEmpty()) {
            return false;
        }
        return Character.isDigit(pathFragment.charAt(0));
    }

    public static boolean isNumeric(final CharSequence cs) {
        if (cs.isEmpty()) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String generateWhitespace(int number) {
        if (number < 1) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < number; i++) {
            builder.append(" ");
        }

        return builder.toString();
    }

    /**
     * Copied from Apache commons lang (String utils)
     */
    public static boolean containsAny(final CharSequence cs, final char... searchChars) {
        if (cs.isEmpty() || searchChars.length == 0) {
            return false;
        }
        final int csLength = cs.length();
        final int searchLength = searchChars.length;
        final int csLast = csLength - 1;
        final int searchLast = searchLength - 1;
        for (int i = 0; i < csLength; i++) {
            final char ch = cs.charAt(i);
            for (int j = 0; j < searchLength; j++) {
                if (searchChars[j] == ch) {
                    if (Character.isHighSurrogate(ch)) {
                        if (j == searchLast) {
                            // missing low surrogate, fine, like String.indexOf(String)
                            return true;
                        }
                        if (i < csLast && searchChars[j + 1] == cs.charAt(i + 1)) {
                            return true;
                        }
                    } else {
                        // ch is in the Basic Multilingual Plane
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
