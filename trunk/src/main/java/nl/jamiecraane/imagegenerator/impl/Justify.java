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
public class Justify implements Align {
    public List<DrawableText> align(String text, FontMetrics fm, int linewidth) {
        List<DrawableText> result = new ArrayList<DrawableText>();

        StringTokenizer tokenizer = new StringTokenizer(text, " ");

        int spaceLeft = linewidth - fm.stringWidth(text);
        // Do not count the spaces because the glue will become the new spaces
        for (int i = 0; i < tokenizer.countTokens() - 1; i++) {
            spaceLeft += fm.charWidth(' ');
        }

        int glue = 0;
        if (tokenizer.countTokens() > 1) {
            // TODO We need to correct rounding issues (calculate the number of pixels to correct (div/remainder) and divide the remaining pixels over the spaces)
            glue = Math.round((float) spaceLeft / (tokenizer.countTokens() - 1));
        }
        int x = 0;
        while (tokenizer.hasMoreTokens()) {
            String nextToken = tokenizer.nextToken();
            result.add(new DrawableText(nextToken, x));
            x += fm.stringWidth(nextToken) + glue;
        }

        return result;
    }
}
