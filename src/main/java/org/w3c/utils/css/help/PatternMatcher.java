package org.w3c.utils.css.help;

import java.util.List;

/**
 * Created by Home on 06.12.2015.
 */
public class PatternMatcher
{
    String hash;
    List<String> samples;

    public PatternMatcher(String hash, List<String> samples)
    {
        assert hash != null;
        assert samples != null;
        assert hash.length() >= 4;
        assert samples.size() > 1;

        this.hash = hash;
        this.samples = samples;
    }

    public String getHash()
    {
        return hash;
    }

    public List<String> getSamples()
    {
        return samples;
    }

    public int count()
    {
        return samples.size();
    }

    public int length()
    {
        return hash.length();
    }


    /**
     * ѕытаетс€ определить максимально возможную длину совпадени€ у найденной группы
     */
    public void maximizeLength()
    {
        int len = hash.length() + 1;

        do
        {
            String pattern = samples.get(0).substring(0, len);

            for (String sample : samples)
            {
                if ( !sample.startsWith(pattern) )
                {
                    if (--len > hash.length())
                    {
                        hash = samples.get(0).substring(0, len);
                    }
                    return;
                }
            }
        }
        while (++len < 1000);

        throw new IllegalStateException();
    }

    @Override
    public String toString()
    {
        return "PatternMatcher{" +
                "hash='" + hash + '\'' +
                ", samples=" + samples +
                '}';
    }
}
