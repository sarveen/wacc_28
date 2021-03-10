package waccFrontEnd.AST;

import waccFrontEnd.AST.Functions.*;
import waccFrontEnd.AST.Assignments.*;
import waccFrontEnd.AST.Expressions.*;
import waccFrontEnd.AST.Statements.*;
import waccFrontEnd.AST.Types.*;

public abstract class ASTBaseVisitor<T> {

    // Base visitor for our internal representation (AST built on expr, stat,
    // type, assignment and func nodes)

    public abstract T visit(BinaryExpressionNode node);
    public abstract T visit(BoolLitNode node);
    public abstract T visit(CharLitNode node);
    public abstract T visit(IdentNode node);
    public abstract T visit(IntLitNode node);
    public abstract T visit(NullNode node);
    public abstract T visit(StrLitNode node);
    public abstract T visit(UnaryExpressionNode node);
    public abstract T visit(AssignmentNode node);
    public abstract T visit(DeclarationNode node);
    public abstract T visit(ExitNode node);
    public abstract T visit(FreeNode node);
    public abstract T visit(IfNode node);
    public abstract T visit(PrintLnNode node);
    public abstract T visit(PrintNode node);
    public abstract T visit(ReadNode node);
    public abstract T visit(ReturnNode node);
    public abstract T visit(ScopeNode node);
    public abstract T visit(SeqNode node);
    public abstract T visit(SkipNode node);
    public abstract T visit(WhileNode node);
    public abstract T visit(ArrayElemNode node);
    public abstract T visit(ArrayLitNode node);
    public abstract T visit(ArrayTypeNode node);
    public abstract T visit(BoolTypeNode node);
    public abstract T visit(CharTypeNode node);
    public abstract T visit(FuncCallNode node);
    public abstract T visit(FuncNode node);
    public abstract T visit(IntTypeNode node);
    public abstract T visit(NewPairNode node);
    public abstract T visit(PairElemNode node);
    public abstract T visit(PairTypeNode node);
    public abstract T visit(ParamNode node);
    public abstract T visit(ProgNode node);
    public abstract T visit(StringTypeNode node);

    public T visit(Node node) {
        if (node instanceof BinaryExpressionNode) {
            return visit((BinaryExpressionNode) node);
        } else if (node instanceof BoolLitNode) {
            return visit((BoolLitNode) node);
        } else if (node instanceof CharLitNode) {
            return visit((CharLitNode) node);
        } else if (node instanceof IdentNode) {
            return visit((IdentNode) node);
        } else if (node instanceof IntLitNode) {
            return visit((IntLitNode) node);
        } else if (node instanceof NullNode) {
            return visit((NullNode) node);
        } else if (node instanceof StrLitNode) {
            return visit((StrLitNode) node);
        } else if (node instanceof UnaryExpressionNode) {
            return visit((UnaryExpressionNode) node);
        } else if (node instanceof AssignmentNode) {
            return visit((AssignmentNode) node);
        } else if (node instanceof DeclarationNode) {
            return visit((DeclarationNode) node);
        } else if (node instanceof ExitNode) {
            return visit((ExitNode) node);
        } else if (node instanceof FreeNode) {
            return visit((FreeNode) node);
        } else if (node instanceof IfNode) {
            return visit((IfNode) node);
        } else if (node instanceof PrintLnNode) {
            return visit((PrintLnNode) node);
        } else if (node instanceof PrintNode) {
            return visit((PrintNode) node);
        } else if (node instanceof ReadNode) {
            return visit((ReadNode) node);
        } else if (node instanceof ReturnNode) {
            return visit((ReturnNode) node);
        } else if (node instanceof ScopeNode) {
            return visit((ScopeNode) node);
        } else if (node instanceof SeqNode) {
            return visit((SeqNode) node);
        } else if (node instanceof SkipNode) {
            return visit((SkipNode) node);
        } else if (node instanceof WhileNode) {
            return visit((WhileNode) node);
        } else if (node instanceof ArrayElemNode) {
            return visit((ArrayElemNode) node);
        } else if (node instanceof ArrayLitNode) {
            return visit((ArrayLitNode) node);
        } else if (node instanceof ArrayTypeNode) {
            return visit((ArrayTypeNode) node);
        } else if (node instanceof BoolTypeNode) {
            return visit((BoolTypeNode) node);
        } else if (node instanceof CharTypeNode) {
            return visit((CharTypeNode) node);
        } else if (node instanceof FuncCallNode) {
            return visit((FuncCallNode) node);
        } else if (node instanceof FuncNode) {
            return visit((FuncNode) node);
        } else if (node instanceof IntTypeNode) {
            return visit((IntTypeNode) node);
        } else if (node instanceof NewPairNode) {
            return visit((NewPairNode) node);
        } else if (node instanceof PairElemNode) {
            return visit((PairElemNode) node);
        } else if (node instanceof PairTypeNode) {
            return visit((PairTypeNode) node);
        } else if (node instanceof ParamNode) {
            return visit((ParamNode) node);
        } else if (node instanceof ProgNode) {
            return visit((ProgNode) node);
        } else if (node instanceof StringTypeNode) {
            return visit((StringTypeNode) node);
        }
        return null;
    }


}
