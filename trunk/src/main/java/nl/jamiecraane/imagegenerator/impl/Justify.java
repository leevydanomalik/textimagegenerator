package nl.jamiecraane.imagegenerator.impl;

import nl.jamiecraane.imagegenerator.Align;
import nl.jamiecraane.imagegenerator.DrawableText;

import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.awt.*;

/**
 * Justifies the given text bsed on the linewidth.
 * Author: Jamie Craane
 * Date: 22-dec-2008
 * Time: 12:39:33
 */
public class Justify extends AbstractAlign implements Align {
    public List<DrawableText> align(String text, FontMetrics fm, int linewidth) {
        List<DrawableText> result = new ArrayList<DrawableText>();

        String[] words = super.getWords(text);

        int spaceLeft = linewidth - fm.stringWidth(text);
        // Do not count the spaces because the glue will become the new spaces
        for (int i = 0; i < words.length - 1; i++) {
            spaceLeft += fm.charWidth(' ');
        }

        int glue = 0;

        if (words.length > 0) {
            glue = Math.round((float) spaceLeft / (words.length - 1));
        }

        int x = 0;
        for (String word : words) {
            result.add(new DrawableText(word, x));
            x += fm.stringWidth(word) + glue;
        }

        return result;
    }
}
