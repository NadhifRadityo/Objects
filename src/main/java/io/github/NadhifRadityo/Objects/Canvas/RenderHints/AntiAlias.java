package io.github.NadhifRadityo.Objects.Canvas.RenderHints;

import java.awt.Graphics2D;
import java.awt.RenderingHints;

import io.github.NadhifRadityo.Objects.Canvas.Managers.GraphicModifierManager.CustomGraphicModifier;

public class AntiAlias extends CustomGraphicModifier {
	protected boolean enabled;
	
	public AntiAlias(boolean enabled) { this.enabled = enabled; }

	public boolean isEnabled() { return enabled; }
	public void setEnabled(boolean enabled) { this.enabled = enabled; }
	
	@Override public void draw(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, enabled ? 
			RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
	}
	@Override public void reset(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, !enabled ? 
				RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
	}
}
