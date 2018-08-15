grammar Chat;

/*
 * Parser rules
 */

chat : line+ EOF ;

line : name command message NEWLINE;

name : WORD WHITESPACE;

command : (SAYS | SHOUTS) ':' WHITESPACE ;

message : (emoticon | link | color | mention | WORD | WHITESPACE)+ ;

emoticon : ':' '-'? ')'
         | ':' '-'? '('
         ;

link : '[' LINKTEXT  ']' '(' LINKTEXT ')';

color : '/' WORD '/' message '/' ;

mention : '@' WORD ;


/*
 * Lexer rules
 */
fragment A : ('A'|'a') ;
fragment S : ('S'|'s') ;
fragment Y : ('Y'|'y') ;
fragment H : ('H'|'h') ;
fragment O : ('O'|'o') ;
fragment U : ('U'|'u') ;
fragment T : ('T'|'t') ;

fragment LOWERCASE: [a-z]+ ;
fragment UPPERCASE: [A-Z]+ ;

SAYS : S A Y S ;

SHOUTS : S H O U T S;

LINKTEXT : {_input.LA(-1) == '[' || _input.LA(-1) == '('}?  ~[\])]+  ;

WORD : (LOWERCASE | UPPERCASE | '_')+ ;

WHITESPACE : (' ' | '\t');

NEWLINE : ('\r'? '\n' | '\r')+;

