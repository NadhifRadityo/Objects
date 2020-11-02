package io.github.NadhifRadityo.Objects;

import io.github.NadhifRadityo.Objects.Utilizations.FontUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;

public class FontDrawTest extends JComponent {
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		Font font = new Font("Courier", Font.PLAIN, 40);
		GlyphVector gv = FontUtils.getVector(font, g2d, "TEST");
		g2d.translate(20, 200);
		g2d.draw(gv.getOutline());
	}

	public static void main(String[] a) {
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(30, 30, 450, 450);
		window.getContentPane().add(new FontDrawTest());
		window.setVisible(true);
	}
}
