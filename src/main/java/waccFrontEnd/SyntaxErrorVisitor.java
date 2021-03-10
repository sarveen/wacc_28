package waccFrontEnd;

import antlr.WaccParser;
import antlr.WaccParserBaseVisitor;
import org.antlr.v4.runtime.ParserRuleContext;

import java.math.BigInteger;

public class SyntaxErrorVisitor extends WaccParserBaseVisitor<String> {

  // ---------------------------------------------------------------------- //
  // Extends the Base Visitor for the parser to traverse it and check ctxs  //
  // for syntax errors.                                                     //
  // ---------------------------------------------------------------------- //

  // Used to return a string representation of a parser context for returning
  // errors
  private String getCtxName(ParserRuleContext ctx) {
    String str = ctx.getClass().getName();
    str = str.substring(str.indexOf("$") + 1, str.lastIndexOf("Context"));
    str = str.toLowerCase();
    return str;
  }

  @Override
  public String visitProg(WaccParser.ProgContext ctx) {
    String errorStart = "Syntax Errors detected during compilation! Exit code 100 returned.\n";
    String error = errorStart;
    for (int i = 0; i < ctx.func().size(); i++) {
      String funcResult = visit(ctx.func(i));
      if (funcResult.startsWith("ERROR")) {
        error += funcResult.substring(5);
      }
    }
    String statResult = visit(ctx.stat());
    if (statResult.startsWith("ERROR")) {
      error += statResult.substring(5);
    }
    if (!error.equals(errorStart)) {
      return error;
    }
    return "SUCCESS";
  }

  @Override
  public String visitFunc(WaccParser.FuncContext ctx) {
    String stmtName = getCtxName(ctx.stat());
    if (stmtName.equals("seq_stmts") || stmtName.equals("if_stat")) {
      stmtName = visit(ctx.stat());
    }
    if (!(stmtName.equals("exit_stat") || stmtName.equals("return_stat"))) {
      String error =
          "Function " + ctx.IDENT().getText() + " does not end with an exit or return stmt\n";
      return "ERROR" + error;
    }
    return "SUCCESS";
  }

  @Override
  public String visitSeq_stmts(WaccParser.Seq_stmtsContext ctx) {
    for (int i = 0; i < ctx.stat().size(); i++) {
      if (visit(ctx.stat(i)).equals("ERROR")) {
        return "ERROR";
      }
    }
    String res = getCtxName(ctx.stat(ctx.stat().size() - 1));
    if (res.equals("if_stat")) {
      res = visit(ctx.stat(ctx.stat().size() - 1));
    }
    return res;
  }

  @Override
  public String visitInt_lit(WaccParser.Int_litContext ctx) {
    BigInteger value = new BigInteger(ctx.getText());
    if (value.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) > 0
        || value.compareTo(BigInteger.valueOf(Integer.MIN_VALUE)) < 0) {
      String error = "Integer overflow: " + value + "\n";
      return "ERROR" + error;
    }
    return "SUCCESS";
  }

  @Override
  public String visitVar_declaration(WaccParser.Var_declarationContext ctx) {
    String ctxName = getCtxName(ctx.assign_rhs());
    if (ctxName.equals("expr_rhs")) {

      return visit(ctx.assign_rhs());
    }
    return "SUCCESS";
  }

  @Override
  public String visitVar_assignment(WaccParser.Var_assignmentContext ctx) {
    String ctxName = getCtxName(ctx.assign_rhs());
    if (ctxName.equals("expr_rhs")) {
      return visit(ctx.assign_rhs());
    }
    return "SUCCESS";
  }

  @Override
  public String visitExpr_rhs(WaccParser.Expr_rhsContext ctx) {
    String ctxName = getCtxName(ctx.expr());
    if (ctxName.equals("int_literal")
        || ctxName.equals("binOp_expr")
        || ctxName.equals("unOp_expr")
        || ctxName.equals("nested_expr")) {
      return visit(ctx.expr());
    }
    return "SUCCESS";
  }

  @Override
  public String visitPrint_stat(WaccParser.Print_statContext ctx) {
    String ctxName = getCtxName(ctx.expr());
    if (ctxName.equals("int_literal")
        || ctxName.equals("binOp_expr")
        || ctxName.equals("unOp_expr")
        || ctxName.equals("nested_expr")) {
      return visit(ctx.expr());
    }
    return "SUCCESS";
  }

  @Override
  public String visitPrintln_stat(WaccParser.Println_statContext ctx) {
    String ctxName = getCtxName(ctx.expr());
    if (ctxName.equals("int_literal")
        || ctxName.equals("binOp_expr")
        || ctxName.equals("unOp_expr")
        || ctxName.equals("nested_expr")) {
      return visit(ctx.expr());
    }
    return "SUCCESS";
  }

  @Override
  public String visitScope_stat(WaccParser.Scope_statContext ctx) {
    return visit(ctx.stat());
  }

  @Override
  public String visitReturn_stat(WaccParser.Return_statContext ctx) {
    String ctxName = getCtxName(ctx.expr());
    if (ctxName.equals("int_literal")
        || ctxName.equals("binOp_expr")
        || ctxName.equals("unOp_expr")
        || ctxName.equals("nested_expr")) {
      return visit(ctx.expr());
    }
    return "SUCCESS";
  }

  @Override
  public String visitExit_stat(WaccParser.Exit_statContext ctx) {
    String ctxName = getCtxName(ctx.expr());
    if (ctxName.equals("int_literal")
        || ctxName.equals("binOp_expr")
        || ctxName.equals("unOp_expr")
        || ctxName.equals("nested_expr")) {
      return visit(ctx.expr());
    }
    return "SUCCESS";
  }

  @Override
  public String visitFree_stat(WaccParser.Free_statContext ctx) {
    return "SUCCESS";
  }

  @Override
  public String visitWhile_stat(WaccParser.While_statContext ctx) {
    String ctxName = getCtxName(ctx.expr());
    if (ctxName.equals("int_literal")
        || ctxName.equals("binOp_expr")
        || ctxName.equals("unOp_expr")
        || ctxName.equals("nested_expr")) {
      if (visit(ctx.expr()).equals("ERROR")) {
        return "ERROR";
      }
    }
    return visit(ctx.stat());
  }

  @Override
  public String visitIf_stat(WaccParser.If_statContext ctx) {
    String ctxName = getCtxName(ctx.expr());
    if (ctxName.equals("int_literal")
        || ctxName.equals("binOp_expr")
        || ctxName.equals("unOp_expr")
        || ctxName.equals("nested_expr")) {
      if (visit(ctx.expr()).equals("ERROR")) {
        return "ERROR";
      }
    }
    for (int i = 0; i < 2; i++) {
      if (visit(ctx.stat(i)).equals("ERROR")) {
        return "ERROR";
      }
    }
    String stat1Name = getCtxName(ctx.stat(0));
    String stat2Name = getCtxName(ctx.stat(1));
    if (stat1Name.equals("seq_stmts") || stat1Name.equals("if_stat")) {
      stat1Name = visit(ctx.stat(0));
    }
    if (stat2Name.equals("seq_stmts") || stat2Name.equals("if_stat")) {
      stat2Name = visit(ctx.stat(1));
    }
    if (((stat1Name.equals("return_stat") || stat1Name.equals("exit_stat")))
        && ((stat2Name.equals("return_stat") || stat2Name.equals("exit_stat")))) {
      return "return_stat";
    }
    return "SUCCESS";
  }

  @Override
  public String visitSkip_stmt(WaccParser.Skip_stmtContext ctx) {
    return "SUCCESS";
  }

  @Override
  public String visitRead_stat(WaccParser.Read_statContext ctx) {
    return "SUCCESS";
  }

  @Override
  public String visitUnOp_expr(WaccParser.UnOp_exprContext ctx) {
    String ctxName = getCtxName(ctx.expr());
    if (ctxName.equals("int_literal")
        || ctxName.equals("binOp_expr")
        || ctxName.equals("unOp_expr")
        || ctxName.equals("nested_expr")) {
      return visit(ctx.expr());
    }
    return "SUCCESS";
  }

  @Override
  public String visitBinOp_expr(WaccParser.BinOp_exprContext ctx) {
    for (int i = 0; i < 2; ++i) {
      String ctxName = getCtxName(ctx.expr().get(i));
      if (ctxName.equals("int_literal")
          || ctxName.equals("binOp_expr")
          || ctxName.equals("unOp_expr")
          || ctxName.equals("nested_expr")) {
        if (visit(ctx.expr(i)).equals("ERROR")) {
          return "ERROR";
        }
      }
    }
    return "SUCCESS";
  }

  @Override
  public String visitNested_expr(WaccParser.Nested_exprContext ctx) {
    String ctxName = getCtxName(ctx.expr());
    if (ctxName.equals("int_literal")
        || ctxName.equals("binOp_expr")
        || ctxName.equals("unOp_expr")
        || ctxName.equals("nested_expr")) {
      return visit(ctx.expr());
    }
    return "SUCCESS";
  }
}
