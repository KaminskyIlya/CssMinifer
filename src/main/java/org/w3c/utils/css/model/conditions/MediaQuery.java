package org.w3c.utils.css.model.conditions;

import java.util.HashSet;
import java.util.Set;

/**
 * �����-������. ������ ��� ������ ��� ������ Media.
 * tv and (color), screen and (min-width: 1024px)
 * screen and (color), projection and (color)
 *
 * ��������� screen and (max-weight: 3kg) and (color), (color) ����� ��������� �� screen and (max-weight: 3kg), (color)
 *
 * Created by Home on 27.08.2016.
 */
public class MediaQuery
{
    //private MediaPrefix prefix; // only, not
    private String mediaType; // screen, tv, projection, aural  (����� ������, �.�. �������������� ����� ���� ������)
    private Set<Expression> expressions = new HashSet<Expression>();

    public MediaQuery()
    {
    }

    public Set<Expression> getExpressions()
    {
        return expressions;
    }

    /**
     * Sugar
     * @param expression
     */
    public void addExpression(Expression expression)
    {
        expressions.add(expression);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof MediaQuery)) return false;

        MediaQuery that = (MediaQuery) o;

        return expressions.equals(that.expressions);

    }

    @Override
    public int hashCode()
    {
        return expressions.hashCode();
    }
}
