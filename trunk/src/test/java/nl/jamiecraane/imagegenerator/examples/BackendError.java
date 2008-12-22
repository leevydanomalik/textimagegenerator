package nl.jamiecraane.imagegenerator.examples;

import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import nl.jamiecraane.imagegenerator.Margin;
import nl.jamiecraane.imagegenerator.TextImage;
import nl.jamiecraane.imagegenerator.impl.TextImageImpl;

public class BackendError {
	public static void main(String[] args) throws Exception {
		BackendError tester = new BackendError();
		
		tester.run();
	}
	
	private void run() throws Exception {
		TextImage image = new TextImageImpl(492, 200, new Margin(5, -3));
		
		InputStream is = this.getClass().getResourceAsStream("verdana.ttf");
		Font verdana = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(11.33f);
		is = this.getClass().getResourceAsStream("verdanab.ttf");
		Font verdanab = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(14f);
		
		image.useFont(verdanab).writeLine("Systeem tijdelijk niet beschikbaar");
		image.useFont(verdana).wrap(true).write("Op dit moment is het systeem niet beschikbaar. Onze excuses voor het ongemak. Probeert u het later nog eens.");
		
		FileOutputStream fos = new FileOutputStream(new File("error.png"));
		try {
			image.createPng(fos);
		} finally {
			fos.close();
		}
	}
}
