package org.w3c.utils.css.help;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Search a patterns in string
 *
 * Created by Home on 06.12.2015.
 */
public class StringPatterns
{
    private static final int MIN_LEN = 4;

    public static List<PatternMatcher> leftPattern(List<String> samples)
    {
        // ������ ��� ������, ������� ����������� �����
        samples = excludeSamplesByLength(samples, MIN_LEN);

        // �������� ������ �� ������, � ������� ������ 4 ������� ���������
        List<PatternMatcher> groups = new ArrayList<PatternMatcher>();

        while ( !samples.isEmpty() )
        {
            String hash = samples.get(0).substring(0, MIN_LEN);
            List<String> group = new ArrayList<String>();

            Iterator<String> iterator = samples.iterator();
            while (iterator.hasNext())
            {
                String sample = iterator.next();
                if (sample.substring(0, MIN_LEN).equals(hash))
                {
                    group.add(sample);
                    iterator.remove();
                }
            }

            if (group.size() > 1)
            {
                groups.add(new PatternMatcher(hash, group));
            }
        }

        // ��� ������ ������ ������ ������������ ����� ������ ����������
        for (PatternMatcher group : groups)
        {
            group.maximizeLength();
        }

        // ������, ����� ������ ����� ������� �� ��� ����� ������ ��������� (��������� ������)

        return groups;
    }


    private static List<String> excludeSamplesByLength(List<String> samples, int len)
    {
        List<String> result = new ArrayList<String>(samples);
        Iterator<String> iterator = result.iterator();
        while (iterator.hasNext())
        {
            if (iterator.next().length() < len) iterator.remove();
        }
        return result;
    }
}
