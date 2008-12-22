package nl.jamiecraane.imagegenerator;

import java.awt.FontMetrics;
import java.util.List;

public interface TextWrapper {
	/**
	 * Wraps the given text and writes in on the specified graphics object.
	 * @param text
	 * @param lineWidth
	 * @param fm
	 * @return A List of Strings. Each entry in the List should appear on a new line.
	 */
	List<String> doWrap(String text, int lineWidth, FontMetrics fm);
}
