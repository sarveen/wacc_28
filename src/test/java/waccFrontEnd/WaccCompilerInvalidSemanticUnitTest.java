package waccFrontEnd;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static wacc.WaccCompiler.getOutputMessage;

public class WaccCompilerInvalidSemanticUnitTest {

  private String dir = "src/test/java/wacc/wacc_examples/invalid/semanticErr";

  // EXIT

  @Test
  public void badCharExitTest() {
    String outputMessage = getOutputMessage(dir + "/exit/badCharExit.wacc", false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("Incompatible"));
    assertThat(outputMessage, containsString("expected: INT"));
    assertThat(outputMessage, containsString("actual: CHAR"));
  }

  @Test
  public void exitNonIntTest() {
    String outputMessage = getOutputMessage(dir + "/exit/exitNonInt.wacc", false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("Incompatible"));
    assertThat(outputMessage, containsString("expected: INT"));
    assertThat(outputMessage, containsString("actual: CHAR"));
  }

  @Test
  public void globalReturnTest() {
    String outputMessage = getOutputMessage(dir + "/exit/globalReturn.wacc", false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("Cannot return from global scope"));
  }

  // Expressions

  @Test
  public void moreArrExprTest() {
    String outputMessage = getOutputMessage(dir + "/expressions/moreArrExpr.wacc", false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("Incompatible"));
    assertThat(outputMessage, containsString("expected: INT OR CHAR"));
    assertThat(outputMessage, containsString("[]"));
  }

  @Test
  public void stringElementErrTest() {
    String outputMessage = getOutputMessage(dir + "/expressions/stringElemErr.wacc", false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("Incompatible"));
    assertThat(outputMessage, containsString("[]"));
    assertThat(outputMessage, containsString("actual: STRING"));
  }

  // Function

  @Test
  public void functionAssignTest() {
    String outputMessage = getOutputMessage(dir + "/function/functionAssign.wacc", false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("not defined"));
  }

  @Test
  public void functionBadArgUseTest() {
    String outputMessage = getOutputMessage(dir + "/function/functionBadArgUse.wacc", false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("Incompatible"));
    assertThat(outputMessage, containsString("expected: BOOL"));
    assertThat(outputMessage, containsString("actual: INT"));
  }

  @Test
  public void functionBadCallTest() {
    String outputMessage = getOutputMessage(dir + "/function/functionBadCall.wacc", false);
    assertThat(outputMessage, containsString("Incompatible"));
    assertThat(outputMessage, containsString("expected: BOOL"));
    assertThat(outputMessage, containsString("actual: INT"));
  }

  @Test
  public void functionBadParamTest() {
    String outputMessage = getOutputMessage(dir + "/function/functionBadParam.wacc", false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("Incompatible"));
    assertThat(outputMessage, containsString("expected: INT"));
    assertThat(outputMessage, containsString("actual: BOOL"));
  }

  @Test
  public void functionBadReturnTest() {
    String outputMessage = getOutputMessage(dir + "/function/functionBadReturn.wacc", false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("Incompatible"));
    assertThat(outputMessage, containsString("expected: INT"));
    assertThat(outputMessage, containsString("actual: CHAR"));
  }

  @Test
  public void functionOverArgsTest() {
    String outputMessage = getOutputMessage(dir + "/function/functionOverArgs.wacc", false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("Incorrect number of parameters"));
    assertThat(outputMessage, containsString("expected: 2"));
    assertThat(outputMessage, containsString("actual: 3"));
  }

  @Test
  public void functionRedefineTest() {
    String outputMessage = getOutputMessage(dir + "/function/functionRedefine.wacc", false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("already defined"));
  }

  @Test
  public void functionSwapArgsTest() {
    String outputMessage = getOutputMessage(dir + "/function/functionSwapArgs.wacc", false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("Incompatible"));
    assertThat(outputMessage, containsString("expected: INT"));
    assertThat(outputMessage, containsString("actual: BOOL"));
  }

  @Test
  public void functionUnderArgsTest() {
    String outputMessage = getOutputMessage(dir + "/function/functionUnderArgs.wacc", false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("Incorrect number of parameters"));
    assertThat(outputMessage, containsString("expected: 2"));
    assertThat(outputMessage, containsString("actual: 1"));
  }

  @Test
  public void funcVarAccessTest() {
    String outputMessage = getOutputMessage(dir + "/function/funcVarAccess.wacc", false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("not defined"));
  }

  // If

  @Test
  public void ifIntConditionTest() {
    String outputMessage = getOutputMessage(dir + "/if/ifIntCondition.wacc", false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("Incompatible"));
    assertThat(outputMessage, containsString("expected: BOOL"));
    assertThat(outputMessage, containsString("actual: INT"));
  }

  // IO

  @Test
  public void readTypeErrTest() {
    String outputMessage = getOutputMessage(dir + "/IO/readTypeErr.wacc", false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("Incompatible"));
    assertThat(outputMessage, containsString("expected: INT or CHAR"));
    assertThat(outputMessage, containsString("actual: BOOL"));
  }

  // multiple

  @Test
  public void funcMessTest() {
    String outputMessage = getOutputMessage(dir + "/multiple/funcMess.wacc", false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("Incompatible"));
    assertThat(outputMessage, containsString("expected: BOOL"));
    assertThat(outputMessage, containsString("actual: INT"));
  }

  @Test
  public void ifAndWhileErrsTest() {
    String outputMessage = getOutputMessage(dir + "/multiple/ifAndWhileErrs.wacc", false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("not defined"));
  }

  @Test
  public void messyExprTest() {
    String outputMessage = getOutputMessage(dir + "/multiple/messyExpr.wacc", false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("Incompatible"));
    assertThat(outputMessage, containsString("expected: INT"));
    assertThat(outputMessage, containsString("actual: BOOL"));
  }

  @Test
  public void multiCaseSensitivityTest() {
    String outputMessage = getOutputMessage(dir + "/multiple/multiCaseSensitivity.wacc",false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("not defined"));
  }

  @Test
  public void multiTypeErrsTest() {
    String outputMessage = getOutputMessage(dir + "/multiple/multiTypeErrs.wacc",false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("Incompatible"));
    assertThat(outputMessage, containsString("expected: INT"));
    assertThat(outputMessage, containsString("actual: BOOL"));
    assertThat(outputMessage, containsString("Incompatible"));
    assertThat(outputMessage, containsString("expected: BOOL"));
    assertThat(outputMessage, containsString("actual: CHAR"));
    assertThat(outputMessage, containsString("Incompatible"));
    assertThat(outputMessage, containsString("expected: CHAR"));
    assertThat(outputMessage, containsString("actual: INT"));
  }

  // pairs

  @Test
  public void freeNonPairTest() {
    String outputMessage = getOutputMessage(dir + "/pairs/freeNonPair.wacc",false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("Incompatible"));
    assertThat(outputMessage, containsString("expected: PAIR or ARRAY"));
    assertThat(outputMessage, containsString("actual: INT"));
  }

  @Test
  public void fstNullTest() {
    String outputMessage = getOutputMessage(dir + "/pairs/fstNull.wacc",false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("null pointer"));
  }

  @Test
  public void sndNullTest() {
    String outputMessage = getOutputMessage(dir + "/pairs/sndNull.wacc",false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("null pointer"));
  }

  // print

  @Test
  public void printTypeErr01Test() {
    String outputMessage = getOutputMessage(dir + "/print/printTypeErr01.wacc",false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("Incompatible"));
    assertThat(outputMessage, containsString("expected: INT"));
    assertThat(outputMessage, containsString("actual: CHAR"));
  }

  // read

  @Test
  public void readTypeErr01Test() {
    String outputMessage = getOutputMessage(dir + "/read/readTypeErr01.wacc",false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("Incompatible"));
    assertThat(outputMessage, containsString("expected: INT or CHAR"));
    assertThat(outputMessage, containsString("actual: PAIR"));
  }

  // scope

  @Test
  public void badScopeRedefineTest() {
    String outputMessage = getOutputMessage(dir + "/scope/badScopeRedefine.wacc",false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("Incompatible"));
    assertThat(outputMessage, containsString("expected: BOOL"));
    assertThat(outputMessage, containsString("actual: INT"));
  }

  // variables

  @Test
  public void basicTypeErr01Test() {
    String outputMessage = getOutputMessage(dir + "/variables/basicTypeErr01.wacc",false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("Incompatible"));
    assertThat(outputMessage, containsString("expected: INT"));
    assertThat(outputMessage, containsString("actual: BOOL"));
  }

  @Test
  public void basicTypeErr02Test() {
    String outputMessage = getOutputMessage(dir + "/variables/basicTypeErr02.wacc",false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("Incompatible"));
    assertThat(outputMessage, containsString("expected: INT"));
    assertThat(outputMessage, containsString("actual: CHAR"));
  }

  @Test
  public void basicTypeErr03Test() {
    String outputMessage = getOutputMessage(dir + "/variables/basicTypeErr03.wacc",false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("Incompatible"));
    assertThat(outputMessage, containsString("expected: INT"));
    assertThat(outputMessage, containsString("actual: STRING"));
  }

  @Test
  public void basicTypeErr04Test() {
    String outputMessage = getOutputMessage(dir + "/variables/basicTypeErr04.wacc",false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("Incompatible"));
    assertThat(outputMessage, containsString("expected: BOOL"));
    assertThat(outputMessage, containsString("actual: INT"));
  }

  @Test
  public void basicTypeErr05Test() {
    String outputMessage = getOutputMessage(dir + "/variables/basicTypeErr05.wacc",false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("Incompatible"));
    assertThat(outputMessage, containsString("expected: BOOL"));
    assertThat(outputMessage, containsString("actual: CHAR"));
  }

  @Test
  public void basicTypeErr06Test() {
    String outputMessage = getOutputMessage(dir + "/variables/basicTypeErr06.wacc",false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("Incompatible"));
    assertThat(outputMessage, containsString("expected: BOOL"));
    assertThat(outputMessage, containsString("actual: STRING"));
  }

  @Test
  public void basicTypeErr07Test() {
    String outputMessage = getOutputMessage(dir + "/variables/basicTypeErr07.wacc",false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("Incompatible"));
    assertThat(outputMessage, containsString("expected: CHAR"));
    assertThat(outputMessage, containsString("actual: INT"));
  }

  @Test
  public void basicTypeErr08Test() {
    String outputMessage = getOutputMessage(dir + "/variables/basicTypeErr08.wacc",false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("Incompatible"));
    assertThat(outputMessage, containsString("expected: CHAR"));
    assertThat(outputMessage, containsString("actual: BOOL"));
  }

  @Test
  public void basicTypeErr09Test() {
    String outputMessage = getOutputMessage(dir + "/variables/basicTypeErr09.wacc",false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("Incompatible"));
    assertThat(outputMessage, containsString("expected: CHAR"));
    assertThat(outputMessage, containsString("actual: STRING"));
  }

  @Test
  public void basicTypeErr10Test() {
    String outputMessage = getOutputMessage(dir + "/variables/basicTypeErr10.wacc",false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("Incompatible"));
    assertThat(outputMessage, containsString("expected: STRING"));
    assertThat(outputMessage, containsString("actual: INT"));
  }

  @Test
  public void basicTypeErr11Test() {
    String outputMessage = getOutputMessage(dir + "/variables/basicTypeErr11.wacc",false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("Incompatible"));
    assertThat(outputMessage, containsString("expected: STRING"));
    assertThat(outputMessage, containsString("actual: BOOL"));
  }

  @Test
  public void basicTypeErr12Test() {
    String outputMessage = getOutputMessage(dir + "/variables/basicTypeErr12.wacc",false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("Incompatible"));
    assertThat(outputMessage, containsString("expected: STRING"));
    assertThat(outputMessage, containsString("actual: CHAR"));
  }

  @Test
  public void caseMattersTest() {
    String outputMessage = getOutputMessage(dir + "/variables/caseMatters.wacc",false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("not defined"));
  }

  @Test
  public void doubleDeclareTest() {
    String outputMessage = getOutputMessage(dir + "/variables/doubleDeclare.wacc",false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("already defined"));
  }

  @Test
  public void undeclaredScopeVarTest() {
    String outputMessage = getOutputMessage(dir + "/variables/undeclaredScopeVar.wacc",false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("not defined"));
  }

  @Test
  public void undeclaredVarTest() {
    String outputMessage = getOutputMessage(dir + "/variables/undeclaredVar.wacc",false);
    System.out.println(outputMessage);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("not defined"));
  }

  @Test
  public void undeclaredVarAccessTest() {
    String outputMessage = getOutputMessage(dir + "/variables/undeclaredVarAccess.wacc",false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("not defined"));
  }

  // while
  @Test
  public void falsErrTest() {
    String outputMessage = getOutputMessage(dir + "/while/falsErr.wacc",false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("not defined"));
  }

  @Test
  public void truErrTest() {
    String outputMessage = getOutputMessage(dir + "/while/truErr.wacc",false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("not defined"));
  }

  @Test
  public void whileIntConditionTest() {
    String outputMessage = getOutputMessage(dir + "/while/whileIntCondition.wacc",false);
    assertThat(outputMessage, containsString("200"));
    assertThat(outputMessage, containsString("Incompatible"));
    assertThat(outputMessage, containsString("expected: BOOL"));
    assertThat(outputMessage, containsString("actual: INT"));
  }
}
