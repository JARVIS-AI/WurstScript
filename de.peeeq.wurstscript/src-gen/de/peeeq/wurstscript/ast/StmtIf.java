//generated by parseq
package de.peeeq.wurstscript.ast;

public interface StmtIf extends SortPos, WStatement {
	SortPos getParent();
	void setSource(WPos source);
	WPos getSource();
	void setCond(Expr cond);
	Expr getCond();
	void setThenBlock(WStatements thenBlock);
	WStatements getThenBlock();
	void setElseBlock(WStatements elseBlock);
	WStatements getElseBlock();
	StmtIf copy();
	public abstract void accept(WPackage.Visitor v);
	public abstract void accept(StmtIf.Visitor v);
	public abstract void accept(CompilationUnit.Visitor v);
	public abstract void accept(ClassMember.Visitor v);
	public abstract void accept(ClassSlots.Visitor v);
	public abstract void accept(TopLevelDeclaration.Visitor v);
	public abstract void accept(StmtWhile.Visitor v);
	public abstract void accept(WEntities.Visitor v);
	public abstract void accept(OnDestroyDef.Visitor v);
	public abstract void accept(ClassDef.Visitor v);
	public abstract void accept(WEntity.Visitor v);
	public abstract void accept(ConstructorDef.Visitor v);
	public abstract void accept(WStatement.Visitor v);
	public abstract void accept(JassToplevelDeclaration.Visitor v);
	public abstract void accept(ClassSlot.Visitor v);
	public abstract void accept(TypeDef.Visitor v);
	public abstract void accept(WStatements.Visitor v);
	public abstract void accept(InitBlock.Visitor v);
	public abstract void accept(FuncDef.Visitor v);
	public abstract void accept(FunctionDefinition.Visitor v);
	public abstract void accept(StmtLoop.Visitor v);
	public abstract void accept(PackageOrGlobal.Visitor v);
	public abstract void accept(WScope.Visitor v);
	public interface Visitor {
		void visit(ExprMemberMethod exprMemberMethod);
		void visit(OpModReal opModReal);
		void visit(NoTypeExpr noTypeExpr);
		void visit(StmtIf stmtIf);
		void visit(OpAssign opAssign);
		void visit(ExprThis exprThis);
		void visit(ExprRealVal exprRealVal);
		void visit(ExprVarAccess exprVarAccess);
		void visit(ExprFuncRef exprFuncRef);
		void visit(ExprCast exprCast);
		void visit(ExprStringVal exprStringVal);
		void visit(OpPlus opPlus);
		void visit(StmtErr stmtErr);
		void visit(StmtExitwhen stmtExitwhen);
		void visit(ExprMemberArrayVar exprMemberArrayVar);
		void visit(OpUnequals opUnequals);
		void visit(ExprNull exprNull);
		void visit(OpNot opNot);
		void visit(OpDivInt opDivInt);
		void visit(ExprVarArrayAccess exprVarArrayAccess);
		void visit(OpEquals opEquals);
		void visit(ExprBoolVal exprBoolVal);
		void visit(ArraySizes arraySizes);
		void visit(OpOr opOr);
		void visit(OpMult opMult);
		void visit(WStatements wStatements);
		void visit(OpMinus opMinus);
		void visit(StmtReturn stmtReturn);
		void visit(StmtDecRefCount stmtDecRefCount);
		void visit(TypeExpr typeExpr);
		void visit(OpLessEq opLessEq);
		void visit(StmtIncRefCount stmtIncRefCount);
		void visit(NoExpr noExpr);
		void visit(ExprBinary exprBinary);
		void visit(Arguments arguments);
		void visit(ExprUnary exprUnary);
		void visit(StmtDestroy stmtDestroy);
		void visit(OpDivReal opDivReal);
		void visit(OpAnd opAnd);
		void visit(StmtWhile stmtWhile);
		void visit(LocalVarDef localVarDef);
		void visit(Indexes indexes);
		void visit(StmtLoop stmtLoop);
		void visit(StmtSet stmtSet);
		void visit(ExprMemberVar exprMemberVar);
		void visit(ExprIntVal exprIntVal);
		void visit(OpModInt opModInt);
		void visit(ExprNewObject exprNewObject);
		void visit(OpLess opLess);
		void visit(OpGreaterEq opGreaterEq);
		void visit(ExprFunctionCall exprFunctionCall);
		void visit(WPos wPos);
		void visit(OpGreater opGreater);
	}
	public static abstract class DefaultVisitor implements Visitor {
		@Override public void visit(ExprMemberMethod exprMemberMethod) {}
		@Override public void visit(OpModReal opModReal) {}
		@Override public void visit(NoTypeExpr noTypeExpr) {}
		@Override public void visit(StmtIf stmtIf) {}
		@Override public void visit(OpAssign opAssign) {}
		@Override public void visit(ExprThis exprThis) {}
		@Override public void visit(ExprRealVal exprRealVal) {}
		@Override public void visit(ExprVarAccess exprVarAccess) {}
		@Override public void visit(ExprFuncRef exprFuncRef) {}
		@Override public void visit(ExprCast exprCast) {}
		@Override public void visit(ExprStringVal exprStringVal) {}
		@Override public void visit(OpPlus opPlus) {}
		@Override public void visit(StmtErr stmtErr) {}
		@Override public void visit(StmtExitwhen stmtExitwhen) {}
		@Override public void visit(ExprMemberArrayVar exprMemberArrayVar) {}
		@Override public void visit(OpUnequals opUnequals) {}
		@Override public void visit(ExprNull exprNull) {}
		@Override public void visit(OpNot opNot) {}
		@Override public void visit(OpDivInt opDivInt) {}
		@Override public void visit(ExprVarArrayAccess exprVarArrayAccess) {}
		@Override public void visit(OpEquals opEquals) {}
		@Override public void visit(ExprBoolVal exprBoolVal) {}
		@Override public void visit(ArraySizes arraySizes) {}
		@Override public void visit(OpOr opOr) {}
		@Override public void visit(OpMult opMult) {}
		@Override public void visit(WStatements wStatements) {}
		@Override public void visit(OpMinus opMinus) {}
		@Override public void visit(StmtReturn stmtReturn) {}
		@Override public void visit(StmtDecRefCount stmtDecRefCount) {}
		@Override public void visit(TypeExpr typeExpr) {}
		@Override public void visit(OpLessEq opLessEq) {}
		@Override public void visit(StmtIncRefCount stmtIncRefCount) {}
		@Override public void visit(NoExpr noExpr) {}
		@Override public void visit(ExprBinary exprBinary) {}
		@Override public void visit(Arguments arguments) {}
		@Override public void visit(ExprUnary exprUnary) {}
		@Override public void visit(StmtDestroy stmtDestroy) {}
		@Override public void visit(OpDivReal opDivReal) {}
		@Override public void visit(OpAnd opAnd) {}
		@Override public void visit(StmtWhile stmtWhile) {}
		@Override public void visit(LocalVarDef localVarDef) {}
		@Override public void visit(Indexes indexes) {}
		@Override public void visit(StmtLoop stmtLoop) {}
		@Override public void visit(StmtSet stmtSet) {}
		@Override public void visit(ExprMemberVar exprMemberVar) {}
		@Override public void visit(ExprIntVal exprIntVal) {}
		@Override public void visit(OpModInt opModInt) {}
		@Override public void visit(ExprNewObject exprNewObject) {}
		@Override public void visit(OpLess opLess) {}
		@Override public void visit(OpGreaterEq opGreaterEq) {}
		@Override public void visit(ExprFunctionCall exprFunctionCall) {}
		@Override public void visit(WPos wPos) {}
		@Override public void visit(OpGreater opGreater) {}
	}
}
