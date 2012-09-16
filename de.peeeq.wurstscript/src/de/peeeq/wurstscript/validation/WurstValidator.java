package de.peeeq.wurstscript.validation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import de.peeeq.wurstscript.ast.Annotation;
import de.peeeq.wurstscript.ast.AstElement;
import de.peeeq.wurstscript.ast.ClassDef;
import de.peeeq.wurstscript.ast.CompilationUnit;
import de.peeeq.wurstscript.ast.ConstructorDef;
import de.peeeq.wurstscript.ast.EnumDef;
import de.peeeq.wurstscript.ast.EnumMember;
import de.peeeq.wurstscript.ast.Expr;
import de.peeeq.wurstscript.ast.ExprBinary;
import de.peeeq.wurstscript.ast.ExprFuncRef;
import de.peeeq.wurstscript.ast.ExprFunctionCall;
import de.peeeq.wurstscript.ast.ExprMemberArrayVar;
import de.peeeq.wurstscript.ast.ExprMemberMethod;
import de.peeeq.wurstscript.ast.ExprMemberVar;
import de.peeeq.wurstscript.ast.ExprNewObject;
import de.peeeq.wurstscript.ast.ExprThis;
import de.peeeq.wurstscript.ast.ExprVarAccess;
import de.peeeq.wurstscript.ast.ExprVarArrayAccess;
import de.peeeq.wurstscript.ast.ExtensionFuncDef;
import de.peeeq.wurstscript.ast.FuncDef;
import de.peeeq.wurstscript.ast.FuncRef;
import de.peeeq.wurstscript.ast.FunctionDefinition;
import de.peeeq.wurstscript.ast.FunctionImplementation;
import de.peeeq.wurstscript.ast.FunctionLike;
import de.peeeq.wurstscript.ast.GlobalVarDef;
import de.peeeq.wurstscript.ast.HasModifier;
import de.peeeq.wurstscript.ast.HasTypeArgs;
import de.peeeq.wurstscript.ast.InterfaceDef;
import de.peeeq.wurstscript.ast.LocalVarDef;
import de.peeeq.wurstscript.ast.ModAbstract;
import de.peeeq.wurstscript.ast.ModConstant;
import de.peeeq.wurstscript.ast.ModOverride;
import de.peeeq.wurstscript.ast.ModStatic;
import de.peeeq.wurstscript.ast.Modifier;
import de.peeeq.wurstscript.ast.Modifiers;
import de.peeeq.wurstscript.ast.ModuleDef;
import de.peeeq.wurstscript.ast.ModuleInstanciation;
import de.peeeq.wurstscript.ast.NameDef;
import de.peeeq.wurstscript.ast.NameRef;
import de.peeeq.wurstscript.ast.NativeFunc;
import de.peeeq.wurstscript.ast.NativeType;
import de.peeeq.wurstscript.ast.NoDefaultCase;
import de.peeeq.wurstscript.ast.PackageOrGlobal;
import de.peeeq.wurstscript.ast.StmtDestroy;
import de.peeeq.wurstscript.ast.StmtIf;
import de.peeeq.wurstscript.ast.StmtReturn;
import de.peeeq.wurstscript.ast.StmtSet;
import de.peeeq.wurstscript.ast.StmtWhile;
import de.peeeq.wurstscript.ast.SwitchCase;
import de.peeeq.wurstscript.ast.SwitchStmt;
import de.peeeq.wurstscript.ast.TranslatedToImFunction;
import de.peeeq.wurstscript.ast.TupleDef;
import de.peeeq.wurstscript.ast.TypeDef;
import de.peeeq.wurstscript.ast.TypeExpr;
import de.peeeq.wurstscript.ast.TypeExprArray;
import de.peeeq.wurstscript.ast.TypeExprSimple;
import de.peeeq.wurstscript.ast.TypeParamDef;
import de.peeeq.wurstscript.ast.VarDef;
import de.peeeq.wurstscript.ast.VisibilityModifier;
import de.peeeq.wurstscript.ast.VisibilityPrivate;
import de.peeeq.wurstscript.ast.VisibilityProtected;
import de.peeeq.wurstscript.ast.VisibilityPublic;
import de.peeeq.wurstscript.ast.WImport;
import de.peeeq.wurstscript.ast.WPackage;
import de.peeeq.wurstscript.ast.WParameter;
import de.peeeq.wurstscript.ast.WPos;
import de.peeeq.wurstscript.ast.WScope;
import de.peeeq.wurstscript.ast.WStatement;
import de.peeeq.wurstscript.ast.WStatements;
import de.peeeq.wurstscript.ast.WurstModel;
import de.peeeq.wurstscript.attributes.CheckHelper;
import de.peeeq.wurstscript.attributes.NameResolution;
import de.peeeq.wurstscript.gui.ProgressHelper;
import de.peeeq.wurstscript.types.FunctionSignature;
import de.peeeq.wurstscript.types.WurstType;
import de.peeeq.wurstscript.types.WurstTypeArray;
import de.peeeq.wurstscript.types.WurstTypeBool;
import de.peeeq.wurstscript.types.WurstTypeClass;
import de.peeeq.wurstscript.types.WurstTypeCode;
import de.peeeq.wurstscript.types.WurstTypeEnum;
import de.peeeq.wurstscript.types.WurstTypeInt;
import de.peeeq.wurstscript.types.WurstTypeInterface;
import de.peeeq.wurstscript.types.WurstTypeModule;
import de.peeeq.wurstscript.types.WurstTypeNamedScope;
import de.peeeq.wurstscript.types.WurstTypeReal;
import de.peeeq.wurstscript.types.WurstTypeString;
import de.peeeq.wurstscript.types.WurstTypeTypeParam;
import de.peeeq.wurstscript.types.WurstTypeVoid;
import de.peeeq.wurstscript.utils.Utils;
import de.peeeq.wurstscript.validation.controlflow.DataflowAnomalyAnalysis;
import de.peeeq.wurstscript.validation.controlflow.ReturnsAnalysis;

/**
 * this class validates a pscript program
 * 
 * it has visit methods for different elements in the AST and checks whether
 * these are correct
 * 
 * the validation phase might not find all errors, code transformation and
 * optimization phases might detect other errors because they do a more
 * sophisticated analysis of the program
 * 
 * also note that many cases are already caught by the calculation of the
 * attributes
 * 
 */
public class WurstValidator {

	private WurstModel prog;
	private int functionCount;
	private int visitedFunctions;
	private List<Method> checkMethods = Lists.newArrayList();
	private HashMultimap<Class<?>, Method> typeToMethod = HashMultimap.create();
	private Multimap<WScope	, WScope> calledFunctions = HashMultimap.create();
	
	public WurstValidator(WurstModel root) {
		this.prog = root;
	}

	public void validate() {
		functionCount = countFunctions();
		visitedFunctions = 0;

//		prog.accept(this);
		// TODO reflection mechanism
		
		
		for (Method m : WurstValidator.class.getMethods()) {
			if (m.getAnnotation(CheckMethod.class) != null) {
				// this is a checkmethod
				checkMethods.add(m);
				if (m.getParameterTypes().length != 1) {
					throw new Error("check method must have exactly one parameter: " + m);
				}
			}
		}
		
		walkTree(prog);		
		postChecks();
//		for (CompileError e : attr.getErrors()) {
//			throw e;
//		}
//		throw new Error("exit..");
	}

	/**
	 * checks done after walking the tree
	 */
	private void postChecks() {
		Multimap<WScope, WScope> calledFunctionsTr = Utils.transientClosure(calledFunctions);
		for (WScope s : calledFunctionsTr.keySet()) {
			if (calledFunctionsTr.containsEntry(s, s)) {
				if (!calledFunctions.containsEntry(s, s)) {
					s.addError(Utils.printElement(s) + " has a cyclic dependency to itself.");
				}
			}
		}
		
	}

	private void walkTree(AstElement e) {
		check(e);
		for (int i=0; i<e.size(); i++) {
			walkTree(e.get(i));
		}
	}

	private void check(AstElement e) {
		if (!typeToMethod.containsKey(e.getClass())) {
			for (Method m : checkMethods) {
				if (m.getParameterTypes()[0].isInstance(e)) {
					typeToMethod.put(e.getClass(), m);
				}
			}
		}
		for (Method m : typeToMethod.get(e.getClass())) {
			try {
				m.invoke(this, e);
			} catch (Error t) {
				throw t;
			} catch (IllegalArgumentException t) {
				throw new Error(t);
			} catch (IllegalAccessException t) {
				throw new Error(t);
			} catch (InvocationTargetException t) {
				Throwable cause = t.getCause();
				if (cause instanceof Error) {
					throw (Error)cause;
				} else {
					throw new Error(cause);
				}
			}
		}
	}

	private int countFunctions() {
		final int functionCount[] = new int[1];
		prog.accept(new WurstModel.DefaultVisitor() {
			@CheckMethod
			public void visit(FuncDef f) {
				functionCount[0]++;
			}
		});
		return functionCount[0];
	}

	@CheckMethod
	public void checkStmtSet(StmtSet s) {

		NameDef nameDef = s.getUpdatedExpr().attrNameDef();
		if (!(nameDef instanceof VarDef)) {
			s.getUpdatedExpr().addError("Invalid assignment. This is not a variable, this is a " + Utils.printElement(nameDef));
		}
		
		WurstType leftType = s.getUpdatedExpr().attrTyp();
		WurstType rightType = s.getRight().attrTyp();

		checkAssignment(Utils.isJassCode(s), s, leftType, rightType);
		
		checkIfAssigningToConstant(s.getUpdatedExpr());
		
	}

	private void checkIfAssigningToConstant(final NameRef left) {
		left.match(new NameRef.MatcherVoid() {
			
			@Override
			public void case_ExprVarArrayAccess(ExprVarArrayAccess e) {
				
			}
			
			@Override
			public void case_ExprVarAccess(ExprVarAccess e) {
				checkVarNotConstant(left, e.attrNameDef());
			}
			
			@Override
			public void case_ExprMemberVar(ExprMemberVar e) {
				if (e.attrNameDef() instanceof WParameter) {
					// we have an assignment to a tuple variable
					// check whether left side is 'this' or a constant variable
					if (e.getLeft() instanceof ExprThis) {
						e.addError("Cannot change 'this'. Tuples are not classes.");
					} else if (e.getLeft() instanceof NameRef) {
						checkIfAssigningToConstant((NameRef) e.getLeft());
					} else {
						e.addError("Ok, so you are trying to assign something to the return value of a function. This wont do nothing. Tuples are not classes.");
					}
				}
				checkVarNotConstant(left, e.attrNameDef());
			}
			
			@Override
			public void case_ExprMemberArrayVar(ExprMemberArrayVar e) {
				
			}
		});
	}
	
	private void checkVarNotConstant(NameRef left, NameDef var) {
		if (var != null && var.attrIsConstant()) {
			left.addError("Cannot assign a new value to constant " + Utils.printElement(var));
		}
	}

	private void checkAssignment(boolean isJassCode, AstElement pos, WurstType leftType, WurstType rightType) {
		if (!rightType.isSubtypeOf(leftType, pos)) {
			if (isJassCode) {
				if (leftType instanceof WurstTypeReal && rightType instanceof WurstTypeInt) {
					// special case: jass allows to assign an integer to a real
					// variable
					return;
				}
			}
			pos.addError("Cannot assign " + rightType + " to " + leftType);
		}
		if (leftType instanceof WurstTypeNamedScope) {
			WurstTypeNamedScope ns = (WurstTypeNamedScope) leftType;
			if (ns.isStaticRef()) {
				pos.addError("Missing variable name in variable declaration.\n" +
				"Cannot assign to " + leftType);
			}
		}
	}

	@CheckMethod
	public void visit(LocalVarDef s) {
		checkVarName(s, false);
		if (s.getInitialExpr() instanceof Expr) {
			Expr initial = (Expr) s.getInitialExpr();
			WurstType leftType = s.attrTyp();
			WurstType rightType = initial.attrTyp();

			checkAssignment(Utils.isJassCode(s), s, leftType, rightType);
		}
	}

	private void checkVarName(VarDef s, boolean isConstant) {
		String varName = s.getName(); 
		if (!Utils.isJassCode(s)) {
			if (!Character.isLowerCase(varName.charAt(0))) {
				if (!varName.matches("[A-Z0-9_]+")) {
					s.addError("Variable names must start with a lower case character. (" + varName + ")");
				}
			}
		}
		if ( varName.matches("handle")) {
			s.addError("\"handle\" is not a valid variable name");
		}
	}
	
	@CheckMethod
	public void visit(WParameter wParameter) {
		checkVarName(wParameter, false);
	}

	@CheckMethod
	public void visit(GlobalVarDef s) {
		checkVarName(s, s.attrIsConstant());
		if (s.getInitialExpr() instanceof Expr) {
			Expr initial = (Expr) s.getInitialExpr();
			WurstType leftType = s.attrTyp();
			WurstType rightType = initial.attrTyp();
			checkAssignment(Utils.isJassCode(s), s, leftType, rightType);
		}
		
		
		if (s.attrTyp() instanceof WurstTypeArray && !s.attrIsStatic() && s.attrIsDynamicClassMember()) {
			s.addError("Array variables must be static.\n" +
			"Hint: use Lists for dynamic stuff.");
		}
	}

	@CheckMethod
	public void visit(StmtIf stmtIf) {
		WurstType condType = stmtIf.getCond().attrTyp();
		if (!(condType instanceof WurstTypeBool)) {
			stmtIf.getCond().addError("If condition must be a boolean but found " + condType);
		}
	}

	@CheckMethod
	public void visit(StmtWhile stmtWhile) {
		WurstType condType = stmtWhile.getCond().attrTyp();
		if (!(condType instanceof WurstTypeBool)) {
			stmtWhile.getCond().addError("While condition must be a boolean but found " + condType);
		}
	}

	@CheckMethod
	public void visit(ExtensionFuncDef func) {
		checkFunctionName(func);
	}

	private void checkFunctionName(FunctionDefinition f) {
		if (!Utils.isJassCode(f)) {
			if (Character.isUpperCase(f.getName().charAt(0))) {
				f.addError("Function names must start with an lower case character.");
			}
		}
	}

	

	

	
	private void checkReturn(FunctionLike func) {
		if (func.getBody().size() > 2) {
			new ReturnsAnalysis().execute(func);
		} else { // no body, check if in interface:
			if (func instanceof FuncDef) {
				FuncDef funcDef = (FuncDef) func;
				if (funcDef.getReturnTyp() instanceof TypeExpr && !(func.attrNearestStructureDef() instanceof InterfaceDef)) {
					func.addError("Function " + funcDef.getName() + " is missing a body. Use the 'skip' statement to define an empty body.");
				}
			}
		}
	}

	@CheckMethod
	public void checkReachability(WStatement s) {
		if (s.getParent() instanceof WStatements) {
			WStatements stmts = (WStatements) s.getParent();
			if (s.attrPreviousStatements().isEmpty()) {
				if (s.attrListIndex() > 0 || !(stmts.getParent() instanceof TranslatedToImFunction)) {
					s.addError("unreachable code");
				}
			}
		}
	}
	
	@CheckMethod
	public void visit(FuncDef func) {
		visitedFunctions++;
		func.getErrorHandler().setProgress(null, ProgressHelper.getValidatorPercent(visitedFunctions, functionCount));

		checkFunctionName(func);
		
		Map<TypeParamDef, WurstType> typeParamBinding = Collections.emptyMap();
		
		// check is override is correct:
		for (FunctionDefinition overriddenFunc : func.attrOverriddenFunctions()) {
			CheckHelper.checkIfIsRefinement(typeParamBinding, func, overriddenFunc, "Can't override function ", false);
		}

		if (func.attrIsAbstract() && func.getBody().size() > 2) {
			func.addError("Abstract function " + func.getName() + " must not have a body.");			
		}
	}
	
	
	@CheckMethod
	public void checkUninitializedVars(FunctionLike f) {
		boolean isAbstract = false;
		if (f instanceof FuncDef) {
			FuncDef func = (FuncDef) f;
			if (func.attrIsAbstract()) {
				isAbstract = true;
				if (func.getBody().size() > 2) {
					func.getBody().get(0).addError("The abstract function " + func.getName()
					+ " must not have any statements.");
				}
			}
		}
		if (!isAbstract) { // not abstract
			checkReturn(f);
			if (!Utils.isJassCode(f)) {
				new DataflowAnomalyAnalysis().execute(f);
			}
		}
	}

	@CheckMethod
	public void visit(ExprFunctionCall stmtCall) {
		String funcName = stmtCall.getFuncName();
		// calculating the exprType should reveal most errors:
		stmtCall.attrTyp();
		
		List<Expr> args = Lists.newArrayList();
		if (stmtCall.attrImplicitParameter() instanceof Expr) {
			args.add((Expr) stmtCall.attrImplicitParameter());
		}
		args.addAll(stmtCall.getArgs());
		checkParams(stmtCall, "", args, stmtCall.attrFunctionSignature());
		
		FunctionImplementation nearestFunc = stmtCall.attrNearestFuncDef();
		if (stmtCall.attrFuncDef() != null) {
			
			FunctionDefinition calledFunc = stmtCall.attrFuncDef();
			if (calledFunc.attrIsDynamicClassMember()) {
				if (!stmtCall.attrIsDynamicContext()) {
							stmtCall.addError("Cannot call dynamic function " + funcName  +
							" from static function " + nearestFunc.getName());
				}
			}
		}
		
		// special check for filter & condition:
		if (Utils.oneOf(funcName, "Condition", "Filter")) {
			Expr firstArg = stmtCall.getArgs().get(0);
			if (firstArg instanceof ExprFuncRef) {
				ExprFuncRef exprFuncRef = (ExprFuncRef) firstArg;
				FunctionDefinition f = exprFuncRef.attrFuncDef();
				if (f != null) {
					if (!(f.getReturnTyp().attrTyp() instanceof WurstTypeBool)) {
						firstArg.addError("Functions passed to Filter or Condition must return boolean.");
					}
				}
			}
		}
	}

//	private void checkParams(AstElement where, List<Expr> args, FunctionDefinition calledFunc) {
//		if (calledFunc == null) {
//			return;
//		}
//		List<PscriptType> parameterTypes = calledFunc.attrParameterTypes();
//		checkParams(where, args, parameterTypes);
//	}
	
	private void checkParams(AstElement where, String preMsg, List<Expr> args, FunctionSignature sig) {
		checkParams(where, preMsg, args, sig.getParamTypes());
	}

	private void checkParams(AstElement where, String preMsg, List<Expr> args, List<WurstType> parameterTypes) {
		if (args.size() > parameterTypes.size()) {
			where.addError(preMsg + "Too many parameters.");
			
		} else if (args.size() < parameterTypes.size()) {
			where.addError(preMsg + "Missing parameters.");
		} else {
			for (int i=0; i<args.size(); i++) {
				
				WurstType actual = args.get(i).attrTyp();
				WurstType expected = parameterTypes.get(i);
//				if (expected instanceof AstElementWithTypeArgs)
				if (!actual.isSubtypeOf(expected, where)) {
					args.get(i).addError(preMsg + "Expected " + expected + " as parameter " + (i+1) + " but  found " + actual);
				}
			}
		}
	}

	@CheckMethod
	public void visit(ExprBinary expr) {
		FunctionDefinition def = expr.attrFuncDef();
		if (def != null) {
			FunctionSignature sig = new FunctionSignature(def.attrParameterTypes(), def.getReturnTyp().attrTyp());
			List<Expr> args = Lists.newArrayList();
			args.add(expr.getLeft());
			args.add(expr.getRight());
			checkParams(expr, "Operator overloading problem: ", args, sig);
		}
	}
	
	@CheckMethod
	public void visit(ExprMemberMethod stmtCall) {
		// calculating the exprType should reveal all errors:
		stmtCall.attrTyp();
		
		List<Expr> args = Lists.newArrayList();
		if (stmtCall.attrImplicitParameter() instanceof Expr) {
			args.add((Expr) stmtCall.attrImplicitParameter());
		}
		args.addAll(stmtCall.getArgs());
		checkParams(stmtCall, "", args, stmtCall.attrFunctionSignature());
	}

	

	@CheckMethod
	public void visit(ExprNewObject stmtCall) {
		stmtCall.attrTyp();
		stmtCall.attrConstructorDef();
	}

	@CheckMethod
	public void visit(Modifiers modifiers) {
		boolean hasVis = false;
		boolean isStatic = false;
		for (Modifier m : modifiers) {
			if (m instanceof VisibilityModifier) {
				if (hasVis) {
					m.addError("Each element can only have one visibility modifier (public, private, ...)");
				}
				hasVis = true;
			} else if (m instanceof ModStatic) {
				if (isStatic) {
					m.addError("double static? - what r u trying to do?");
				}
				isStatic = true;
			}
		}
	}

	@CheckMethod
	public void visit(StmtReturn s) {
		FunctionImplementation func = s.attrNearestFuncDef();
		if (func == null) {
			s.addError("return statements can only be used inside functions");
			return;
		}
		WurstType returnType = func.getReturnTyp().attrTyp();
		if (s.getReturnedObj() instanceof Expr) {
			Expr returned = (Expr) s.getReturnedObj();
			if (returnType.isSubtypeOf(WurstTypeVoid.instance(), s)) {
				s.addError("Cannot return a value from a function which returns nothing");
			} else {
				WurstType returnedType = returned.attrTyp();
				if (!returnedType.isSubtypeOf(returnType, s)) {
					s.addError("Cannot return " + returnedType + ", expected expression of type "
					+ returnType);
				}
			}
		} else { // empty return
			if (!returnType.isSubtypeOf(WurstTypeVoid.instance(), s)) {
				s.addError("Missing return value");
			}
		}
	}

	@CheckMethod
	public void visit(ClassDef classDef) {
		checkTypeName(classDef.getSource(), classDef.getName());
		
		// calculate all functions to find possible errors
		Map<String, FuncDef> functions = classDef.attrAllFunctions();
		
		// calculate private names to get overridden funcs
		classDef.attrVisibleNamesPrivate();
		
		if (!classDef.attrIsAbstract()) {
			// check that there are no abstract functions in a class
			for (FunctionDefinition f : functions.values()) {
				if (f.attrIsAbstract()) {
					classDef.addError("The abstract method " + f.getName()
					+ " must be implemented in class " + classDef.getName() + ".");
				}
			}
		}
		
		
		
		// check overridden methods
		if (classDef.getExtendedClass() instanceof TypeExpr) {
			WurstType extendedType = classDef.getExtendedClass().attrTyp();
			if (extendedType instanceof WurstTypeClass) {
				WurstTypeClass ptc = (WurstTypeClass) extendedType;
				Map<TypeParamDef, WurstType> typeParamMapping = ptc.getTypeArgBinding();
				ClassDef superClass = ptc.getClassDef();
				Multimap<String, NameDef> superClassFunctions = superClass.attrVisibleNamesProtected();
				for (FunctionDefinition f : functions.values()) {
					// TODO is this transitive?
					FunctionDefinition superClassFunction = getFunction(superClassFunctions, f.getName());
					if (superClassFunction == null) {
						if (f.attrIsOverride() && f.attrOverriddenFunctions().size() == 0) {
							f.addError("Function " + f.getName() + " does not exist in super type.");
						}
					} else {
						if (!f.attrIsOverride()) {
							f.addError("Function " + f.getName() + " must have the override annotation.");
						}
						
						CheckHelper.checkIfIsRefinement(typeParamMapping , f, superClassFunction, "Cannot extend class because of function ", true);
					}
				}
			} else {
				classDef.getExtendedClass().addError("Cannot extend " + extendedType + ". " +
				"Only classes can be extended.");
			}
		} else {
			for (FunctionDefinition f : functions.values()) {
				if (f.attrIsOverride() && f.attrOverriddenFunctions().size() == 0) {
					f.addError("Function " + f.getName() + " uses override notation but there is no superclass.");
				}
			}
		}
	}

	private FunctionDefinition getFunction(
			Multimap<String, NameDef> superClassFunctions, String name) {
		for (NameDef n : superClassFunctions.get(name)) {
			if (n instanceof FunctionDefinition) {
				return (FunctionDefinition) n;
			}
		}
		return null;
	}

	private void checkTypeName(WPos source, String name) {
		if (!Character.isUpperCase(name.charAt(0))) {
			source.addError("Type names must start with upper case characters.");
		}
	}

	@CheckMethod
	public void visit(ModuleDef moduleDef) {
		checkTypeName(moduleDef.getSource(), moduleDef.getName());
		// calculate all functions to find possible errors
		moduleDef.attrAllFunctions();
	}
	

	@CheckMethod
	public void visit(StmtDestroy stmtDestroy) {
		WurstType typ = stmtDestroy.getDestroyedObj().attrTyp();
		if (typ instanceof WurstTypeModule) {
			
		} else if (typ instanceof WurstTypeClass) {
			WurstTypeClass c = (WurstTypeClass) typ;
			if (c.isStaticRef()) {
				stmtDestroy.addError("Cannot destroy class " + typ);
			}
			calledFunctions.put(stmtDestroy.attrNearestScope(), c.getClassDef().getOnDestroy()); 
		} else {
			stmtDestroy.addError("Cannot destroy objects of type " + typ);
			return;
		}
	}
	
	@CheckMethod 
	public void visit(ExprVarAccess e) {
		checkVarRef(e, e.attrIsDynamicContext());
	}

	
	
	@CheckMethod
	public void visit(WImport wImport) {
		if (wImport.attrImportedPackage() == null) {
			wImport.addError("Could not find imported package " + wImport.getPackagename());
		}
	}

	/**
	 * check if the nameRef e is accessed correctly
	 * i.e. not using a dynamic variable from a static context
	 * @param e
	 * @param dynamicContext
	 */
	private void checkVarRef(NameRef e, boolean dynamicContext) {
		NameDef def = e.attrNameDef();
		if (def instanceof GlobalVarDef) {
			GlobalVarDef g = (GlobalVarDef) def;
			if (g.attrIsDynamicClassMember() && !dynamicContext) {
				e.addError("Cannot reference dynamic variable " +e.getVarName() + " from static context.");
			}
		}
	}
	
	@CheckMethod
	public void checkTypeBinding(HasTypeArgs e) {
		for (Entry<TypeParamDef, WurstType> t : e.attrTypeParameterBindings().entrySet()) {
			WurstType typ = t.getValue();
			if (!(typ instanceof WurstTypeInt)
					&& !(typ instanceof WurstTypeNamedScope)
					&& !(typ instanceof WurstTypeTypeParam)) {
				e.addError("Type parameters can only be bound to ints and class types, but " +
				"not to " + typ);
			}
		}
	}
	
	@CheckMethod
	public void checkFuncRef(FuncRef ref) {
		FunctionDefinition called = ref.attrFuncDef();
		calledFunctions.put(ref.attrNearestScope(), called);
	}
	
	@CheckMethod
	public void checkFuncRef(ExprFuncRef ref) {
		FunctionDefinition called = ref.attrFuncDef();
		if (called == null) {
			return;
		}
		if (ref.attrTyp() instanceof WurstTypeCode) {
			if (called.attrParameterTypes().size() > 0) {
				ref.addError("Can only use functions without parameters in 'code' function references.");
			}
		}
	}
	
	@CheckMethod
	public void checkModifiers(final HasModifier e) {
		for (final Modifier m : e.getModifiers()) {
			final StringBuilder error = new StringBuilder();
			
			e.match(new HasModifier.MatcherVoid() {
				
				@Override
				public void case_WParameter(WParameter wParameter) {
					check(ModConstant.class);
				}
				
				@Override
				public void case_TypeParamDef(TypeParamDef typeParamDef) {
					error.append("Type Parameters must not have modifiers");
				}
				
				@Override
				public void case_NativeType(NativeType nativeType) {
					check(VisibilityPublic.class);
				}
				
				private void check(Class<? extends Modifier> ...allowed) {
					boolean isAllowed = false;
					for (Class<? extends Modifier> a : allowed) {
						if (a.isInstance(m)) {
							isAllowed  = true;
							break;
						}
					}
					if (!isAllowed) {
						error.append("Modifier " + printMod(m) + " not allowed for " +
								Utils.printElement(e) + ".\n Allowed are the following" +
								" modifiers: ");
						boolean first = true;
						for (Class<? extends Modifier> c : allowed) {
							if (!first) {
								error.append(", ");
							}
							error.append(printMod(c));
							first = false;
						}
					}
				}

				@Override
				public void case_NativeFunc(NativeFunc nativeFunc) {
					check(VisibilityPublic.class, Annotation.class);
				}
				
				@Override
				public void case_ModuleInstanciation(ModuleInstanciation moduleInstanciation) {
					check(VisibilityPrivate.class, VisibilityProtected.class);
				}
				
				@Override
				public void case_ModuleDef(ModuleDef moduleDef) {
					check(VisibilityPublic.class);
				}
				
				@SuppressWarnings("unchecked")
				@Override
				public void case_LocalVarDef(LocalVarDef localVarDef) {
					check(ModConstant.class);
				}
				
				@SuppressWarnings("unchecked")
				@Override
				public void case_GlobalVarDef(GlobalVarDef g) {
					if (g.attrNearestClassOrModule() != null) {
						check(VisibilityPrivate.class, VisibilityProtected.class,
								ModStatic.class, ModConstant.class);
					} else {
						check(VisibilityPublic.class, ModConstant.class);
					}
				}
				
				@SuppressWarnings("unchecked")
				@Override
				public void case_FuncDef(FuncDef f) {
					if (f.attrNearestStructureDef() != null) {
						check(VisibilityPrivate.class, VisibilityProtected.class,
								ModAbstract.class, ModOverride.class, ModStatic.class);
					} else {
						check(VisibilityPublic.class, Annotation.class);
					}
				}
				
				@Override
				public void case_ExtensionFuncDef(ExtensionFuncDef extensionFuncDef) {
					check(VisibilityPublic.class, Annotation.class);
				}
				
				@Override
				public void case_ConstructorDef(ConstructorDef constructorDef) {
					check(VisibilityPrivate.class);
				}
				
				@Override
				public void case_ClassDef(ClassDef classDef) {
					check(VisibilityPublic.class, ModAbstract.class);
				}

				@Override
				public void case_InterfaceDef(InterfaceDef interfaceDef) {
					check(VisibilityPublic.class);
				}

				@Override
				public void case_TupleDef(TupleDef tupleDef) {
					check(VisibilityPublic.class);
				}

				@Override
				public void case_WPackage(WPackage wPackage) {
					check();
				}

				@Override
				public void case_EnumDef(EnumDef enumDef) {
					check(VisibilityPublic.class);
				}

				@Override
				public void case_EnumMember(EnumMember enumMember) {
					check();
				}

			});
			if (error.length() > 0) {
				e.addError(error.toString());
			}
		}
	}

	protected String printMod(Class<? extends Modifier> c) {
		String name = c.getSimpleName().toLowerCase();
		name = name.replaceAll("^(mod|visibility)", "");
		name = name.replaceAll("impl$", "");
		return name;
	}

	protected String printMod(Modifier m) {
		if (m instanceof Annotation) {
			return ((Annotation) m).getAnnotationType();
		}
		return printMod(m.getClass());
	}
	
	@CheckMethod
	public void checkConstructor(ConstructorDef d) {
		if (d.attrNearestClassOrModule() instanceof ModuleDef) {
			if (d.getParameters().size() > 0) {
				d.getParameters().addError("Module constructors must not have parameters.");
			}
		}
		ClassDef c = d.attrNearestClassDef();
		if (c != null && c.attrExtendedClass() != null) {
			;
			// check if super constructor is called correctly...
			// TODO check constr.
			ConstructorDef sc = d.attrSuperConstructor();
			if (sc == null) {
				d.addError("No super constructor found.");
			} else {
				List<WurstType> paramTypes = Lists.newArrayList();
				for (WParameter p : sc.getParameters()) {
					paramTypes.add(p.attrTyp());
				}
				checkParams(d, "Incorrect call to super constructor: ", d.getSuperArgs(), paramTypes);
			}
		}
	}
	
	@CheckMethod
	public void checkInstanceDef(ClassDef classDef) {
		for (WurstTypeInterface interfaceType : classDef.attrImplementedInterfaces()) {
			InterfaceDef interfaceDef = interfaceType.getInterfaceDef();
			Map<TypeParamDef, WurstType> typeParamMapping = interfaceType.getTypeArgBinding();
			// TODO check type mapping

			nextFunction: 
			for (FuncDef i_funcDef : interfaceDef.getMethods()) {
				Collection<NameDef> c_funcDefs = classDef.attrVisibleNamesPrivate().get(i_funcDef.getName());
				for (NameDef c_nameDef : c_funcDefs) {
					if (c_nameDef instanceof FuncDef) {
						FuncDef c_funcDef = (FuncDef) c_nameDef;

						CheckHelper.checkIfIsRefinement(typeParamMapping, i_funcDef, c_funcDef, "Cannot implement interface because of function ", true);
						continue nextFunction;
					}
				}
				if (i_funcDef.getBody().size() <= 2) {
					classDef.addError("The class " + classDef.getName() + " must implement the function " +
					i_funcDef.getName() + ".");
				}
			}
		}
		
		if (!classDef.attrIsAbstract() && classDef.attrExtendedClass() != null) {
			for (Entry<String, NameDef> e : classDef.attrExtendedClass().attrVisibleNamesPrivate().entries()) {
				if (e.getValue() instanceof FuncDef) {
					FuncDef f = (FuncDef) e.getValue();
					if (f.attrIsAbstract()) {
						boolean implemented = false;
						Collection<NameDef> c_funcDefs = classDef.attrVisibleNamesPrivate().get(f.getName());
						for (NameDef c_nameDef : c_funcDefs) {
							if (c_nameDef instanceof FuncDef) {
								FuncDef f2 = (FuncDef) c_nameDef;
								if (!f2.attrIsAbstract()) {
									implemented = true;
								}
							}
						}
						if (!implemented) {
							classDef.addError("Class " + classDef.getName() + " must implement the abstract function " +
									f.getName() + " from class " + f.attrNearestClassDef().getName());
						}
					}
				}
			}
		}
	}
	
	@CheckMethod
	public void checkInterfaceDef(InterfaceDef i) {
		checkTypeName(i.getSource(), i.getName());
		// TODO check if functions are refinements
	}
	 
	@CheckMethod
	public void checkNewObj(ExprNewObject e) {
		ConstructorDef constr = e.attrConstructorDef();
		if (constr != null) {
			calledFunctions.put(e.attrNearestScope(), constr);
			if (constr.attrNearestClassDef().attrIsAbstract()) {
				e.addError("Cannot create an instance of the abstract class " + constr.attrNearestClassDef().getName());
				return;
			} 
			checkParams(e, "Wrong object creation: ", e.getArgs(), e.attrFunctionSignature());
		}
		
	}
	
	
	@CheckMethod
	public void nameDefsMustNotBeNamedAfterJassNativeTypes(NameDef n) {
		PackageOrGlobal p = n.attrNearestPackage();
		if (p == null) {
			System.out.println("...");
			n.addError("Not in package or global: " + n.getName());
			return;
		}
		checkIfTypeDefExists(n, p);
		if (p instanceof WPackage) {
			// check global scope
			p = p.getParent().attrNearestPackage();
			checkIfTypeDefExists(n, p);
		}
	}

	private void checkIfTypeDefExists(NameDef n, PackageOrGlobal p) {
		if (n instanceof WPackage) {
			// TODO check that there is no other package with same name?
			return;
		}
		List<TypeDef> types = NameResolution.searchTypedName(TypeDef.class, n.getName(), p, false);
		types.remove(n);
		if (types.size() > 0) {
			n.addError("The definition for "+Utils.printElement(n)+" defines the same name as the type definition " + Utils.printElement(types.get(0)));
		}
//		for (NameDef e : p.attrVisibleNamesPrivate().get(n.getName())) {
//			if (e != n && e instanceof TypeDef) {
//				attr.addError(n.getSource(), "The definition for "+Utils.printElement(n)+" defines the same name as the type definition " + Utils.printElement(e));
//			}
//		}
	}
	
	
	@CheckMethod
	public void checkMemberVar(ExprMemberVar e) {
		if (e.getVarName().length() == 0) {
			e.addError("Incomplete member access.");
		} if (e.getParent() instanceof WStatements) {
			e.addError("Incomplete statement.");
		}
	}
	
	@CheckMethod
	public void checkPackageName(CompilationUnit cu) {
		if (cu.getPackages().size() == 1 && cu.getFile().endsWith(".wurst")) {
			// only one package in a wurst file
			WPackage p = cu.getPackages().get(0);
			if (!cu.getFile().endsWith(p.getName()+".wurst")) {
				p.addError("The file must have the same name as the package " + p.getName());
			}
		}
	}
	
	@CheckMethod
	public void checkBannedFunctions(ExprFunctionCall e) {
		String[] banned = new String[] {"TriggerRegisterVariableEvent", "ExecuteFunc"};
		for (String name : banned) {
			if (e.getFuncName().equals(name)) {
				e.addError("The function " + name + " is not allowed in Wurst.");
			}
		}
	}
	
	private boolean isViableSwitchtype(Expr expr) {
		WurstType typ = expr.attrTyp();
		if( typ.equalsType(WurstTypeInt.instance(), null) 
				|| typ.equalsType(WurstTypeString.instance(), null) 
				|| (typ instanceof WurstTypeEnum) ) {
			return true;
			
		}else {
			return false;
		}
	}
	
	@CheckMethod
	public void checkSwitch(SwitchStmt s) {
		if (! isViableSwitchtype(s.getExpr()))
			s.addError("The type " + s.getExpr().attrTyp() + " is not viable as switchtype.");
		else {
			for (SwitchCase c : s.getCases()) {		
				if( !s.getExpr().attrTyp().equalsType(c.getExpr().attrTyp(), c)) {
					c.addError("The type " + c.getExpr().attrTyp() + " does not match the switchtype "
							+  s.getExpr().attrTyp() + ".");
				}
			}
		}
		if(s.getExpr().attrTyp() instanceof WurstTypeEnum) {
			WurstTypeEnum wurstTypeEnum = (WurstTypeEnum) s.getExpr().attrTyp();
			if(s.getSwitchDefault() instanceof NoDefaultCase)
				nextMember: for( EnumMember e : wurstTypeEnum.getDef().getMembers()) {
					String name = e.getName();
					for (SwitchCase c : s.getCases()) {		
						if( c.getExpr() instanceof NameRef) {
							NameRef exprVarAccess = (NameRef) c.getExpr();
							if (exprVarAccess.attrNameDef() == e) {
								continue nextMember;
							}
						}
					}
					s.addError("Enum member " + e.getName() + " from enum " + wurstTypeEnum.getName() + " not covered in switchstatement and no default found.");
				}
				
		}
		// TODO check if all cases for switch are covered
		
	}

	public static void computeFlowAttributes(AstElement node) {
		if (node instanceof WStatement) {
			WStatement s = (WStatement) node;
			s.attrNextStatements();
		}
		
		// traverse childs
		for (int i =0; i<node.size(); i++) {
			computeFlowAttributes(node.get(i));
		}
	}
	
	@CheckMethod
	public void chechCodeArrays(TypeExprArray e) {
		if (e.getBase() instanceof TypeExprSimple) {
			TypeExprSimple base = (TypeExprSimple) e.getBase();
			if (base.getTypeName().equals("code")) {
				e.addError("Code arrays are not supported. Try using an array of triggers or conditionfuncs.");
			}
			
		}
	}
	
}
