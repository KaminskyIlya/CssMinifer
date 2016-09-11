package org.w3c.utils.css.help;

/**
 * Интерфейс, реализуемый объектами, которые могут иметь пустое содержимое.
 * Пустые объекты, в некоторых случаях, удобнее использовать там, где пришлось бы возвращать null.
 *
 * Created by home on 15.01.15.
 */
public interface Nullable
{
    /**
     * @return true, если объект пуст
     */
    public boolean isEmpty();
}
