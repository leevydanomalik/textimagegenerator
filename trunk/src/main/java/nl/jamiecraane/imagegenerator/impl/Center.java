package nl.jamiecraane.imagegenerator.impl;

import nl.jamiecraane.imagegenerator.Align;
import nl.jamiecraane.imagegenerator.DrawableText;

import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.awt.*;

/**
 * Centers the given text bsed on the linewidth.
 * Author: Jamie Craane
 * Date: 22-dec-2008
 * Time: 12:37:15
 */
public class Center extends AbstractAlign implements Align {
    public List<DrawableText> align(String text, FontMetrics fm, int linewidth) {
        List<DrawableText> result = new ArrayList<DrawableText>();

        String[] words = super.getWords(text);
        int x = (linewidth - fm.stringWidth(text)) / 2;
        for (String word : words) {
            if (word.length() == 0) {
                // This is the delimiter
                result.add(new DrawableText(DELIMITER, x));
                x += fm.stringWidth(DELIMITER);
            }
            word += DELIMITER;
            result.add(new DrawableText(word, x));
            x += fm.stringWidth(word);
        }

        return result;
    }
}
