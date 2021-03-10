package waccFrontEnd;

import waccFrontEnd.AST.Node;

import java.util.*;

public class SymbolTable {

    // -------------------------------------------------------------------- //
    // SymbolTable built up in SemanticErrorVisitor and simultaneously used //
    // to add information to the internal representation.                   //
    // -------------------------------------------------------------------- //

    private static final int ONE_BYTE = 1;
    private static final int FOUR_BYTES = 4;

    private final List<Map<String, Node>> scopes;

    private final Map<String, Node> funcScope;

    private boolean isFuncScope;

    private int funcScopeNum;

    private static SymbolTable instance = null;

    public static SymbolTable getInstance(){
        return new SymbolTable();
    }

    private SymbolTable() {
        this.scopes = new ArrayList<>();
        this.funcScope = new HashMap<>();
        this.isFuncScope = false;
        this.funcScopeNum = 0;
    }

    public static int getFourBytes() {
        return FOUR_BYTES;
    }

    public static int getOneByte() {
        return ONE_BYTE;
    }

    public void updateSymbol(String ident, Node newInfo){
        for (int i = scopes.size() - 1; i >= 0; --i) {
            Map<String, Node> scope = scopes.get(i);
            if (scope.containsKey(ident)) {
                scope.put(ident,newInfo);
                break;
            }
        }
    }

    public void setFuncScope(boolean funcScope) {
        if (funcScope) {
            funcScopeNum = scopes.size() - 1;
        }
        isFuncScope = funcScope;
    }

    public boolean isFuncScope() {
        return isFuncScope;
    }

    public void createScope() {
        scopes.add(new HashMap<>());
    }

    public Collection<Node> removeScope() {
        Collection<Node> oldScope = scopes.get(scopes.size() - 1).values();
        scopes.remove(scopes.size() - 1);
        return oldScope;
    }

    public Node getSymbolInfo(String ident) {
        for (int i = scopes.size() - 1; i >= 0; --i) {
            Map<String, Node> scope = scopes.get(i);
            if (scope.containsKey(ident)) {
                return scope.get(ident);
            }
        }
        return null;
    }

    public void addSymbolToFuncScope(String ident, Node info) {
        funcScope.put(ident, info);
    }

    public boolean isDeclaredInFuncScope(String symbol) {
        return funcScope.containsKey(symbol);
    }

    public Node getSymbolInfoFromFuncScope(String symbol) {
        return funcScope.get(symbol);
    }

    public void addSymbol(String ident, Node info) {
        scopes.get(scopes.size() - 1).put(ident, info);
    }

    public boolean isDeclaredInCurrentScope(String symbol) {
        return scopes.get(scopes.size() - 1).containsKey(symbol);
    }

    public boolean isDeclaredInAnyScope(String symbol) {
        if (!isFuncScope) {
            for (int i = scopes.size() - 1; i >= 0; --i) {
                if (scopes.get(i).containsKey(symbol)) {
                    return true;
                }
            }
        } else {
            for (int i = scopes.size() - 1; i >= funcScopeNum; --i) {
                if (scopes.get(i).containsKey(symbol)) {
                    return true;
                }
            }
        }
        return false;
    }
}
