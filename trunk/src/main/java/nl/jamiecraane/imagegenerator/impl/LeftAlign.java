package nl.jamiecraane.imagegenerator.impl;

import nl.jamiecraane.imagegenerator.Align;
import nl.jamiecraane.imagegenerator.DrawableText;

import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.awt.*;

/**
 * Left aligns the given text bsed on the linewidth.
 * Author: Jamie Craane
 * Date: 22-dec-2008
 * Time: 10:49:03
 */
public class LeftAlign implements Align {
    public List<DrawableText> align(String text, FontMetrics fm, int linewidth) {
        List<DrawableText> result = new ArrayList<DrawableText>();

        StringTokenizer tokenizer = new StringTokenizer(text, " ");
        int x = 0;
        while (tokenizer.hasMoreTokens()) {
            String nextToken = tokenizer.nextToken();
            if (tokenizer.hasMoreTokens()) {
                nextToken += " ";
            }
            result.add(new DrawableText(nextToken, x));
            x += fm.stringWidth(nextToken);
        }
        
        return result;
    }
}
