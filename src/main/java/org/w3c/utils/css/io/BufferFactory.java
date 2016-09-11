package org.w3c.utils.css.io;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Home on 21.11.2015.
 */
@Deprecated
public class BufferFactory
{
    public static RecycledCharBuffer buildFromFile(File file) throws IOException
    {
        FileReader reader = new FileReader(file);
        StringBuffer buffer = new StringBuffer((int)file.length());

        char cache[] = new char[16384];
        int n;
        while ((n = reader.read(cache)) != -1)
        {
            buffer.append(cache, 0, n);
        }
        reader.close();

        return new RecycledCharBuffer(buffer);
    }

    public static String readFile(File file) throws IOException
    {
        FileReader reader = new FileReader(file);
        StringBuffer buffer = new StringBuffer((int)file.length());

        char cache[] = new char[16384];
        int n;
        while ((n = reader.read(cache)) != -1)
        {
            buffer.append(cache, 0, n);
        }
        reader.close();

        return buffer.toString();
    }
}
