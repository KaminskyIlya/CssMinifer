package org.w3c.utils.css;

import org.w3c.utils.css.filters.*;
import org.w3c.utils.css.io.BufferFactory;
import org.w3c.utils.css.io.RecycledCharBuffer;
import org.w3c.utils.css.model.exceptions.EExceptionLevel;

import java.io.File;
import java.io.IOException;

/**
 * Отработать здесь набросок API.
 * Intelligent
 *
 * Created by Home on 04.11.2015.
 */
public class Temp
{
    public static void main(String args[]) throws Exception
    {
        //Pattern HEX_NUMBER = Pattern.compile("0x[\\D]{1,16}");
        //System.out.println(HEX_NUMBER.matcher("AB").matches());
        jsonWriterDemo();

    }

    private static void jsonWriterDemo() throws Exception
    {
    }

    private static void reporterDemo() throws Exception
    {
        String name = "print-setup";

        Reporter reporter = new Reporter();
        reporter.setLevel(EExceptionLevel.WARN);
        reporter.addMessage(String.format("Unknown rule in line <%d>:", 10));
        reporter.addMessage("--------------------------------------------------");
        reporter.addMessage("@" + name);
        reporter.addAccent(1, name.length(), "rule declaration will not be optimized");
        reporter.report();
    }

    private static void filterCompressorDemo() throws IOException
    {
        RecycledCharBuffer buffer = BufferFactory.buildFromFile(new File("D:\\sitex\\cms_editor\\css\\bootstrap\\bootstrap-3.min.css")).refill(); //118 202 байта

        new CommentsBlockFilter(buffer.reuse()).apply();
        new InlineCommentsFilter(buffer.reuse()).apply();
        new WhiteSpacesFilter(buffer.reuse()).apply();

        ColorFilter color = new ColorFilter(buffer.reuse());
        color.setReduceColorSpace(true);
        color.apply();

        NumericFilter number = new NumericFilter(buffer.reuse());
        number.setLossAccuracy(true);
        number.setAccuracyScale(3);
        number.apply();

        String output = buffer.getOutput();
        System.out.println(output);
        System.out.println(118202);
        System.out.println(output.length()); //115559 //115535 //115543 //115770
        System.out.println(118202 - output.length());
        System.out.println((1 - output.length()/118202f)*100);
    }


}
