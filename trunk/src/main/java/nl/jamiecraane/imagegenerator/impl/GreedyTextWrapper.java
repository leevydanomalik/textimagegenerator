package nl.jamiecraane.imagegenerator.impl;

import java.awt.FontMetrics;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import nl.jamiecraane.imagegenerator.TextWrapper;

/**
 * This class uses a greedy based text wrapping algorithm which uses all
 * available space on the line to fit in the words.
 */
public class GreedyTextWrapper implements TextWrapper {
    private static final String SPACE = " ";
    private static final char CSPACE = ' ';

    /**
     * @see TextWrapper#doWrap(String, int, FontMetrics)
     */
    public List<String> doWrap(String text, int lineWidth, FontMetrics fm) {
        List<String> lines = new ArrayList<String>();

        StringTokenizer tokenizer = new StringTokenizer(text, SPACE);
        int spaceLeft = lineWidth;
        StringBuilder builder = new StringBuilder();
        boolean removed = false;
        String word = "";
        boolean nospaceleft;
        while (tokenizer.hasMoreTokens()) {
            if (removed) {
                // continue with last read word
                removed = false;
            } else {
                word = tokenizer.nextToken() + SPACE;
            }

            char[] chars = new char[word.length()];
            word.getChars(0, word.length(), chars, 0);

            for (int i = 0; i < chars.length; i++) {
                if (fm.charWidth(chars[i]) > spaceLeft) {
                    if (chars[i] != CSPACE) {
                        builder.delete(builder.length() - i, builder.length());
                        removed = true;
                    }

                    if (builder.charAt(builder.length() - 1) == CSPACE) {
                        builder.delete(builder.length() - 1, builder.length());
                    }

                    lines.add(builder.toString());
                    spaceLeft = lineWidth;
                    builder = new StringBuilder();
                    nospaceleft = true;
                } else {
                    spaceLeft -= fm.charWidth(chars[i]);
                    nospaceleft = false;
                }

                if (!removed && !nospaceleft) {
                    builder.append(chars[i]);
                } else {
                    break;
                }
            }
        }

        // In case the last word did not fit in
        if (removed) {
            removed = false;
            builder.append(word.trim());
        }

        if (builder.length() > 1 && builder.charAt(builder.length() - 1) == CSPACE) {
            builder.delete(builder.length() - 1, builder.length());
        }

        lines.add(builder.toString());

        return lines;
    }

}
