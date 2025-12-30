grammar mona;

program : function* EOF ;
function : type IDENTIFIER '(' params? ')' block ;
params : param (',' param)* ;
param : type IDENTIFIER ;
block : '{' stmt* '}' ;

stmt
  : varDecl ';'
  | assign ';'
  | returnStmt ';'
  | expr ';'              // call like foo(); allowed
  | block                 // nested blocks
  ;

varDecl
  : type IDENTIFIER ('=' expr)?
  ;

assign
  : IDENTIFIER '=' expr
  ;

returnStmt
  : 'return' expr?
  ;

// Expression with precedence (ANTLR4 direct left recursion)
expr
  : expr '*' expr         # Mul
  | expr '/' expr         # Div
  | expr '+' expr         # Add
  | expr '-' expr         # Sub
  | '&' expr              # AddressOf
  | '*' expr              # Deref
  | IDENTIFIER '(' args? ')' # Call
  | IDENTIFIER            # Var
  | NUMBER                # IntLit
  | '(' expr ')'          # Parens
  ;

args
  : expr (',' expr)*
  ;


// allow multiple pointer levels
type : ('void' | 'u8' | 'u16') ('*')* ;


NUMBER : [0-9]+ ;
IDENTIFIER : [a-zA-Z_][a-zA-Z_0-9]* ;

LINE_COMMENT : '//' ~[\r\n]* -> skip ;
BLOCK_COMMENT: '/*' .*? '*/' -> skip ;

WS : [ \t\r\n\f]+ -> skip ;