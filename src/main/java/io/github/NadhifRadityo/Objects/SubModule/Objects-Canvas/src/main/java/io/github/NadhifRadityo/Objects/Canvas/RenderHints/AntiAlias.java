package io.github.NadhifRadityo.Objects.Canvas.RenderHints;

import io.github.NadhifRadityo.Objects.Canvas.Managers.GraphicModifierManager;
import io.github.NadhifRadityo.Objects.Canvas.Sprite;

import java.awt.*;

public class AntiAlias extends GraphicModifierManager.CustomGraphicModifier {
	protected boolean enabled;
	
	public AntiAlias(boolean enabled, Sprite... sprites) { super(sprites); this.enabled = enabled; }

	public boolean isEnabled() { return enabled; }
	public void setEnabled(boolean enabled) { this.enabled = enabled; }
	
	@Override public void draw(Graphics g) {
		if(!(g instanceof Graphics2D)) return;
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, enabled ? 
			RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
	}
	@Override public void reset(Graphics g) {
		if(!(g instanceof Graphics2D)) return;
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, !enabled ? 
				RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
	}
}
