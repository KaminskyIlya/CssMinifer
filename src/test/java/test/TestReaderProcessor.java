package test;

import org.w3c.utils.css.filters.proc.FlowProcessor;
import org.w3c.utils.css.io.SymbolReader;

/**
 * Created by Home on 27.08.2016.
 */
public final class TestReaderProcessor implements FlowProcessor
{
    private final SymbolReader reader;

    public TestReaderProcessor(SymbolReader reader)
    {
        this.reader = reader;
    }

    public void reset() {

    }

    public void before(char current)
    {
        reader.read();
    }

    public void after(char current)
    {

    }

    public boolean canProcess()
    {
        return true;
    }
}
