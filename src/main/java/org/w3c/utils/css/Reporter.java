package org.w3c.utils.css;

import org.w3c.utils.css.model.exceptions.EExceptionLevel;

import java.util.ArrayList;
import java.util.List;

/**
 * Errors reporter builder.
 * Produce human-readable reports about parsing & working errors.
 *
 * Created by Home on 23.08.2016.
 */
public class Reporter
{
    private EExceptionLevel level;
    private List<Accent> accents = new ArrayList<Accent>();

    public void setLevel(EExceptionLevel level)
    {
        this.level = level;
    }

    public void addMessage(String message)
    {
        accents.add(new Accent(0, 0, message));
    }

    public void addAccent(int col, int length, String notice)
    {
        accents.add(new Accent(col, length, notice));
    }

    public void report()
    {
        boolean first = true;
        for (Accent accent : accents)
        {
            if (first)
            {
                System.out.print("[" + level + "]: ");
                first = false;
            }
            System.out.println(accent);
        }
        System.out.println();
        System.out.println();
    }




    private static class Accent
    {
        private final int start;
        private final int length;
        private final String notice;

        public Accent(int start, int length, String notice)
        {
            this.start = start;
            this.length = length;
            this.notice = notice;
        }

        private String getCharsDuplicates(char c, int count)
        {
            if (count > 0)
            {
                StringBuilder b = new StringBuilder(count);
                for ( ; count > 0; count-- ) b.append(c);
                return b.toString();
            }
            else
                return "";
        }

        @Override
        public String toString()
        {
            return getCharsDuplicates(' ', start) + getCharsDuplicates('^', length) + (length > 0 ? ' ' : "") + notice;
        }
    }
}
