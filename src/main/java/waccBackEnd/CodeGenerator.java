package waccBackEnd;

import waccBackEnd.ARM11Instructions.*;
import waccFrontEnd.AST.ASTBaseVisitor;
import waccFrontEnd.AST.Assignments.ArrayLitNode;
import waccFrontEnd.AST.Assignments.NewPairNode;
import waccFrontEnd.AST.Assignments.PairElemNode;
import waccFrontEnd.AST.Expressions.*;
import waccFrontEnd.AST.Functions.FuncCallNode;
import waccFrontEnd.AST.Functions.FuncNode;
import waccFrontEnd.AST.Functions.ParamNode;
import waccFrontEnd.AST.ProgNode;
import waccFrontEnd.AST.Node;
import waccFrontEnd.AST.Statements.*;
import waccFrontEnd.AST.Types.*;

import java.util.List;

public class CodeGenerator extends ASTBaseVisitor<ARMRegister> {

  // -------------------------------------------------------------------- //
  // Extension of the ASTBaseVisitor traversing the AST to add ARM11      //
  // instructions to the internal program representation. Also uses the   //
  // MemoryManagement class to keep track of which registers are being    //
  // and where variables exist on the stack representation.               //
  // -------------------------------------------------------------------- //

  private final ARM11ProgramRepresentation rep;
  private final MemoryManagement memoryManager;

  public CodeGenerator() {
    this.rep = new ARM11ProgramRepresentation();
    this.memoryManager = new MemoryManagement(rep);
  }

  public ARM11ProgramRepresentation getRep() {
    return rep;
  }

  // Helper Methods
  private void subtractScopeSize(int scopeSize) {
    if(scopeSize == 0){
      return;
    }
    while (scopeSize > 1024) {
      rep.addInstruction(new SUBInstruction(ARMRegister.sp, ARMRegister.sp, 1024));
      scopeSize -= 1024;
    }
    rep.addInstruction(new SUBInstruction(ARMRegister.sp, ARMRegister.sp, scopeSize));
  }

  private void addScopeSize(int scopeSize) {
    if(scopeSize == 0){
      return;
    }
    while (scopeSize > 1024) {
      rep.addInstruction(new ADDInstruction(ARMRegister.sp, ARMRegister.sp, 1024));
      scopeSize -= 1024;
    }
    rep.addInstruction(new ADDInstruction(ARMRegister.sp, ARMRegister.sp, scopeSize));
  }

  // Visit Methods
  @Override
  public ARMRegister visit(ProgNode node) {
    List<FuncNode> funcs = node.getFunctionNodes();
    for (FuncNode func : funcs) {
      visit(func);
    }
    rep.openLabel("main");
    rep.addInstruction(new PUSHInstruction(ARMRegister.lr));
    int scopeSize = node.getStmtScopeSize();
    subtractScopeSize(scopeSize);
    memoryManager.resetStackPointer(node.getStmtScopeSize());
    memoryManager.incrementCurrentStackPointer(node.getStmtScopeSize());
    visit(node.getStatementNode());
    addScopeSize(scopeSize);
    rep.addInstruction(new LDRInstruction(ARMRegister.r0, 0));
    rep.addInstruction(new POPInstruction(ARMRegister.pc));
    rep.closeLabel();
    return null;
  }

  @Override
  public ARMRegister visit(BinaryExpressionNode node) {
    ARMRegister op1Register = visit(node.getLeftOperand());
    ARMRegister op2Register = visit(node.getRightOperand());
    ARMRegister dstRegister = op1Register;
    if (op1Register.ordinal() >= 10 && op2Register.ordinal() >= 10) {
      rep.addInstruction(new POPInstruction(ARMRegister.r11));
      op1Register = ARMRegister.r11;
      op2Register = ARMRegister.r10;
    }
    switch (node.getOperator()) {
      case "+":
        rep.addInstruction(new ADDSInstruction(dstRegister, op1Register, op2Register));
        rep.addInstruction(new BLVSInstruction("p_throw_overflow_error"));
        rep.setProgramLabel("overflowLabelCreated");
        break;
      case "-":
        rep.addInstruction(new SUBSInstruction(dstRegister, op1Register, op2Register));
        rep.addInstruction(new BLVSInstruction("p_throw_overflow_error"));
        rep.setProgramLabel("overflowLabelCreated");
        break;
      case "*":
        rep.addInstruction(new SMULLInstruction(op1Register, op2Register));
        rep.addInstruction(new CMPWithConditionInstruction(op2Register, op1Register, "ASR #31"));
        rep.addInstruction(new BLNEInstruction("p_throw_overflow_error"));
        rep.setProgramLabel("overflowLabelCreated");
        break;
      case "/":
        rep.addInstruction(new MOVInstruction(ARMRegister.r0, op1Register));
        rep.addInstruction(new MOVInstruction(ARMRegister.r1, op2Register));
        rep.setProgramLabel("divideByZeroLabelCreated");
        rep.addInstruction(new BLInstruction("p_check_divide_by_zero"));
        rep.addInstruction(new BLInstruction("__aeabi_idiv"));
        rep.addInstruction(new MOVInstruction(op1Register, ARMRegister.r0));
        break;
      case "%":
        rep.addInstruction(new MOVInstruction(ARMRegister.r0, op1Register));
        rep.addInstruction(new MOVInstruction(ARMRegister.r1, op2Register));
        rep.setProgramLabel("divideByZeroLabelCreated");
        rep.addInstruction(new BLInstruction("p_check_divide_by_zero"));
        rep.addInstruction(new BLInstruction("__aeabi_idivmod"));
        rep.addInstruction(new MOVInstruction(op1Register, ARMRegister.r1));
        break;
      case "&&":
        rep.addInstruction(new ANDInstruction(dstRegister, op1Register, op2Register));
        break;
      case "||":
        rep.addInstruction(new ORRInstruction(dstRegister, op1Register, op2Register));
        break;
      case "<":
        rep.addInstruction(new CMPInstruction(op1Register, op2Register));
        rep.addInstruction(new MOVLTInstruction(op1Register, 1));
        rep.addInstruction(new MOVGEInstruction(op1Register, 0));
        break;
      case ">":
        rep.addInstruction(new CMPInstruction(op1Register, op2Register));
        rep.addInstruction(new MOVGTInstruction(op1Register, 1));
        rep.addInstruction(new MOVLEInstruction(op1Register, 0));
        break;
      case "<=":
        rep.addInstruction(new CMPInstruction(op1Register, op2Register));
        rep.addInstruction(new MOVLEInstruction(op1Register, 1));
        rep.addInstruction(new MOVGTInstruction(op1Register, 0));
        break;
      case ">=":
        rep.addInstruction(new CMPInstruction(op1Register, op2Register));
        rep.addInstruction(new MOVGEInstruction(op1Register, 1));
        rep.addInstruction(new MOVLTInstruction(op1Register, 0));
        break;
      case "==":
        rep.addInstruction(new CMPInstruction(op1Register, op2Register));
        rep.addInstruction(new MOVEQInstruction(op1Register, 1));
        rep.addInstruction(new MOVNEInstruction(op1Register, 0));
        break;
      case "!=":
        rep.addInstruction(new CMPInstruction(op1Register, op2Register));
        rep.addInstruction(new MOVNEInstruction(op1Register, 1));
        rep.addInstruction(new MOVEQInstruction(op1Register, 0));
        break;
    }
    if (op1Register.equals(ARMRegister.r11)) {
      op1Register = ARMRegister.r10;
    }
    memoryManager.freeRegister(op2Register);
    return op1Register;
  }

  @Override
  public ARMRegister visit(BoolLitNode node) {
    ARMRegister register = memoryManager.allocateFreeRegister();
    int value = node.getValue() ? 1 : 0;
    rep.addInstruction(new MOVInstruction(register, value));
    return register;
  }

  private boolean isEscapeCharacter(char c) {
    return c == '\t' || c == '\0' || c == '\n' || c == '\b' || c == '\f' || c == '\r';
  }

  @Override
  public ARMRegister visit(CharLitNode node) {
    ARMRegister register = memoryManager.allocateFreeRegister();
    char c = node.getValue();
    if (isEscapeCharacter(c)) {
      rep.addInstruction(new MOVInstruction(register, (int) c));
    } else {
      rep.addInstruction(new MOVInstruction(register, "'" + c + "'"));
    }
    return register;
  }

  @Override
  public ARMRegister visit(IdentNode node) {
    String ident = node.getValue();
    int offset = memoryManager.getStackOffset(ident);
    ARMRegister register = memoryManager.allocateFreeRegister();
    Node typeNode = node.getTypeNode();
    if (typeNode instanceof CharTypeNode
        || typeNode instanceof CharLitNode
        || typeNode instanceof BoolTypeNode
        || typeNode instanceof BoolLitNode) {
      rep.addInstruction(new LDRSBInstruction(register, ARMRegister.sp, offset));
    } else {
      rep.addInstruction(new LDRFromMemoryInstruction(register, ARMRegister.sp, offset));
    }
    return register;
  }

  @Override
  public ARMRegister visit(IntLitNode node) {
    ARMRegister register = memoryManager.allocateFreeRegister();
    rep.addInstruction(new LDRInstruction(register, node.getValue()));
    return register;
  }

  @Override
  public ARMRegister visit(NullNode node) {
    ARMRegister register = memoryManager.allocateFreeRegister();
    rep.addInstruction(new LDRInstruction(register, 0));
    return register;
  }

  @Override
  public ARMRegister visit(StrLitNode node) {
    ARMRegister register = memoryManager.allocateFreeRegister();
    String msg = node.getValue();
    String msgLabel = rep.addMessage(msg);
    rep.addInstruction(new LDRInstruction(register, msgLabel));
    return register;
  }

  @Override
  public ARMRegister visit(UnaryExpressionNode node) {
    ARMRegister operand_register = visit(node.getOperand());
    switch (node.getOperator()) {
      case "-":
        rep.addInstruction(new RSBSInstruction(operand_register, operand_register, 0));
        rep.addInstruction(new BLVSInstruction("p_throw_overflow_error"));
        rep.setProgramLabel("overflowLabelCreated");
        break;
      case "!":
        rep.addInstruction(new EORInstruction(operand_register, operand_register, 1));
        break;
      case "len":
        rep.addInstruction(new LDRFromMemoryInstruction(operand_register, operand_register, 0));
      default:
        break;
    }
    return operand_register;
  }

  @Override
  public ARMRegister visit(AssignmentNode node) {
    Node rhs = node.getRhs();
    ARMRegister rhsRegister = visit(rhs);
    // RhsPairElem
    if(rhs instanceof PairElemNode){
      Node typeNode = ((PairElemNode) rhs).getTypeNode();
      if(typeNode instanceof CharTypeNode || typeNode instanceof CharLitNode || typeNode instanceof BoolTypeNode || typeNode instanceof BoolLitNode){
        rep.addInstruction(new LDRSBInstruction(rhsRegister, rhsRegister, 0));
      } else {
        rep.addInstruction(new LDRFromMemoryInstruction(rhsRegister, rhsRegister, 0));
      }
    }
    Node lhs = node.getLhs();
    // Ident
    if (lhs instanceof IdentNode) {
      String ident = ((IdentNode) lhs).getValue();
      int offset = memoryManager.getStackOffset(ident);
      Node typeNode = ((IdentNode) lhs).getTypeNode();
      if (typeNode instanceof CharTypeNode
          || typeNode instanceof CharLitNode
          || typeNode instanceof BoolLitNode
          || typeNode instanceof BoolTypeNode) {
        rep.addInstruction(new STRInstruction(rhsRegister, ARMRegister.sp, offset, false, true));
      } else {
        rep.addInstruction(new STRInstruction(rhsRegister, ARMRegister.sp, offset));
      }
    // LhsPairElem or LhsArrayElem
    } else if (lhs instanceof ArrayElemNode || lhs instanceof PairElemNode) {
      ARMRegister lhsRegister = visit(lhs);
      Node typeNode;
      if (lhs instanceof ArrayElemNode) {
        typeNode = ((ArrayElemNode) lhs).getTypeNode();
      } else {
        typeNode = ((PairElemNode) lhs).getTypeNode();
      }
      if (typeNode instanceof CharTypeNode
          || typeNode instanceof CharLitNode
          || typeNode instanceof BoolLitNode
          || typeNode instanceof BoolTypeNode) {
        rep.addInstruction(new STRInstruction(rhsRegister, lhsRegister, 0, false, true));
      } else {
        rep.addInstruction(new STRInstruction(rhsRegister, lhsRegister, 0));
      }
      memoryManager.freeRegister(lhsRegister);
    }
    memoryManager.freeRegister(rhsRegister);
    return null;
  }

  @Override
  public ARMRegister visit(DeclarationNode node) {
    Node rhs = node.getRhs();
    Node type = node.getType();
    ARMRegister register = visit(rhs);
    // ArrayELem or PairElem
    if (rhs instanceof ArrayElemNode || rhs instanceof PairElemNode) {
      Node typeNode;
      if(rhs instanceof ArrayElemNode){
        typeNode = ((ArrayElemNode) rhs).getTypeNode();
      }else{
        typeNode = ((PairElemNode) rhs).getTypeNode();
      }
      if(typeNode instanceof CharTypeNode || typeNode instanceof CharLitNode || typeNode instanceof BoolTypeNode || typeNode instanceof BoolLitNode){
        rep.addInstruction(new LDRSBInstruction(register, register, 0));
      } else {
        rep.addInstruction(new LDRFromMemoryInstruction(register, register, 0));
      }
    }
    int dec = 4;
    if (type instanceof CharTypeNode || type instanceof BoolTypeNode) {
      dec = 1;
    }
    memoryManager.decrementCurrentStackPointer(dec);
    if (dec == 1) {
      rep.addInstruction(
          new STRInstruction(
              register, ARMRegister.sp, memoryManager.getCurrentStackPointer(), false, true));
    } else {
      rep.addInstruction(
          new STRInstruction(register, ARMRegister.sp, memoryManager.getCurrentStackPointer()));
    }
    memoryManager.freeRegister(register);
    memoryManager.addToStack(((IdentNode) node.getLhs()).getValue());
    return null;
  }

  @Override
  public ARMRegister visit(ExitNode node) {
    ARMRegister reg = visit(node.getExpr());
    rep.addInstruction(new MOVInstruction(ARMRegister.r0, reg));
    memoryManager.freeRegister(reg);
    rep.addInstruction(new BLInstruction("exit"));
    return null;
  }

  @Override
  public ARMRegister visit(FreeNode node) {
    ARMRegister register = visit(node.getExpr());
    rep.addInstruction(new MOVInstruction(ARMRegister.r0, register));
    Node typeNode = node.getTypeNode();
    if (typeNode instanceof ArrayTypeNode || typeNode instanceof ArrayLitNode) {
      rep.addInstruction(new BLInstruction("p_free_array"));
      rep.setProgramLabel("freeArrayLabelCreated");
    } else {
      rep.addInstruction(new BLInstruction("p_free_pair"));
      rep.setProgramLabel("freePairLabelCreated");
    }
    memoryManager.freeRegister(register);
    return null;
  }

  @Override
  public ARMRegister visit(IfNode node) {
    // Condition
    ARMRegister register = visit(node.getCond());
    rep.addInstruction(new CMPInstruction(register, 0));
    memoryManager.freeRegister(register);
    String label1 = rep.generateLabel();
    String label2 = rep.generateLabel();
    rep.addInstruction(new BEQInstruction(label1));
    // Else
    rep.openLabel(label1);
    subtractScopeSize(node.getElseScopeSize());
    memoryManager.resetStackPointer(node.getElseScopeSize());
    memoryManager.incrementCurrentStackPointer(node.getElseScopeSize());
    visit(node.getElseStat());
    memoryManager.goBackToOldStackPointer();
    addScopeSize(node.getElseScopeSize());
    rep.closeLabel();
    // Then
    subtractScopeSize(node.getThenScopeSize());
    memoryManager.resetStackPointer(node.getThenScopeSize());
    memoryManager.incrementCurrentStackPointer(node.getThenScopeSize());
    visit(node.getThenStat());
    memoryManager.goBackToOldStackPointer();
    addScopeSize(node.getThenScopeSize());
    rep.addInstruction(new BLInstruction(label2));
    rep.closeLabel();
    rep.openLabel(label2);
    return null;
  }

  // Since the differences functionally between visiting printLn and print
  // Nodes are so minute, we made a helper function taking a boolean flag to
  // handle these and remove code duplication.

  @Override
  public ARMRegister visit(PrintLnNode node) {
    return printHelper(node, true);
  }

  @Override
  public ARMRegister visit(PrintNode node) {
    return printHelper(node, false);
  }

  public ARMRegister printHelper(PrintNode node, boolean isLn) {
    Node exprNode = node.getExpr();
    ARMRegister register = visit(exprNode);
    Node typeNode = exprNode;
    // Ident
    if (exprNode instanceof IdentNode) {
      typeNode = ((IdentNode) exprNode).getTypeNode();
    // ArrayElem
    } else if (exprNode instanceof ArrayElemNode) {
      typeNode = ((ArrayElemNode) exprNode).getTypeNode();
      if (typeNode instanceof CharTypeNode
          || typeNode instanceof CharLitNode
          || typeNode instanceof BoolLitNode
          || typeNode instanceof BoolTypeNode) {
        rep.addInstruction(new LDRSBInstruction(register, register, 0));
      }else{
        rep.addInstruction(new LDRFromMemoryInstruction(register, register, 0));
      }
    // UnaryExpression
    } else if (exprNode instanceof UnaryExpressionNode) {
      typeNode = ((UnaryExpressionNode) exprNode).getTypeNode();
    // BinaryExpression
    } else if (exprNode instanceof BinaryExpressionNode) {
      typeNode = ((BinaryExpressionNode) exprNode).getTypeNode();
    }
    rep.addInstruction(new MOVInstruction(ARMRegister.r0, register));
    if (typeNode instanceof ArrayTypeNode) {
      if (((ArrayTypeNode) typeNode).getType() instanceof CharTypeNode
          || ((ArrayTypeNode) typeNode).getType() instanceof CharLitNode) {
        typeNode = new StringTypeNode();
      }
    // ArrayLit
    } else if (typeNode instanceof ArrayLitNode) {
      if (((ArrayLitNode) typeNode).getInnerType() instanceof CharLitNode
          || ((ArrayLitNode) typeNode).getInnerType() instanceof CharTypeNode) {
        typeNode = new StringTypeNode();
      }
    }
    // Char
    if (typeNode instanceof CharTypeNode || typeNode instanceof CharLitNode) {
      rep.addInstruction(new BLInstruction(("putchar")));
    // Int
    } else if (typeNode instanceof IntTypeNode || typeNode instanceof IntLitNode) {
      rep.addInstruction(new BLInstruction("p_print_int"));
      rep.setProgramLabel("printIntLabelCreated");
    // String
    } else if (typeNode instanceof StringTypeNode || typeNode instanceof StrLitNode) {
      rep.addInstruction(new BLInstruction("p_print_string"));
      rep.setProgramLabel("printStringLabelCreated");
    // Bool
    } else if (typeNode instanceof BoolTypeNode || typeNode instanceof BoolLitNode) {
      rep.addInstruction(new BLInstruction(("p_print_bool")));
      rep.setProgramLabel("printBoolLabelCreated");
    // Pairs and Arrays
    } else {
      rep.addInstruction(new BLInstruction("p_print_reference"));
      rep.setProgramLabel("printRefLabelCreated");
    }
    // if printing on new line or not
    if (isLn) {
      rep.addInstruction(new BLInstruction("p_print_ln"));
      rep.setProgramLabel("printLnLabelCreated");
    }
    memoryManager.freeRegister(register);
    return null;
  }

  @Override
  public ARMRegister visit(ReadNode node) {
    Node exprNode = node.getExpr();
    ARMRegister register = visit(exprNode);
    Node typenode = exprNode;
    // Ident
    if (exprNode instanceof IdentNode) {
      String ident = ((IdentNode) exprNode).getValue();
      typenode = ((IdentNode) exprNode).getTypeNode();
      int offset = memoryManager.getStackOffset(ident);
      rep.addInstruction(new ADDInstruction(register, ARMRegister.sp, offset));
    // ArrayElem
    } else if (exprNode instanceof ArrayElemNode) {
      typenode = ((ArrayElemNode) exprNode).getTypeNode();
      rep.addInstruction(new MOVInstruction(register, register));
    // PairElem
    } else if (exprNode instanceof PairElemNode) {
      typenode = ((PairElemNode) exprNode).getTypeNode();
    }
    rep.addInstruction(new MOVInstruction(ARMRegister.r0, register));
    if (typenode instanceof IntLitNode || typenode instanceof IntTypeNode) {
      rep.addInstruction(new BLInstruction("p_read_int"));
      rep.setProgramLabel("readIntLabelCreated");
    } else if (typenode instanceof CharLitNode || typenode instanceof CharTypeNode) {
      rep.addInstruction(new BLInstruction("p_read_char"));
      rep.setProgramLabel("readCharLabelCreated");
    }
    memoryManager.freeRegister(register);
    return null;
  }

  @Override
  public ARMRegister visit(ScopeNode node) {
    subtractScopeSize(node.getScopeSize());
    memoryManager.resetStackPointer(node.getScopeSize());
    memoryManager.incrementCurrentStackPointer(node.getScopeSize());
    visit(node.getStmt());
    addScopeSize(node.getScopeSize());
    memoryManager.goBackToOldStackPointer();
    return null;
  }

  @Override
  public ARMRegister visit(SeqNode node) {
    for (Node statNode : node.getStats()) {
      visit(statNode);
    }
    return null;
  }

  @Override
  public ARMRegister visit(SkipNode node) {
    return null;
  }

  @Override
  public ARMRegister visit(WhileNode node) {
    String condLabel = rep.generateLabel();
    String bodyLabel = rep.generateLabel();
    rep.addInstruction(new BInstruction(condLabel));
    rep.closeLabel();
    // Body
    rep.openLabel(bodyLabel);
    memoryManager.resetStackPointer(node.getScopeSize());
    memoryManager.incrementCurrentStackPointer(node.getScopeSize());
    visit(node.getStat());
    memoryManager.goBackToOldStackPointer();
    rep.closeLabel();
    // Condition
    rep.openLabel(condLabel);
    ARMRegister register = visit(node.getCond());
    rep.addInstruction(new CMPInstruction(register, 1));
    rep.addInstruction(new BEQInstruction(bodyLabel));
    memoryManager.freeRegister(register);
    return null;
  }

  @Override
  public ARMRegister visit(ArrayElemNode node) {
    List<Node> indices = node.getIndices();
    String ident = node.getValue();
    int typeSize = 4;
    Node typeNode = node.getTypeNode();
    if (typeNode instanceof CharTypeNode
        || typeNode instanceof BoolTypeNode
        || typeNode instanceof CharLitNode
        || typeNode instanceof BoolLitNode) {
      typeSize = 1;
    }
    int offset = memoryManager.getStackOffset(ident);
    ARMRegister register = memoryManager.allocateFreeRegister();
    rep.addInstruction(new ADDInstruction(register, ARMRegister.sp, offset));
    for (int i = 0; i < indices.size(); i++) {
      ARMRegister indexRegister = visit(indices.get(i));
      rep.addInstruction(new LDRFromMemoryInstruction(register, register, 0));
      rep.setProgramLabel("arrayOutOfBoundsLabelCreated");
      rep.addInstruction(new MOVInstruction(ARMRegister.r0, indexRegister));
      rep.addInstruction(new MOVInstruction(ARMRegister.r1, register));
      rep.addInstruction(new BLInstruction("p_check_array_bounds"));
      rep.addInstruction(new ADDInstruction(register, register, 4));
      if (i == indices.size() - 1 && typeSize == 1) {
        rep.addInstruction(new ADDInstruction(register, register, indexRegister));
      } else {
        rep.addInstruction(new ADDWithShiftInstruction(register, register, indexRegister, 2));
      }
      memoryManager.freeRegister(indexRegister);
    }
    return register;
  }

  @Override
  public ARMRegister visit(ArrayLitNode node) {
    int typeSize = 4;
    Node typeNode = node.getTypeNode();
    if (typeNode instanceof CharTypeNode
        || typeNode instanceof BoolTypeNode
        || typeNode instanceof CharLitNode
        || typeNode instanceof BoolLitNode) {
      typeSize = 1;
    }
    List<Node> exprs = node.getExprNodes();
    int mallocSize = node.getExprNodes().size();
    int mallocBytes = typeSize * mallocSize + 4;
    rep.addInstruction(new LDRInstruction(ARMRegister.r0, mallocBytes));
    rep.addInstruction(new BLInstruction("malloc"));
    ARMRegister register = memoryManager.allocateFreeRegister();
    rep.addInstruction(new MOVInstruction(register, ARMRegister.r0));
    ARMRegister exprRegister = null;
    for (int i = 0; i < exprs.size(); i++) {
      exprRegister = visit(exprs.get(i));
      if (i == 0) {
        if(typeSize == 1){
          rep.addInstruction(new STRInstruction(exprRegister, register, 4,false,true));
        } else {
          rep.addInstruction(new STRInstruction(exprRegister, register, 4));
        }
      } else {
        if (typeSize == 1) {
          rep.addInstruction(new STRInstruction(exprRegister, register, 4 + (i) * typeSize,false,true));
        } else {
          rep.addInstruction(new STRInstruction(exprRegister, register, 4 + (i) * typeSize));
        }
      }
      memoryManager.freeRegister(exprRegister);
    }
    if (exprRegister == null) {
      exprRegister = memoryManager.allocateFreeRegister();
      memoryManager.freeRegister(exprRegister);
    }
    rep.addInstruction(new LDRInstruction(exprRegister, exprs.size()));
    rep.addInstruction(new STRInstruction(exprRegister, register, 0));
    return register;
  }

  @Override
  public ARMRegister visit(ArrayTypeNode node) {
    return null;
  }

  @Override
  public ARMRegister visit(BoolTypeNode node) {
    return null;
  }

  @Override
  public ARMRegister visit(CharTypeNode node) {
    return null;
  }

  @Override
  public ARMRegister visit(PairTypeNode node) {
    return null;
  }


  @Override
  public ARMRegister visit(StringTypeNode node) {
    return null;
  }

  @Override
  public ARMRegister visit(IntTypeNode node) {
    return null;
  }

  @Override
  public ARMRegister visit(FuncCallNode node) {
    List<Node> args = node.getArgs();
    List<Node> typeNodes = node.getTypeNodes();
    int totalSize = 0;
    ARMRegister register = memoryManager.allocateFreeRegister();
    for (int i = args.size() - 1; i >= 0; i--) {
      memoryManager.freeRegister(register);
      register = visit(args.get(i));
      Node typeNode = typeNodes.get(i);
      int size = 4;
      if (typeNode instanceof CharTypeNode
          || typeNode instanceof CharLitNode
          || typeNode instanceof BoolTypeNode
          || typeNode instanceof BoolLitNode) {
        size = 1;
      }
      totalSize += size;
      if(size == 1){
        rep.addInstruction(new STRInstruction(register, ARMRegister.sp, (size * -1), true,true));
      } else {
        rep.addInstruction(new STRInstruction(register, ARMRegister.sp, (size * -1), true));
      }
      memoryManager.setExclamationOffset(size+memoryManager.getExclamationOffset());
    }
    memoryManager.setExclamationOffset(0);
    rep.addInstruction(new BLInstruction("f_" + node.getValue()));
    rep.addInstruction(new ADDInstruction(ARMRegister.sp, ARMRegister.sp, totalSize));
    rep.addInstruction(new MOVInstruction(register, ARMRegister.r0));
    return register;
  }

  @Override
  public ARMRegister visit(FuncNode node) {
    rep.openLabel("f_" + node.getName());
    memoryManager.resetStackPointer(node.getScopeSize());
    memoryManager.setLatestFuncStackSize(node.getScopeSize());
    memoryManager.incrementCurrentStackPointer(node.getScopeSize());
    rep.addInstruction(new PUSHInstruction(ARMRegister.lr));
    subtractScopeSize(node.getScopeSize());
    for (Node paramNode : node.getParams()) {
      visit(paramNode);
    }
    int totalSize = 0;
    for (Node paramNode : node.getParams()) {
      ParamNode param = (ParamNode) paramNode;
      Node typeNode = param.getType();
      if(typeNode instanceof BoolTypeNode || typeNode instanceof CharTypeNode){
        totalSize+=1;
      }else{
        totalSize+=4;
      }
    }
    memoryManager.decrementCurrentStackPointer(totalSize);
    visit(node.getBody());
    addScopeSize(node.getScopeSize());
    rep.addInstruction(new POPInstruction(ARMRegister.pc));
    rep.addInstruction(new POPInstruction(ARMRegister.pc));
    memoryManager.goBackToOldStackPointer();
    rep.closeLabel();
    return null;
  }

  @Override
  public ARMRegister visit(ParamNode node) {
    int inc = 4;
    Node typeNode = node.getType();
    if (typeNode instanceof CharTypeNode || typeNode instanceof BoolTypeNode) {
      inc = 1;
    }
    memoryManager.incrementCurrentStackPointer(4);
    memoryManager.addToStack(node.getName());
    memoryManager.incrementCurrentStackPointer(inc);
    memoryManager.decrementCurrentStackPointer(4);
    return null;
  }

  @Override
  public ARMRegister visit(ReturnNode node) {
    ARMRegister register = visit(node.getExpr());
    rep.addInstruction(new MOVInstruction(ARMRegister.r0, register));
    rep.addInstruction(new ADDInstruction(ARMRegister.sp,ARMRegister.sp,memoryManager.getLatestFuncStackSize()));
    rep.addInstruction(new POPInstruction(ARMRegister.pc));
    memoryManager.freeRegister(register);
    return null;
  }

  @Override
  public ARMRegister visit(NewPairNode node) {
    Node fstTypeNode = node.getFstType();
    int type1Bytes = 4;
    int type2Bytes = 4;
    if (fstTypeNode instanceof CharTypeNode || fstTypeNode instanceof BoolTypeNode) {
      type1Bytes = 1;
    }
    Node sndTypeNode = node.getSndType();
    if (sndTypeNode instanceof CharTypeNode || sndTypeNode instanceof BoolTypeNode) {
      type2Bytes = 1;
    }
    rep.addInstruction(new LDRInstruction(ARMRegister.r0, 8));
    rep.addInstruction(new BLInstruction("malloc"));
    ARMRegister register = memoryManager.allocateFreeRegister();
    rep.addInstruction(new MOVInstruction(register, ARMRegister.r0));
    ARMRegister exprRegister = visit(node.getFstExpr());
    rep.addInstruction(new LDRInstruction(ARMRegister.r0, type1Bytes));
    rep.addInstruction(new BLInstruction("malloc"));
    if(type1Bytes == 1){
      rep.addInstruction(new STRInstruction(exprRegister, ARMRegister.r0, 0,false,true));
    } else {
      rep.addInstruction(new STRInstruction(exprRegister, ARMRegister.r0, 0));
    }
    rep.addInstruction(new STRInstruction(ARMRegister.r0, register, 0));
    memoryManager.freeRegister(exprRegister);
    exprRegister = visit(node.getSndExpr());
    rep.addInstruction(new LDRInstruction(ARMRegister.r0, type2Bytes));
    rep.addInstruction(new BLInstruction("malloc"));
    if (type2Bytes == 1) {
      rep.addInstruction(new STRInstruction(exprRegister, ARMRegister.r0, 0,false,true));
    }else{
      rep.addInstruction(new STRInstruction(exprRegister, ARMRegister.r0, 0));
    }
    rep.addInstruction(new STRInstruction(ARMRegister.r0, register, 4));
    memoryManager.freeRegister(exprRegister);
    return register;
  }

  @Override
  public ARMRegister visit(PairElemNode node) {
    String ident = node.getName();
    int offset = memoryManager.getStackOffset(ident);
    ARMRegister register = memoryManager.allocateFreeRegister();
    rep.addInstruction(new LDRFromMemoryInstruction(register, ARMRegister.sp, offset));
    rep.addInstruction(new MOVInstruction(ARMRegister.r0, register));
    rep.addInstruction(new BLInstruction("p_check_null_pointer"));
    rep.setProgramLabel("nullPointerLabelCreated");
    if (node.isFst()) {
      rep.addInstruction(new LDRFromMemoryInstruction(register, register, 0));
    } else {
      rep.addInstruction(new LDRFromMemoryInstruction(register, register, 4));
    }
    return register;
  }

}
