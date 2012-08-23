package de.peeeq.wurstscript.types;

import de.peeeq.wurstscript.ast.AstElement;


public class WurstTypeString extends WurstTypePrimitive {

	private static final WurstTypeString instance = new WurstTypeString();

	// make constructor private as we only need one instance
	private WurstTypeString() {
		super("string");
	}
	
	@Override
	public boolean isSubtypeOf(WurstType other, AstElement location) {
		return other instanceof WurstTypeString;
	}


	@Override
	public String getName() {
		return "string";
	}

	@Override
	public String getFullName() {
		return "string";
	}

	public static WurstTypeString instance() {
		return instance;
	}
	
}