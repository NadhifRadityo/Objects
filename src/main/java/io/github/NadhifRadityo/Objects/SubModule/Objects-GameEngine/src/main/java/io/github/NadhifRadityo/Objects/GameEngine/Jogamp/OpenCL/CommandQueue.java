package io.github.NadhifRadityo.Objects.GameEngine.Jogamp.OpenCL;

public class CommandQueue extends io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL.CommandQueue<Long, CommandQueue> {
	public CommandQueue(Device device, int properties) {
		super(device, properties);
	}
	public CommandQueue(Device device, int... properties) {
		super(device, properties);
	}
}
