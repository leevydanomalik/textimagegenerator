package nl.jamiecraane.imagegenerator.impl;

import org.junit.Test;

import java.awt.*;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * @author Jamie Craane
 */
public class GreedyTextWrapperTest {
    @Test
    public void wrapTextWithSpaces() {
        GreedyTextWrapper textWrapper = GreedyTextWrapper.createWithDefaultSplitPoints();
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 10);
        FontMetrics fontMetrics = new FontMetrics(font) {
            @Override
            public int charWidth(final char c) {
                return 10;
            }
        };

        List<String> lines = textWrapper.doWrap("This is a line which should be wrapped.", 100, fontMetrics);
        System.out.println("lines = " + lines);
        assertEquals("This is a", lines.get(0));
        assertEquals("line which", lines.get(1));
        assertEquals("should be", lines.get(2));
        assertEquals("wrapped.", lines.get(3));
    }

    @Test
    public void wrapTextWithSpaces_2() {
        GreedyTextWrapper textWrapper = GreedyTextWrapper.createWithDefaultSplitPoints();
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 10);
        FontMetrics fontMetrics = new FontMetrics(font) {
            @Override
            public int charWidth(final char c) {
                return 10;
            }
        };

        List<String> lines = textWrapper.doWrap("This is another line which should be wrapped.", 100, fontMetrics);
        System.out.println("lines = " + lines);
        assertEquals("This is", lines.get(0));
        assertEquals("another", lines.get(1));
        assertEquals("line which", lines.get(2));
        assertEquals("should be", lines.get(3));
        assertEquals("wrapped.", lines.get(4));
    }
   
    @Test
    public void wrapWithHyphen() {
        GreedyTextWrapper textWrapper = GreedyTextWrapper.createWithDefaultSplitPoints();
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 10);
        FontMetrics fontMetrics = new FontMetrics(font) {
            @Override
            public int charWidth(final char c) {
                return 10;
            }
        };

        List<String> lines = textWrapper.doWrap("This is a-longer line which should be wrapped.", 100, fontMetrics);
        System.out.println("lines = " + lines);
        assertEquals("This is a-", lines.get(0));
        assertEquals("longer", lines.get(1));
        assertEquals("line which", lines.get(2));
        assertEquals("should be", lines.get(3));
        assertEquals("wrapped.", lines.get(4));
    }
    
    @Test
    public void wrapContinuousText() {
        GreedyTextWrapper textWrapper = GreedyTextWrapper.createWithDefaultSplitPoints();
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 10);
        FontMetrics fontMetrics = new FontMetrics(font) {
            @Override
            public int charWidth(final char c) {
                return 10;
            }
        };

        List<String> lines = textWrapper.doWrap("Thisisalineofcontinuoustexttoseeifthetextwrapsproperly. And more text.", 100, fontMetrics);
        System.out.println("lines = " + lines);
        assertEquals("Thisisalin", lines.get(0));
        assertEquals("eofcontinu", lines.get(1));
        assertEquals("oustexttos", lines.get(2));
        assertEquals("eeifthetex", lines.get(3));
        assertEquals("twrapsprop", lines.get(4));
        assertEquals("erly. And", lines.get(5));
        assertEquals("more text.", lines.get(6));
    }
}
