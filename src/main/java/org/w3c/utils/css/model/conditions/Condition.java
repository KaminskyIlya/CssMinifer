package org.w3c.utils.css.model.conditions;

import org.w3c.utils.css.model.CssSimpleDeclaration;

/**
 * ������� ��� @media ��� @condition.
 * ������ ��������� ���� (property:value).
 *
 * Created by Home on 27.08.2016.
 */
public class Condition extends CssSimpleDeclaration
{
    public Condition(String property, String value)
    {
        super(property, value);
    }

    //TODO: ���������� ���������� equals & hashcode ��� ��������� �������.  ������, ��� (min-width: 960px) � (min-width: 10in) ����� ���� ������������
    //� ������, CssSimpleDeclaration ������ ����� ���������� CssValue, ������� ����� ���� �������������� � ��������� � �������
}
