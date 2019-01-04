package fwcd.circuitbuilder.view.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class CollapserButton extends JButton {
	private static final long serialVersionUID = 2147636771774743441L;
	
	public CollapserButton() {
		this("");
	}
	
	public CollapserButton(String label) {
		super(label);
		setBorder(BorderFactory.createEmptyBorder());
		setContentAreaFilled(false);
		setPreferredSize(new Dimension(10, 50));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		int width = getWidth();
		int height = getHeight();
		Color background = getBackground();
		
		if (getModel().isPressed()) {
			g2d.setColor(background.darker());
		} else {
			g2d.setColor(background);
		}
		
		g2d.fillRect(0, 0, width, height);
		
		FontMetrics metrics = g2d.getFontMetrics();
		Color foreground = getForeground();
		String str = getText();
		int strWidth = metrics.stringWidth(str);
		int strHeight = metrics.getAscent();
		int strX = (width / 2) - (strWidth / 2);
		int strY = (height / 2) - (strHeight / 2);
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(foreground);
		g2d.drawString(str, strX, strY);
	}
}
