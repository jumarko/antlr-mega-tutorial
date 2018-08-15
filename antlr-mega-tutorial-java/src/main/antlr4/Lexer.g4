grammar Lexer;
/*
 * Parser rules
 */

 operation : NUMER '+' NUMBER ;

 /*
  * Lexer rules
  */

 NUMBER : [0-9]+ ;
 
 WHITESPACE : ' ' -> skip ;
