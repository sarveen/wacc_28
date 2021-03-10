lexer grammar WaccLexer ;

// scope
BEGIN: 'begin' ;
END: 'end' ;

// function
IS: 'is' ;
CALL: 'call' ;

// statements
SKIP_STMT: 'skip' ;
READ: 'read' ;
FREE_STMT: 'free' ;
RETURN: 'return' ;
EXIT: 'exit' ;
PRINT: 'print' ;
PRINTLN: 'println';
IF: 'if' ;
THEN: 'then' ;
ELSE: 'else' ;
FI: 'fi' ;
FOR: 'for' ;
WHILE: 'while' ;
DO: 'do' ;
DONE: 'done' ;

// unary operators
NOT: '!' ;
LEN: 'len' ;
ORD: 'ord' ;
CHR: 'chr' ;

// binary operators
PLUS: '+' ;
MINUS: '-' ;
MULT: '*' ;
DIV: '/' ;
MOD: '%' ;
GT: '>' ;
LT: '<' ;
LEQ: '<=' ;
GEQ: '>=' ;
EQ: '==' ;
NEQ: '!=' ;
AND: '&&' ;
OR: '||' ;

// types
INT: 'int' ;
BOOL: 'bool' ;
CHAR: 'char' ;
STRING: 'string' ;

// punctuation
OPEN_PARENTHESES: '(' ;
CLOSE_PARENTHESES: ')' ;
COMMA: ',' ;
ASSIGN: '=' ;
SEMICOLON: ';' ;
SQUARE_OPEN: '[' ;
SQUARE_CLOSE: ']' ;
SINGLE_QUOTE: '\'' ;
DOUBLE_QUOTE: '"' ;

// numbers
fragment DIGIT: '0'..'9' ;
INTEGER: DIGIT+ ;

// pair
FST: 'fst' ;
SND: 'snd' ;
PAIR: 'pair' ;
NEWPAIR: 'newpair' ;
NULL: 'null' ;

// booleans
TRUE: 'true' ;
FALSE: 'false' ;

// escape
EOL: '\r'? '\n' -> skip ;
SKIPPED: (' ' | '\t') -> skip ;

// comment
COMMENT: '#' ~[\r\n]* '\r'? '\n' -> skip ;

// character - ASCII char except \,',"
fragment ALPHABET: [a-zA-Z] ;
fragment UNDERSCORE: '_' ;
fragment CHARACTER: ~('\'' | '"' | '\\') | '\\' ESCAPED_CHAR ;
fragment ESCAPED_CHAR:   ('0' | 'b' | 't' | 'n' | 'f' | 'r'
                | '"' | '\'' | '\\') ;

// literals
CHARACTER_LIT: SINGLE_QUOTE CHARACTER SINGLE_QUOTE ;
STRING_LIT: DOUBLE_QUOTE (CHARACTER)* DOUBLE_QUOTE ;

// ident
IDENT: (UNDERSCORE | ALPHABET) (UNDERSCORE | ALPHABET | DIGIT)* ;
