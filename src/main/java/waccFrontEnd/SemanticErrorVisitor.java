package waccFrontEnd;

import antlr.WaccParser;
import antlr.WaccParserBaseVisitor;
import waccFrontEnd.AST.Assignments.ArrayLitNode;
import waccFrontEnd.AST.Assignments.NewPairNode;
import waccFrontEnd.AST.Assignments.PairElemNode;
import waccFrontEnd.AST.ErrorNode;
import waccFrontEnd.AST.Expressions.*;
import waccFrontEnd.AST.Functions.FuncCallNode;
import waccFrontEnd.AST.Functions.FuncNode;
import waccFrontEnd.AST.Functions.ParamNode;
import waccFrontEnd.AST.Node;
import waccFrontEnd.AST.ProgNode;
import waccFrontEnd.AST.Statements.*;
import waccFrontEnd.AST.Types.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SemanticErrorVisitor extends WaccParserBaseVisitor<Node> {

  // ---------------------------------------------------------------------- //
  // Extension of parser base visitor to visit parser ctxs to check for     //
  // semantic errors and simultaneously builds a node representation for    //
  // for them to be used in the internal representation (AST).              //
  // ---------------------------------------------------------------------- //

  private boolean visitedFunctions;

  private final SymbolTable symbolTable = SymbolTable.getInstance();

  // HELPER FUNCTIONS

  private String generateSemanticError(int lineNumber) {
    return "Semantic Error at line:" + lineNumber;
  }

  // Returns required stack size for the provided scope.
  private int getScopeSize(Collection<Node> nodes){
    int bytes = 0;
    for (Node node : nodes) {
      if (isNodeComparableWithChar(node) || isNodeComparableWithBool(node)) {
        bytes += symbolTable.getOneByte();
      } else {
        bytes += symbolTable.getFourBytes();
      }
    }
    return bytes;
  }

  // Transforms a node into a type node so we can get the type representation
  // for any statement, expression or function in order to check what they are
  // comparable with.
  private Node transformNode(Node node) {
    if (node instanceof ParamNode) {
      return ((ParamNode) node).getType();
    }
    if (node instanceof IdentNode) {
      return transformNode(((IdentNode) node).getIdentType());
    }
    if (node instanceof BinaryExpressionNode) {
      switch ((((BinaryExpressionNode) node).getOperator())){
        case "*":
        case "/":
        case "%":
        case "+":
        case "-":
          return new IntTypeNode();
        default:
          return new BoolTypeNode();
      }
    }
    if (node instanceof UnaryExpressionNode) {
      switch ((((UnaryExpressionNode) node).getOperator())) {
        case "-":
        case "len":
        case "ord":
          return new IntTypeNode();
        case "chr":
          return new CharTypeNode();
        default:
          return new BoolTypeNode();
      }
    }
    if (node instanceof ArrayElemNode) {
      List<Node> indices = ((ArrayElemNode) node).getIndices();
      Node arrayNode = transformNode(((ArrayElemNode) node).getIdentNode());
      for (int i =0; i < indices.size(); i++) {
        if (arrayNode instanceof ArrayLitNode) {
          arrayNode = ((ArrayLitNode) arrayNode).getInnerType();
        } else if (arrayNode instanceof ArrayTypeNode){
          arrayNode = ((ArrayTypeNode) arrayNode).getType();
        }else{
          arrayNode = transformNode(arrayNode);
          i--;
        }
      }
      return transformNode(arrayNode);
    }
    if (node instanceof PairElemNode) {
       Node pair = transformNode(((PairElemNode) node).getIdentNode());
      if (((PairElemNode) node).isFst()) {
        if (pair instanceof NullNode) {
          node = ((NullNode) pair).getFstType();
        }else if (pair instanceof PairTypeNode){
          node = ((PairTypeNode) pair).getFstType();
        } else {
          node = ((NewPairNode) pair).getFstExpr();
        }
      } else {
         if (pair instanceof NullNode) {
          node = ((NullNode) pair).getSndType();
        }else if (pair instanceof PairTypeNode){
          node = ((PairTypeNode) pair).getSndType();
        } else {
          node = ((NewPairNode) pair).getSndExpr();
        }
      }
      return transformNode(node);
    }
    if (node instanceof FuncCallNode) {
      Node funcNode = symbolTable.getSymbolInfoFromFuncScope(((FuncCallNode) node).getValue());
      return ((FuncNode) funcNode).getReturnType();
    }
    return node;
  }

  // Checks if nodes are comparable for semantic analysis
  private boolean areNodesComparable(Node node1, Node node2) {
    node1 = transformNode(node1);
    node2 = transformNode(node2);
    if (isNodeComparableWithArray(node1) && isNodeComparableWithArray(node2)) {
      if (node1 instanceof ArrayTypeNode) {
        node1 = ((ArrayTypeNode) node1).getType();
      } else {
        node1 = ((ArrayLitNode) node1).getInnerType();
      }
      if (node2 instanceof ArrayTypeNode) {
        node2 = ((ArrayTypeNode) node2).getType();
      } else {
        node2 = ((ArrayLitNode) node2).getInnerType();
      }
      if (node1 == null || node2 == null) {
        return true;
      }
      return areNodesComparable(node1, node2);
    }
    if (isNodeComparableWithPair(node1) && isNodeComparableWithPair(node2)) {
      Node node1Fst;
      Node node1Snd;
      Node node2Fst;
      Node node2Snd;
      if (node1 instanceof PairTypeNode) {
        node1Fst = ((PairTypeNode) node1).getFstType();
        node1Snd = ((PairTypeNode) node1).getSndType();
      } else if (node1 instanceof NewPairNode) {
        node1Fst = ((NewPairNode) node1).getFstExpr();
        node1Snd = ((NewPairNode) node1).getSndExpr();
      } else {
        return true;
      }
      if (node2 instanceof PairTypeNode) {
        node2Fst = ((PairTypeNode) node2).getFstType();
        node2Snd = ((PairTypeNode) node2).getSndType();
      } else if (node2 instanceof NewPairNode) {
        node2Fst = ((NewPairNode) node2).getFstExpr();
        node2Snd = ((NewPairNode) node2).getSndExpr();
      } else {
        return true;
      }
      if (node1Fst == null || node2Fst == null || node1Snd == null || node2Snd == null) {
        return true;
      }
      return areNodesComparable(node1Fst, node2Fst) && areNodesComparable(node1Snd, node2Snd);
    }
    return (isNodeComparableWithInt(node1) && isNodeComparableWithInt(node2))
        || (isNodeComparableWithBool(node1) && isNodeComparableWithBool(node2))
        || (isNodeComparableWithString(node1) && isNodeComparableWithString(node2))
        || (isNodeComparableWithChar(node1) && isNodeComparableWithChar(node2));
  }

  // Helper functions for areNodesComparable()

  private boolean isNodeComparableWithBool(Node node) {
     node = transformNode(node);
    return (node instanceof BoolLitNode || node instanceof BoolTypeNode);
  }

  private boolean isNodeComparableWithString(Node node) {
    node = transformNode(node);
    return (node instanceof StrLitNode || node instanceof StringTypeNode);
  }

  private boolean isNodeComparableWithChar(Node node) {
  node = transformNode(node);
    return (node instanceof CharLitNode || node instanceof CharTypeNode);
  }

  private boolean isNodeComparableWithInt(Node node) {
   node = transformNode(node);
    return (node instanceof IntLitNode || node instanceof IntTypeNode);
  }

  private boolean isNodeComparableWithArray(Node node) {
  node = transformNode(node);
    return (( node instanceof ArrayLitNode) || node instanceof ArrayTypeNode);
  }

  private boolean isNodeComparableWithPair(Node node) {
   node = transformNode(node);
    return (node instanceof PairTypeNode
        || node instanceof NullNode
        || node instanceof NewPairNode);
  }

  // Returns type info as a string for returning error messages.
  private String getTypeInfoDescription(Node node) {
    node = transformNode(node);
    return node.getInfoType();
  }

  // ERRORS

  private String incompatibleTypesError(
      int lineNumber, String text, String expectedType, String actualType) {
    return generateSemanticError(lineNumber)
        + " -- Incompatible type at "
        + text
        + " (expected: "
        + expectedType
        + " actual: "
        + actualType
        + ")\n";
  }

  private String invalidAccessError(int lineNumber) {
    return generateSemanticError(lineNumber)
            + " -- Invalid access, cannot access a null pointer at null\n";
  }


  public String variableNotDeclared(int lineNumber, String ident) {
    return generateSemanticError(lineNumber)
        + " -- Variable "
        + ident
        + " is not defined in this scope\n";
  }

  public String variableAlreadyDeclared(int lineNumber, String ident) {
    return generateSemanticError(lineNumber)
        + " -- Variable "
        + ident
        + " already defined in this scope\n";
  }

  public String functionAlreadyDeclared(int lineNumber, String ident) {
    return generateSemanticError(lineNumber) + " -- Function " + ident + " already defined\n";
  }

  public String functionNotDeclaredError(int lineNumber, String funcName) {
    return generateSemanticError(lineNumber) + " -- Function " + funcName + " is not defined\n";
  }

  public String incorrectNumberOfArguments(int lineNumber, String ident, int expected, int actual) {
    return generateSemanticError(lineNumber)
        + " -- Incorrect number of parameters for "
        + ident
        + " (expected: "
        + expected
        + " actual: "
        + actual
        + ")\n";
  }

  private String cannotReturnFromGlobalScopeError(int lineNumber, String text) {
    return generateSemanticError(lineNumber)
        + " -- Cannot return from global scope at "
        + text
        + "\n";
  }

  // END OF HELPER FUNCTIONS

  // PROGRAM

  // First visits all functions in order to check for errors in declarations,
  // then visits the function bodies.

  public Node visitProg(WaccParser.ProgContext ctx) {
    symbolTable.createScope();
    String errors = "Errors detected during compilation! Exit code 200 returned.\n";
    ErrorNode errorNode = new ErrorNode(errors);
    visitedFunctions = false;
    List<FuncNode> functions = new ArrayList<>();
    for (WaccParser.FuncContext funcCtx : ctx.func()) {
      Node info = visit(funcCtx);
      if (info instanceof ErrorNode) {
        errorNode.merge((ErrorNode) info);
      }else{
        functions.add((FuncNode) info);
      }
    }
    visitedFunctions = true;
    for (WaccParser.FuncContext funcCtx : ctx.func()) {
      Node info = visit(funcCtx);
      if (info instanceof ErrorNode) {
        errorNode.merge((ErrorNode) info);
      }
    }
    Node stmt_info = visit(ctx.stat());
    int stmtScopeSize = getScopeSize(symbolTable.removeScope());
    if (stmt_info instanceof ErrorNode) {
      errorNode.merge((ErrorNode) stmt_info);
    }
    if (errorNode.getErrors().size() == 1) {
      return new ProgNode(stmt_info,functions, stmtScopeSize);
    } else {
      return errorNode;
    }
  }

  public Node visitFuncBody(WaccParser.FuncContext ctx) {
    String funcName = ctx.IDENT().getText();
    if (!symbolTable.isDeclaredInFuncScope(funcName)) {
      return new SkipNode();
    }
    FuncNode funcInfo = (FuncNode) symbolTable.getSymbolInfoFromFuncScope(funcName);
    symbolTable.createScope();
    symbolTable.setFuncScope(true);
    List<Node> params = funcInfo.getParams();
    if (params != null) {
      for (int i = 0; i < params.size(); ++i) {
        symbolTable.addSymbol(ctx.param_list().param(i).IDENT().getText(), ((ParamNode)params.get(i)).getType());
      }
    }
    Node bodyInfo = visit(ctx.stat());
    int lineNumber = ctx.getStart().getLine();
    if (bodyInfo instanceof ErrorNode || bodyInfo instanceof ExitNode) {
      funcInfo.setBody(bodyInfo);
      symbolTable.setFuncScope(false);
      int scopeSize = getScopeSize(symbolTable.removeScope());
      for(int i=0;i<funcInfo.getParams().size();i++){
        ParamNode node = (ParamNode) funcInfo.getParams().get(i);
        Node nodeType = node.getType();
        if(nodeType instanceof CharLitNode || nodeType instanceof CharTypeNode || nodeType instanceof BoolTypeNode || nodeType instanceof BoolLitNode){
          scopeSize--;
        }else{
          scopeSize-=4;
        }
      }
      funcInfo.setScopeSize(scopeSize);
      return bodyInfo;
    } else if (bodyInfo instanceof ReturnNode
            && !areNodesComparable(((ReturnNode) bodyInfo).getExpr(), funcInfo.getReturnType())) {
      funcInfo.setBody(bodyInfo);
      symbolTable.setFuncScope(false);
      symbolTable.removeScope();
      return new ErrorNode(
              incompatibleTypesError(
                      lineNumber,
                      ((ReturnNode) bodyInfo).getValue(),
                      getTypeInfoDescription(funcInfo.getReturnType()),
                      getTypeInfoDescription(((ReturnNode) bodyInfo).getExpr())));
    }
    funcInfo.setBody(bodyInfo);
    symbolTable.setFuncScope(false);
    int scopeSize = getScopeSize(symbolTable.removeScope());
    for(int i=0;i<funcInfo.getParams().size();i++){
      ParamNode node = (ParamNode) funcInfo.getParams().get(i);
      Node nodeType = node.getType();
      if(nodeType instanceof CharLitNode || nodeType instanceof CharTypeNode || nodeType instanceof BoolTypeNode || nodeType instanceof BoolLitNode){
        scopeSize--;
      }else{
        scopeSize-=4;
      }
    }
    funcInfo.setScopeSize(scopeSize);
    return funcInfo;
  }

  @Override
  public Node visitFunc(WaccParser.FuncContext ctx) {
    if (visitedFunctions) {
      return visitFuncBody(ctx);
    }
    int lineNumber = ctx.getStart().getLine();
    Node funcReturnTypeInfo = visit(ctx.type());
    String funcName = ctx.IDENT().getText();
    if (symbolTable.isDeclaredInFuncScope(funcName)) {
      return new ErrorNode(functionAlreadyDeclared(lineNumber, funcName));
    }
    List<Node> params = new ArrayList<>();
    if (ctx.param_list() != null) {
      for (WaccParser.ParamContext paramCtx : ctx.param_list().param()) {
        Node param = visit(paramCtx);
        params.add(param);
      }
    }
    FuncNode funcInfo = new FuncNode(funcName, funcReturnTypeInfo, params, null);
    symbolTable.addSymbolToFuncScope(funcName, funcInfo);
    return funcInfo;
  }

  @Override
  public Node visitParam(WaccParser.ParamContext ctx) {
    Node type = visit(ctx.type());
    String name = ctx.IDENT().getText();
    return new ParamNode(name, type);
  }

  // EXPR

  @Override
  public Node visitInt_lit(WaccParser.Int_litContext ctx) {
    int value = Integer.parseInt(ctx.getText());
    return new IntLitNode(value);
  }

  @Override
  public Node visitBool_lit(WaccParser.Bool_litContext ctx) {
    boolean value = Boolean.parseBoolean(ctx.getText());
    return new BoolLitNode(value);
  }

  // Removes extra escape character used in wacc for finding string lengths.

  private char transformStringToChar(String str){
    if(str.equals("'\\t'")){
      return '\t';
    }else if(str.equals("'\\b'")){
      return '\b';
    }else if(str.equals("'\\0'")){
      return '\0';
    }else if(str.equals("'\\n'")){
      return '\n';
    }else if(str.equals("'\\f'")){
      return '\f';
    }else if(str.equals("'\\r'")){
      return '\r';
    }else if(str.equals("'\\\"'")){
      return '\"';
    }else if(str.equals("'\\''")){
      return '\'';
    }else if(str.equals("'\\'")){
      return '\\';
    }
    return str.charAt(1);
  }

  @Override
  public Node visitChar_literal(WaccParser.Char_literalContext ctx) {
    char value = transformStringToChar(ctx.getText());
    return new CharLitNode(value);
  }

  @Override
  public Node visitString_literal(WaccParser.String_literalContext ctx) {
    String value = ctx.getText().substring(1,ctx.getText().length()-1);
    return new StrLitNode(value);
  }

  @Override
  public Node visitPair_literal(WaccParser.Pair_literalContext ctx) {
    return new NullNode();
  }

  @Override
  public Node visitIdent(WaccParser.IdentContext ctx) {
    String ident = ctx.IDENT().getText();
    int lineNumber = ctx.getStart().getLine();
    if (symbolTable.isDeclaredInAnyScope(ident)) {
      return new IdentNode(ident,symbolTable.getSymbolInfo(ident),transformNode(symbolTable.getSymbolInfo(ident)));
    } else {
      return new ErrorNode(variableNotDeclared(lineNumber, ident));
    }
  }

  @Override
  public Node visitUnOp_expr(WaccParser.UnOp_exprContext ctx) {
    String unaryOperator = ctx.getChild(0).getText();
    Node exprInfo = visit(ctx.expr());
    int lineNumber = ctx.getStart().getLine();
    if (exprInfo instanceof ErrorNode) {
      return exprInfo;
    }
    switch (unaryOperator) {
      case "!":
        if (!isNodeComparableWithBool(exprInfo)) {
          return new ErrorNode(
              incompatibleTypesError(
                  lineNumber, ctx.expr().getText(), "BOOL", getTypeInfoDescription(exprInfo)));
        }
        break;
      case "len":
        if (!((isNodeComparableWithString(exprInfo)) || isNodeComparableWithArray(exprInfo))) {
          return new ErrorNode(
              incompatibleTypesError(
                  lineNumber,
                  ctx.expr().getText(),
                  "STRING or T[]",
                  getTypeInfoDescription(exprInfo)));
        }
        break;
      case "ord":
        if (!isNodeComparableWithChar(exprInfo)) {
          return new ErrorNode(
              incompatibleTypesError(
                  lineNumber, ctx.expr().getText(), "CHAR", getTypeInfoDescription(exprInfo)));
        }
        break;
      default:
        if (!isNodeComparableWithInt(exprInfo)) {
          return new ErrorNode(
              incompatibleTypesError(
                  lineNumber, ctx.expr().getText(), "INT", getTypeInfoDescription(exprInfo)));
        }
        break;
    }
    UnaryExpressionNode unaryExpressionNode = new UnaryExpressionNode(unaryOperator, exprInfo);
    unaryExpressionNode.setTypeNode(transformNode(unaryExpressionNode));
    return unaryExpressionNode;
  }

  @Override
  public Node visitBinOp_expr(WaccParser.BinOp_exprContext ctx) {
    String binaryOperator = ctx.getChild(1).getText();
    Node expression1Info = visit(ctx.expr(0));
    Node expression2Info = visit(ctx.expr(1));
    int lineNumber = ctx.getStart().getLine();
    ErrorNode errors = new ErrorNode();
    if (expression1Info instanceof ErrorNode) {
      errors.merge((ErrorNode) expression1Info);
    }
    if (expression2Info instanceof ErrorNode) {
      errors.merge((ErrorNode) expression2Info);
    }

    switch (binaryOperator) {
      case "*":
      case "/":
      case "%":
      case "+":
      case "-":
        if (!(expression1Info instanceof ErrorNode) && !isNodeComparableWithInt(expression1Info)) {
          errors.addErrorMessage(
              incompatibleTypesError(
                  lineNumber,
                  ctx.expr(0).getText(),
                  "INT",
                  getTypeInfoDescription(expression1Info)));
        }
        if (!(expression2Info instanceof ErrorNode) && !isNodeComparableWithInt(expression2Info)) {
          errors.addErrorMessage(
              incompatibleTypesError(
                  lineNumber,
                  ctx.expr(1).getText(),
                  "INT",
                  getTypeInfoDescription(expression2Info)));
        }

        if (!errors.getErrors().isEmpty()) {
          return errors;
        }
        break;
      case ">":
      case ">=":
      case "<":
      case "<=":
        if (!(expression1Info instanceof ErrorNode)
            && !(isNodeComparableWithInt(expression1Info)
                || isNodeComparableWithChar(expression1Info))) {
          errors.addErrorMessage(
              incompatibleTypesError(
                  lineNumber,
                  ctx.expr(0).getText(),
                  "INT OR CHAR",
                  getTypeInfoDescription(expression1Info)));
        }

        if (!(expression2Info instanceof ErrorNode)
            && !areNodesComparable(expression1Info, expression2Info)) {
          errors.addErrorMessage(
              incompatibleTypesError(
                  lineNumber,
                  ctx.expr(1).getText(),
                  getTypeInfoDescription(expression1Info),
                  getTypeInfoDescription(expression2Info)));
        }
        if (!errors.getErrors().isEmpty()) {
          return errors;
        }
        break;
      case "==":
      case "!=":
        if (!(expression1Info instanceof ErrorNode)
            && !(expression2Info instanceof ErrorNode)
            && !areNodesComparable(expression1Info, expression2Info)) {
          return new ErrorNode(
              incompatibleTypesError(
                  lineNumber,
                  ctx.expr(1).getText(),
                  getTypeInfoDescription(expression1Info),
                  getTypeInfoDescription(expression1Info)));
        }
        break;
      default:
        if (!(expression1Info instanceof ErrorNode) && !isNodeComparableWithBool(expression1Info)) {
          errors.addErrorMessage(
              incompatibleTypesError(
                  lineNumber,
                  ctx.expr(0).getText(),
                  "BOOL",
                  getTypeInfoDescription(expression1Info)));
        }
        if (!(expression2Info instanceof ErrorNode) && !isNodeComparableWithBool(expression2Info)) {
          errors.addErrorMessage(
              incompatibleTypesError(
                  lineNumber,
                  ctx.expr(1).getText(),
                  "BOOL",
                  getTypeInfoDescription(expression2Info)));
        }
        if (!errors.getErrors().isEmpty()) {
          return errors;
        }
        break;
    }
    BinaryExpressionNode binaryExpressionNode =  new BinaryExpressionNode(binaryOperator, expression1Info, expression2Info);
    binaryExpressionNode.setTypeNode(transformNode(binaryExpressionNode));
    return binaryExpressionNode;
  }

  @Override
  public Node visitNested_expr(WaccParser.Nested_exprContext ctx) {
    return visit(ctx.expr());
  }

  // ASSIGN RHS
  @Override
  public Node visitArray_lit(WaccParser.Array_litContext ctx) {
    List<WaccParser.ExprContext> exprs = ctx.expr();
    int lineNumber = ctx.getStart().getLine();
    if (exprs.size() == 0) {
      return new ArrayLitNode(new ArrayList<>());
    }
    Node expr1Info = visit(ctx.expr(0));
    ErrorNode errors = new ErrorNode();
    List<Node> exprNodes = new ArrayList<>();
    for (int i = 0; i < exprs.size(); i++) {
      Node exprInfo = visit(ctx.expr(i));
      if (exprInfo instanceof ErrorNode) {
        errors.merge((ErrorNode) exprInfo);
      } else if (!areNodesComparable(exprInfo, expr1Info)) {
        errors.addErrorMessage(
            incompatibleTypesError(
                lineNumber,
                ctx.expr(i).getText(),
                getTypeInfoDescription(expr1Info),
                getTypeInfoDescription(exprInfo)));
      } else {
        exprNodes.add(exprInfo);
      }
    }
    if (!errors.getErrors().isEmpty()) {
      return errors;
    }
    ArrayLitNode arrayLitNode = new ArrayLitNode(exprNodes);
    arrayLitNode.setTypeNode(transformNode(expr1Info));
    return arrayLitNode;
  }

  @Override
  public Node visitPair_init(WaccParser.Pair_initContext ctx) {
    Node expr1Info = visit(ctx.expr(0));
    Node expr2Info = visit(ctx.expr(1));
    ErrorNode errors = new ErrorNode();
    if (expr1Info instanceof ErrorNode) {
      errors.merge((ErrorNode) expr1Info);
    }
    if (expr2Info instanceof ErrorNode) {
      errors.merge((ErrorNode) expr2Info);
    }
    if (!errors.getErrors().isEmpty()) {
      return errors;
    }
    return new NewPairNode(expr1Info, expr2Info);
  }

  public Node visitFunc_call(WaccParser.Func_callContext ctx) {
    String funcName = ctx.IDENT().getText();
    int lineNumber = ctx.getStart().getLine();
    if (!symbolTable.isDeclaredInFuncScope(funcName)) {
      return new ErrorNode(functionNotDeclaredError(lineNumber, funcName));
    }
    FuncNode funcInfo = (FuncNode) symbolTable.getSymbolInfoFromFuncScope(funcName);
    List<Node> params = funcInfo.getParams();
    List<Node> args = new ArrayList<>();
    List<Node> typeNodes = new ArrayList<>();
    ErrorNode errors = new ErrorNode();
    if (ctx.arg_list() != null) {
      List<WaccParser.ExprContext> exprs = ctx.arg_list().expr();
      for (WaccParser.ExprContext expr : exprs) {
        Node exprInfo = visit(expr);
        if (exprInfo instanceof ErrorNode) {
          errors.merge((ErrorNode) exprInfo);
        }
        typeNodes.add(transformNode(exprInfo));
        args.add(exprInfo);
      }
    } else {
      return new FuncCallNode(funcName, args,typeNodes);
    }
    if (!errors.getErrors().isEmpty()) {
      return errors;
    }
    if (params == null) {
      errors.addErrorMessage(
              incorrectNumberOfArguments(lineNumber, funcName, 0, args.size()));
      return errors;
    }
    if (args.size() != params.size()) {
      errors.addErrorMessage(
              incorrectNumberOfArguments(lineNumber, funcName, params.size(), args.size()));
    }
    if (!errors.getErrors().isEmpty()) {
      return errors;
    }
    for (int i = 0; i < params.size(); i++) {
      if (!areNodesComparable(params.get(i), args.get(i))) {
        errors.addErrorMessage(
                incompatibleTypesError(
                        lineNumber,
                        ctx.arg_list().expr(i).getText(),
                        getTypeInfoDescription(params.get(i)),
                        getTypeInfoDescription(args.get(i))));
      }
    }
    if (!errors.getErrors().isEmpty()) {
      return errors;
    }

    return new FuncCallNode(funcName, args, funcInfo,typeNodes);
  }

  // ASSIGN_LHS

  @Override
  public Node visitPair_elem(WaccParser.Pair_elemContext ctx) {
    Node exprInfo = visit(ctx.expr());
    int lineNumber = ctx.getStart().getLine();
    String ident = ctx.expr().getText();
    if (exprInfo instanceof ErrorNode) {
      return new ErrorNode(variableNotDeclared(lineNumber, ident));
    }
    if (!isNodeComparableWithPair(exprInfo)) {
      return new ErrorNode(
          incompatibleTypesError(
              lineNumber, ctx.expr().getText(), "PAIR", getTypeInfoDescription(exprInfo)));
    }
    if(exprInfo instanceof NullNode){
      return new ErrorNode(invalidAccessError(lineNumber));
    }
    if (ctx.getText().startsWith("fst")) {
      PairElemNode pairElemNode = new PairElemNode(ident, true, symbolTable.getSymbolInfo(ident));
      pairElemNode.setTypeNode(transformNode(pairElemNode));
      return pairElemNode;
    }
    PairElemNode pairElemNode = new PairElemNode(ident, false, symbolTable.getSymbolInfo(ident));
    pairElemNode.setTypeNode(transformNode(pairElemNode));
    return pairElemNode;
  }

  @Override
  public Node visitArray_elem(WaccParser.Array_elemContext ctx) {
    int lineNumber = ctx.getStart().getLine();
    String ident = ctx.IDENT().getText();
    if (!symbolTable.isDeclaredInAnyScope(ident)) {
      return new ErrorNode(variableNotDeclared(lineNumber, ident));
    }
    Node arrayInfo = symbolTable.getSymbolInfo(ident);
    List<WaccParser.ExprContext> exprs = ctx.expr();
    for (WaccParser.ExprContext expr : exprs) {
      if (!isNodeComparableWithArray(arrayInfo)) {
        return new ErrorNode(
            incompatibleTypesError(lineNumber, ident, "T[]", getTypeInfoDescription(arrayInfo)));
      }
      arrayInfo = transformNode(arrayInfo);
      if (arrayInfo instanceof ArrayTypeNode) {
        arrayInfo = ((ArrayTypeNode) arrayInfo).getType();
      }else{
        arrayInfo = ((ArrayLitNode) arrayInfo).getInnerType();
      }
    }
    ErrorNode errors = new ErrorNode();
    List<Node> indices = new ArrayList<>();
    for (int i = 0; i < exprs.size(); ++i) {
      Node expressionInfo = visit(exprs.get(i));
      if (expressionInfo instanceof ErrorNode) {
        errors.merge((ErrorNode) expressionInfo);
      } else if (!isNodeComparableWithInt(expressionInfo)) {
        errors.addErrorMessage(
            incompatibleTypesError(
                lineNumber, exprs.get(i).getText(), "INT", getTypeInfoDescription(expressionInfo)));
      } else {
        indices.add(expressionInfo);
      }
    }
    if (!errors.getErrors().isEmpty()) {
      return errors;
    }
    Node identNode = symbolTable.getSymbolInfo(ident);
    ArrayElemNode arrayElemNode = new ArrayElemNode(ident, indices, symbolTable.getSymbolInfo(ident));
    arrayElemNode.setTypeNode(transformNode(arrayElemNode));
    return arrayElemNode;
  }

  // STMTS
  @Override
  public Node visitSkip_stmt(WaccParser.Skip_stmtContext ctx) {
    return new SkipNode();
  }

  @Override
  public Node visitVar_declaration(WaccParser.Var_declarationContext ctx) {
    Node lhsTypeInfo = visit(ctx.type());
    ErrorNode errors = new ErrorNode();
    if (lhsTypeInfo instanceof ErrorNode) {
      errors.merge((ErrorNode) lhsTypeInfo);
    }
    String ident = ctx.IDENT().getText();
    Node rhsInfo = visit(ctx.assign_rhs());
    if (rhsInfo instanceof ErrorNode) {
      errors.merge((ErrorNode) rhsInfo);
    }
    if (!errors.getErrors().isEmpty()) {
      return errors;
    }
    int lineNumber = ctx.getStart().getLine();
    if (!areNodesComparable(lhsTypeInfo, rhsInfo)) {
      return new ErrorNode(
          incompatibleTypesError(
              lineNumber,
              ctx.assign_rhs().getText(),
              getTypeInfoDescription(lhsTypeInfo),
              getTypeInfoDescription(rhsInfo)));
    }
    if (symbolTable.isDeclaredInCurrentScope(ident)) {
      return new ErrorNode(variableAlreadyDeclared(lineNumber, ident));
    }
    if(transformNode(rhsInfo) instanceof PairTypeNode){
      symbolTable.addSymbol(ident,lhsTypeInfo);
      return new DeclarationNode(lhsTypeInfo, new IdentNode(ident,lhsTypeInfo,transformNode(lhsTypeInfo)), rhsInfo);
    }
    if(rhsInfo instanceof NullNode){
      ((NullNode) rhsInfo).setFstType(((PairTypeNode)lhsTypeInfo).getFstType());
      ((NullNode) rhsInfo).setSndType(((PairTypeNode)lhsTypeInfo).getSndType());
    }
    if(rhsInfo instanceof ArrayLitNode){
      ((ArrayLitNode) rhsInfo).setTypeNode(((ArrayTypeNode)lhsTypeInfo).getType());
    }
    if(rhsInfo instanceof NewPairNode){
      ((NewPairNode) rhsInfo).setFstType(((PairTypeNode)lhsTypeInfo).getFstType());
      ((NewPairNode) rhsInfo).setSndType(((PairTypeNode)lhsTypeInfo).getSndType());
    }
    symbolTable.addSymbol(ident, rhsInfo);
    return new DeclarationNode(lhsTypeInfo, new IdentNode(ident,lhsTypeInfo,transformNode(lhsTypeInfo)), rhsInfo);
  }

  @Override
  public Node visitVar_assignment(WaccParser.Var_assignmentContext ctx) {
    Node lhsInfo = visit(ctx.assign_lhs());
    int lineNumber = ctx.getStart().getLine();
    ErrorNode errors = new ErrorNode();
    if (lhsInfo instanceof ErrorNode) {
      errors.merge((ErrorNode) lhsInfo);
    }
    Node rhsInfo = visit(ctx.assign_rhs());
    if (rhsInfo instanceof ErrorNode) {
      errors.merge((ErrorNode) rhsInfo);
    }
    if (!errors.getErrors().isEmpty()) {
      return errors;
    }
    if (!areNodesComparable(lhsInfo, rhsInfo)) {
      return new ErrorNode(
          incompatibleTypesError(
              lineNumber,
              ctx.assign_rhs().getText(),
              getTypeInfoDescription(lhsInfo),
              getTypeInfoDescription(rhsInfo)));
    }
    return new AssignmentNode(lhsInfo, rhsInfo);
  }

  @Override
  public Node visitPrint_stat(WaccParser.Print_statContext ctx) {
    Node exprInfo = visit(ctx.expr());
    if (exprInfo instanceof ErrorNode) {
      return exprInfo;
    }
    return new PrintNode(exprInfo);
  }

  @Override
  public Node visitPrintln_stat(WaccParser.Println_statContext ctx) {
    Node exprInfo = visit(ctx.expr());
    if (exprInfo instanceof ErrorNode) {
      return exprInfo;
    }
    return new PrintLnNode(exprInfo);
  }

  @Override
  public Node visitScope_stat(WaccParser.Scope_statContext ctx) {
    symbolTable.createScope();
    Node stmtInfo = visit(ctx.stat());
    if (stmtInfo instanceof ErrorNode) {
      symbolTable.removeScope();
      return stmtInfo;
    }
    int scopeSize = getScopeSize(symbolTable.removeScope());
    return new ScopeNode(stmtInfo, scopeSize);
  }

  @Override
  public Node visitReturn_stat(WaccParser.Return_statContext ctx) {
    Node exprInfo = visit(ctx.expr());
    int lineNumber = ctx.getStart().getLine();
    if (!symbolTable.isFuncScope()) {
      return new ErrorNode(cannotReturnFromGlobalScopeError(lineNumber, ctx.getText()));
    }
    if (exprInfo instanceof ErrorNode) {
      return exprInfo;
    }
    return new ReturnNode(exprInfo);
  }

  @Override
  public Node visitExit_stat(WaccParser.Exit_statContext ctx) {
    Node expressionInfo = visit(ctx.expr());
    if (expressionInfo instanceof ErrorNode) {
      return expressionInfo;
    }
    if (!isNodeComparableWithInt(expressionInfo)) {
      int lineNumber = ctx.getStart().getLine();
      return new ErrorNode(
          generateSemanticError(lineNumber)
              + incompatibleTypesError(
                  lineNumber, ctx.expr().getText(), "INT", getTypeInfoDescription(expressionInfo)));
    }
    return new ExitNode(expressionInfo);
  }

  @Override
  public Node visitFree_stat(WaccParser.Free_statContext ctx) {
    int lineNumber = ctx.getStart().getLine();
    Node exprInfo = visit(ctx.expr());
    if (exprInfo instanceof ErrorNode) {
      return exprInfo;
    }
    if (!(isNodeComparableWithArray(exprInfo) || isNodeComparableWithPair(exprInfo))) {
      return new ErrorNode(
          incompatibleTypesError(
              lineNumber, ctx.expr().getText(), "PAIR or ARRAY", getTypeInfoDescription(exprInfo)));
    }
    Node typeNode = transformNode(exprInfo);
    return new FreeNode(exprInfo,typeNode);
  }

  @Override
  public Node visitWhile_stat(WaccParser.While_statContext ctx) {
    Node exprInfo = visit(ctx.expr());
    int lineNumber = ctx.getStart().getLine();
    ErrorNode errors = new ErrorNode();
    if (exprInfo instanceof ErrorNode) {
      errors.merge((ErrorNode) exprInfo);
    } else if (!isNodeComparableWithBool(exprInfo)) {
      errors.addErrorMessage(
          incompatibleTypesError(
              lineNumber, ctx.expr().getText(), "BOOL", getTypeInfoDescription(exprInfo)));
    }
    symbolTable.createScope();
    Node stmtInfo = visit(ctx.stat());
    if (stmtInfo instanceof ErrorNode) {
      errors.merge((ErrorNode) stmtInfo);
    }
    int scopeSize = getScopeSize(symbolTable.removeScope());
    if (!errors.getErrors().isEmpty()) {
      return errors;
    }
    return new WhileNode(exprInfo, stmtInfo, scopeSize);
  }

  @Override
  public Node visitIf_stat(WaccParser.If_statContext ctx) {
    Node exprInfo = visit(ctx.expr());
    int lineNumber = ctx.getStart().getLine();
    ErrorNode errors = new ErrorNode();
    if (exprInfo instanceof ErrorNode) {
      errors.merge((ErrorNode) exprInfo);
    } else if (!isNodeComparableWithBool(exprInfo)) {
      errors.addErrorMessage(
          incompatibleTypesError(
              lineNumber, ctx.expr().getText(), "BOOL", getTypeInfoDescription(exprInfo)));
    }
    symbolTable.createScope();
    Node thenStmtInfo = visit(ctx.stat(0));
    int thenScopeSize = getScopeSize(symbolTable.removeScope());
    if (thenStmtInfo instanceof ErrorNode) {
      errors.merge((ErrorNode) thenStmtInfo);
    }
    symbolTable.createScope();
    Node elseStmtInfo = visit(ctx.stat(1));
    int elseScopeSize = getScopeSize(symbolTable.removeScope());
    if (elseStmtInfo instanceof ErrorNode) {
      errors.merge((ErrorNode) elseStmtInfo);
    }
    if (!errors.getErrors().isEmpty()) {
      return errors;
    }
    return new IfNode(exprInfo, thenStmtInfo, elseStmtInfo, thenScopeSize, elseScopeSize);
  }

  @Override
  public Node visitRead_stat(WaccParser.Read_statContext ctx) {
    Node exprInfo = visit(ctx.assign_lhs());
    int lineNumber = ctx.getStart().getLine();
    if (exprInfo instanceof ErrorNode) {
      return exprInfo;
    }
    if (!(isNodeComparableWithChar(exprInfo) || isNodeComparableWithInt(exprInfo))) {
      return new ErrorNode(
          incompatibleTypesError(
              lineNumber,
              ctx.assign_lhs().getText(),
              "INT or CHAR",
              getTypeInfoDescription(exprInfo)));
    }
    return new ReadNode(exprInfo);
  }

  @Override
  public Node visitSeq_stmts(WaccParser.Seq_stmtsContext ctx) {
    Node statementInfo;
    ErrorNode errors = new ErrorNode();
    List<Node> stats = new ArrayList<>();
    for (WaccParser.StatContext stmt : ctx.stat()) {
      statementInfo = visit(stmt);
      if (statementInfo instanceof ErrorNode) {
        errors.merge((ErrorNode) statementInfo);
      } else {
        stats.add(statementInfo);
      }
    }
    if (!errors.getErrors().isEmpty()) {
      return errors;
    }
    return new SeqNode(stats);
  }

  // Types
  @Override
  public Node visitInt_t(WaccParser.Int_tContext ctx) {
    return new IntTypeNode();
  }

  @Override
  public Node visitChar_t(WaccParser.Char_tContext ctx) {
    return new CharTypeNode();
  }

  @Override
  public Node visitBool_t(WaccParser.Bool_tContext ctx) {
    return new BoolTypeNode();
  }

  @Override
  public Node visitString_t(WaccParser.String_tContext ctx) {
    return new StringTypeNode();
  }

  @Override
  public Node visitArray_t(WaccParser.Array_tContext ctx) {
    Node innerType = visit(ctx.type());
    return new ArrayTypeNode(innerType);
  }

  @Override
  public Node visitArray_type(WaccParser.Array_typeContext ctx) {
    Node innerType = visit(ctx.type());
    return new ArrayTypeNode(innerType);
  }

  @Override
  public Node visitPair_t(WaccParser.Pair_tContext ctx) {
    Node fstType = visit(ctx.pair_elem_type(0));
    Node sndType = visit(ctx.pair_elem_type(1));
    return new PairTypeNode(fstType, sndType);
  }

  @Override
  public Node visitPair(WaccParser.PairContext ctx) {
    return new PairTypeNode(null, null);
  }
}
