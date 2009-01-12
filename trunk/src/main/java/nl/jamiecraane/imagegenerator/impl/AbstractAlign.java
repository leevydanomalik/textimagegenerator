package nl.jamiecraane.imagegenerator.impl;

import nl.jamiecraane.imagegenerator.Align;
import nl.jamiecraane.imagegenerator.DrawableText;

import java.util.List;
import java.awt.*;

/**
 * Author: Jamie Craane
 * Date: 12-jan-2009
 * Time: 21:22:46
 */
public abstract class AbstractAlign implements Align {
    static final String DELIMITER = " ";

    /**
     * Split the given text based on the DELIMITER and returns the result as a String[]
     * @param text
     * @return
     */
    String[] getWords(String text) {
        return text.split(DELIMITER);
    }

    abstract public List<DrawableText> align(String text, FontMetrics fm, int linewidth);
}
