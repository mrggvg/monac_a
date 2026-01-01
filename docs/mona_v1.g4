grammar mona_v1;

// ---------- Parser ----------

program : function* EOF ;
function : type IDENTIFIER LPAREN parameters? RPAREN compound_statement ;
parameters : parameter (COMMA parameter)* ;
parameter : type IDENTIFIER ;

block_item : var_declaration | statement ;

statement
    : compound_statement
    | expression_statement
    | selection_statement
    | iteration_statement
    | jump_statement
    ;

compound_statement : LBRACE block_item* RBRACE ;
selection_statement : IF LPAREN expression RPAREN statement (ELSE statement)? ;
expression_statement: expression? SEMICOLON ;
iteration_statement : WHILE LPAREN expression RPAREN statement ;

jump_statement
    : BREAK SEMICOLON
    | RETURN expression? SEMICOLON
    ;

var_declaration : type IDENTIFIER ASSIGN expression SEMICOLON ;


// Expressions

expression
    : assignment_expression
    | equality_expression
    ;

assignment_expression : IDENTIFIER ASSIGN expression ;
equality_expression : relational_expression ((EQ | NEQ) relational_expression)* ;
relational_expression : additive_expression ((LE | GE | LT | GT) additive_expression)* ;
additive_expression : multiplicative_expression ((ADD | SUB) multiplicative_expression)* ;
multiplicative_expression : unary_expression ((MUL | DIV | MOD) unary_expression)* ;
unary_expression : SUB unary_expression | primary_expression ;

primary_expression
    : IDENTIFIER (LPAREN arguments? RPAREN)? // identifier, function call
    | CONSTANT
    | LPAREN expression RPAREN
    ;

arguments : expression (COMMA expression)* ;

type : VOID | BYTE | WORD ;


// ---------- Lexer ----------


// Keywords
RETURN  : 'return' ;
IF      : 'if' ;
ELSE    : 'else' ;
WHILE   : 'while' ;
BREAK   : 'break' ;

// Types
VOID    : 'void' ;
BYTE    : 'byte' ;
WORD    : 'word' ;


// Operators
ASSIGN  : '='   ;     // assignment

ADD     : '+'   ;     // addition
SUB     : '-'   ;     // subtraction

MUL     : '*'   ;     // multiplication
DIV     : '/'   ;     // division
MOD     : '%'   ;     // modulo

EQ      : '=='  ;
NEQ     : '!='  ;

LE      : '<='  ;
GE      : '>='  ;
LT      : '<'   ;
GT      : '>'   ;

// Others
SEMICOLON   : ';' ;
LPAREN      : '(' ;
RPAREN      : ')' ;
LBRACE      : '{' ;
RBRACE      : '}' ;
COMMA       : ',' ;

CONSTANT    : '0' | [1-9][0-9]* ;
IDENTIFIER  : [a-z][a-z0-9_]* ;

LINE_COMMENT    : '//' ~[\r\n]* -> skip ;
BLOCK_COMMENT   : '/*' .*? '*/' -> skip ;
WHITE_SPACE     : [ \t\r\n\f]+ -> skip ;