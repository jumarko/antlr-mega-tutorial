parser grammar MarkupParser;

options { tokenVocab=MarkupLexer ; }

file : element*;

element : (content | tag) ;

tag : '[' ID attribute? ']' element* '[' '/' ID ']' ;

attribute : ID '=' STRING ;

content : TEXT ;

