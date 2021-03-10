package waccFrontEnd;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static wacc.WaccCompiler.getOutputMessage;

public class WaccCompilerValidUnitTest {

  private String dir = "src/test/java/wacc/wacc_examples/valid";
  private String expected = "-- Finished\n";

  //Advanced
  @Test
  public void binaryShortTreeTest() {
    assertEquals(getOutputMessage(dir + "/advanced/binarySortTree.wacc",false), expected);
  }

  @Test
  public void hashTableTest() {
    assertEquals(getOutputMessage(dir + "/advanced/hashTable.wacc",false), expected);
  }

  @Test
  public void ticTacToeTest() {
    assertEquals(getOutputMessage(dir + "/advanced/ticTacToe.wacc",false), expected);
  }

  // While
  @Test
  public void fibonacciFullItTest() {
    assertEquals(getOutputMessage(dir + "/while/fibonacciFullIt.wacc",false), expected);
  }

  @Test
  public void fibonacciIterativeTest() {
    assertEquals(getOutputMessage(dir + "/while/fibonacciIterative.wacc",false), expected);
  }

  @Test
  public void loopCharConditionTest() {
    assertEquals(getOutputMessage(dir + "/while/loopCharCondition.wacc",false), expected);
  }

  @Test
  public void loopIntConditionTest() {
    assertEquals(getOutputMessage(dir + "/while/loopIntCondition.wacc",false), expected);
  }

  @Test
  public void maxTest() {
    assertEquals(getOutputMessage(dir + "/while/max.wacc",false), expected);
  }

  @Test
  public void minTest() {
    assertEquals(getOutputMessage(dir + "/while/min.wacc",false), expected);
  }

  @Test
  public void rmStyleAddTest() {
    assertEquals(getOutputMessage(dir + "/while/rmStyleAdd.wacc",false), expected);
  }

  @Test
  public void rmStyleAddIOTest() {
    assertEquals(getOutputMessage(dir + "/while/rmStyleAddIO.wacc",false), expected);
  }

  @Test
  public void whileBasicTest() {
    assertEquals(getOutputMessage(dir + "/while/whileBasic.wacc",false), expected);
  }

  @Test
  public void whileBoolFlipTest() {
    assertEquals(getOutputMessage(dir + "/while/whileBoolFlip.wacc",false), expected);
  }

  @Test
  public void whileCountTest() {
    assertEquals(getOutputMessage(dir + "/while/whileCount.wacc",false), expected);
  }

  @Test
  public void whileFalseTest() {
    assertEquals(getOutputMessage(dir + "/while/whileFalse.wacc",false), expected);
  }

  // Variables

  @Test
  public void VarNameTest() {
    assertEquals(getOutputMessage(dir + "/variables/_VarNames.wacc",false), expected);
  }

  @Test
  public void boolDeclarationTest() {
    assertEquals(getOutputMessage(dir + "/variables/boolDeclaration.wacc",false), expected);
  }

  @Test
  public void boolDeclaration2Test() {
    assertEquals(getOutputMessage(dir + "/variables/boolDeclaration2.wacc",false), expected);
  }

  @Test
  public void capCharDeclarationTest() {
    assertEquals(getOutputMessage(dir + "/variables/capCharDeclaration.wacc",false), expected);
  }

  @Test
  public void CharDeclarationTest() {
    assertEquals(getOutputMessage(dir + "/variables/charDeclaration.wacc",false), expected);
  }

  @Test
  public void CharDeclaration2Test() {
    assertEquals(getOutputMessage(dir + "/variables/charDeclaration2.wacc",false), expected);
  }

  @Test
  public void emptyStringDeclarationTest() {
    assertEquals(getOutputMessage(dir + "/variables/emptyStringDeclaration.wacc",false), expected);
  }

  @Test
  public void intDeclarationTest() {
    assertEquals(getOutputMessage(dir + "/variables/intDeclaration.wacc",false), expected);
  }

  @Test
  public void longVarNamesTest() {
    assertEquals(getOutputMessage(dir + "/variables/longVarNames.wacc",false), expected);
  }

  @Test
  public void manyVariablesTest() {
    assertEquals(getOutputMessage(dir + "/variables/manyVariables.wacc",false), expected);
  }

  @Test
  public void negIntDeclarationTest() {
    assertEquals(getOutputMessage(dir + "/variables/negIntDeclaration.wacc",false), expected);
  }

  @Test
  public void puncCharDeclarationTest() {
    assertEquals(getOutputMessage(dir + "/variables/puncCharDeclaration.wacc",false), expected);
  }

  @Test
  public void stringDeclarationTest() {
    assertEquals(getOutputMessage(dir + "/variables/stringDeclaration.wacc",false), expected);
  }

  @Test
  public void zeroIntDeclarationTest() {
    assertEquals(getOutputMessage(dir + "/variables/zeroIntDeclaration.wacc",false), expected);
  }

  // Sequence

  @Test
  public void basicSeqTest() {
    assertEquals(getOutputMessage(dir + "/sequence/basicSeq.wacc",false), expected);
  }

  @Test
  public void basicSeq2Test() {
    assertEquals(getOutputMessage(dir + "/sequence/basicSeq2.wacc",false), expected);
  }

  @Test
  public void boolAssignmentTest() {
    assertEquals(getOutputMessage(dir + "/sequence/boolAssignment.wacc",false), expected);
  }

  @Test
  public void charAssignmentTest() {
    assertEquals(getOutputMessage(dir + "/sequence/charAssignment.wacc",false), expected);
  }

  @Test
  public void exitSimpleTest() {
    assertEquals(getOutputMessage(dir + "/sequence/exitSimple.wacc",false), expected);
  }

  @Test
  public void intAssignmentTest() {
    assertEquals(getOutputMessage(dir + "/sequence/intAssignment.wacc",false), expected);
  }

  @Test
  public void intLeadingZerosTest() {
    assertEquals(getOutputMessage(dir + "/sequence/intLeadingZeros.wacc",false), expected);
  }

  @Test
  public void stringAssignmentTest() {
    assertEquals(getOutputMessage(dir + "/sequence/stringAssignment.wacc",false), expected);
  }

  // Scope

  @Test
  public void ifNested1Test() {
    assertEquals(getOutputMessage(dir + "/scope/ifNested1.wacc",false), expected);
  }

  @Test
  public void ifNested2Test() {
    assertEquals(getOutputMessage(dir + "/scope/ifNested2.wacc",false), expected);
  }

  @Test
  public void indentationNotImportantTest() {
    assertEquals(getOutputMessage(dir + "/scope/indentationNotImportant.wacc",false), expected);
  }

  @Test
  public void intsAndKeywordsTest() {
    assertEquals(getOutputMessage(dir + "/scope/intsAndKeywords.wacc",false), expected);
  }

  @Test
  public void printAllTypesTest() {
    assertEquals(getOutputMessage(dir + "/scope/printAllTypes.wacc",false), expected);
  }

  @Test
  public void scopeTest() {
    assertEquals(getOutputMessage(dir + "/scope/scope.wacc",false), expected);
  }

  @Test
  public void scopeBasicTest() {
    assertEquals(getOutputMessage(dir + "/scope/scopeBasic.wacc",false), expected);
  }

  @Test
  public void scopeRedefineTest() {
    assertEquals(getOutputMessage(dir + "/scope/scopeRedefine.wacc",false), expected);
  }

  @Test
  public void scopeSimpleRedefineTest() {
    assertEquals(getOutputMessage(dir + "/scope/scopeSimpleRedefine.wacc",false), expected);
  }

  @Test
  public void scopeVarsTest() {
    assertEquals(getOutputMessage(dir + "/scope/scopeVars.wacc",false), expected);
  }

  // RunTimeErr

  // arrayoutofbounds
  @Test
  public void arrayNegBoundsTest() {
    assertEquals(
        getOutputMessage(dir + "/runtimeErr/arrayOutOfBounds/arrayNegBounds.wacc",false), expected);
  }

  @Test
  public void arrayOutOfBoundsTest() {
    assertEquals(
        getOutputMessage(dir + "/runtimeErr/arrayOutOfBounds/arrayOutOfBounds.wacc",false), expected);
  }

  @Test
  public void arrayOutOfBoundsWriteTest() {
    assertEquals(
        getOutputMessage(dir + "/runtimeErr/arrayOutOfBounds/arrayOutOfBoundsWrite.wacc",false),
        expected);
  }

  // divideByZero
  @Test
  public void divideByZeroTest() {
    assertEquals(getOutputMessage(dir + "/runtimeErr/divideByZero/divideByZero.wacc",false), expected);
  }

  @Test
  public void divZeroTest() {
    assertEquals(getOutputMessage(dir + "/runtimeErr/divideByZero/divZero.wacc",false), expected);
  }

  @Test
  public void modByZeroTest() {
    assertEquals(getOutputMessage(dir + "/runtimeErr/divideByZero/modByZero.wacc",false), expected);
  }

  // integerOverflow

  @Test
  public void intJustOverflowTest() {
    assertEquals(
        getOutputMessage(dir + "/runtimeErr/integerOverflow/intJustOverflow.wacc",false), expected);
  }

  @Test
  public void intmultOverflowTest() {
    assertEquals(
        getOutputMessage(dir + "/runtimeErr/integerOverflow/intmultOverflow.wacc",false), expected);
  }

  @Test
  public void intnegateOverflowTest() {
    assertEquals(
        getOutputMessage(dir + "/runtimeErr/integerOverflow/intnegateOverflow.wacc",false), expected);
  }

  @Test
  public void intnegateOverflow2Test() {
    assertEquals(
        getOutputMessage(dir + "/runtimeErr/integerOverflow/intnegateOverflow2.wacc",false), expected);
  }

  @Test
  public void intnegateOverflow3Test() {
    assertEquals(
        getOutputMessage(dir + "/runtimeErr/integerOverflow/intnegateOverflow3.wacc",false), expected);
  }

  @Test
  public void intnegateOverflow4Test() {
    assertEquals(
        getOutputMessage(dir + "/runtimeErr/integerOverflow/intnegateOverflow4.wacc",false), expected);
  }

  @Test
  public void intnegateUnderflowTest() {
    assertEquals(getOutputMessage(dir + "/runtimeErr/integerOverflow/intUnderflow.wacc",false), expected);
  }

  @Test
  public void intWayOverflowTest() {
    assertEquals(
        getOutputMessage(dir + "/runtimeErr/integerOverflow/intWayOverflow.wacc",false), expected);
  }

  // nullDereference
  @Test
  public void freeNullTest() {
    assertEquals(getOutputMessage(dir + "/runtimeErr/nullDereference/freeNull.wacc",false), expected);
  }

  @Test
  public void readNull1Test() {
    assertEquals(getOutputMessage(dir + "/runtimeErr/nullDereference/readNull1.wacc",false), expected);
  }

  @Test
  public void readNull2Test() {
    assertEquals(getOutputMessage(dir + "/runtimeErr/nullDereference/readNull2.wacc",false), expected);
  }

  @Test
  public void setNull1Test() {
    assertEquals(getOutputMessage(dir + "/runtimeErr/nullDereference/setNull1.wacc",false), expected);
  }

  @Test
  public void setNull2Test() {
    assertEquals(getOutputMessage(dir + "/runtimeErr/nullDereference/setNull2.wacc",false), expected);
  }

  @Test
  public void useNull1Test() {
    assertEquals(getOutputMessage(dir + "/runtimeErr/nullDereference/useNull1.wacc",false), expected);
  }

  @Test
  public void useNull2Test() {
    assertEquals(getOutputMessage(dir + "/runtimeErr/nullDereference/useNull2.wacc",false), expected);
  }
  // pairs

  @Test
  public void checkRefPairTest() {
    assertEquals(getOutputMessage(dir + "/pairs/checkRefPair.wacc",false), expected);
  }

  @Test
  public void createPairTest() {
    assertEquals(getOutputMessage(dir + "/pairs/createPair.wacc",false), expected);
  }

  @Test
  public void createPair02Test() {
    assertEquals(getOutputMessage(dir + "/pairs/createPair02.wacc",false), expected);
  }

  @Test
  public void createPair03Test() {
    assertEquals(getOutputMessage(dir + "/pairs/createPair03.wacc",false), expected);
  }

  @Test
  public void createRefPairTest() {
    assertEquals(getOutputMessage(dir + "/pairs/createRefPair.wacc",false), expected);
  }

  @Test
  public void freeTest() {
    assertEquals(getOutputMessage(dir + "/pairs/free.wacc",false), expected);
  }

  @Test
  public void linkedListTest() {
    assertEquals(getOutputMessage(dir + "/pairs/linkedList.wacc",false), expected);
  }

  @Test
  public void nestedPairTest() {
    assertEquals(getOutputMessage(dir + "/pairs/nestedPair.wacc",false), expected);
  }

  @Test
  public void nullTest() {
    assertEquals(getOutputMessage(dir + "/pairs/null.wacc",false), expected);
  }

  @Test
  public void printNullTest() {
    assertEquals(getOutputMessage(dir + "/pairs/null.wacc",false), expected);
  }

  @Test
  public void printNullPairTest() {
    assertEquals(getOutputMessage(dir + "/pairs/printNullPair.wacc",false), expected);
  }

  @Test
  public void printPairOfNullsTest() {
    assertEquals(getOutputMessage(dir + "/pairs/printPairOfNulls.wacc",false), expected);
  }

  @Test
  public void readPairTest() {
    assertEquals(getOutputMessage(dir + "/pairs/readPair.wacc",false), expected);
  }

  @Test
  public void writeFstTest() {
    assertEquals(getOutputMessage(dir + "/pairs/writeFst.wacc",false), expected);
  }

  @Test
  public void writeSndTest() {
    assertEquals(getOutputMessage(dir + "/pairs/writeSnd.wacc",false), expected);
  }

  // if

  @Test
  public void if1Test() {
    assertEquals(getOutputMessage(dir + "/if/if1.wacc",false), expected);
  }

  @Test
  public void if2Test() {
    assertEquals(getOutputMessage(dir + "/if/if2.wacc",false), expected);
  }

  @Test
  public void if3Test() {
    assertEquals(getOutputMessage(dir + "/if/if3.wacc",false), expected);
  }

  @Test
  public void if4Test() {
    assertEquals(getOutputMessage(dir + "/if/if4.wacc",false), expected);
  }

  @Test
  public void if5Test() {
    assertEquals(getOutputMessage(dir + "/if/if5.wacc",false), expected);
  }

  @Test
  public void if6Test() {
    assertEquals(getOutputMessage(dir + "/if/if6.wacc",false), expected);
  }

  @Test
  public void ifBasicTest() {
    assertEquals(getOutputMessage(dir + "/if/ifBasic.wacc",false), expected);
  }

  @Test
  public void ifFalseTest() {
    assertEquals(getOutputMessage(dir + "/if/ifFalse.wacc",false), expected);
  }

  @Test
  public void if4TrueTest() {
    assertEquals(getOutputMessage(dir + "/if/ifTrue.wacc",false), expected);
  }

  @Test
  public void whitespaceTest() {
    assertEquals(getOutputMessage(dir + "/if/whitespace.wacc",false), expected);
  }

  // IO

  @Test
  public void hashInProgramTest() {
    assertEquals(getOutputMessage(dir + "/IO/print/hashInProgram.wacc",false), expected);
  }

  @Test
  public void printTest() {
    assertEquals(getOutputMessage(dir + "/IO/print/print.wacc",false), expected);
  }

  @Test
  public void printBackspaceTest() {
    assertEquals(getOutputMessage(dir + "/IO/print/print-backspace.wacc",false), expected);
  }

  @Test
  public void printBoolTest() {
    assertEquals(getOutputMessage(dir + "/IO/print/printBool.wacc",false), expected);
  }

  @Test
  public void printCarridgeReturnTest() {
    assertEquals(getOutputMessage(dir + "/IO/print/print-carridge-return.wacc",false), expected);
  }

  @Test
  public void printCharTest() {
    assertEquals(getOutputMessage(dir + "/IO/print/printChar.wacc",false), expected);
  }

  @Test
  public void printCharArrayTest() {
    assertEquals(getOutputMessage(dir + "/IO/print/printCharArray.wacc",false), expected);
  }

  @Test
  public void printCharAsStringTest() {
    assertEquals(getOutputMessage(dir + "/IO/print/printCharAsString.wacc",false), expected);
  }

  @Test
  public void printEscCharTest() {
    assertEquals(getOutputMessage(dir + "/IO/print/printEscChar.wacc",false), expected);
  }

  @Test
  public void printIntTest() {
    assertEquals(getOutputMessage(dir + "/IO/print/printInt.wacc",false), expected);
  }

  @Test
  public void printlnTest() {
    assertEquals(getOutputMessage(dir + "/IO/print/println.wacc",false), expected);
  }

  @Test
  public void echoBigIntTest() {
    assertEquals(getOutputMessage(dir + "/IO/read/echoBigInt.wacc",false), expected);
  }

  @Test
  public void echoBigNegIntTest() {
    assertEquals(getOutputMessage(dir + "/IO/read/echoBigNegInt.wacc",false), expected);
  }

  @Test
  public void echoCharTest() {
    assertEquals(getOutputMessage(dir + "/IO/read/echoChar.wacc",false), expected);
  }

  @Test
  public void echoNegIntTest() {
    assertEquals(getOutputMessage(dir + "/IO/read/echoNegInt.wacc",false), expected);
  }

  @Test
  public void echoPuncCharTest() {
    assertEquals(getOutputMessage(dir + "/IO/read/echoPuncChar.wacc",false), expected);
  }

  @Test
  public void readTest() {
    assertEquals(getOutputMessage(dir + "/IO/read/read.wacc",false), expected);
  }

  @Test
  public void ioLoopTest() {
    assertEquals(getOutputMessage(dir + "/IO/IOLoop.wacc",false), expected);
  }

  @Test
  public void ioSequenceTest() {
    assertEquals(getOutputMessage(dir + "/IO/IOSequence.wacc",false), expected);
  }

  // Function

  // nested_functions
  @Test
  public void fibonacciFullRecTest() {
    assertEquals(
        getOutputMessage(dir + "/function/nested_functions/fibonacciFullRec.wacc",false), expected);
  }

  @Test
  public void fibonacciRecursiveTest() {
    assertEquals(
        getOutputMessage(dir + "/function/nested_functions/fibonacciRecursive.wacc",false), expected);
  }

  @Test
  public void fixedPointRealArithmeticTest() {
    assertEquals(
        getOutputMessage(dir + "/function/nested_functions/fixedPointRealArithmetic.wacc",false),
        expected);
  }

  @Test
  public void functionConditionalReturnTest() {
    assertEquals(
        getOutputMessage(dir + "/function/nested_functions/functionConditionalReturn.wacc",false),
        expected);
  }

  @Test
  public void mutualRecursionTest() {
    assertEquals(
        getOutputMessage(dir + "/function/nested_functions/mutualRecursion.wacc",false), expected);
  }

  @Test
  public void printInputTriangleTest() {
    assertEquals(
        getOutputMessage(dir + "/function/nested_functions/printInputTriangle.wacc",false), expected);
  }

  @Test
  public void printTriangleTest() {
    assertEquals(getOutputMessage(dir + "/function/nested_functions/printTriangle.wacc",false), expected);
  }

  @Test
  public void simpleRecursionTest() {
    assertEquals(
        getOutputMessage(dir + "/function/nested_functions/simpleRecursion.wacc",false), expected);
  }

  // simple_functions

  @Test
  public void asciiTableTest() {
    assertEquals(getOutputMessage(dir + "/function/simple_functions/asciiTable.wacc",false), expected);
  }

  @Test
  public void functionDeclarationTest() {
    assertEquals(
        getOutputMessage(dir + "/function/simple_functions/functionDeclaration.wacc",false), expected);
  }

  @Test
  public void functionManyArgumentsTest() {
    assertEquals(
        getOutputMessage(dir + "/function/simple_functions/functionManyArguments.wacc",false), expected);
  }

  @Test
  public void functionReturnPairTest() {
    assertEquals(
        getOutputMessage(dir + "/function/simple_functions/functionReturnPair.wacc",false), expected);
  }

  @Test
  public void functionSimpleTest() {
    assertEquals(
        getOutputMessage(dir + "/function/simple_functions/functionSimple.wacc",false), expected);
  }

  @Test
  public void functionSimpleLoopTest() {
    assertEquals(
        getOutputMessage(dir + "/function/simple_functions/functionSimpleLoop.wacc",false), expected);
  }

  @Test
  public void functionUpdateParameterTest() {
    assertEquals(
        getOutputMessage(dir + "/function/simple_functions/functionUpdateParameter.wacc",false),
        expected);
  }

  @Test
  public void incFunctionTest() {
    assertEquals(getOutputMessage(dir + "/function/simple_functions/incFunction.wacc",false), expected);
  }

  @Test
  public void negFunctionTest() {
    assertEquals(getOutputMessage(dir + "/function/simple_functions/negFunction.wacc",false), expected);
  }

  @Test
  public void sameArgNameTest() {
    assertEquals(getOutputMessage(dir + "/function/simple_functions/sameArgName.wacc",false), expected);
  }

  @Test
  public void sameArgName2Test() {
    assertEquals(getOutputMessage(dir + "/function/simple_functions/sameArgName2.wacc",false), expected);
  }

  @Test
  public void sameNameAsVarTest() {
    assertEquals(getOutputMessage(dir + "/function/simple_functions/sameNameAsVar.wacc",false), expected);
  }

  // basic

  // exit
  @Test
  public void exit_1Test() {
    assertEquals(getOutputMessage(dir + "/basic/exit/exit-1.wacc",false), expected);
  }

  @Test
  public void exitBasicTest() {
    assertEquals(getOutputMessage(dir + "/basic/exit/exitBasic.wacc",false), expected);
  }

  @Test
  public void exitBasic2Test() {
    assertEquals(getOutputMessage(dir + "/basic/exit/exitBasic2.wacc",false), expected);
  }

  @Test
  public void exitWrapTest() {
    assertEquals(getOutputMessage(dir + "/basic/exit/exitWrap.wacc",false), expected);
  }

  // Skip
  @Test
  public void commentTest() {
    assertEquals(getOutputMessage(dir + "/basic/skip/comment.wacc",false), expected);
  }

  @Test
  public void commentInLineTest() {
    assertEquals(getOutputMessage(dir + "/basic/skip/commentInLine.wacc",false), expected);
  }

  @Test
  public void skipTest() {
    assertEquals(getOutputMessage(dir + "/basic/skip/skip.wacc",false), expected);
  }

  // expressions

  @Test
  public void andExprTest() {
    assertEquals(getOutputMessage(dir + "/expressions/andExpr.wacc",false), expected);
  }

  @Test
  public void boolCalcTest() {
    assertEquals(getOutputMessage(dir + "/expressions/boolCalc.wacc",false), expected);
  }

  @Test
  public void boolExpr1Test() {
    assertEquals(getOutputMessage(dir + "/expressions/boolExpr1.wacc",false), expected);
  }

  @Test
  public void charComparisonExprTest() {
    assertEquals(getOutputMessage(dir + "/expressions/charComparisonExpr.wacc",false), expected);
  }

  @Test
  public void divExprTest() {
    assertEquals(getOutputMessage(dir + "/expressions/divExpr.wacc",false), expected);
  }

  @Test
  public void equalsExprTest() {
    assertEquals(getOutputMessage(dir + "/expressions/equalsExpr.wacc",false), expected);
  }

  @Test
  public void greaterEqExprTest() {
    assertEquals(getOutputMessage(dir + "/expressions/greaterEqExpr.wacc",false), expected);
  }

  @Test
  public void greaterExprTest() {
    assertEquals(getOutputMessage(dir + "/expressions/greaterExpr.wacc",false), expected);
  }

  @Test
  public void intCalcTest() {
    assertEquals(getOutputMessage(dir + "/expressions/intCalc.wacc",false), expected);
  }

  @Test
  public void intExpr1Test() {
    assertEquals(getOutputMessage(dir + "/expressions/intExpr1.wacc",false), expected);
  }

  @Test
  public void lessCharExprTest() {
    assertEquals(getOutputMessage(dir + "/expressions/lessCharExpr.wacc",false), expected);
  }

  @Test
  public void lessEqExprTest() {
    assertEquals(getOutputMessage(dir + "/expressions/lessEqExpr.wacc",false), expected);
  }

  @Test
  public void lessExprTest() {
    assertEquals(getOutputMessage(dir + "/expressions/lessExpr.wacc",false), expected);
  }

  @Test
  public void longExprTest() {
    assertEquals(getOutputMessage(dir + "/expressions/longExpr.wacc",false), expected);
  }

  @Test
  public void longExpr2Test() {
    assertEquals(getOutputMessage(dir + "/expressions/longExpr2.wacc",false), expected);
  }

  @Test
  public void longExpr3Test() {
    assertEquals(getOutputMessage(dir + "/expressions/longExpr3.wacc",false), expected);
  }

  @Test
  public void longSplitExprTest() {
    assertEquals(getOutputMessage(dir + "/expressions/longSplitExpr.wacc",false), expected);
  }

  @Test
  public void longSplitExpr2Test() {
    assertEquals(getOutputMessage(dir + "/expressions/longSplitExpr2.wacc",false), expected);
  }

  @Test
  public void minusExprTest() {
    assertEquals(getOutputMessage(dir + "/expressions/minusExpr.wacc",false), expected);
  }

  @Test
  public void minusMinusExprTest() {
    assertEquals(getOutputMessage(dir + "/expressions/minusMinusExpr.wacc",false), expected);
  }

  @Test
  public void minusNoWhitespaceExprTest() {
    assertEquals(getOutputMessage(dir + "/expressions/minusNoWhitespaceExpr.wacc",false), expected);
  }

  @Test
  public void minusPlusExprTest() {
    assertEquals(getOutputMessage(dir + "/expressions/minusPlusExpr.wacc",false), expected);
  }

  @Test
  public void modExprTest() {
    assertEquals(getOutputMessage(dir + "/expressions/modExpr.wacc",false), expected);
  }

  @Test
  public void multExprTest() {
    assertEquals(getOutputMessage(dir + "/expressions/multExpr.wacc",false), expected);
  }

  @Test
  public void multNoWhitespaceExprTeest() {
    assertEquals(getOutputMessage(dir + "/expressions/multExpr.wacc",false), expected);
  }

  @Test
  public void negBothDivTest() {
    assertEquals(getOutputMessage(dir + "/expressions/negBothDiv.wacc",false), expected);
  }

  @Test
  public void negBothModTest() {
    assertEquals(getOutputMessage(dir + "/expressions/negBothMod.wacc",false), expected);
  }

  @Test
  public void negDividendDivTest() {
    assertEquals(getOutputMessage(dir + "/expressions/negDividendDiv.wacc",false), expected);
  }

  @Test
  public void negDividendModTest() {
    assertEquals(getOutputMessage(dir + "/expressions/negDividendMod.wacc",false), expected);
  }

  @Test
  public void negDivisorDivTest() {
    assertEquals(getOutputMessage(dir + "/expressions/negDivisorDiv.wacc",false), expected);
  }

  @Test
  public void negDivisorModTest() {
    assertEquals(getOutputMessage(dir + "/expressions/negDivisorMod.wacc",false), expected);
  }

  @Test
  public void negExprTest() {
    assertEquals(getOutputMessage(dir + "/expressions/negExpr.wacc",false), expected);
  }

  @Test
  public void notequalsExprTest() {
    assertEquals(getOutputMessage(dir + "/expressions/notequalsExpr.wacc",false), expected);
  }

  @Test
  public void notExprTest() {
    assertEquals(getOutputMessage(dir + "/expressions/notExpr.wacc",false), expected);
  }

  @Test
  public void ordAndchrExprTest() {
    assertEquals(getOutputMessage(dir + "/expressions/ordAndchrExpr.wacc",false), expected);
  }

  @Test
  public void orExprTest() {
    assertEquals(getOutputMessage(dir + "/expressions/orExpr.wacc",false), expected);
  }

  @Test
  public void plusExprTest() {
    assertEquals(getOutputMessage(dir + "/expressions/plusExpr.wacc",false), expected);
  }

  @Test
  public void plusMinusExprTest() {
    assertEquals(getOutputMessage(dir + "/expressions/plusMinusExpr.wacc",false), expected);
  }

  @Test
  public void plusNoWhiteSpaceExprTest() {
    assertEquals(getOutputMessage(dir + "/expressions/plusNoWhitespaceExpr.wacc",false), expected);
  }

  @Test
  public void plusPlusExprTest() {
    assertEquals(getOutputMessage(dir + "/expressions/plusPlusExpr.wacc",false), expected);
  }

  @Test
  public void sequentialCountExprTest() {
    assertEquals(getOutputMessage(dir + "/expressions/sequentialCount.wacc",false), expected);
  }

  @Test
  public void stringEqualsExprTest() {
    assertEquals(getOutputMessage(dir + "/expressions/stringEqualsExpr.wacc",false), expected);
  }

  // ARRAY

  @Test
  public void arrayTest() {
    assertEquals(getOutputMessage(dir + "/array/array.wacc",false), expected);
  }

  @Test
  public void arrayBasicTest() {
    assertEquals(getOutputMessage(dir + "/array/arrayBasic.wacc",false), expected);
  }

  @Test
  public void arrayEmptyTest() {
    assertEquals(getOutputMessage(dir + "/array/arrayEmpty.wacc",false), expected);
  }

  @Test
  public void arrayLengthTest() {
    assertEquals(getOutputMessage(dir + "/array/arrayLength.wacc",false), expected);
  }

  @Test
  public void arrayLookupTest() {
    assertEquals(getOutputMessage(dir + "/array/arrayLookup.wacc",false), expected);
  }

  @Test
  public void arrayNestedTest() {
    assertEquals(getOutputMessage(dir + "/array/arrayNested.wacc",false), expected);
  }

  @Test
  public void arrayPrintTest() {
    assertEquals(getOutputMessage(dir + "/array/arrayPrint.wacc",false), expected);
  }

  @Test
  public void arraySimpleTest() {
    assertEquals(getOutputMessage(dir + "/array/arraySimple.wacc",false), expected);
  }

  @Test
  public void modifyStringTest() {
    assertEquals(getOutputMessage(dir + "/array/modifyString.wacc",false), expected);
  }

  @Test
  public void printRefTest() {
    assertEquals(getOutputMessage(dir + "/array/printRef.wacc",false), expected);
  }
}
