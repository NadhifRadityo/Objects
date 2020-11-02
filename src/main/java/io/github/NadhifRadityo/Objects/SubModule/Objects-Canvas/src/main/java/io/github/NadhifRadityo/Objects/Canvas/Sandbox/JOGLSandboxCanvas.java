package io.github.NadhifRadityo.Objects.Canvas.Sandbox;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import io.github.NadhifRadityo.Objects.Canvas.CanvasPanel;
import io.github.NadhifRadityo.Objects.Canvas.Managers.*;
import io.github.NadhifRadityo.Objects.Canvas.RenderHints.AntiAlias;
import io.github.NadhifRadityo.Objects.Canvas.RenderHints.FontChanger;
import io.github.NadhifRadityo.Objects.Canvas.Shapes.Text;
import io.github.NadhifRadityo.Objects.Canvas.Shapes.Rectangle;
import io.github.NadhifRadityo.Objects.Thread.Handler;
import io.github.NadhifRadityo.Objects.Thread.HandlerThread;
import io.github.NadhifRadityo.Objects.Utilizations.*;
import io.github.NadhifRadityo.Objects.Utilizations.ColorPalettes.Defo;
import io.github.NadhifRadityo.Objects.Utilizations.Library.JogampLibrary;
import io.github.NadhifRadityo.Objects.Utilizations.Library.LibraryUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public abstract class JOGLSandboxCanvas implements GLEventListener {
	public JFrame debugFrame;
	public CanvasPanel debugCanvas;

	public Dimension windowDim;
	public JFrame mainFrame;
	public CanvasPanel mainCanvas;

	public JOGLSandboxCanvas(Dimension dimension, Dimension debugDim) {
		this.windowDim = dimension;
		this.mainFrame = new JFrame();
		mainFrame.setSize(dimension);
		mainFrame.setPreferredSize(dimension);
		mainFrame.setUndecorated(true);
		mainFrame.setBackground(new Color(0, 0, 0, 0));
		mainFrame.setLayout(new GridLayout());
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.mainCanvas = new CanvasPanel();
		mainCanvas.setSize(DimensionUtils.getMaxDimension());
		mainCanvas.setPreferredSize(DimensionUtils.getMaxDimension());
		mainFrame.add(mainCanvas);

		GraphicModifierManager graphicManager = new GraphicModifierManager(true, -2);
		graphicManager.addModifier(new AntiAlias(true));
		graphicManager.addModifier(new FontChanger(new Font("Segoe UI", Font.PLAIN, 20)));
		graphicManager.addModifier(new GraphicModifierManager.CustomGraphicModifier() {
			@Override public void draw(Graphics g) {
				mainCanvas.setSize(mainFrame.getWidth(), mainFrame.getHeight());
				g.setColor(new Color(0, 0, 0, 0));
				g.fillRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
				g.setColor(Defo.Clouds.getColor());
//				g.fillRect(0, 0, mainCanvas.getWidth() / 2, mainCanvas.getHeight() / 2);
			} @Override public void reset(Graphics g) { }
		}, -2);
		mainCanvas.addManager(graphicManager);

		if(debugDim == null) { debugFrame = null; debugCanvas = null;
		} else {
			debugFrame = new JFrame();
			debugFrame.setSize(debugDim);
			debugFrame.setPreferredSize(debugDim);
			debugFrame.setLayout(new GridLayout());
			debugFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			debugFrame.setLocation(mainFrame.getWidth(), mainFrame.getY());

			debugCanvas = new CanvasPanel();
			debugCanvas.setSize(DimensionUtils.getMaxDimension());
			debugCanvas.setPreferredSize(DimensionUtils.getMaxDimension());
			debugFrame.add(debugCanvas);

			GraphicModifierManager debugGraphicManager = new GraphicModifierManager(true, -2);
			debugGraphicManager.addModifier(new AntiAlias(true));
			debugGraphicManager.addModifier(new FontChanger(new Font("Segoe UI", Font.PLAIN, 20)));
			debugCanvas.addManager(debugGraphicManager);
		} initMain();
	}

	Color fpsColor = Color.BLACK;
	boolean insertFpsValue = false;
	boolean insertLagValue = false;
	long lagValue = 0;

	protected GLJPanel glPanel;

	public void initMain() {
		HandlerThread handlerThread = new HandlerThread("Animate Stuffs");
		handlerThread.start();
		handlerThread.getLooper().setExceptionHandler(Throwable::printStackTrace);
		Handler handler = new Handler(handlerThread.getLooper());

		Text fpsText = new Text(0, 0, "FPS: 0");
		List<Long> lates = new ArrayList<>();

		FrameLooperManager frameLooper = new FrameLooperManager(true, true, handler);
		frameLooper.setFps(120);
		frameLooper.addUpdater(new FrameLooperManager.FrameUpdater() {
			long delta; long lastTime; int frameCount;
			@Override public void update() {
				if(lagValue > 0) try { Thread.sleep(lagValue); } catch (InterruptedException e) { e.printStackTrace(); }
				long current = System.currentTimeMillis();
				delta += current - lastTime; lastTime = current;
				frameCount++; if(delta < 1000) return;
				delta = delta % 1000;
				fpsText.setText("FPS: " + frameCount); frameCount = 0;
			}
		});
		frameLooper.addUpdater(new FrameLooperManager.FrameUpdater() { // Debug painter and GLCanvas resizer
			int lastWidth, lastHeight;
			@Override public void update() {
				if((lastWidth != mainFrame.getWidth() || lastHeight != mainFrame.getHeight()) && glPanel != null) {
					lastWidth = mainFrame.getWidth(); lastHeight = mainFrame.getHeight();
					glPanel.setSize(lastWidth, lastHeight);
				} debugCanvas.repaint();
			}
		}, -1);
		frameLooper.setRunBehindCallback(lates::add);
		// Start the loop!
		mainCanvas.addManager(frameLooper);

		if(debugCanvas != null) {
			GraphicModifierManager debugGraphicModifier = new GraphicModifierManager(false);
			debugGraphicModifier.addSprite(fpsText);
			debugGraphicModifier.addModifier(new GraphicModifierManager.CustomGraphicModifier(fpsText) {
				Color oldColor;
				@Override public void draw(Graphics g) {
					fpsText.setPosition(debugFrame.getWidth() - fpsText.getWidth() - 17, 0);
					oldColor = g.getColor(); g.setColor(fpsColor);
				} @Override public void reset(Graphics g) { g.setColor(oldColor); }
			});
			debugGraphicModifier.addSprite(new Rectangle(0, 0, 0, 0) {
				int graphWidth = 2; boolean labelsAdded = false;
				Text topText, midText, botText;
				@Override public void draw(Graphics g) {
					if(!labelsAdded) {
						topText = new Text(0, y, ""); debugGraphicModifier.addSprite(topText);
						midText = new Text(0, height / 2 - y - 17, ""); debugGraphicModifier.addSprite(midText);
						botText = new Text(0, height - y - 27,  "0ms"); debugGraphicModifier.addSprite(botText);
						labelsAdded = true;
					}

					width = debugCanvas.getWidth(); height = debugCanvas.getHeight() - y;
					if(lates.size() == 0) return; if(lates.size() > (width / graphWidth)) lates.remove(0);
					long highest = NumberUtils.max(ArrayUtils.toPrimitive(lates.toArray(new Long[0])));
					if(highest == 0) return;

					topText.setY(y);
					midText.setY(height / 2 - y - 17);
					botText.setY(height - y - 27);
					topText.setText(highest + "ms");
					midText.setText(highest / 2 + "ms");
					for(int i = 0; i < lates.size(); i++) {
						int dataHeight = (int) NumberUtils.map(lates.get(i), 0, highest, 0, height);
						g.fillRect(i * graphWidth, y + height - dataHeight, graphWidth, dataHeight);
					}
				}
			}, -1);
			debugCanvas.addManager(debugGraphicModifier);
		}

		// -------- START DRAWING --------
//		System.setProperty("jogl.verbose", "true");
//		System.setProperty("jogl.debug", "true");
//		System.setProperty("jogamp.debug", "true");
		System.setProperty("newt.debug.Window", "true");

		GLJPanelBridgeManager glJPanelManager = new GLJPanelBridgeManager();
		mainCanvas.addManager(glJPanelManager);
//		ComponentBridgeManager componentManager = new ComponentBridgeManager();
//		mainCanvas.addManager(componentManager);

		GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(profile);
		capabilities.setDoubleBuffered(false);
		capabilities.setAlphaBits(8);
//		GLWindow glWindow = GLWindow.create(capabilities);
//		glWindow.setSize(400, 400);
//		glWindow.addGLEventListener(this);
//		glWindow.setVisible(true);
//		NewtCanvasAWT newtCanvasAWT = new NewtCanvasAWT(glWindow);
//		JFrame dumpPeer = new JFrame();
//		dumpPeer.setSize(400, 400);
//		dumpPeer.add(newtCanvasAWT);
////		dumpPeer.addNotify();
//		dumpPeer.setVisible(true);
//		newtCanvasAWT.setupPrint(1, 1, 0, -1, -1);
//		componentManager.addComponent(newtCanvasAWT);

		glPanel = new GLJPanel(capabilities);
		glPanel.setOpaque(false);
		glPanel.setSize(400, 400);
		glPanel.addGLEventListener(this);
		glJPanelManager.addGLJPanel(glPanel);

		// For testing performance.
//		EventQueue.invokeLater(() -> {
//			JFrame frame = new JFrame("JOGL SANDBOX");
//			frame.add(glPanel);
//			frame.setSize( 500, 400 );
//			frame.setVisible(true);
//		});
//
//		FPSAnimator animator = new FPSAnimator(120);
////		animator.add(glPanel);
//		animator.add(glWindow);
//		animator.start();

		// --------- END DRAWING ---------

		MouseListenerManager mouseManager = new MouseListenerManager(true);
		mouseManager.addListener(new MouseListenerManager.CustomMouseListener(null) {
			@Override public void mouseClicked(MouseEvent e) {
				mainCanvas.requestFocus();
			}
		});
		mainCanvas.addManager(mouseManager);

		KeyListenerManager keyManager = new KeyListenerManager(true);
		keyManager.addListener(new KeyListenerManager.CustomKeyListener(null, KeyEvent.VK_F) {
			public void keyPressed(KeyEvent e) { System.out.println("Insert Fps"); insertFpsValue = true; fpsColor = Color.GREEN; }
		});
		keyManager.addListener(new KeyListenerManager.CustomKeyListener(null, KeyEvent.VK_L) {
			public void keyPressed(KeyEvent e) { System.out.println("Insert Lag"); insertLagValue = true; fpsColor = Color.RED; }
		});
		keyManager.addListener(new KeyListenerManager.CustomKeyListener(null) {
			String inputNumber = "";
			public void keyPressed(KeyEvent e) {
				if(!insertLagValue && !insertFpsValue) { inputNumber = ""; return; }
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_ENTER) {
					fpsColor = Color.BLACK;
					if(insertLagValue) { insertLagValue = false;
						lagValue = (e.getKeyCode() == KeyEvent.VK_ESCAPE) ? lagValue : Long.parseLong(inputNumber);
					} if(insertFpsValue) { insertFpsValue = false;
						frameLooper.setFps((e.getKeyCode() == KeyEvent.VK_ESCAPE) ? frameLooper.getFps() : Integer.parseInt(inputNumber));
					} inputNumber = ""; return;
				} if(!NumberUtils.isNumber(e.getKeyChar() + "")) return;
				inputNumber += e.getKeyChar();
				System.out.println(inputNumber);
			}
		});
		keyManager.addListener(new KeyListenerManager.CustomKeyListener(null, KeyEvent.VK_ALT) {
			public void keyPressed(KeyEvent e) { mainFrame.setSize(windowDim); }
		});
		mainCanvas.addManager(keyManager);
	}

	public static void extract() throws IOException, URISyntaxException {
		if(!JogampLibrary.isInited()) {
			System.out.println("Extracting native libraries...");
			File currentJar = JarUtils.getCurrentJar();
			if(currentJar != null) LibraryUtils.initLibraries();
			if(!JogampLibrary.isInited()) {
				System.out.println("Libraries not found! Try from classpaths");
				System.out.println("You might running this from your IDE");
				String[] classPaths = System.getProperty("java.class.path").split(File.pathSeparator);
				for(String classPath : classPaths) {
					if(!FileUtils.getFileExtension(classPath).equalsIgnoreCase("jar")) continue;
					LibraryUtils.initLibraries(new File(classPath), LibraryUtils.defExtractDirFile);
				}
			} if(!JogampLibrary.isInited()) System.err.println("Library not found!");
		}
	}
}
