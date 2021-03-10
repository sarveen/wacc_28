package waccFrontEnd;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static wacc.WaccCompiler.getOutputMessage;

public class WaccCompilerInvalidSyntaxUnitTest {

  private String dir = "src/test/java/wacc/wacc_examples/invalid/syntaxErr";
  private String expected = "Exit code 100";

  // array

  @Test
  public void arrayExprTest() {
    assertThat(getOutputMessage(dir + "/array/arrayExpr.wacc",false), containsString(expected));
  }

  // basic

  @Test
  public void badCommentTest() {
    assertThat(getOutputMessage(dir + "/basic/badComment.wacc",false), containsString(expected));
  }

  @Test
  public void badComment2Test() {

    assertThat(getOutputMessage(dir + "/basic/badComment2.wacc",false), containsString(expected));
  }

  @Test
  public void badEscapeTest() {
    assertThat(getOutputMessage(dir + "/basic/badEscape.wacc",false), containsString(expected));
  }

  @Test
  public void beginNoendTest() {
    assertThat(getOutputMessage(dir + "/basic/beginNoend.wacc",false), containsString(expected));
  }

  @Test
  public void bgnErrTest() {
    assertThat(getOutputMessage(dir + "/basic/bgnErr.wacc",false), containsString(expected));
  }

  @Test
  public void multipleBeginsTest() {
    assertThat(getOutputMessage(dir + "/basic/multipleBegins.wacc",false), containsString(expected));
  }

  @Test
  public void noBodyTest() {
    assertThat(getOutputMessage(dir + "/basic/noBody.wacc",false), containsString(expected));
  }

  @Test
  public void skpErrTest() {
    assertThat(getOutputMessage(dir + "/basic/skpErr.wacc",false), containsString(expected));
  }

  @Test
  public void unescapedCharTest() {
    assertThat(getOutputMessage(dir + "/basic/unescapedChar.wacc",false), containsString(expected));
  }

  // expressions

  @Test
  public void missingOperand1Test() {
    assertThat(
        getOutputMessage(dir + "/expressions/missingOperand1.wacc",false), containsString(expected));
  }

  @Test
  public void missingOperand2Test() {
    assertThat(
        getOutputMessage(dir + "/expressions/missingOperand2.wacc",false), containsString(expected));
  }

  @Test
  public void printlnConcatTest() {
    assertThat(getOutputMessage(dir + "/expressions/printlnConcat.wacc",false), containsString(expected));
  }

  // function

  @Test
  public void badlyNamedTest() {
    assertThat(getOutputMessage(dir + "/function/badlyNamed.wacc",false), containsString(expected));
  }

  @Test
  public void badlyPlacedTest() {
    assertThat(getOutputMessage(dir + "/function/badlyPlaced.wacc",false), containsString(expected));
  }

  @Test
  public void funcExprTest() {
    assertThat(getOutputMessage(dir + "/function/funcExpr.wacc",false), containsString(expected));
  }

  @Test
  public void funcExpr2Test() {
    assertThat(getOutputMessage(dir + "/function/funcExpr2.wacc",false), containsString(expected));
  }

  @Test
  public void functionConditionalNoReturnTest() {
    assertThat(
        getOutputMessage(dir + "/function/functionConditionalNoReturn.wacc",false),
        containsString(expected));
  }

  @Test
  public void functionJunkAfterReturnTest() {
    assertThat(
        getOutputMessage(dir + "/function/functionJunkAfterReturn.wacc",false), containsString(expected));
  }

  @Test
  public void functionLateDefineTest() {
    assertThat(
        getOutputMessage(dir + "/function/functionLateDefine.wacc",false), containsString(expected));
  }

  @Test
  public void functionMissingCallTest() {
    assertThat(
        getOutputMessage(dir + "/function/functionMissingCall.wacc",false), containsString(expected));
  }

  @Test
  public void functionMissingParamTest() {
    assertThat(
        getOutputMessage(dir + "/function/functionMissingParam.wacc",false), containsString(expected));
  }

  @Test
  public void functionMissingPTypeTest() {
    assertThat(
        getOutputMessage(dir + "/function/functionMissingPType.wacc",false), containsString(expected));
  }

  @Test
  public void functionMissingTypeTest() {
    assertThat(
        getOutputMessage(dir + "/function/functionMissingType.wacc",false), containsString(expected));
  }

  @Test
  public void functionNoReturnTest() {
    assertThat(getOutputMessage(dir + "/function/functionNoReturn.wacc",false), containsString(expected));
  }

  @Test
  public void functionReturnInLoopTest() {
    assertThat(
        getOutputMessage(dir + "/function/functionReturnInLoop.wacc",false), containsString(expected));
  }

  @Test
  public void functionScopeDefTest() {
    assertThat(getOutputMessage(dir + "/function/functionScopeDef.wacc",false), containsString(expected));
  }

  @Test
  public void mutualRecursionNoReturnTest() {
    assertThat(
        getOutputMessage(dir + "/function/mutualRecursionNoReturn.wacc",false), containsString(expected));
  }

  @Test
  public void noBodyAfterFuncsTest() {
    assertThat(getOutputMessage(dir + "/function/noBodyAfterFuncs.wacc",false), containsString(expected));
  }

  @Test
  public void thisIsNotCTest() {
    assertThat(getOutputMessage(dir + "/function/thisIsNotC.wacc",false), containsString(expected));
  }

  // if

  @Test
  public void ifiErrTest() {
    assertThat(getOutputMessage(dir + "/if/ifiErr.wacc",false), containsString(expected));
  }

  @Test
  public void ifNoelseTest() {
    assertThat(getOutputMessage(dir + "/if/ifNoelse.wacc",false), containsString(expected));
  }

  @Test
  public void ifNofiTest() {
    assertThat(getOutputMessage(dir + "/if/ifNofi.wacc",false), containsString(expected));
  }

  @Test
  public void ifNothenTest() {
    assertThat(getOutputMessage(dir + "/if/ifNothen.wacc",false), containsString(expected));
  }

  // pairs

  @Test
  public void badLookup01() {
    assertThat(getOutputMessage(dir + "/pairs/badLookup01.wacc",false), containsString(expected));
  }

  @Test
  public void badLookup02Test() {
    assertThat(getOutputMessage(dir + "/pairs/badLookup02.wacc",false), containsString(expected));
  }

  // print

  @Test
  public void printInCharArrayTest() {
    assertThat(getOutputMessage(dir + "/print/printlnCharArry.wacc",false), containsString(expected));
  }

  // sequence

  @Test
  public void doubleSeqTest() {
    assertThat(getOutputMessage(dir + "/sequence/doubleSeq.wacc",false), containsString(expected));
  }

  @Test
  public void emptySeqTest() {
    assertThat(getOutputMessage(dir + "/sequence/emptySeq.wacc",false), containsString(expected));
  }

  @Test
  public void endSeqTest() {
    assertThat(getOutputMessage(dir + "/sequence/endSeq.wacc",false), containsString(expected));
  }

  @Test
  public void extraSeqTest() {
    assertThat(getOutputMessage(dir + "/sequence/extraSeq.wacc",false), containsString(expected));
  }

  @Test
  public void missingSeqTest() {
    assertThat(getOutputMessage(dir + "/sequence/missingSeq.wacc",false), containsString(expected));
  }

  // variables

  @Test
  public void badintAssignmentsTest() {
    assertThat(
        getOutputMessage(dir + "/variables/badintAssignments.wacc",false), containsString(expected));
  }

  @Test
  public void badintAssignments1Test() {
    assertThat(
        getOutputMessage(dir + "/variables/badintAssignments1.wacc",false), containsString(expected));
  }

  @Test
  public void badintAssignments2Test() {
    assertThat(
        getOutputMessage(dir + "/variables/badintAssignments2.wacc",false), containsString(expected));
  }

  @Test
  public void bigIntAssignmentTest() {
    assertThat(
        getOutputMessage(dir + "/variables/bigIntAssignment.wacc",false), containsString(expected));
  }

  @Test
  public void varNoNameTest() {
    assertThat(getOutputMessage(dir + "/variables/varNoName.wacc",false), containsString(expected));
  }

  // while

  @Test
  public void donoErrTest() {
    assertThat(getOutputMessage(dir + "/while/donoErr.wacc",false), containsString(expected));
  }

  @Test
  public void dooErrTest() {
    assertThat(getOutputMessage(dir + "/while/dooErr.wacc",false), containsString(expected));
  }

  @Test
  public void whileNodoTest() {
    assertThat(getOutputMessage(dir + "/while/whileNodo.wacc",false), containsString(expected));
  }

  @Test
  public void whileNodoneTest() {
    assertThat(getOutputMessage(dir + "/while/whileNodone.wacc",false), containsString(expected));
  }

  @Test
  public void whilErrTest() {
    assertThat(getOutputMessage(dir + "/while/whilErr.wacc",false), containsString(expected));
  }
}
