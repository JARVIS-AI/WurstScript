//generated by parseq
package de.peeeq.wurstscript.ast;

class VisibilityPublicImpl implements VisibilityPublic, SortPosIntern {
	VisibilityPublicImpl(WPos source) {
		if (source == null) throw new IllegalArgumentException();
		((SortPosIntern)source).setParent(this);
		this.source = source;
	}

	private SortPos parent;
	public SortPos getParent() { return parent; }
	public void setParent(SortPos parent) {
		if (parent != null && this.parent != null) { throw new Error("Parent already set."); }
		this.parent = parent;
	}

	private WPos source;
	public void setSource(WPos source) {
		if (source == null) throw new IllegalArgumentException();
		((SortPosIntern)this.source).setParent(null);
		((SortPosIntern)source).setParent(this);
		this.source = source;
	} 
	public WPos getSource() { return source; }

	@Override public void accept(JassToplevelDeclaration.Visitor v) {
		source.accept(v);
		v.visit(this);
	}
	@Override public void accept(JassGlobalBlock.Visitor v) {
		source.accept(v);
		v.visit(this);
	}
	@Override public void accept(NativeType.Visitor v) {
		source.accept(v);
		v.visit(this);
	}
	@Override public void accept(ClassMember.Visitor v) {
		source.accept(v);
		v.visit(this);
	}
	@Override public void accept(FunctionDefinition.Visitor v) {
		source.accept(v);
		v.visit(this);
	}
	@Override public void accept(VarDef.Visitor v) {
		source.accept(v);
		v.visit(this);
	}
	@Override public void accept(ClassSlot.Visitor v) {
		source.accept(v);
		v.visit(this);
	}
	@Override public void accept(TypeDef.Visitor v) {
		source.accept(v);
		v.visit(this);
	}
	@Override public void accept(WEntity.Visitor v) {
		source.accept(v);
		v.visit(this);
	}
	@Override public void accept(ConstructorDef.Visitor v) {
		source.accept(v);
		v.visit(this);
	}
	@Override public void accept(FuncDef.Visitor v) {
		source.accept(v);
		v.visit(this);
	}
	@Override public void accept(ClassSlots.Visitor v) {
		source.accept(v);
		v.visit(this);
	}
	@Override public void accept(WPackage.Visitor v) {
		source.accept(v);
		v.visit(this);
	}
	@Override public void accept(ClassDef.Visitor v) {
		source.accept(v);
		v.visit(this);
	}
	@Override public void accept(WEntities.Visitor v) {
		source.accept(v);
		v.visit(this);
	}
	@Override public void accept(GlobalVarDef.Visitor v) {
		source.accept(v);
		v.visit(this);
	}
	@Override public void accept(WScope.Visitor v) {
		source.accept(v);
		v.visit(this);
	}
	@Override public void accept(TopLevelDeclaration.Visitor v) {
		source.accept(v);
		v.visit(this);
	}
	@Override public void accept(VisibilityPublic.Visitor v) {
		source.accept(v);
		v.visit(this);
	}
	@Override public void accept(VisibilityModifier.Visitor v) {
		source.accept(v);
		v.visit(this);
	}
	@Override public void accept(CompilationUnit.Visitor v) {
		source.accept(v);
		v.visit(this);
	}
	@Override public void accept(PackageOrGlobal.Visitor v) {
		source.accept(v);
		v.visit(this);
	}
	@Override public <T> T match(VisibilityModifier.Matcher<T> matcher) {
		return matcher.case_VisibilityPublic(this);
	}
	@Override public void match(VisibilityModifier.MatcherVoid matcher) {
		matcher.case_VisibilityPublic(this);
	}

}
