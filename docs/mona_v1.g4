grammar mona_v1;

function : type Identifier '(' parameters? ')' '{' ('return' add_expr ';')* '}' ;

parameters : parameter (',' parameters)* ;
parameter : type Identifier ;

type : 'void' | 'byte' | 'word' ;

add_expr : mul_expr (('+'|'-') add_expr)* ;
mul_expr : primary (('*'|'/'|'%') mul_expr)* ;
primary
    : Identifier
    | Constant
    | '(' add_expr ')'
    | Identifier '(' primary? (',' primary)* ')'
    ;

// Lexer tokens
Constant : '0' | [1-9][0-9]* ;
Identifier : [a-z][a-z0-9_]* ;

// Skipping
LineComment : '//' ~[\r\n]* -> skip ;
BlockComment : '/*' .*? '*/' -> skip ;
Ws : [ \t\r\n\f]+ -> skip ;