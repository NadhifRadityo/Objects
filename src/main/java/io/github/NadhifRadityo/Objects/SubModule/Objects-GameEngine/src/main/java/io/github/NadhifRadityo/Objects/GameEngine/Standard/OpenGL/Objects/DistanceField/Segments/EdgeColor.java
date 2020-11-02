package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Segments;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec2;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.Contour;
import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects.DistanceField.ContourShape;

import java.util.ArrayList;

import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.abs;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.cross;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.dot;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.length;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.normalize;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.sin;
import static io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils.sub;

public enum EdgeColor {
	BLACK(0), RED(1), GREEN(2), YELLOW(3), BLUE(4), MAGENTA(5), CYAN(6), WHITE(7);

	public final int c;
	EdgeColor(int c) { this.c = c; }

	public static EdgeColor from(int c) {
		for(EdgeColor color : values())
			if(color.c == c) return color;
		return null;
	}

	protected static final EdgeColor[] start = new EdgeColor[] { CYAN, MAGENTA, YELLOW };
	protected static boolean isCorner(Vec2 aDir, Vec2 bDir, double crossThreshold) { return dot(aDir, bDir) <= 0 || abs(cross(aDir, bDir)) > crossThreshold; }
	protected static double estimateEdgeLength(EdgeSegment<?> edge) {
		double len = 0; Vec2 prev = edge.point(0);
		for(int i = 1; i <= 4; ++i) {
			Vec2 cur = edge.point(1. / 4 * i);
			len += length(sub(cur, prev));
			prev = cur;
		} return len;
	}
	protected static EdgeColor switchColor(EdgeColor color, long seed, EdgeColor banned) {
		EdgeColor combined = from(color.c & banned.c);
		if(combined == RED || combined == GREEN || combined == BLUE) return from(combined.c ^ WHITE.c);
		if(color == BLACK || color == WHITE) return start[(int) (seed % 3)];
		int shifted = color.c << (1 + (seed & 1)); return from((shifted | shifted >> 3) & WHITE.c);
	}
	protected static long switchColorSeed(EdgeColor color, long seed, EdgeColor banned) {
		EdgeColor combined = from(color.c & banned.c);
		if(combined == RED || combined == GREEN || combined == BLUE) return seed;
		if(color == BLACK || color == WHITE) return seed / 3; return seed >> 1;
	}
	protected static EdgeColor switchColor(EdgeColor color, long seed) { return switchColor(color, seed, BLACK); }
	protected static long switchColorSeed(EdgeColor color, long seed) { return switchColorSeed(color, seed, BLACK); }

	protected static <SEGMENT extends EdgeSegment<SEGMENT>> EdgeSegment<?>[] splitInThirds(SEGMENT segment) {
		SEGMENT part1 = segment.create();
		SEGMENT part2 = segment.create();
		SEGMENT part3 = segment.create();
		segment.splitInThirds(part1, part2, part3);
		return new EdgeSegment[] { part1, part2, part3 };
	}
	@SuppressWarnings({"unchecked", "ConstantConditions"})
	public static <SEGMENT extends EdgeSegment<SEGMENT>> void edgeColoringSimple(ContourShape shape, double angleThreshold, long seed) {
		double crossThreshold = sin(angleThreshold);
		ArrayList<Integer> corners = new ArrayList<>();
		for(Contour contour : shape.getContours()) {
			corners.clear();
			if(!contour.getEdges().isEmpty()) {
				Vec2 prevDirection = contour.getEdges().get(contour.getEdges().size() - 1).direction(1);
				int index = 0;
				for(EdgeSegment<?> edge : contour.getEdges()) {
					if(isCorner(normalize(prevDirection), normalize(edge.direction(0)), crossThreshold))
						corners.add(index);
					prevDirection = edge.direction(1);
					index++;
				}
			}

			if(corners.isEmpty())
				for(EdgeSegment<?> edge : contour.getEdges())
					edge.setColor(WHITE);
			else if(corners.size() == 1) {
				EdgeColor[] colors = new EdgeColor[] { WHITE, WHITE, null };
				colors[2] = switchColor(colors[0], seed);
				seed = switchColorSeed(colors[0], seed);
				colors[0] = colors[2];
				colors[2] = switchColor(colors[0], seed);
				int corner = corners.get(0);
				if(contour.getEdges().size() >= 3) {
					int m = contour.getEdges().size();
					for(int i = 0; i < m; i++)
						contour.getEdges().get((corner + i) % m).setColor(colors[(int) (3 + 2.875 * i / (m - 1) - 1.4375 + .5) - 3 + 1]);
				} else if(contour.getEdges().size() >= 1) {
					EdgeSegment<?>[] parts = new EdgeSegment[6];
					EdgeSegment<?>[] splits = splitInThirds((SEGMENT) contour.getEdges().get(0));
					parts[3 * corner] = splits[0];
					parts[3 * corner + 1] = splits[1];
					parts[3 * corner + 2] = splits[2];
					if(contour.getEdges().size() >= 2) {
						splits = splitInThirds((SEGMENT) contour.getEdges().get(1));
						parts[3 - 3 * corner] = splits[0];
						parts[4 - 3 * corner] = splits[1];
						parts[5 - 3 * corner] = splits[2];
						parts[0].setColor(colors[0]); parts[1].setColor(colors[0]);
						parts[2].setColor(colors[1]); parts[3].setColor(colors[1]);
						parts[4].setColor(colors[2]); parts[5].setColor(colors[2]);
					} else {
						parts[0].setColor(colors[0]);
						parts[1].setColor(colors[1]);
						parts[2].setColor(colors[2]);
					}
					contour.getEdges().clear();
					for(EdgeSegment<?> part : parts) contour.getEdges().add(part);
				}
			} else {
				int cornerCount = corners.size();
				int spline = 0;
				int start = corners.get(0);
				int m = contour.getEdges().size();
				EdgeColor color = WHITE;
				EdgeColor temp = switchColor(color, seed);
				seed = switchColorSeed(color, seed);
				color = temp;
				EdgeColor initialColor = color;
				for(int i = 0; i < m; i++) {
					int index = (start + i) % m;
					if(spline + 1 < cornerCount && corners.get(spline + 1) == index) {
						spline++;
						EdgeColor banned = from((spline == cornerCount - 1 ? 1 : 0) * initialColor.c);
						temp = switchColor(color, seed, banned);
						seed = switchColorSeed(color, seed, banned);
						color = temp;
					} contour.getEdges().get(index).setColor(color);
				}
			}
		}
	}

	protected static class EdgeColoringInkTrapCorner {
		int index;
		double prevEdgeLengthEstimate;
		boolean minor;
		EdgeColor color;
	}
	@SuppressWarnings({"unchecked", "ConstantConditions"})
	public static <SEGMENT extends EdgeSegment<SEGMENT>> void edgeColoringInkTrap(ContourShape shape, double angleThreshold, long seed) {
		double crossThreshold = sin(angleThreshold);
		ArrayList<EdgeColoringInkTrapCorner> corners = new ArrayList<>();
		for(Contour contour : shape.getContours()) {
			// Identify corners
			double splineLength = 0;
			corners.clear();
			if(!contour.getEdges().isEmpty()) {
				Vec2 prevDirection = contour.getEdges().get(contour.getEdges().size() - 1).direction(1);
				int index = 0;
				for(EdgeSegment<?> edge : contour.getEdges()) {
					if(isCorner(normalize(prevDirection), normalize(edge.direction(0)), crossThreshold)) {
						EdgeColoringInkTrapCorner corner = new EdgeColoringInkTrapCorner();
						corner.index = index;
						corner.prevEdgeLengthEstimate = splineLength;
						corners.add(corner);
						splineLength = 0;
					}
					splineLength += estimateEdgeLength(edge);
					prevDirection = edge.direction(1);
				}
			}

			// Smooth contour
			if(corners.isEmpty())
				for(EdgeSegment<?> edge : contour.getEdges())
					edge.setColor(WHITE);
			else if(corners.size() == 1) {
				EdgeColor[] colors = new EdgeColor[] { WHITE, WHITE, null };
				colors[2] = switchColor(colors[0], seed);
				seed = switchColorSeed(colors[0], seed);
				colors[0] = colors[2];
				colors[2] = switchColor(colors[0], seed);
				int corner = corners.get(0).index;
				if(contour.getEdges().size() >= 3) {
					int m = contour.getEdges().size();
					for(int i = 0; i < m; i++)
						contour.getEdges().get((corner + i) % m).setColor(colors[(int) (3 + 2.875 * i / (m - 1) - 1.4375 + .5) - 3 + 1]);
				} else if(contour.getEdges().size() >= 1) {
					EdgeSegment<?>[] parts = new EdgeSegment[6];
					EdgeSegment<?>[] splits = splitInThirds((SEGMENT) contour.getEdges().get(0));
					parts[3 * corner] = splits[0];
					parts[3 * corner + 1] = splits[1];
					parts[3 * corner + 2] = splits[2];
					if(contour.getEdges().size() >= 2) {
						splits = splitInThirds((SEGMENT) contour.getEdges().get(1));
						parts[3 - 3 * corner] = splits[0];
						parts[4 - 3 * corner] = splits[1];
						parts[5 - 3 * corner] = splits[2];
						parts[0].setColor(colors[0]); parts[1].setColor(colors[0]);
						parts[2].setColor(colors[1]); parts[3].setColor(colors[1]);
						parts[4].setColor(colors[2]); parts[5].setColor(colors[2]);
					} else {
						parts[0].setColor(colors[0]);
						parts[1].setColor(colors[1]);
						parts[2].setColor(colors[2]);
					}
					contour.getEdges().clear();
					for(EdgeSegment<?> part : parts) contour.getEdges().add(part);
				}
			}
			// Multiple corners
			else {
				int cornerCount = corners.size();
				int majorCornerCount = cornerCount;
				if(cornerCount > 3) {
					corners.get(0).prevEdgeLengthEstimate += splineLength;
					for(int i = 0; i < cornerCount; ++i) {
						if(
								corners.get(i).prevEdgeLengthEstimate > corners.get((i + 1) % cornerCount).prevEdgeLengthEstimate &&
								corners.get((i + 1) % cornerCount).prevEdgeLengthEstimate < corners.get((i + 2) % cornerCount).prevEdgeLengthEstimate
						) {
							corners.get(i).minor = true;
							majorCornerCount--;
						}
					}
				}
				EdgeColor color = WHITE;
				EdgeColor initialColor = BLACK;
				EdgeColor temp;
				for(EdgeColoringInkTrapCorner corner : corners) {
					if(!corner.minor) {
						majorCornerCount--;
						EdgeColor banned = from((majorCornerCount == 0 ? 1 : 0) * initialColor.c);
						temp = switchColor(color, seed, banned);
						seed = switchColorSeed(color, seed, banned);
						color = temp;
						corner.color = color;
						if(initialColor.c == 0) initialColor = color;
					}
				}
				for(int i = 0; i < cornerCount; ++i) {
					if(corners.get(i).minor) {
						EdgeColor nextColor = corners.get((i + 1) % cornerCount).color;
						corners.get(i).color = from((color.c & nextColor.c) ^ WHITE.c);
					} else color = corners.get(i).color;
				}
				int spline = 0;
				int start = corners.get(0).index;
				color = corners.get(0).color;
				int m = contour.getEdges().size();
				for(int i = 0; i < m; i++) {
					int index = (start + i) % m;
					if(spline + 1 < cornerCount && corners.get(spline+1).index == index)
						color = corners.get(++spline).color;
					contour.getEdges().get(index).color = color;
				}
			}
		}
	}
}
