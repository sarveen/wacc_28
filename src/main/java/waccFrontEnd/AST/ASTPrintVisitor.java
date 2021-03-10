package waccFrontEnd.AST;

import waccFrontEnd.AST.Functions.*;
import waccFrontEnd.AST.Assignments.*;
import waccFrontEnd.AST.Expressions.*;
import waccFrontEnd.AST.Statements.*;
import waccFrontEnd.AST.Types.*;

import java.util.List;

public class ASTPrintVisitor extends ASTBaseVisitor<Void> {

    // ASTPrintVisitor extends ASTBaseVisitor to traverse our AST nodes and
    // generate a stylised output of the AST based on the reference compiler
    // using the methods of the PrettyPrinter class.

    // Each Node that is visited has a getValue() method which returns a
    // representation of what it is to add to the printer, and then any of it's
    // child nodes are visited.

    PrettyPrinter printer = new PrettyPrinter();

    @Override
    public Void visit(BinaryExpressionNode node) {
        printer.printLine(node.getValue());
        printer.incrementIndent();
        visit(node.getLeftOperand());
        visit(node.getRightOperand());
        printer.decrementIndent();
        return null;
    }

    @Override
    public Void visit(BoolLitNode node) {
        printer.printLine(node.getValue().toString());
        return null;
    }

    @Override
    public Void visit(CharLitNode node) {
        printer.printLine(node.getValue().toString());
        return null;
    }

    @Override
    public Void visit(IdentNode node) {
        printer.printLine(node.getValue());
        return null;
    }

    @Override
    public Void visit(IntLitNode node) {
        printer.printLine(node.getValue().toString());
        return null;
    }

    @Override
    public Void visit(NullNode node) {
        printer.printLine(node.getValue());
        return null;
    }

    @Override
    public Void visit(StrLitNode node) {
        printer.printLine(node.getValue());
        return null;
    }

    @Override
    public Void visit(UnaryExpressionNode node) {
        printer.printLine(node.getValue());

        printer.incrementIndent();
        visit(node.getOperand());
        printer.decrementIndent();

        return null;
    }

    @Override
    public Void visit(AssignmentNode node) {
        printer.printLine(node.getValue());

        printer.incrementIndent();

        printer.printLine("LHS");
        printer.incrementIndent();
        visit(node.getLhs());
        printer.decrementIndent();

        printer.printLine("RHS");
        printer.incrementIndent();
        visit(node.getRhs());
        printer.decrementIndent();

        printer.decrementIndent();

        return null;
    }

    @Override
    public Void visit(DeclarationNode node) {
        printer.printLine(node.getValue());

        printer.incrementIndent();

        printer.printLine("TYPE");
        printer.incrementIndent();
        visit(node.getType());
        printer.decrementIndent();

        printer.printLine("LHS");
        printer.incrementIndent();
        visit(node.getLhs());
        printer.decrementIndent();

        printer.printLine("RHS");
        printer.incrementIndent();
        visit(node.getRhs());
        printer.decrementIndent();

        printer.decrementIndent();

        return null;
    }

    @Override
    public Void visit(ExitNode node) {
        printer.printLine(node.getValue());

        printer.incrementIndent();
        visit(node.getExpr());
        printer.decrementIndent();

        return null;
    }

    @Override
    public Void visit(FreeNode node) {
        printer.printLine(node.getValue());

        printer.incrementIndent();
        visit(node.getExpr());
        printer.decrementIndent();

        return null;
    }

    @Override
    public Void visit(IfNode node) {
        printer.printLine(node.getValue());

        printer.incrementIndent();

        printer.printLine("CONDITION");
        printer.incrementIndent();
        visit(node.getCond());
        printer.decrementIndent();

        printer.printLine("THEN");
        printer.incrementIndent();
        visit(node.getThenStat());
        printer.decrementIndent();

        printer.printLine("ELSE");
        printer.incrementIndent();
        visit(node.getElseStat());
        printer.decrementIndent();

        printer.decrementIndent();

        return null;
    }

    @Override
    public Void visit(PrintLnNode node) {
        printer.printLine(node.getValue());

        printer.incrementIndent();
        visit(node.getExpr());
        printer.decrementIndent();

        return null;
    }

    @Override
    public Void visit(PrintNode node) {
        printer.printLine(node.getValue());

        printer.incrementIndent();
        visit(node.getExpr());
        printer.decrementIndent();

        return null;
    }

    @Override
    public Void visit(ReadNode node) {
        printer.printLine(node.getValue());

        printer.incrementIndent();
        visit(node.getExpr());
        printer.decrementIndent();

        return null;
    }

    @Override
    public Void visit(ReturnNode node) {
        printer.printLine(node.getValue());

        printer.incrementIndent();
        visit(node.getExpr());
        printer.decrementIndent();

        return null;
    }

    @Override
    public Void visit(ScopeNode node) {
        printer.printLine(node.getValue());

        printer.incrementIndent();
        visit(node.getStmt());
        printer.decrementIndent();

        return null;
    }

    @Override
    public Void visit(SeqNode node) {
        List<Node> stats = node.getStats();
        for (Node stmt : stats) {
            visit(stmt);
        }
        return null;
    }

    @Override
    public Void visit(SkipNode node) {
        printer.printLine(node.getValue());
        return null;
    }

    @Override
    public Void visit(WhileNode node) {
        printer.printLine(node.getValue());

        printer.incrementIndent();

        printer.printLine("CONDITION");
        printer.incrementIndent();
        visit(node.getCond());
        printer.decrementIndent();

        printer.printLine("DO");
        printer.incrementIndent();
        visit(node.getStat());
        printer.decrementIndent();

        printer.decrementIndent();

        return null;
    }

    @Override
    public Void visit(ArrayElemNode node) {
        printer.printLine(node.getValue());

        List<Node> indices = node.getIndices();
        printer.incrementIndent();

        for (Node i : indices) {
            printer.printLine("[]");
            printer.incrementIndent();
            visit(i);
            printer.decrementIndent();
        }

        printer.decrementIndent();

        return null;
    }

    @Override
    public Void visit(ArrayLitNode node) {
        printer.printLine(node.getValue());

        List<Node> exprNodes = node.getExprNodes();
        printer.incrementIndent();

        for (Node expr : exprNodes) {
            visit(expr);
        }

        printer.decrementIndent();

        return null;
    }

    @Override
    public Void visit(ArrayTypeNode node) {
        printer.printLine(node.getValue());
        return null;
    }

    @Override
    public Void visit(BoolTypeNode node) {
        printer.printLine(node.getValue());
        return null;
    }

    @Override
    public Void visit(CharTypeNode node) {
        printer.printLine(node.getValue());
        return null;
    }

    @Override
    public Void visit(FuncCallNode node) {
        printer.printLine(node.getValue());

        List<Node> args = node.getArgs();
        for (Node arg : args) {
            visit(arg);
        }

        return null;
    }

    @Override
    public Void visit(FuncNode node) {
        printer.printLine(node.getValue());

        printer.incrementIndent();
        visit(node.getBody());
        printer.decrementIndent();

        return null;
    }

    @Override
    public Void visit(IntTypeNode node) {
        printer.printLine(node.getValue());
        return null;
    }

    @Override
    public Void visit(NewPairNode node) {
        printer.printLine(node.getValue());

        printer.incrementIndent();

        printer.printLine("FST");
        printer.incrementIndent();
        visit(node.getFstExpr());
        printer.decrementIndent();

        printer.printLine("SND");
        printer.incrementIndent();
        visit(node.getSndExpr());
        printer.decrementIndent();

        printer.decrementIndent();

        return null;
    }

    @Override
    public Void visit(PairElemNode node) {
        printer.printLine(node.getValue());
        return null;
    }

    @Override
    public Void visit(PairTypeNode node) {
        printer.printLine(node.getValue());
        return null;
    }

    @Override
    public Void visit(ParamNode node) {
        printer.printLine(node.getValue());
        return null;
    }

    @Override
    public Void visit(ProgNode node) {
        printer.printLine(node.getValue());
        printer.incrementIndent();

        List<FuncNode> functions = node.getFunctionNodes();
        for (Node function : functions) {
            visit(function);
        }

        printer.printLine("int main()");

        printer.incrementIndent();
        visit(node.getStatementNode());
        printer.decrementIndent();

        printer.decrementIndent();
        System.out.println(printer.toString());
        return null;
    }

    @Override
    public Void visit(StringTypeNode node) {
        printer.printLine(node.getValue());
        return null;
    }

}

