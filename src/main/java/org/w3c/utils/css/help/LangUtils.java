package org.w3c.utils.css.help;

import java.util.Collection;
import java.util.Map;

/**
 * Набор статичных утилитарных функций для расширения возможностей языка Java.
 *
 * Created by home on 06.01.15.
 */
public final class LangUtils
{
    private LangUtils()
    {
        throw new UnsupportedOperationException("Try to create static library class!");
    }

    /**
     * Тестирует на пустоту указанный объект.
     *
     * @param item строка, коллекция, карта, массив или любой другой объект.
     * @return true если объект равен null или пуст.
     *         Для массивов проверяются все его размерности и содержание.
     */
    public static boolean isNullable(Object item)
    {
        if ( item == null )
        {
            return true;
        }
        if (item instanceof Nullable)
        {
            return isNullable((Nullable)item);
        }
        if (item instanceof String)
        {
            return isNullable((String) item);
        }
        if (item instanceof Collection)
        {
            return isNullable((Collection)item);
        }
        if (item instanceof Map)
        {
            return isNullable((Map)item);
        }
        if (item instanceof Object[])
        {
            return isNullable((Object[])item);
        }
        if (item instanceof int[])
        {
            return isNullable((int [])item);
        }
        if (item instanceof short[])
        {
            return isNullable((short [])item);
        }
        if (item instanceof long[])
        {
            return isNullable((long [])item);
        }
        if (item instanceof byte[])
        {
            return isNullable((byte [])item);
        }
        if (item instanceof char[])
        {
            return isNullable((char [])item);
        }
        if (item instanceof boolean[])
        {
            return isNullable((boolean [])item);
        }
        if (item instanceof float[])
        {
            return isNullable((float [])item);
        }
        if (item instanceof double[])
        {
            return isNullable((double [])item);
        }
        return false;
    }

    public static <T extends Object> T getDefault(T item, T value)
    {
        return isNullable(item) ? item : value;
    }


    public static boolean getBool(Object value)
    {
        return (value instanceof Boolean) ? (Boolean)value : false;
    }

    public static int getInt(Object value)
    {
        return (value instanceof Number) ? ((Number) value).intValue() : 0;
    }

    public static double getReal(Object value)
    {
        return (value instanceof Number) ? ((Number) value).doubleValue() : 0;
    }


    private static boolean isNullable(String string)
    {
        return string.isEmpty();
    }
    
    private static boolean isNullable(Nullable item)
    {
        return item.isEmpty();
    }

    private static boolean isNullable(Collection collection)
    {
        return collection.isEmpty();
    }

    private static boolean isNullable(Map map)
    {
        return map.isEmpty();
    }

    /**
     * Тестирует массив на пустоту. Если массив многомерный, то
     * никакая из его размерностей не должна быть равна 0.
     *
     * @param array тестируемый массив
     * @return истину, если массив равен null или пуст
     */
    private static boolean isNullable(Object array[])
    {
        if ( (array != null) && (array.length > 0) )
        {
            for (Object item : array)
            {
                if ( !isNullable(item) ) return false;
            }
        }
        return true;
    }

    private static boolean isNullable(int array[])
    {
        return (array.length == 0);
    }

    private static boolean isNullable(double array[])
    {
        return (array.length == 0);
    }

    private static boolean isNullable(float array[])
    {
        return (array.length == 0);
    }

    private static boolean isNullable(boolean array[])
    {
        return (array.length == 0);
    }

    private static boolean isNullable(byte array[])
    {
        return (array.length == 0);
    }

    private static boolean isNullable(char array[])
    {
        return (array.length == 0);
    }

    private static boolean isNullable(long array[])
    {
        return (array.length == 0);
    }

    private static boolean isNullable(short array[])
    {
        return (array.length == 0);
    }
}
