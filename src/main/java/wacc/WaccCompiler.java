package wacc;

import antlr.WaccLexer;
import antlr.WaccParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import waccBackEnd.ARM11ProgramRepresentation;
import waccBackEnd.CodeGenerator;
import waccFrontEnd.AST.ErrorNode;
import waccFrontEnd.AST.Node;
import waccFrontEnd.SemanticErrorVisitor;
import waccFrontEnd.SyntaxErrorVisitor;

import java.io.*;
import java.util.List;

public class WaccCompiler {

  public static void main(String[] args) {
    assert (args.length == 1);
    String fileName = args[0];
    String compilationResult = getOutputMessage(fileName, true);
    System.out.print(compilationResult);
    if (compilationResult.contains("Finished")) {
      System.exit(0);
    } else if (compilationResult.contains("100")) {
      System.exit(100);
    } else {
      System.exit(200);
    }
  }

  // FrontEnd
  public static String getOutputMessage(String fileName, boolean createAssemblyFile) {
    try {
      WaccLexer lexer = new WaccLexer(CharStreams.fromFileName(fileName));
      CommonTokenStream tokens = new CommonTokenStream(lexer);
      WaccParser parser = new WaccParser(tokens);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      System.setErr(new PrintStream(baos));
      ParseTree tree = parser.prog();
      System.setErr(new PrintStream(new FileOutputStream(FileDescriptor.out)));
      if (parser.getNumberOfSyntaxErrors() == 0) {
        SyntaxErrorVisitor syntaxVisitor = new SyntaxErrorVisitor();
        String syntaxVisitResult = syntaxVisitor.visit(tree);
        if (syntaxVisitResult.equals("SUCCESS")) {
          SemanticErrorVisitor semanticVisitor = new SemanticErrorVisitor();
          Node tree_info = semanticVisitor.visit(tree);
          if(tree_info instanceof ErrorNode){
            List<String> errors = ((ErrorNode) tree_info).getErrors();
            StringBuilder sb = new StringBuilder();
            for (String error : errors) {
              sb.append(error);
            }
          return sb.toString();
          }else{
            if (createAssemblyFile) {
              createAssemblyFile(fileName, tree_info);
            }
            return "-- Finished\n";
          }
        } else {
          return syntaxVisitResult;
        }
      } else {
        return "Errors detected during compilation! Exit code 100 returned.\n" + baos.toString();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  // BackEnd
  private static void createAssemblyFile(String fileName, Node tree_info) throws IOException {
    int startIndex = 0;
    for(int i = fileName.length()-1; i>=0; i--){
      if(fileName.charAt(i) == '\\' || fileName.charAt(i) == '/'){
        startIndex = i+1;
        break;
      }
    }
    CodeGenerator cg = new CodeGenerator();
    cg.visit(tree_info);
    ARM11ProgramRepresentation rep = cg.getRep();
    String assemblyFileName = fileName.substring(startIndex, fileName.length()-4)+"s";
    File assemblyFile = new File(assemblyFileName);
    assemblyFile.createNewFile();
    rep.printInstructionsToFile(assemblyFileName);
  }

}
