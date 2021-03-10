package antlr;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class WaccLexerUnitTest {

  public List<Token> getTokens(String input) {
    WaccLexer lexer = new WaccLexer(CharStreams.fromString(input));
    CommonTokenStream tokenStream = new CommonTokenStream(lexer);
    tokenStream.fill();
    return tokenStream.getTokens();
  }

  @Test
  public void plusTokenIsRecognized() {
    List<Token> tokens = getTokens("+");
    assertEquals(2, tokens.size()); // includes EOF
    assertEquals(WaccLexer.PLUS, tokens.get(0).getType());
  }

  @Test
  public void minusTokenIsRecognized() {
    List<Token> tokens = getTokens("-");
    assertEquals(2, tokens.size()); // includes EOF
    assertEquals(WaccLexer.MINUS, tokens.get(0).getType());
  }

  @Test
  public void multTokenIsRecognized() {
    List<Token> tokens = getTokens("*");
    assertEquals(2, tokens.size()); // includes EOF
    assertEquals(WaccLexer.MULT, tokens.get(0).getType());
  }

  @Test
  public void divTokenIsRecognized() {
    List<Token> tokens = getTokens("/");
    assertEquals(2, tokens.size()); // includes EOF
    assertEquals(WaccLexer.DIV, tokens.get(0).getType());
  }

  @Test
  public void modTokenIsRecognized() {
    List<Token> tokens = getTokens("%");
    assertEquals(2, tokens.size()); // includes EOF
    assertEquals(WaccLexer.MOD, tokens.get(0).getType());
  }

  @Test
  public void parenthesesTokensAreRecognized() {
    List<Token> tokens = getTokens("()");
    assertEquals(3, tokens.size()); // includes EOF
    assertEquals(WaccLexer.OPEN_PARENTHESES, tokens.get(0).getType());
    assertEquals(WaccLexer.CLOSE_PARENTHESES, tokens.get(1).getType());
  }

  @Test
  public void singleDigitIntegerTokenIsRecognized() {
    List<Token> tokens = getTokens("7");
    assertEquals(2, tokens.size()); // includes EOF
    assertEquals(WaccLexer.INTEGER, tokens.get(0).getType());
  }

  @Test
  public void multipleDigitIntegerTokenIsRecognized() {
    List<Token> tokens = getTokens("234");
    assertEquals(2, tokens.size()); // includes EOF
    assertEquals(WaccLexer.INTEGER, tokens.get(0).getType());
  }

  @Test
  public void whiteSpaceCharactersAreSkipped() {
    List<Token> tokens = getTokens(" ");
    assertEquals(1, tokens.size()); // includes EOF
    assertEquals(WaccLexer.EOF, tokens.get(0).getType());
  }

  @Test
  public void tabCharactersAreSkipped() {
    List<Token> tokens = getTokens("5\t");
    assertEquals(2, tokens.size()); // includes EOF
    assertEquals(WaccLexer.INTEGER, tokens.get(0).getType());
    assertEquals(WaccLexer.EOF, tokens.get(1).getType());
  }

  @Test
  public void newLineCharacterTokenIsRecognized() {
    List<Token> tokens = getTokens("\n");
    assertEquals(1, tokens.size()); // includes EOF
    assertEquals(WaccLexer.EOF, tokens.get(0).getType());
  }

  @Test
  public void windowsNewLineCharacterTokenIsRecognized() {
    List<Token> tokens = getTokens("\r\n");
    assertEquals(1, tokens.size()); // includes EOF
    assertEquals(WaccLexer.EOF, tokens.get(0).getType());
  }

  @Test
  public void stringLiteralIsRecognized() {
    List<Token> tokens = getTokens("\"Hello\"");
    assertEquals(2, tokens.size()); // includes EOF
    assertEquals(WaccLexer.STRING_LIT, tokens.get(0).getType());
  }

  @Test
  public void characterLiteralIsRecognized() {
    List<Token> tokens = getTokens("'x'");
    assertEquals(2, tokens.size()); // includes EOF
    assertEquals(WaccLexer.CHARACTER_LIT, tokens.get(0).getType());
  }

  @Test
  public void wholeLineAfterCommentTokenIsSkipped() {
    List<Token> tokens = getTokens("123#a. random.comment\n4+5");
    assertEquals(5, tokens.size()); // includes EOF
    assertEquals(WaccLexer.INTEGER, tokens.get(0).getType());
    assertEquals(WaccLexer.INTEGER, tokens.get(1).getType());
    assertEquals(WaccLexer.PLUS, tokens.get(2).getType());
    assertEquals(WaccLexer.INTEGER, tokens.get(3).getType());
  }

  @Test
  public void LoopTokensAreRecognized() {
    List<Token> tokens = getTokens("while do done for");
    assertEquals(5, tokens.size());
    assertEquals(WaccLexer.WHILE, tokens.get(0).getType());
    assertEquals(WaccLexer.DO, tokens.get(1).getType());
    assertEquals(WaccLexer.DONE, tokens.get(2).getType());
    assertEquals(WaccLexer.FOR, tokens.get(3).getType());
  }

  @Test
  public void booleanTokensAreRecognized() {
    List<Token> tokens = getTokens("true");
    assertEquals(2, tokens.size());
    assertEquals(WaccLexer.TRUE, tokens.get(0).getType());
  }

  @Test
  public void identifiersTokensAreRecognized() {
    List<Token> tokens = getTokens("fefe_de34_");
    assertEquals(2, tokens.size());
    assertEquals(WaccLexer.IDENT, tokens.get(0).getType());
  }

  @Test
  public void pairTokensAreRecognized() {
    List<Token> tokens = getTokens("pair newpair fst snd null");
    assertEquals(6, tokens.size());
    Integer[] expectedTokens = {
      WaccLexer.PAIR, WaccLexer.NEWPAIR, WaccLexer.FST, WaccLexer.SND, WaccLexer.NULL, WaccLexer.EOF
    };
    Integer[] actualTokens = tokens.stream().map(Token::getType).toArray(Integer[]::new);
    assertArrayEquals(expectedTokens, actualTokens);
  }

  @Test
  public void punctuationsAreRecognized() {
    List<Token> tokens = getTokens("(),=;[]'\"");
    assertEquals(10, tokens.size());
    Integer[] expectedTokens = {
      WaccLexer.OPEN_PARENTHESES,
      WaccLexer.CLOSE_PARENTHESES,
      WaccLexer.COMMA,
      WaccLexer.ASSIGN,
      WaccLexer.SEMICOLON,
      WaccLexer.SQUARE_OPEN,
      WaccLexer.SQUARE_CLOSE,
      WaccLexer.SINGLE_QUOTE,
      WaccLexer.DOUBLE_QUOTE,
      WaccLexer.EOF
    };
    Integer[] actualTokens = tokens.stream().map(Token::getType).toArray(Integer[]::new);
    assertArrayEquals(expectedTokens, actualTokens);
  }

  @Test
  public void stringWithEscapedQuotesIsRecognized() {
    List<Token> tokens = getTokens("\"This is a \\\"Quote\\\".\"");
    assertEquals(2, tokens.size());
    assertEquals(WaccLexer.STRING_LIT, tokens.get(0).getType());
  }

  @Test
  public void unaryOperatorsAreRecognized() {
    List<Token> tokens = getTokens("! len ord chr");
    assertEquals(5, tokens.size());
    assertEquals(WaccLexer.NOT, tokens.get(0).getType());
    assertEquals(WaccLexer.LEN, tokens.get(1).getType());
    assertEquals(WaccLexer.ORD, tokens.get(2).getType());
    assertEquals(WaccLexer.CHR, tokens.get(3).getType());
  }

  @Test
  public void comparisonAndBooleanOperatorsAreRecognized() {
    List<Token> tokens = getTokens("true || false && !=");
    assertEquals(6, tokens.size());
    Integer[] expectedTokens = {
      WaccLexer.TRUE, WaccLexer.OR, WaccLexer.FALSE, WaccLexer.AND, WaccLexer.NEQ, WaccLexer.EOF
    };
    Integer[] actualTokens = tokens.stream().map(Token::getType).toArray(Integer[]::new);
    assertArrayEquals(expectedTokens, actualTokens);
  }

  @Test
  public void baseTypesAreRecognized() {
    List<Token> tokens = getTokens("int string bool char");
    assertEquals(5, tokens.size());
    assertEquals(WaccLexer.INT, tokens.get(0).getType());
    assertEquals(WaccLexer.STRING, tokens.get(1).getType());
    assertEquals(WaccLexer.BOOL, tokens.get(2).getType());
    assertEquals(WaccLexer.CHAR, tokens.get(3).getType());
  }

  @Test
  public void scopeTokensAreRecognized() {
    List<Token> tokens = getTokens("begin end");
    assertEquals(3, tokens.size());
    assertEquals(WaccLexer.BEGIN, tokens.get(0).getType());
    assertEquals(WaccLexer.END, tokens.get(1).getType());
  }

  @Test
  public void functionTokensAreRecognized() {
    List<Token> tokens = getTokens("is call");
    assertEquals(3, tokens.size());
    assertEquals(WaccLexer.IS, tokens.get(0).getType());
    assertEquals(WaccLexer.CALL, tokens.get(1).getType());
  }

  @Test
  public void ifStatementTokensAreRecognized() {
    List<Token> tokens = getTokens("if then else fi");
    assertEquals(5, tokens.size());
    assertEquals(WaccLexer.IF, tokens.get(0).getType());
    assertEquals(WaccLexer.THEN, tokens.get(1).getType());
    assertEquals(WaccLexer.ELSE, tokens.get(2).getType());
    assertEquals(WaccLexer.FI, tokens.get(3).getType());
  }

  @Test
  public void otherStatementsAreRecognized() {
    List<Token> tokens = getTokens("skip read free return exit print println");
    assertEquals(8, tokens.size());
    Integer[] expectedTokens = {
      WaccLexer.SKIP_STMT,
      WaccLexer.READ,
      WaccLexer.FREE_STMT,
      WaccLexer.RETURN,
      WaccLexer.EXIT,
      WaccLexer.PRINT,
      WaccLexer.PRINTLN,
      WaccLexer.EOF
    };
    Integer[] actualTokens = tokens.stream().map(Token::getType).toArray(Integer[]::new);
    assertArrayEquals(expectedTokens, actualTokens);
  }
}
