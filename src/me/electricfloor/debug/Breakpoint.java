package me.electricfloor.debug;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Breakpoint {
	
	@SuppressWarnings("unused")
	private StackTraceElement[] stackTrace;
	
	@SuppressWarnings("unused")
	private ArrayList<Field> fields;

	public Breakpoint() {
		this.stackTrace = null;
		this.fields = null;
	}
	
	public void collect() {
		this.stackTrace = Thread.currentThread().getStackTrace();
	}
	
}
