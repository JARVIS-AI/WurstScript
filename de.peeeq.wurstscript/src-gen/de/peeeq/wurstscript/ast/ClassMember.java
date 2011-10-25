//generated by parseq
package de.peeeq.wurstscript.ast;

public interface ClassMember extends SortPos, ClassSlot{
	SortPos getParent();
	void setSource(WPos source);
	WPos getSource();
	void setVisibility(VisibilityModifier visibility);
	VisibilityModifier getVisibility();
	<T> T match(Matcher<T> s);
	void match(MatcherVoid s);
	public interface Matcher<T> {
		T case_GlobalVarDef(GlobalVarDef globalVarDef);
		T case_FuncDef(FuncDef funcDef);
	}

	public interface MatcherVoid {
		void case_GlobalVarDef(GlobalVarDef globalVarDef);
		void case_FuncDef(FuncDef funcDef);
	}

	ClassMember copy();
	public abstract void accept(WPackage.Visitor v);
	public abstract void accept(CompilationUnit.Visitor v);
	public abstract void accept(ClassMember.Visitor v);
	public abstract void accept(ClassSlots.Visitor v);
	public abstract void accept(TopLevelDeclaration.Visitor v);
	public abstract void accept(WEntities.Visitor v);
	public abstract void accept(ClassDef.Visitor v);
	public abstract void accept(WEntity.Visitor v);
	public abstract void accept(ClassSlot.Visitor v);
	public abstract void accept(TypeDef.Visitor v);
	public abstract void accept(PackageOrGlobal.Visitor v);
	public abstract void accept(WScope.Visitor v);
	public interface Visitor {
		void visit(ExprMemberMethod exprMemberMethod);
		void visit(OpModReal opModReal);
		void visit(NoTypeExpr noTypeExpr);
		void visit(VisibilityDefault visibilityDefault);
		void visit(StmtIf stmtIf);
		void visit(OpAssign opAssign);
		void visit(ExprThis exprThis);
		void visit(ExprRealVal exprRealVal);
		void visit(ExprVarAccess exprVarAccess);
		void visit(ExprFuncRef exprFuncRef);
		void visit(ExprCast exprCast);
		void visit(OpPlus opPlus);
		void visit(ExprStringVal exprStringVal);
		void visit(StmtErr stmtErr);
		void visit(StmtExitwhen stmtExitwhen);
		void visit(WParameter wParameter);
		void visit(ExprMemberArrayVar exprMemberArrayVar);
		void visit(OpUnequals opUnequals);
		void visit(ExprNull exprNull);
		void visit(OpNot opNot);
		void visit(OpDivInt opDivInt);
		void visit(ExprVarArrayAccess exprVarArrayAccess);
		void visit(OpEquals opEquals);
		void visit(FuncSignature funcSignature);
		void visit(ExprBoolVal exprBoolVal);
		void visit(WParameters wParameters);
		void visit(ArraySizes arraySizes);
		void visit(OpOr opOr);
		void visit(OpMult opMult);
		void visit(WStatements wStatements);
		void visit(OpMinus opMinus);
		void visit(StmtReturn stmtReturn);
		void visit(StmtDecRefCount stmtDecRefCount);
		void visit(TypeExpr typeExpr);
		void visit(OpLessEq opLessEq);
		void visit(VisibilityProtected visibilityProtected);
		void visit(StmtIncRefCount stmtIncRefCount);
		void visit(Arguments arguments);
		void visit(ExprBinary exprBinary);
		void visit(NoExpr noExpr);
		void visit(ExprUnary exprUnary);
		void visit(StmtDestroy stmtDestroy);
		void visit(OpDivReal opDivReal);
		void visit(OpAnd opAnd);
		void visit(StmtWhile stmtWhile);
		void visit(FuncDef funcDef);
		void visit(LocalVarDef localVarDef);
		void visit(Indexes indexes);
		void visit(StmtLoop stmtLoop);
		void visit(StmtSet stmtSet);
		void visit(GlobalVarDef globalVarDef);
		void visit(ExprIntVal exprIntVal);
		void visit(ExprMemberVar exprMemberVar);
		void visit(OpModInt opModInt);
		void visit(VisibilityPrivate visibilityPrivate);
		void visit(ExprNewObject exprNewObject);
		void visit(VisibilityPublicread visibilityPublicread);
		void visit(OpLess opLess);
		void visit(OpGreaterEq opGreaterEq);
		void visit(WPos wPos);
		void visit(ExprFunctionCall exprFunctionCall);
		void visit(VisibilityPublic visibilityPublic);
		void visit(OpGreater opGreater);
	}
	public static abstract class DefaultVisitor implements Visitor {
		@Override public void visit(ExprMemberMethod exprMemberMethod) {}
		@Override public void visit(OpModReal opModReal) {}
		@Override public void visit(NoTypeExpr noTypeExpr) {}
		@Override public void visit(VisibilityDefault visibilityDefault) {}
		@Override public void visit(StmtIf stmtIf) {}
		@Override public void visit(OpAssign opAssign) {}
		@Override public void visit(ExprThis exprThis) {}
		@Override public void visit(ExprRealVal exprRealVal) {}
		@Override public void visit(ExprVarAccess exprVarAccess) {}
		@Override public void visit(ExprFuncRef exprFuncRef) {}
		@Override public void visit(ExprCast exprCast) {}
		@Override public void visit(OpPlus opPlus) {}
		@Override public void visit(ExprStringVal exprStringVal) {}
		@Override public void visit(StmtErr stmtErr) {}
		@Override public void visit(StmtExitwhen stmtExitwhen) {}
		@Override public void visit(WParameter wParameter) {}
		@Override public void visit(ExprMemberArrayVar exprMemberArrayVar) {}
		@Override public void visit(OpUnequals opUnequals) {}
		@Override public void visit(ExprNull exprNull) {}
		@Override public void visit(OpNot opNot) {}
		@Override public void visit(OpDivInt opDivInt) {}
		@Override public void visit(ExprVarArrayAccess exprVarArrayAccess) {}
		@Override public void visit(OpEquals opEquals) {}
		@Override public void visit(FuncSignature funcSignature) {}
		@Override public void visit(ExprBoolVal exprBoolVal) {}
		@Override public void visit(WParameters wParameters) {}
		@Override public void visit(ArraySizes arraySizes) {}
		@Override public void visit(OpOr opOr) {}
		@Override public void visit(OpMult opMult) {}
		@Override public void visit(WStatements wStatements) {}
		@Override public void visit(OpMinus opMinus) {}
		@Override public void visit(StmtReturn stmtReturn) {}
		@Override public void visit(StmtDecRefCount stmtDecRefCount) {}
		@Override public void visit(TypeExpr typeExpr) {}
		@Override public void visit(OpLessEq opLessEq) {}
		@Override public void visit(VisibilityProtected visibilityProtected) {}
		@Override public void visit(StmtIncRefCount stmtIncRefCount) {}
		@Override public void visit(Arguments arguments) {}
		@Override public void visit(ExprBinary exprBinary) {}
		@Override public void visit(NoExpr noExpr) {}
		@Override public void visit(ExprUnary exprUnary) {}
		@Override public void visit(StmtDestroy stmtDestroy) {}
		@Override public void visit(OpDivReal opDivReal) {}
		@Override public void visit(OpAnd opAnd) {}
		@Override public void visit(StmtWhile stmtWhile) {}
		@Override public void visit(FuncDef funcDef) {}
		@Override public void visit(LocalVarDef localVarDef) {}
		@Override public void visit(Indexes indexes) {}
		@Override public void visit(StmtLoop stmtLoop) {}
		@Override public void visit(StmtSet stmtSet) {}
		@Override public void visit(GlobalVarDef globalVarDef) {}
		@Override public void visit(ExprIntVal exprIntVal) {}
		@Override public void visit(ExprMemberVar exprMemberVar) {}
		@Override public void visit(OpModInt opModInt) {}
		@Override public void visit(VisibilityPrivate visibilityPrivate) {}
		@Override public void visit(ExprNewObject exprNewObject) {}
		@Override public void visit(VisibilityPublicread visibilityPublicread) {}
		@Override public void visit(OpLess opLess) {}
		@Override public void visit(OpGreaterEq opGreaterEq) {}
		@Override public void visit(WPos wPos) {}
		@Override public void visit(ExprFunctionCall exprFunctionCall) {}
		@Override public void visit(VisibilityPublic visibilityPublic) {}
		@Override public void visit(OpGreater opGreater) {}
	}
}
