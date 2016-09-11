package org.w3c.utils.css.model.spec.border;

import org.w3c.utils.css.Configuration;
import org.w3c.utils.css.enums.SideType;
import org.w3c.utils.css.io.PropertyWriter;
import org.w3c.utils.css.model.DeclarationOptimizer;
import org.w3c.utils.css.model.spec.common.CssValue;
import org.w3c.utils.css.model.spec.common.SidesValueInterface;

import java.util.Map;

/**
 * Created by Home on 22.08.2016.
 */
public class BorderOptimizer implements DeclarationOptimizer
{
    private final Border border;
    private Configuration config;

    public BorderOptimizer(Border border)
    {
        this.border = border;
    }

    public void setConfig(Configuration config)
    {
        this.config = config;
    }

    public void optimize()
    {
    }

    public void render(PropertyWriter writer)
    {
        // ��������� �������� �������
        // ���� ���� �� ���� �� ������� ���������� � �������� �������
        if ( border.getStyle().isAllSidesDefined() || border.getWidth().isAllSidesDefined() || border.getColor().isAllSidesDefined() )
        {
            writer.beginProperty(border.getProperty());

            //TODO: ��� ���������� ������� ���: ���� ��� ������ SingleValue - �������� ��� getSingleValue,
            //����� - ������� ����� ����������� �����
            if (isCanIncludedInCommonForm(border.getStyle())) writer.addValue(border.getStyle().getSingleValue());
            if (isCanIncludedInCommonForm(border.getWidth())) writer.addValue(border.getWidth().getSingleValue());
            if (isCanIncludedInCommonForm(border.getColor())) writer.addValue(border.getColor().getSingleValue());
        }

        writeQuaternaryValue(writer, "style", border.getStyle());
        writeQuaternaryValue(writer, "width", border.getWidth());
        writeQuaternaryValue(writer, "color", border.getColor());
    }

    private boolean isCanIncludedInCommonForm(SidesValueInterface value)
    {
        return value.isSingleValue() || (value.isAllSidesDefined() && value.countUniqueValues() == 2 && !value.isDoubleValue() && !value.isTripleValue());
        // || (value.isTripleValue() && value.getLeftValue().getOptimized().length >= 6
    }

    // ��� ����� ������� � ��������� �������������� �����, ������������������ �� ��������� ��������
    private void writeQuaternaryValue(PropertyWriter writer, String name, SidesValueInterface value)
    {
        int defCount = value.countDefinedValues();

        // ���� �� ��� �� 4-� ��������� �������� ���� ����������
        if ( defCount < 4 )
        {
            // ���� ��� ��������� �������� �������� ��������
            // border-*-*: value
            Map<SideType, CssValue> defined = value.getDefinedValues();
            for (SideType side : defined.keySet())
            {
                writer.beginProperty(border.getProperty());
                writer.addPropertyName(side.toString().toLowerCase());
                writer.addPropertyName(name);
                writer.addValue(defined.get(side).getOptimized());
            }
            // �� �� ������ ����� ��������� �������� ������������� �������� ���� none/0/rgba(0,0,0,0)
            // �.�. ��� ����� ������������ � ������ ������
            // � ���� ��� ����� ����������� � ���� �� ������, �� � ��� ������� ��� �� �������
        }
        else
            // ������� �������� � �������
            // border-*: val1 val2
            if ( value.isDoubleValue() )
            {
                writer.beginProperty(border.getProperty());
                writer.addPropertyName(name);
                writer.addValue(value.getDoubleValue());
            }
            else
                // ������� �������� � �������
                // border-*: val1 val2 val3
                if ( value.isTripleValue() )
                {
                    writer.beginProperty(border.getProperty());
                    writer.addPropertyName(name);
                    writer.addValue(value.getTripleValue());
                }
                else
                {
                    // ���� ������������ 2 ������ �������� + ������ ����� �����
                    // border: ? red ?; border-*-color: blue;
                    if (value.countUniqueValues() == 2)
                    {
                        writer.beginProperty(border.getProperty());

                        // ��������� �� ��� �������� � ����� singleValue, ������ � ������� ������ ���������� ��������
                        String first = value.getSingleValue();
                        Map<SideType, CssValue> defined = value.getDefinedValues();

                        for (SideType side : defined.keySet())
                        {
                            String second = defined.get(side).getOptimized();
                            if ( !second.equals(first) )
                            {
                                writer.addPropertyName(side.toString().toLowerCase());
                                writer.addPropertyName(name);
                                writer.addValue(second);
                            }
                        }
                    }
                    // ���� ��� �������� �� ���� ������ �� ���������� ���� � ��� ����� �� 2-� ��������� ��������, ������� ��� ��� ��������
                    // border-*: val1 val2 val3 val4
                    else
                    {
                        writer.beginProperty(border.getProperty());
                        writer.addPropertyName(name);
                        writer.addValue(value.getQuadValue());
                    }
                }
    }
}
