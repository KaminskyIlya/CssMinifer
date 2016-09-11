package org.w3c.utils.css.enums;

/**
 * @See specifications at http://www.w3.org/TR/css3-values/
 *
 * Created by Home on 22.11.2015.
 */
public enum LengthType
{
    em("em") {
        @Override
        public double convertFrom(LengthType type, double value)
        {
            throw new IllegalStateException("Operation not supported for relative length unit.");
        }

        @Override
        public boolean isAbsolute()
        {
            return false;
        }

        @Override
        public boolean isRelative()
        {
            return true;
        }
    },
    ex("ex") {
        @Override
        public double convertFrom(LengthType type, double value)
        {
            throw new IllegalStateException("Operation not supported for relative length unit.");
        }

        @Override
        public boolean isAbsolute()
        {
            return false;
        }

        @Override
        public boolean isRelative()
        {
            return true;
        }
    },
    ch("ch") {
        @Override
        public double convertFrom(LengthType type, double value)
        {
            throw new IllegalStateException("Operation not supported for relative length unit.");
        }

        @Override
        public boolean isAbsolute()
        {
            return false;
        }

        @Override
        public boolean isRelative()
        {
            return true;
        }
    },
    rem("rem") {
        @Override
        public double convertFrom(LengthType type, double value)
        {
            throw new IllegalStateException("Operation not supported for relative length unit.");
        }

        @Override
        public boolean isAbsolute()
        {
            return false;
        }

        @Override
        public boolean isRelative()
        {
            return true;
        }
    },
    vw("vw") {
        @Override
        public double convertFrom(LengthType type, double value)
        {
            throw new IllegalStateException("Operation not supported for relative length unit.");
        }

        @Override
        public boolean isAbsolute()
        {
            return false;
        }

        @Override
        public boolean isRelative()
        {
            return true;
        }
    },
    vh("vh") {
        @Override
        public double convertFrom(LengthType type, double value)
        {
            throw new IllegalStateException("Operation not supported for relative length unit.");
        }

        @Override
        public boolean isAbsolute()
        {
            return false;
        }

        @Override
        public boolean isRelative()
        {
            return true;
        }
    },
    vmin("vmin") {
        @Override
        public double convertFrom(LengthType type, double value)
        {
            throw new IllegalStateException("Operation not supported for relative length unit.");
        }

        @Override
        public boolean isAbsolute()
        {
            return false;
        }

        @Override
        public boolean isRelative()
        {
            return true;
        }
    },
    vmax("vmax") {
        @Override
        public double convertFrom(LengthType type, double value)
        {
            throw new IllegalStateException("Operation not supported for relative length unit.");
        }

        @Override
        public boolean isAbsolute()
        {
            return false;
        }

        @Override
        public boolean isRelative()
        {
            return true;
        }
    },
    percent("%") {
        @Override
        public double convertFrom(LengthType type, double value)
        {
            throw new IllegalStateException("Operation not supported for relative length unit.");
        }

        @Override
        public boolean isAbsolute()
        {
            return false;
        }

        @Override
        public boolean isRelative()
        {
            return true;
        }
    },

    cm("cm") {
        @Override
        public double convertFrom(LengthType type, double value)
        {
            switch (type)
            {
                case cm: return value;
                case mm: return value/10;
                case q: return value/40;
                case In: return value*2.54;
                case pc: return value*2.54/6;
                case pt: return value*2.54/72;
                case px: return value*2.54/96;
            }
            throw new IllegalArgumentException();
        }

        @Override
        public boolean isAbsolute()
        {
            return true;
        }

        @Override
        public boolean isRelative()
        {
            return false;
        }
    },
    mm("mm") {
        @Override
        public double convertFrom(LengthType type, double value)
        {
            switch (type)
            {
                case cm: return value*10;
                case mm: return value;
                case q: return value/4;
                case In: return value*25.4;
                case pc: return value*25.4/6;
                case pt: return value*25.4/72;
                case px: return value*25.4/96;
            }
            throw new IllegalArgumentException();
        }

        @Override
        public boolean isAbsolute()
        {
            return true;
        }

        @Override
        public boolean isRelative()
        {
            return false;
        }
    },
    q("q") {
        @Override
        public double convertFrom(LengthType type, double value)
        {
            switch (type)
            {
                case cm: return value*40;
                case mm: return value*4;
                case q: return value;
                case In: return value*2.54/40;
                case pc: return value*2.54/6/40;
                case pt: return value*2.54/72/40;
                case px: return value*2.54/96/40;
            }
            throw new IllegalArgumentException();
        }

        @Override
        public boolean isAbsolute()
        {
            return true;
        }

        @Override
        public boolean isRelative()
        {
            return false;
        }
    },
    In("in") {
        @Override
        public double convertFrom(LengthType type, double value)
        {
            switch (type)
            {
                case cm: return value/2.54;
                case mm: return value/10/2.54;
                case q: return value/40/2.54;
                case In: return value;
                case pc: return value/6;
                case pt: return value/72;
                case px: return value/96;
            }
            throw new IllegalArgumentException();
        }

        @Override
        public boolean isAbsolute()
        {
            return true;
        }

        @Override
        public boolean isRelative()
        {
            return false;
        }
    },
    pc("pc") {
        @Override
        public double convertFrom(LengthType type, double value)
        {
            switch (type)
            {
                case cm: return value/2.54*6;
                case mm: return value/25.4*6;
                case q: return value/40/2.54*6;
                case In: return value*6;
                case pc: return value;
                case pt: return value/72*6;
                case px: return value/96*6;
            }
            throw new IllegalArgumentException();
        }

        @Override
        public boolean isAbsolute()
        {
            return true;
        }

        @Override
        public boolean isRelative()
        {
            return false;
        }
    },
    pt("pt") {
        @Override
        public double convertFrom(LengthType type, double value)
        {
            switch (type)
            {
                case cm: return value/2.54*72;
                case mm: return value/25.4*72;
                case q: return value/40/2.54*72;
                case In: return value*72;
                case pc: return value/6*72;
                case pt: return value;
                case px: return value/96*72;
            }
            throw new IllegalArgumentException();
        }

        @Override
        public boolean isAbsolute()
        {
            return true;
        }

        @Override
        public boolean isRelative()
        {
            return false;
        }
    },
    px("px") {
        @Override
        public double convertFrom(LengthType type, double value)
        {
            switch (type)
            {
                case cm: return value/2.54*96;
                case mm: return value/25.4*96;
                case q: return value/40/2.54*96;
                case In: return value*96;
                case pc: return value/6*96;
                case pt: return value/72*96;
                case px: return value;
            }
            throw new IllegalArgumentException();
        }

        @Override
        public boolean isAbsolute()
        {
            return true;
        }

        @Override
        public boolean isRelative()
        {
            return false;
        }
    };


    private final String text;

    LengthType(String text)
    {
        this.text = text;
    }

    @Override
    public String toString()
    {
        return text;
    }

    public double convertFrom(LengthType type, double value)
    {
        throw new AbstractMethodError();
    }

    public boolean isAbsolute()
    {
        throw new AbstractMethodError();
    }

    public boolean isRelative()
    {
        throw new AbstractMethodError();
    }
}
