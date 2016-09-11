package org.w3c.utils.css.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Byte stream loader from streams.
 * For optimized memory use.
 *
 * Created by Home on 26.08.2016.
 */
public class ByteStreamLoader extends ByteArrayOutputStream
{
    /**
     * Create byte buffer from input stream.
     * Load all data from source.
     *
     * @param source
     * @throws IOException
     */
    public ByteStreamLoader(InputStream source) throws IOException
    {
        super(65536);
        readFrom(source);
    }

    private void readFrom(InputStream stream) throws IOException
    {
        while(true)
        {
            if(this.count == this.buf.length)
            {
                byte[] var2 = new byte[this.buf.length * 2];
                System.arraycopy(this.buf, 0, var2, 0, this.buf.length);
                this.buf = var2;
            }

            int var3 = stream.read(this.buf, this.count, this.buf.length - this.count);
            if(var3 < 0) return;

            this.count += var3;
        }
    }

    /**
     *
     * @return internal buffer as is
     */
    @Override
    public synchronized byte[] toByteArray()
    {
        return this.buf;
    }

    /**
     *
     * @throws UnsupportedOperationException
     */
    @Override
    @Deprecated
    public synchronized void write(int i)
    {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @throws UnsupportedOperationException
     */
    @Override
    @Deprecated
    public synchronized void write(byte[] bytes, int i, int i1)
    {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @throws UnsupportedOperationException
     */
    @Override
    @Deprecated
    public synchronized void writeTo(OutputStream outputStream) throws IOException
    {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @throws UnsupportedOperationException
     */
    @Override
    @Deprecated
    public synchronized void reset()
    {
        throw new UnsupportedOperationException();
    }
}
