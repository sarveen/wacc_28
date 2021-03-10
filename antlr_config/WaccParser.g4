parser grammar WaccParser ;

options {
  tokenVocab= WaccLexer ;
}

prog: BEGIN (func)* stat END EOF ;

func: type IDENT OPEN_PARENTHESES (param_list)? CLOSE_PARENTHESES IS stat END ;

param_list: param (COMMA param)* ;

param: type IDENT ;

stat : SKIP_STMT #skip_stmt
  | type IDENT ASSIGN assign_rhs # var_declaration
  | assign_lhs ASSIGN assign_rhs # var_assignment
  | READ assign_lhs #read_stat
  | FREE_STMT expr #free_stat
  | RETURN expr  #return_stat
  | EXIT expr #exit_stat
  | PRINT expr #print_stat
  | PRINTLN expr #println_stat
  | IF expr THEN stat ELSE stat FI #if_stat
  | WHILE expr DO stat DONE #while_stat
  | FOR OPEN_PARENTHESES stat SEMICOLON expr SEMICOLON stat CLOSE_PARENTHESES DO stat DONE #for_stat
  | BEGIN stat END #scope_stat
  | stat SEMICOLON stat #seq_stmts ;

assign_lhs : ident
           | pair_elem
           | array_elem ;

assign_rhs : expr       #expr_rhs
            | SQUARE_OPEN (expr (COMMA expr)*)? SQUARE_CLOSE  #array_lit
            | NEWPAIR OPEN_PARENTHESES expr COMMA expr CLOSE_PARENTHESES #pair_init
            | pair_elem #pair_elem_rhs
            | CALL IDENT OPEN_PARENTHESES (arg_list)? CLOSE_PARENTHESES#func_call ;

arg_list: expr (COMMA expr)* ;

pair_elem: FST expr
         | SND expr ;

type: base_type    #base_t
    | type SQUARE_OPEN SQUARE_CLOSE #array_t
    | PAIR OPEN_PARENTHESES pair_elem_type COMMA pair_elem_type CLOSE_PARENTHESES  #pair_t;

base_type :
    INT #int_t
    | BOOL #bool_t
    | CHAR #char_t
    | STRING #string_t ;

pair_elem_type:
    PAIR  #pair
    | type SQUARE_OPEN SQUARE_CLOSE #array_type
    | base_type #base ;


expr:  int_lit    #int_literal
     | bool_lit   #bool_literal
     | CHARACTER_LIT    #char_literal
     | STRING_LIT       #string_literal
     | pair_lit         #pair_literal
     | ident            #ident_expr
     | array_elem       #array_element
     | OPEN_PARENTHESES expr CLOSE_PARENTHESES #nested_expr
     | unary_oper expr  #unOp_expr
     | expr (MULT | DIV | MOD) expr #binOp_expr
     | expr (PLUS | MINUS) expr #binOp_expr
     | expr (GT | GEQ | LT | LEQ) expr #binOp_expr
     | expr (EQ | NEQ) expr #binOp_expr
     | expr (AND | OR) expr #binOp_expr ;

ident: IDENT ;

unary_oper: MINUS
            | NOT
            | LEN
            | ORD
            | CHR  ;

array_elem: IDENT (SQUARE_OPEN expr SQUARE_CLOSE)+ ;

int_lit: int_sign? INTEGER ;

int_sign: PLUS | MINUS ;

bool_lit: TRUE | FALSE ;

pair_lit: NULL ;
