package waccFrontEnd.AST;

public class PrettyPrinter {

    private int indent = 0;
    private final StringBuilder sb = new StringBuilder();

    public void printLine(String line) {
        sb.append("\t".repeat(indent * 2)).append(line).append("\n");
    }

    public void incrementIndent() {
        indent++;
    }

    void decrementIndent() {
        indent--;
    }

    @Override
    public String toString() {
        return sb.toString();
    }

}
