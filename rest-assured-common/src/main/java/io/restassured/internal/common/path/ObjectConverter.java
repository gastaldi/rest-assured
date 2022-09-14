package io.restassured.internal.common.path;

public class ObjectConverter {
    public static <T> T convertObjectTo(Object object, Class<T> explicitType) {
        Object returnObject;
        if (object == null) {
            returnObject = null;
        } else if (!object.getClass().isAssignableFrom(explicitType)) {
            final String toString = object.toString();
            if (explicitType == Integer.class || explicitType == int.class) {
                returnObject = Integer.parseInt(toString);
            } else if (explicitType.isAssignableFrom(Boolean.class) || explicitType.isAssignableFrom(boolean.class)) {
                returnObject = Boolean.parseBoolean(toString);
            } else if (explicitType.isAssignableFrom(Character.class) || explicitType.isAssignableFrom(char.class)) {
                returnObject = toString.charAt(0);
            } else if (explicitType.isAssignableFrom(Byte.class) || explicitType.isAssignableFrom(byte.class)) {
                returnObject = Byte.parseByte(toString);
            } else if (explicitType.isAssignableFrom(Short.class) || explicitType.isAssignableFrom(short.class)){
                returnObject = Short.parseShort(toString);
            } else if (explicitType.isAssignableFrom(Float.class) || explicitType.isAssignableFrom(float.class)) {
                returnObject = Float.parseFloat(toString);
            } else if (explicitType.isAssignableFrom(Double.class) || explicitType.isAssignableFrom(double.class)) {
                returnObject = Double.parseDouble(toString);
            } else if (explicitType.isAssignableFrom(Long.class) || explicitType.isAssignableFrom(long.class)) {
                returnObject = Long.parseLong(toString);
            } else if (explicitType.isAssignableFrom(java.math.BigDecimal.class)) {
                returnObject = new java.math.BigDecimal(toString);
            } else if (explicitType.isAssignableFrom(String.class)) {
                returnObject = toString;
            } else if (explicitType.isAssignableFrom(java.util.UUID.class)) {
                returnObject = java.util.UUID.fromString(toString);
            } else {
                try {
                    returnObject = explicitType.cast(object);
                } catch (ClassCastException e) {
                    throw new ClassCastException(
                            "Cannot convert " + object.getClass() + " to " + String.valueOf(explicitType) + ".");
                }

            }

        } else {
            returnObject = explicitType.cast(object);
        }

        return (T) returnObject;
    }

    public static boolean canConvert(Object object, Class type) {
        try {
            convertObjectTo(object, type);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

}
