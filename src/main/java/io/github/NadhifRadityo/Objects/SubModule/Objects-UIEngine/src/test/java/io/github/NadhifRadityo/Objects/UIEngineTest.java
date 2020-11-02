package io.github.NadhifRadityo.Objects;

import io.github.NadhifRadityo.Objects.AWTComponent.AnsiSupportedTextArea;
import io.github.NadhifRadityo.Objects.AWTComponent.ModernScrollPane;
import io.github.NadhifRadityo.Objects.AWTComponent.PooledObjectMonitor;
import io.github.NadhifRadityo.Objects.Console.Logger;
import io.github.NadhifRadityo.Objects.Thread.Handler;
import io.github.NadhifRadityo.Objects.Utilizations.DimensionUtils;

import javax.swing.*;
import java.awt.*;

public class UIEngineTest {
	public static void main(String... args) throws Exception {
		PooledObjectMonitorTest();
//		AnsiSupportedTextAreaTest(logger);
	}

	public static void PooledObjectMonitorTest() throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		JFrame frame = new JFrame();

		PooledObjectMonitor.getMainPooledObjectMonitor().setSize(DimensionUtils.getMaxDimension());
		PooledObjectMonitor.getMainPooledObjectMonitor().setPreferredSize(DimensionUtils.getMaxDimension());
		Component[] components = PooledObjectMonitor.getMainPooledObjectMonitor().getComponents();
		for(Component component : components) {
			component.setSize(DimensionUtils.getMaxDimension());
			component.setPreferredSize(DimensionUtils.getMaxDimension());
		} frame.getContentPane().add(PooledObjectMonitor.getMainPooledObjectMonitor());

		Handler handler = Handler.getMain();
		handler.post(() -> frame.setVisible(true));
		PooledObjectMonitor.setUpdateHandler(handler);
	}

	public static void AnsiSupportedTextAreaTest(Logger logger) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		JFrame frame = new JFrame();

		AnsiSupportedTextArea ansiSupportedTextArea = new AnsiSupportedTextArea();
		ModernScrollPane scrollPane = new ModernScrollPane(ansiSupportedTextArea);
		scrollPane.setSize(DimensionUtils.getMaxDimension());
		scrollPane.setPreferredSize(DimensionUtils.getMaxDimension());
		frame.getContentPane().add(scrollPane);
		Handler.getMain().post(() -> frame.setVisible(true));
		logger.addListener(record -> ansiSupportedTextArea.append(record.asString()));
	}
}
