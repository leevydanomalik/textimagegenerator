package nl.jamiecraane.imagegenerator.impl;

import nl.jamiecraane.imagegenerator.TextWrapper;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * This class uses a greedy based text wrapping algorithm which uses all
 * available space on the line to fit in the words.
 *
 * The default split characters are the space ' '  and the hyphen '-'. The default is created with createWithDefaultSplitPoints.
 *
 * The method createWithSplitPoints accepts user passed-in splitpoints.
 *
 * If a single word is larger than the linewidth, the word is truncated at position linewidth.
 *
 * @author Jamie Craane
 */
public final class GreedyTextWrapper implements TextWrapper {
    private static final char SPACE_SPLIT_POINT = ' ';
    private static final char HYPHEN_SPLIT_POINT = '-';

    private Set<Character> splitPoints;

    /**
     * @deprecated As of release 1.1.1 replace with {@link #createWithDefaultSplitPoints()} or {@link #createWithSplitPoints(Character...)}
     */
    public GreedyTextWrapper() {
        splitPoints = new HashSet<Character>(Arrays.asList(new Character[] {SPACE_SPLIT_POINT}));
    }

    /**
     * Creates an instance of the wrapper using the default splitpoints. The default splitpoints are the space ' ' and
     * hyphen '-' character.
     * @return
     */
    public static GreedyTextWrapper createWithDefaultSplitPoints() {
        GreedyTextWrapper greedyTextWrapper = new GreedyTextWrapper();
        greedyTextWrapper.splitPoints = new HashSet<Character>(Arrays.asList(new Character[] {SPACE_SPLIT_POINT, HYPHEN_SPLIT_POINT}));
        return greedyTextWrapper;
    }

    /**
     * Creates an instance if the wrapper with the passed-in splitpoints.
     * @param points The splitpoint characters to use.
     * @return
     * @throws IllegalArgumentException If points is null, empty or contains null elements.
     */
    public static GreedyTextWrapper createWithSplitPoints(Character... points) {
        GreedyTextWrapper greedyTextWrapper = new GreedyTextWrapper();
        greedyTextWrapper.splitPoints = new HashSet<Character>(Arrays.asList(points));
        return greedyTextWrapper;
    }

    /**
     * {@inheritDoc}
     */
    public List<String> doWrap(final String text, final int lineWidth, final FontMetrics fm) {
        char[] chars = text.toCharArray();
        List<Integer> potentialSplitPoints = new ArrayList<Integer>();
        potentialSplitPoints.add(0);
        int potentialSplitPointPosition = 0;
        int spaceLeftOnLine = lineWidth;

        for (int positionInText = 0; positionInText < chars.length; positionInText++) {
            char currentChar = chars[positionInText];
            potentialSplitPointPosition = getPotentialSplitPoint(potentialSplitPointPosition, positionInText, currentChar);

            if (endOfTextReached(chars, positionInText)) {
                potentialSplitPointPosition = text.length();
                break;
            }

            int widthOfCurrentChar = fm.charWidth(currentChar);
            spaceLeftOnLine -= widthOfCurrentChar;
            if (noMoreSpaceLeft(spaceLeftOnLine)) {
                if (nextCharIsSplitPoint(positionInText, chars)) {
                    positionInText++;
                    potentialSplitPointPosition = createSplitPoint(positionInText);
                } else {
                    int lengthOfCurrentWord = calculateLengthOfCurrentWord(potentialSplitPointPosition, chars, fm);
                    if (currentWordDoesNotFitOnOneLine(lineWidth, lengthOfCurrentWord)) {
                        potentialSplitPointPosition = createSplitPoint(positionInText);
                    } else {
                        // Current word does fit on one line, reposition the index to the last split position to make
                        // sure the complete word is read (instead of form the current position in the text).
                        positionInText = potentialSplitPointPosition - 1;
                    }
                }

                potentialSplitPoints.add(potentialSplitPointPosition);
                spaceLeftOnLine = lineWidth;
            }
        }

        if (lastSplitPointPositionNotEqualsTextLength(text, potentialSplitPoints)) {
            potentialSplitPoints.add(text.length());
        }

        return splitTextOnSplitPoints(text, potentialSplitPoints);
    }

    private boolean currentWordDoesNotFitOnOneLine(final int lineWidth, final int lengthOfCurrentWord) {
        return lengthOfCurrentWord > lineWidth;
    }

    private int calculateLengthOfCurrentWord(final int potentialSplitPointPosition, final char[] chars, final FontMetrics fm) {
        int wordLength = 0;

        int index = potentialSplitPointPosition;
        while (index < chars.length && !isSplitPoint(chars[index])) {
            wordLength += fm.charWidth(chars[index]);
            index++;
        }

        return wordLength;
    }

    private int getPotentialSplitPoint(int potentialSplitPointPosition, final int positionInText, final char currentChar) {
        if (isSplitPoint(currentChar)) {
            potentialSplitPointPosition = createSplitPoint(positionInText);
        }

        return potentialSplitPointPosition;
    }

    private boolean isSplitPoint(final char currentChar) {
        return splitPoints.contains(currentChar);
    }

    private int createSplitPoint(final int positionInText) {
        return positionInText + 1;
    }

    private boolean endOfTextReached(final char[] chars, final int positionInText) {
        return positionInText + 1 == chars.length;
    }

    private boolean noMoreSpaceLeft(final int spaceLeftOnLine) {
        return spaceLeftOnLine <= 0;
    }

    private boolean nextCharIsSplitPoint(final int positionInText, final char[] chars) {
        int nextCharPosition = positionInText + 1;
        if (nextCharPosition < chars.length) {
            return isSplitPoint(chars[nextCharPosition]);
        }

        return false;
    }

    private boolean lastSplitPointPositionNotEqualsTextLength(final String text, final List<Integer> potentialSplitPoints) {
        return potentialSplitPoints.get(potentialSplitPoints.size() - 1) != text.length();
    }

    private List<String> splitTextOnSplitPoints(final String text, final List<Integer> potentialSplitPoints) {
        List<String> lines = new ArrayList<String>();

        int index = 0;
        while (atLeastTwoSplitPointsLeft(potentialSplitPoints, index)) {
            int firstSplitPoint = potentialSplitPoints.get(index);
            int secondSplitPoint = potentialSplitPoints.get(index + 1);
            lines.add(text.substring(firstSplitPoint, secondSplitPoint).trim());
            index++;
        }

        return lines;
    }

    private boolean atLeastTwoSplitPointsLeft(final List<Integer> potentialSplitPoints, final int index) {
        return index < potentialSplitPoints.size() - 1;
    }
}
