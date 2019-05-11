package io.github.NadhifRadityo.Objects.Utilizations;

import java.awt.Color;
import java.util.Random;

public enum FlatColor {
	//https://flatuicolors.com/palette/defo
	Turquoise("Turquoise", new Color(26, 188, 156)), //Turquoise
	Green_Sea("Green Sea", new Color(22, 160, 133)), //Green Sea
	Emerald("Emerald", new Color(46, 204, 113)), //Emerald
	Nephritis("Nephritis", new Color(39, 174, 96)), //Nephritis
	Peter_River("Peter River", new Color(52, 152, 219)), //Peter River
	Belize_Hole("Belize Hole", new Color(41, 128, 185)), //Belize Hole
	Amethyst("Amethyst", new Color(155, 89, 182)), //Amethyst
	Wisteria("Wisteria", new Color(142, 68, 173)), //Wisteria
	Wet_Asphalt("Wet Asphalt", new Color(52, 73, 94)), //Wet Asphalt
	Midnight_Blue("Midnight Blue", new Color(44, 62, 80)), //Midnight Blue
	Sunflower("Sunflower", new Color(241, 196, 15)), //Sunflower
	Orange("Orange", new Color(243, 156, 18)), //Orange
	Carrot("Carrot", new Color(230, 126, 34)), //Carrot
	Pumpkin("Pumpkin", new Color(211, 84, 0)), //Pumpkin
	Alizarin("Alizarin", new Color(231, 76, 60)), //Alizarin
	Pomegranate("Pomegranate", new Color(192, 57, 43)), //Pomegranate
	Clouds("Clouds", new Color(236, 240, 241)), //Clouds
	Silver("Silver", new Color(189, 195, 199)), //Silver
	Concrete("Concrete", new Color(149, 165, 166)), //Concrete
	Asbestos("Asbestos", new Color(127, 140, 141)); //Asbestos
	
	private String name;
	private Color color;
	private FlatColor(String name, Color color) {
		this.name = name;
		this.color = color;
	}
	
	public String getName() { return name; }
	public Color getColor() { return color; }
	public Color getColor(float alpha) { return new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) NumberUtils.map(alpha, 0, 1, 0, 255)); }
	
	public static FlatColor pickRandom(Random random) { return values()[random.nextInt(values().length)]; }
	public static FlatColor pickRandom() { return pickRandom(PublicRandom.getRandom()); }
}
