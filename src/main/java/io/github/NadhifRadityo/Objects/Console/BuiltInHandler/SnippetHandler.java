package io.github.NadhifRadityo.Objects.Console.BuiltInHandler;

import io.github.NadhifRadityo.Objects.Console.LogHandler;
import io.github.NadhifRadityo.Objects.Console.LogRecord;
import io.github.NadhifRadityo.Objects.Pool.Pool;

import java.util.ArrayList;

public class SnippetHandler implements LogHandler {

	protected SnippetListener snippet;
	public SnippetHandler(SnippetListener snippet) {
		this.snippet = snippet;
	}

	protected SnippetListener getSnippet() { return snippet; }
	protected void setSnippet(SnippetListener snippet) { this.snippet = snippet; }

	@Override public void manipulateLog(LogRecord record) {
		ArrayList<Object> args = record.getArgs();
		ArrayList<Object> toSnip = Pool.tryBorrow(Pool.getPool(ArrayList.class)); try {
			int position = snippet.get(toSnip, this, record); args.addAll(position, toSnip);
		} finally { Pool.returnObject(ArrayList.class, toSnip); }
	}

	interface SnippetListener {
		int get(ArrayList<Object> snippet, SnippetHandler handler, LogRecord record);
	}
}
