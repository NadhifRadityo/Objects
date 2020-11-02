package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Objects;

import io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenGL.Program;

@SuppressWarnings("rawtypes")
public class ObjectsProgram<PACK extends ObjectsProgram.GlobalVarGLSLInfoPack> extends OpenGLObjectHolder {
	protected Program<? extends Number> program;
	protected PACK pack;

	public ObjectsProgram(Program<? extends Number> program, PACK variablesPack) {
		super(program.getGL());
		this.program = program;
		this.pack = variablesPack;
	}

	public void onProgramBind() { }
	public void onProgramUnbind() { }

	public Program<? extends Number> getProgram() { return program; }
	public PACK getVariablesPack() { return pack; }
	public boolean isVariablesIdentified() { assertVariablesPack(); return pack.isVariablesIdentified(); }
	public void identifyVariables() { assertVariablesPack(); pack.identifyVariables(program); }
	public void identifyVariables(String... variablesName) { assertVariablesPack(); pack.identifyVariables(program, variablesName); }

	public void setProgram(Program<? extends Number> program) {
		assertNotDead(); assertCreated();
		if(!this.program.isDead())
			this.program.detachObject(this);
		this.program = program;
		this.program.attachObject(this);
		if(pack != null)
			identifyVariables();
	}
	public void setVariablesPack(PACK variablesPack) {
		assertNotDead(); assertCreated();
		this.pack = variablesPack;
		if(variablesPack != null)
			identifyVariables();
	}

	@Override protected void arrange() {
		program.attachObject(this);
		if(pack != null && !isVariablesIdentified())
			identifyVariables();
	}
	@Override protected void disarrange() {
		program.detachObject(this);
	}

	public void assertVariablesPack() { if(pack == null) throw newException("VariablesPack is null"); }
	public void assertNotVariablesPack() { if(pack != null) throw newException("VariablesPack is not null"); }

	public interface GlobalVarGLSLInfoPack {
		Program.GlobalVarGLSLInfo getVariableInformations(int id);
		<ID extends Number> void identifyVariables(Program<ID> program, String... variablesName);
		<ID extends Number> void identifyVariables(Program<ID> program);
		boolean isVariablesIdentified();

		default void throwUnidentifiedID() { throw newException("ID is not identified"); }
	}
}
