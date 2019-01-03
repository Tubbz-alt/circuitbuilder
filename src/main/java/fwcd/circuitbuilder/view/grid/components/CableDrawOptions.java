package fwcd.circuitbuilder.view.grid.components;

public class CableDrawOptions {
	private final int thickness;
	private final boolean drawDots;
	
	public CableDrawOptions(int thickness, boolean drawDots) {
		this.thickness = thickness;
		this.drawDots = drawDots;
	}
	
	public int getThickness() { return thickness; }
	
	public boolean drawDots() { return drawDots; }
}
