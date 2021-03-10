package waccBackEnd;

import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static wacc.WaccCompiler.getOutputMessage;

public class WaccCompilerBackEndTest {

    String dir = "src/test/java/wacc/wacc_examples/valid/";
    ProcessBuilder processBuilder = new ProcessBuilder();

    private String getActualOutput(String folder, String file, List<String> inputs) throws IOException, InterruptedException {
        String testFile = dir + folder + "/" + file;
        getOutputMessage(testFile + ".wacc",true);
        processBuilder.command("arm-linux-gnueabi-gcc", "-o", "EXEName", "-mcpu=arm1176jzf-s", "-mtune=arm1176jzf-s", file + ".s");
        processBuilder.start().waitFor();
        processBuilder.command("qemu-arm", "-L", "/usr/arm-linux-gnueabi/", "EXEName");
        Process process = processBuilder.start();
        if (!inputs.isEmpty()) {
            OutputStream outputStream = process.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream);
            for (String input : inputs) {
                writer.write(input);
            }
            writer.close();
            outputStream.close();
        }
        process.waitFor();
        String output = new String(process.getInputStream().readAllBytes());
        processBuilder.command("rm", file + ".s", "EXEName").start().waitFor();
        if (output.equals("")) {
            return "#empty#";
        }

        output = output.replaceAll("0x\\w+", "0x");
        output = output.replaceAll(".\b", "");
        return output.replaceAll("\n?\r?", "");
    }

    private String getExpectedOutput(String folder, String file, List<String> outputs) throws IOException {
        String testFile = dir + folder + "/" + file;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(testFile + ".wacc"));
        String line = bufferedReader.readLine();
        while (!line.equals("# Output:")) {
            line = bufferedReader.readLine();
        }
        StringBuilder stringBuilder = new StringBuilder();
        line = bufferedReader.readLine();
        int nextInput = 0;
        while (!line.isEmpty()) {
            if (line.contains("#input#")) {
                line = line.replaceAll("#input#\\s?", "");
            }
            if (line.contains("#output#")) {
                line = line.replace("#output#", outputs.get(nextInput++));
            } else if (line.contains("#runtime_error#")) {
                line = line.replace("#runtime_error#", outputs.get(nextInput++));
            }
            stringBuilder.append(line.substring(2));
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        String output = stringBuilder.toString();
        output = output.replaceAll("#addrs#", "0x");
        return output;
    }

    private void checkTest(String folder, String file, List<String> inputs, List<String> outputs) throws IOException, InterruptedException {
        String actualOutput = getActualOutput(folder, file, inputs);
        String expectedOutput = getExpectedOutput(folder, file, outputs);
        assertEquals(expectedOutput, actualOutput);
    }

    private void checkTest(String folder, String file) throws IOException, InterruptedException {
        checkTest(folder, file, new ArrayList<>(), new ArrayList<>());
    }

    // ARRAY

    @Test
    public void arrayTest() throws IOException, InterruptedException {
        String folder = "array";
        String file = "array";
        checkTest(folder, file);
    }
    @Test
    public void arrayBasicTest() throws IOException, InterruptedException {
        String folder = "array";
        String file = "arrayBasic";
        checkTest(folder, file);
    }
    @Test
    public void arrayEmptyTest() throws IOException, InterruptedException {
        String folder = "array";
        String file = "arrayEmpty";
        checkTest(folder, file);
    }

    @Test
    public void arrayLengthTest() throws IOException, InterruptedException {
        String folder = "array";
        String file = "arrayLength";
        checkTest(folder, file);
    }

    @Test
    public void arrayLookupTest() throws IOException, InterruptedException {
        String folder = "array";
        String file = "arrayLookup";
        checkTest(folder, file);
    }

    @Test
    public void arrayNestedTest() throws IOException, InterruptedException {
        String folder = "array";
        String file = "arrayNested";
        checkTest(folder, file);
    }

    @Test
    public void arrayPrintTest() throws IOException, InterruptedException {
        String folder = "array";
        String file = "arrayPrint";
        checkTest(folder, file);
    }

    @Test
    public void arraySimpleTest() throws IOException, InterruptedException {
        String folder = "array";
        String file = "arraySimple";
        checkTest(folder, file);
    }

    @Test
    public void modifyStringTest() throws IOException, InterruptedException {
        String folder = "array";
        String file = "modifyString";
        checkTest(folder, file);
    }

    @Test
    public void printRefTest() throws IOException, InterruptedException {
        String folder = "array";
        String file = "printRef";
        checkTest(folder, file);
    }

    // BASIC

    // EXIT
    @Test
    public void exit1Test() throws IOException, InterruptedException {
        String folder = "basic/exit";
        String file = "exit-1";
        checkTest(folder, file);
    }
    @Test
    public void exitBasicTest() throws IOException, InterruptedException {
        String folder = "basic/exit";
        String file = "exitBasic";
        checkTest(folder, file);
    }
    @Test
    public void exitBasic2Test() throws IOException, InterruptedException {
        String folder = "basic/exit";
        String file = "exitBasic2";
        checkTest(folder, file);
    }
    @Test
    public void exitWrapTest() throws IOException, InterruptedException {
        String folder = "basic/exit";
        String file = "exitWrap";
        checkTest(folder, file);
    }

    // SKIP

    @Test
    public void commentTest() throws IOException, InterruptedException {
        String folder = "basic/skip";
        String file = "comment";
        checkTest(folder, file);
    }

    @Test
    public void commentInLineTest() throws IOException, InterruptedException {
        String folder = "basic/skip";
        String file = "commentInLine";
        checkTest(folder, file);
    }

    @Test
    public void skipTest() throws IOException, InterruptedException {
        String folder = "basic/skip";
        String file = "skip";
        checkTest(folder, file);
    }

    // EXPRESSIONS

    @Test
    public void andExprTest() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "andExpr";
        checkTest(folder, file);
    }

    @Test
    public void boolCalcTest() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "boolCalc";
        checkTest(folder, file);
    }
    @Test
    public void boolExpr1Test() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "boolExpr1";
        checkTest(folder, file);
    }
    @Test
    public void charComparisonExprTest() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "charComparisonExpr";
        checkTest(folder, file);
    }
    @Test
    public void divExprTest() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "divExpr";
        checkTest(folder, file);
    }
    @Test
    public void equalsExprTest() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "equalsExpr";
        checkTest(folder, file);
    }

    @Test
    public void greaterEqExprTest() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "greaterEqExpr";
        checkTest(folder, file);
    }

    @Test
    public void greaterExprTest() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "greaterExpr";
        checkTest(folder, file);
    }

    @Test
    public void intCalcTest() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "intCalc";
        checkTest(folder, file);
    }

    @Test
    public void intExpr1Test() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "intExpr1";
        checkTest(folder, file);
    }

    @Test
    public void lessCharExprTest() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "lessCharExpr";
        checkTest(folder, file);
    }

    @Test
    public void lessEqExprTest() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "lessEqExpr";
        checkTest(folder, file);
    }

    @Test
    public void lessExprTest() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "lessExpr";
        checkTest(folder, file);
    }

    @Test
    public void longExprTest() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "longExpr";
        checkTest(folder, file);
    }

    @Test
    public void longExpr2Test() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "longExpr2";
        checkTest(folder, file);
    }

    @Test
    public void longExpr3Test() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "longExpr3";
        checkTest(folder, file);
    }

    @Test
    public void longSplitExpr() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "longSplitExpr";
        checkTest(folder, file);
    }

    @Test
    public void longSplitExpr2() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "longSplitExpr2";
        checkTest(folder, file);
    }

    @Test
    public void minusExprTest() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "minusExpr";
        checkTest(folder, file);
    }

    @Test
    public void minusMinusExprTest() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "minusMinusExpr";
        checkTest(folder, file);
    }

    @Test
    public void minusNoWhitespaceExprTest() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "minusNoWhitespaceExpr";
        checkTest(folder, file);
    }

    @Test
    public void minusPlusExprTest() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "minusPlusExpr";
        checkTest(folder, file);
    }

    @Test
    public void modExprTest() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "modExpr";
        checkTest(folder, file);
    }

    @Test
    public void multExprTest() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "multExpr";
        checkTest(folder, file);
    }

    @Test
    public void multNoWhitespaceExprTest() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "multNoWhitespaceExpr";
        checkTest(folder, file);
    }

    @Test
    public void negBothDivTest() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "negBothDiv";
        checkTest(folder, file);
    }

    @Test
    public void negBothModTest() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "negBothMod";
        checkTest(folder, file);
    }

    @Test
    public void negDividendDivTest() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "negDividendDiv";
        checkTest(folder, file);
    }

    @Test
    public void negDividendModTest() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "negDividendMod";
        checkTest(folder, file);
    }

    @Test
    public void negDivisorDivTest() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "negDivisorDiv";
        checkTest(folder, file);
    }

    @Test
    public void negDivisorModTest() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "negDivisorMod";
        checkTest(folder, file);
    }

    @Test
    public void negExprTest() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "negExpr";
        checkTest(folder, file);
    }

    @Test
    public void notEqualsExprTest() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "notequalsExpr";
        checkTest(folder, file);
    }

    @Test
    public void notExprTest() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "notExpr";
        checkTest(folder, file);
    }

    @Test
    public void ordAndChrExprTest() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "ordAndchrExpr";
        checkTest(folder, file);
    }

    @Test
    public void orExprTest() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "orExpr";
        checkTest(folder, file);
    }

    @Test
    public void plusExprTest() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "plusExpr";
        checkTest(folder, file);
    }

    @Test
    public void plusMinusExprTest() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "plusMinusExpr";
        checkTest(folder, file);
    }

    @Test
    public void plusNoWhitespaceExprTest() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "plusNoWhitespaceExpr";
        checkTest(folder, file);
    }

    @Test
    public void plusPlusExprTest() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "plusPlusExpr";
        checkTest(folder, file);
    }

    @Test
    public void sequentialCountTest() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "sequentialCount";
        checkTest(folder, file);
    }

    @Test
    public void stringEqualsExprTest() throws IOException, InterruptedException {
        String folder = "expressions";
        String file = "stringEqualsExpr";
        checkTest(folder, file);
    }

    //FUNCTION

    //NESTED_FUNCTIONS

    @Test
    public void fibonacciFullRecTest() throws IOException, InterruptedException {
        String folder = "function/nested_functions";
        String file = "fibonacciFullRec";
        checkTest(folder, file, Collections.singletonList("3"), Arrays.asList("3", "2"));
    }

    @Test
    public void fibonacciRecursiveTest() throws IOException, InterruptedException {
        String folder = "function/nested_functions";
        String file = "fibonacciRecursive";
        checkTest(folder, file);
    }

    @Test
    public void fixedPointRealArithmeticTest() throws IOException, InterruptedException {
        String folder = "function/nested_functions";
        String file = "fixedPointRealArithmetic";
        assertEquals("Using fixed-point real: 10 / 3 * 3 = 10", getActualOutput(folder, file, new ArrayList<>()));
    }

    @Test
    public void functionConditionalReturnTest() throws IOException, InterruptedException {
        String folder = "function/nested_functions";
        String file = "functionConditionalReturn";
        checkTest(folder, file);
    }

    @Test
    public void mutualRecursionTest() throws IOException, InterruptedException {
        String folder = "function/nested_functions";
        String file = "mutualRecursion";
        checkTest(folder, file);
    }

    @Test
    public void printInputTriangleTest() throws IOException, InterruptedException {
        String folder = "function/nested_functions";
        String file = "printInputTriangle";
        checkTest(folder, file, Collections.singletonList("5"), Collections.singletonList(" ---------------"));
    }

    @Test
    public void printTriangleTest() throws IOException, InterruptedException {
        String folder = "function/nested_functions";
        String file = "printTriangle";
        checkTest(folder, file);
    }

    @Test
    public void simpleRecursionTest() throws IOException, InterruptedException {
        String folder = "function/nested_functions";
        String file = "simpleRecursion";
        checkTest(folder, file);
    }

    //SIMPLE_FUNCTION

    @Test
    public void asciiTableTest() throws IOException, InterruptedException {
        String folder = "function/simple_functions";
        String file = "asciiTable";
        checkTest(folder, file);
    }

    @Test
    public void functionDeclarationTest() throws IOException, InterruptedException {
        String folder = "function/simple_functions";
        String file = "functionDeclaration";
        checkTest(folder, file);
    }


    @Test
    public void functionManyArgumentsTest() throws IOException, InterruptedException {
        String folder = "function/simple_functions";
        String file = "functionManyArguments";
        checkTest(folder, file);
    }



    @Test
    public void functionReturnPairTest() throws IOException, InterruptedException {
        String folder = "function/simple_functions";
        String file = "functionReturnPair";
        checkTest(folder, file);
    }

    @Test
    public void functionSimpleTest() throws IOException, InterruptedException {
        String folder = "function/simple_functions";
        String file = "functionSimple";
        checkTest(folder, file);
    }

    @Test
    public void functionSimpleLoopTest() throws IOException, InterruptedException {
        String folder = "function/simple_functions";
        String file = "functionSimpleLoop";
        checkTest(folder, file);
    }

    @Test
    public void functionUpdateParameterTest() throws IOException, InterruptedException {
        String folder = "function/simple_functions";
        String file = "functionUpdateParameter";
        checkTest(folder, file);
    }

    @Test
    public void incFunctionTest() throws IOException, InterruptedException {
        String folder = "function/simple_functions";
        String file = "incFunction";
        checkTest(folder, file);
    }

    @Test
    public void negFunctionTest() throws IOException, InterruptedException {
        String folder = "function/simple_functions";
        String file = "negFunction";
        checkTest(folder, file);
    }

    @Test
    public void sameArgNameTest() throws IOException, InterruptedException {
        String folder = "function/simple_functions";
        String file = "sameArgName";
        checkTest(folder, file);
    }

    @Test
    public void sameArgName2Test() throws IOException, InterruptedException {
        String folder = "function/simple_functions";
        String file = "sameArgName2";
        checkTest(folder, file);
    }

    @Test
    public void sameNameAsVarTest() throws IOException, InterruptedException {
        String folder = "function/simple_functions";
        String file = "sameNameAsVar";
        checkTest(folder, file);
    }

    //IF

    @Test
    public void if1Test() throws IOException, InterruptedException {
        String folder = "if";
        String file = "if1";
        checkTest(folder, file);
    }

    @Test
    public void if2Test() throws IOException, InterruptedException {
        String folder = "if";
        String file = "if2";
        checkTest(folder, file);
    }

    @Test
    public void if3Test() throws IOException, InterruptedException {
        String folder = "if";
        String file = "if3";
        checkTest(folder, file);
    }

    @Test
    public void if4Test() throws IOException, InterruptedException {
        String folder = "if";
        String file = "if4";
        checkTest(folder, file);
    }

    @Test
    public void if5Test() throws IOException, InterruptedException {
        String folder = "if";
        String file = "if5";
        checkTest(folder, file);
    }

    @Test
    public void if6Test() throws IOException, InterruptedException {
        String folder = "if";
        String file = "if6";
        checkTest(folder, file);
    }

    @Test
    public void ifBasicTest() throws IOException, InterruptedException {
        String folder = "if";
        String file = "ifBasic";
        checkTest(folder, file);
    }

    @Test
    public void ifFalseTest() throws IOException, InterruptedException {
        String folder = "if";
        String file = "ifFalse";
        checkTest(folder, file);
    }

    @Test
    public void ifTrueTest() throws IOException, InterruptedException {
        String folder = "if";
        String file = "ifTrue";
        checkTest(folder, file);
    }

    @Test
    public void whitespaceTest() throws IOException, InterruptedException {
        String folder = "if";
        String file = "whitespace";
        checkTest(folder, file);
    }


    // IO

    //PRINT

    @Test
    public void hashInProgramTest() throws IOException, InterruptedException {
        String folder = "IO/print";
        String file = "hashInProgram";
        checkTest(folder, file);
    }

    @Test
    public void multipleStringsAssignmentTest() throws IOException, InterruptedException {
        String folder = "IO/print";
        String file = "multipleStringsAssignment";
        checkTest(folder, file);
    }

    @Test
    public void printTest() throws IOException, InterruptedException {
        String folder = "IO/print";
        String file = "print";
        checkTest(folder, file);
    }

    @Test
    public void printBackspaceTest() throws IOException, InterruptedException {
        String folder = "IO/print";
        String file = "print-backspace";
        checkTest(folder, file);
    }

    @Test
    public void printCarridgeReturnTest() throws IOException, InterruptedException {
        String folder = "IO/print";
        String file = "print-carridge-return";
        checkTest(folder, file);
    }

    @Test
    public void printBoolTest() throws IOException, InterruptedException {
        String folder = "IO/print";
        String file = "printBool";
        checkTest(folder, file);
    }

    @Test
    public void printCharTest() throws IOException, InterruptedException {
        String folder = "IO/print";
        String file = "printChar";
        checkTest(folder, file);
    }

    @Test
    public void printCharArrayTest() throws IOException, InterruptedException {
        String folder = "IO/print";
        String file = "printCharArray";
        checkTest(folder, file);
    }

    @Test
    public void printCharAsStringTest() throws IOException, InterruptedException {
        String folder = "IO/print";
        String file = "printCharAsString";
        checkTest(folder, file);
    }

    @Test
    public void printEscCharTest() throws IOException, InterruptedException {
        String folder = "IO/print";
        String file = "printEscChar";
        checkTest(folder, file);
    }

    @Test
    public void printIntTest() throws IOException, InterruptedException {
        String folder = "IO/print";
        String file = "printInt";
        checkTest(folder, file);
    }

    @Test
    public void printlnTest() throws IOException, InterruptedException {
        String folder = "IO/print";
        String file = "println";
        checkTest(folder, file);
    }

    //READ

    @Test
    public void echoBigIntTest() throws IOException, InterruptedException {
        String folder = "IO/read";
        String file = "echoBigInt";
        checkTest(folder, file, Collections.singletonList("2000000000"), Collections.singletonList("2000000000"));
    }

    @Test
    public void echoBigNegIntTest() throws IOException, InterruptedException {
        String folder = "IO/read";
        String file = "echoBigNegInt";
        checkTest(folder, file, Collections.singletonList("-2000000000"), Collections.singletonList("-2000000000"));
    }


    @Test
    public void echoCharTest() throws IOException, InterruptedException {
        String folder = "IO/read";
        String file = "echoChar";
        checkTest(folder, file, Collections.singletonList("c"), Collections.singletonList("c"));
    }


    @Test
    public void echoIntTest() throws IOException, InterruptedException {
        String folder = "IO/read";
        String file = "echoInt";
        checkTest(folder, file, Collections.singletonList("4"), Collections.singletonList("4"));
    }


    @Test
    public void echoNegIntTest() throws IOException, InterruptedException {
        String folder = "IO/read";
        String file = "echoNegInt";
        checkTest(folder, file, Collections.singletonList("-1"), Collections.singletonList("-1"));
    }


    @Test
    public void echoPuncCharTest() throws IOException, InterruptedException {
        String folder = "IO/read";
        String file = "echoPuncChar";
        checkTest(folder, file, Collections.singletonList("?"), Collections.singletonList("?"));
    }


    @Test
    public void readTest() throws IOException, InterruptedException {
        String folder = "IO/read";
        String file = "read";
        checkTest(folder, file, Collections.singletonList("5"), Collections.singletonList("5"));
    }

    //General IO


    @Test
    public void ioLoopTest() throws IOException, InterruptedException {
        String folder = "IO";
        String file = "IOLoop";
        checkTest(folder, file, Arrays.asList("4", "N"), Collections.singletonList("4"));
    }



    @Test
    public void ioSequenceTest() throws IOException, InterruptedException {
        String folder = "IO";
        String file = "IOSequence";
        checkTest(folder, file, Collections.singletonList("9"), Collections.singletonList("9"));
    }



    // PAIRS


    @Test
    public void checkRefPairTest() throws IOException, InterruptedException {
        String folder = "pairs";
        String file = "checkRefPair";
        checkTest(folder, file);
    }

    @Test
    public void createPairTest() throws IOException, InterruptedException {
        String folder = "pairs";
        String file = "createPair";
        checkTest(folder, file);
    }

    @Test
    public void createPair02Test() throws IOException, InterruptedException {
        String folder = "pairs";
        String file = "createPair02";
        checkTest(folder, file);
    }

    @Test
    public void createPair03Test() throws IOException, InterruptedException {
        String folder = "pairs";
        String file = "createPair03";
        checkTest(folder, file);
    }

    @Test
    public void createRefPairTest() throws IOException, InterruptedException {
        String folder = "pairs";
        String file = "createRefPair";
        checkTest(folder, file);
    }

    @Test
    public void freeTest() throws IOException, InterruptedException {
        String folder = "pairs";
        String file = "free";
        checkTest(folder, file);
    }

    @Test
    public void linkedListTest() throws IOException, InterruptedException {
        String folder = "pairs";
        String file = "linkedList";
        checkTest(folder, file);
    }

    @Test
    public void nestedPairTest() throws IOException, InterruptedException {
        String folder = "pairs";
        String file = "nestedPair";
        checkTest(folder, file);
    }

    @Test
    public void nullTest() throws IOException, InterruptedException {
        String folder = "pairs";
        String file = "null";
        checkTest(folder, file);
    }

    @Test
    public void printNullTest() throws IOException, InterruptedException {
        String folder = "pairs";
        String file = "printNull";
        checkTest(folder, file);
    }

    @Test
    public void printNullPairTest() throws IOException, InterruptedException {
        String folder = "pairs";
        String file = "printNullPair";
        checkTest(folder, file);
    }

    @Test
    public void printPairTest() throws IOException, InterruptedException {
        String folder = "pairs";
        String file = "printPair";
        checkTest(folder, file);
    }

    @Test
    public void printPairOfNullsTest() throws IOException, InterruptedException {
        String folder = "pairs";
        String file = "printPairOfNulls";
        checkTest(folder, file);
    }

    @Test
    public void readPairTest() throws IOException, InterruptedException {
        String folder = "pairs";
        String file = "readPair";
        checkTest(folder, file, Arrays.asList("c", "4"), Arrays.asList("c", "4"));
    }

    @Test
    public void writeFstTest() throws IOException, InterruptedException {
        String folder = "pairs";
        String file = "writeFst";
        checkTest(folder, file);
    }

    @Test
    public void writeSndTest() throws IOException, InterruptedException {
        String folder = "pairs";
        String file = "writeSnd";
        checkTest(folder, file);
    }

    // RUNTIME_ERRORS

    // ARRAY_OUT_OF_BOUNDS

    @Test
    public void arrayNegBoundsTest() throws IOException, InterruptedException {
        String folder = "runtimeErr/arrayOutOfBounds";
        String file = "arrayNegBounds";
        checkTest(folder, file, new ArrayList<>(), Collections.singletonList("ArrayIndexOutOfBoundsError: negative index"));
    }

    @Test
    public void arrayOutOfBoundsTest() throws IOException, InterruptedException {
        String folder = "runtimeErr/arrayOutOfBounds";
        String file = "arrayOutOfBounds";
        checkTest(folder, file, new ArrayList<>(), Collections.singletonList("ArrayIndexOutOfBoundsError: index too large"));
    }

    @Test
    public void arrayOutOfBoundsWriteTest() throws IOException, InterruptedException {
        String folder = "runtimeErr/arrayOutOfBounds";
        String file = "arrayOutOfBoundsWrite";
        checkTest(folder, file, new ArrayList<>(), Collections.singletonList("ArrayIndexOutOfBoundsError: index too large"));
    }

    // DIVIDE_BY_ZERO

    @Test
    public void divideByZeroTest() throws IOException, InterruptedException {
        String folder = "runtimeErr/divideByZero";
        String file = "divideByZero";
        checkTest(folder, file, new ArrayList<>(), Collections.singletonList("DivideByZeroError: divide or modulo by zero"));
    }


    @Test
    public void divZeroTest() throws IOException, InterruptedException {
        String folder = "runtimeErr/divideByZero";
        String file = "divZero";
        checkTest(folder, file, new ArrayList<>(), Collections.singletonList("DivideByZeroError: divide or modulo by zero"));
    }

    @Test
    public void modByZeroTest() throws IOException, InterruptedException {
        String folder = "runtimeErr/divideByZero";
        String file = "modByZero";
        checkTest(folder, file, new ArrayList<>(), Collections.singletonList("DivideByZeroError: divide or modulo by zero"));
    }

    // INTEGER_OVERFLOW

    @Test
    public void intJustOverflowTest() throws IOException, InterruptedException {
        String folder = "runtimeErr/integerOverflow";
        String file = "intJustOverflow";
        checkTest(folder, file, new ArrayList<>(), Collections.singletonList("OverflowError: the result is too small/large to store in a 4-byte signed-integer."));
    }

    @Test
    public void intMultOverflowTest() throws IOException, InterruptedException {
        String folder = "runtimeErr/integerOverflow";
        String file = "intmultOverflow";
        checkTest(folder, file, new ArrayList<>(), Collections.singletonList("OverflowError: the result is too small/large to store in a 4-byte signed-integer."));
    }

    @Test
    public void intNegateOverflowTest() throws IOException, InterruptedException {
        String folder = "runtimeErr/integerOverflow";
        String file = "intnegateOverflow";
        checkTest(folder, file, new ArrayList<>(), Collections.singletonList("OverflowError: the result is too small/large to store in a 4-byte signed-integer."));
    }

    @Test
    public void intNegateOverflow2Test() throws IOException, InterruptedException {
        String folder = "runtimeErr/integerOverflow";
        String file = "intnegateOverflow2";
        checkTest(folder, file, new ArrayList<>(), Collections.singletonList("OverflowError: the result is too small/large to store in a 4-byte signed-integer."));
    }

    @Test
    public void intNegateOverflow3Test() throws IOException, InterruptedException {
        String folder = "runtimeErr/integerOverflow";
        String file = "intnegateOverflow3";
        checkTest(folder, file, new ArrayList<>(), Collections.singletonList("OverflowError: the result is too small/large to store in a 4-byte signed-integer."));
    }

    @Test
    public void intNegateOverflow4Test() throws IOException, InterruptedException {
        String folder = "runtimeErr/integerOverflow";
        String file = "intnegateOverflow4";
        checkTest(folder, file, new ArrayList<>(), Collections.singletonList("OverflowError: the result is too small/large to store in a 4-byte signed-integer."));
    }

    @Test
    public void intUnderflowTest() throws IOException, InterruptedException {
        String folder = "runtimeErr/integerOverflow";
        String file = "intUnderflow";
        checkTest(folder, file, new ArrayList<>(), Collections.singletonList("OverflowError: the result is too small/large to store in a 4-byte signed-integer."));
    }

    @Test
    public void intWayOverflowTest() throws IOException, InterruptedException {
        String folder = "runtimeErr/integerOverflow";
        String file = "intWayOverflow";
        checkTest(folder, file, new ArrayList<>(), Collections.singletonList("OverflowError: the result is too small/large to store in a 4-byte signed-integer."));
    }

    // NULL_DEREFERENCE

    @Test
    public void freeNullTest() throws IOException, InterruptedException {
        String folder = "runtimeErr/nullDereference";
        String file = "freeNull";
        checkTest(folder, file, new ArrayList<>(), Collections.singletonList("NullReferenceError: dereference a null reference"));
    }

    @Test
    public void readNull1Test() throws IOException, InterruptedException {
        String folder = "runtimeErr/nullDereference";
        String file = "readNull1";
        checkTest(folder, file, new ArrayList<>(), Collections.singletonList("NullReferenceError: dereference a null reference"));
    }

    @Test
    public void readNull2Test() throws IOException, InterruptedException {
        String folder = "runtimeErr/nullDereference";
        String file = "readNull2";
        checkTest(folder, file, new ArrayList<>(), Collections.singletonList("NullReferenceError: dereference a null reference"));
    }

    @Test
    public void setNull1Test() throws IOException, InterruptedException {
        String folder = "runtimeErr/nullDereference";
        String file = "setNull1";
        checkTest(folder, file, new ArrayList<>(), Collections.singletonList("NullReferenceError: dereference a null reference"));
    }

    @Test
    public void setNull2Test() throws IOException, InterruptedException {
        String folder = "runtimeErr/nullDereference";
        String file = "setNull2";
        checkTest(folder, file, new ArrayList<>(), Collections.singletonList("NullReferenceError: dereference a null reference"));
    }

    @Test
    public void useNull1Test() throws IOException, InterruptedException {
        String folder = "runtimeErr/nullDereference";
        String file = "useNull1";
        checkTest(folder, file, new ArrayList<>(), Collections.singletonList("NullReferenceError: dereference a null reference"));
    }

    @Test
    public void useNull2Test() throws IOException, InterruptedException {
        String folder = "runtimeErr/nullDereference";
        String file = "useNull2";
        checkTest(folder, file, new ArrayList<>(), Collections.singletonList("NullReferenceError: dereference a null reference"));
    }

    // SCOPE

    @Test
    public void ifNested1Test() throws IOException, InterruptedException {
        String folder = "scope";
        String file = "ifNested1";
        checkTest(folder, file);
    }

    @Test
    public void ifNested2Test() throws IOException, InterruptedException {
        String folder = "scope";
        String file = "ifNested2";
        checkTest(folder, file);
    }

    @Test
    public void indentationNotImportantTest() throws IOException, InterruptedException {
        String folder = "scope";
        String file = "indentationNotImportant";
        checkTest(folder, file);
    }

    @Test
    public void intsAndKeywordsTest() throws IOException, InterruptedException {
        String folder = "scope";
        String file = "intsAndKeywords";
        checkTest(folder, file);
    }

    @Test
    public void printAllTypesTest() throws IOException, InterruptedException {
        String folder = "scope";
        String file = "printAllTypes";
        checkTest(folder, file);
    }

    @Test
    public void scopeTest() throws IOException, InterruptedException {
        String folder = "scope";
        String file = "scope";
        checkTest(folder, file);
    }

    @Test
    public void scopeBasicTest() throws IOException, InterruptedException {
        String folder = "scope";
        String file = "scopeBasic";
        checkTest(folder, file);
    }

    @Test
    public void scopeRedefineTest() throws IOException, InterruptedException {
        String folder = "scope";
        String file = "scopeRedefine";
        checkTest(folder, file);
    }

    @Test
    public void scopeSimpleRedefineTest() throws IOException, InterruptedException {
        String folder = "scope";
        String file = "scopeSimpleRedefine";
        checkTest(folder, file);
    }

    @Test
    public void scopeVarsTest() throws IOException, InterruptedException {
        String folder = "scope";
        String file = "scopeVars";
        checkTest(folder, file);
    }

    // SEQUENCE

    @Test
    public void basicSeqTest() throws IOException, InterruptedException {
        String folder = "sequence";
        String file = "basicSeq";
        checkTest(folder, file);
    }

    @Test
    public void basicSeq2Test() throws IOException, InterruptedException {
        String folder = "sequence";
        String file = "basicSeq2";
        checkTest(folder, file);
    }

    @Test
    public void boolAssignmentTest() throws IOException, InterruptedException {
        String folder = "sequence";
        String file = "boolAssignment";
        checkTest(folder, file);
    }

    @Test
    public void charAssignmentTest() throws IOException, InterruptedException {
        String folder = "sequence";
        String file = "charAssignment";
        checkTest(folder, file);
    }

    @Test
    public void exitSimpleTest() throws IOException, InterruptedException {
        String folder = "sequence";
        String file = "exitSimple";
        checkTest(folder, file);
    }

    @Test
    public void intAssignmentTest() throws IOException, InterruptedException {
        String folder = "sequence";
        String file = "intAssignment";
        checkTest(folder, file);
    }

    @Test
    public void intLeadingZerosTest() throws IOException, InterruptedException {
        String folder = "sequence";
        String file = "intLeadingZeros";
        checkTest(folder, file);
    }

    @Test
    public void stringAssignmentTest() throws IOException, InterruptedException {
        String folder = "sequence";
        String file = "stringAssignment";
        checkTest(folder, file);
    }

    // VARIABLES

    @Test
    public void _VarNamesTest() throws IOException, InterruptedException {
        String folder = "variables";
        String file = "_VarNames";
        checkTest(folder, file);
    }

    @Test
    public void boolDeclarationTest() throws IOException, InterruptedException {
        String folder = "variables";
        String file = "boolDeclaration";
        checkTest(folder, file);
    }

    @Test
    public void boolDeclaration2Test() throws IOException, InterruptedException {
        String folder = "variables";
        String file = "boolDeclaration2";
        checkTest(folder, file);
    }

    @Test
    public void capCharDeclarationTest() throws IOException, InterruptedException {
        String folder = "variables";
        String file = "capCharDeclaration";
        checkTest(folder, file);
    }

    @Test
    public void charDeclarationTest() throws IOException, InterruptedException {
        String folder = "variables";
        String file = "charDeclaration";
        checkTest(folder, file);
    }

    @Test
    public void charDeclaration2Test() throws IOException, InterruptedException {
        String folder = "variables";
        String file = "charDeclaration2";
        checkTest(folder, file);
    }

    @Test
    public void emptyStringDeclarationTest() throws IOException, InterruptedException {
        String folder = "variables";
        String file = "emptyStringDeclaration";
        checkTest(folder, file);
    }

    @Test
    public void intDeclarationTest() throws IOException, InterruptedException {
        String folder = "variables";
        String file = "intDeclaration";
        checkTest(folder, file);
    }

    @Test
    public void longVarNamesTest() throws IOException, InterruptedException {
        String folder = "variables";
        String file = "longVarNames";
        checkTest(folder, file);
    }

    @Test
    public void manyVariablesTest() throws IOException, InterruptedException {
        String folder = "variables";
        String file = "manyVariables";
        checkTest(folder, file);
    }

    @Test
    public void negIntDeclarationTest() throws IOException, InterruptedException {
        String folder = "variables";
        String file = "negIntDeclaration";
        checkTest(folder, file);
    }

    @Test
    public void puncCharDeclarationTest() throws IOException, InterruptedException {
        String folder = "variables";
        String file = "puncCharDeclaration";
        checkTest(folder, file);
    }

    @Test
    public void stringDeclarationTest() throws IOException, InterruptedException {
        String folder = "variables";
        String file = "stringDeclaration";
        checkTest(folder, file);
    }

    @Test
    public void zeroIntDeclarationTest() throws IOException, InterruptedException {
        String folder = "variables";
        String file = "zeroIntDeclaration";
        checkTest(folder, file);
    }

    // WHILE

    @Test
    public void fibonacciFullItTest() throws IOException, InterruptedException {
        String folder = "while";
        String file = "fibonacciFullIt";
        checkTest(folder, file, Collections.singletonList("4"), Arrays.asList("4", "3"));
    }

    @Test
    public void fibonacciIterativeTest() throws IOException, InterruptedException {
        String folder = "while";
        String file = "fibonacciIterative";
        checkTest(folder, file);
    }

    @Test
    public void loopCharConditionTest() throws IOException, InterruptedException {
        String folder = "while";
        String file = "loopCharCondition";
        checkTest(folder, file);
    }

    @Test
    public void loopIntConditionTest() throws IOException, InterruptedException {
        String folder = "while";
        String file = "loopIntCondition";
        checkTest(folder, file);
    }

    @Test
    public void maxTest() throws IOException, InterruptedException {
        String folder = "while";
        String file = "max";
        checkTest(folder, file);
    }

    @Test
    public void minTest() throws IOException, InterruptedException {
        String folder = "while";
        String file = "min";
        checkTest(folder, file);
    }

    @Test
    public void rmStyleAddTest() throws IOException, InterruptedException {
        String folder = "while";
        String file = "rmStyleAdd";
        checkTest(folder, file);
    }

    @Test
    public void rmStyleAddIOTest() throws IOException, InterruptedException {
        String folder = "while";
        String file = "rmStyleAddIO";
        checkTest(folder, file, Arrays.asList("5", "4"), Arrays.asList("5", "4", "54"));
    }

    @Test
    public void whileBasicTest() throws IOException, InterruptedException {
        String folder = "while";
        String file = "whileBasic";
        checkTest(folder, file);
    }

    @Test
    public void whileBoolFlipTest() throws IOException, InterruptedException {
        String folder = "while";
        String file = "whileBoolFlip";
        checkTest(folder, file);
    }

    @Test
    public void whileCountTest() throws IOException, InterruptedException {
        String folder = "while";
        String file = "whileCount";
        checkTest(folder, file);
    }

    @Test
    public void whileFalseTest() throws IOException, InterruptedException {
        String folder = "while";
        String file = "whileFalse";
        checkTest(folder, file);
    }

}
