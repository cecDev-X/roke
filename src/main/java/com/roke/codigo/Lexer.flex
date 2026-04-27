package com.roke.codigo;
import java_cup.runtime.*;

%%
%class Lexer
%unicode
%cup
%line
%column
%public

%{
  private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
  }
  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline, yycolumn, value);
  }
%}

// --- MACROS
Letra = [a-zA-Z_]
Digito = [0-9]
Identificador = {Letra}({Letra}|{Digito})*
NumeroEntero = {Digito}+
NumeroDecimal = {Digito}+ \. {Digito}+
Espacio = [ \t\r\n]
ContenidoTxt = [^\"]*
ComentarioSimple = "#" [^\n\r]*
ComentarioMulti = "#" [^#]* "#"

%%
<YYINITIAL> {
    // --- PALABRAS RESERVADAS ---
    "numerin"  { return symbol(sym.TIPO_NUMERIN); }
    "duvalin"  { return symbol(sym.TIPO_DUVALIN); }
    "txt"      { return symbol(sym.TIPO_TXT); }
    "ver"      { return symbol(sym.VER); }

    // --- SÍMBOLOS ---
    "->"       { return symbol(sym.ASIGNACION); }
    "$"        { return symbol(sym.FIN_SENTENCIA); }
    
    // --- OPERADORES ---
    "+"        { return symbol(sym.MAS); }
    "-"        { return symbol(sym.MENOS); }
    "*"        { return symbol(sym.MULT); }
    "/"        { return symbol(sym.DIV); }

    // --- STRINGS ---
    "\"" {ContenidoTxt} "\"" { 
        return symbol(sym.VALOR_TXT, yytext().substring(1, yytext().length()-1)); 
    }

    // --- VARIABLES Y VALORES ---
    {NumeroEntero}  { return symbol(sym.VALOR_ENTERO, yytext()); }
    {NumeroDecimal} { return symbol(sym.VALOR_DECIMAL, yytext()); }
    {Identificador} { return symbol(sym.ID, yytext()); }

    // --- IGNORAR ---
    {ComentarioSimple} { /* Ignorar */ }
    {ComentarioMulti}  { /* Ignorar */ }
    {Espacio}          { /* Ignorar */ }

    // Error léxico
    .  { System.err.println("Error Léxico: Carácter no reconocido '" + yytext() + "' en línea " + (yyline+1)); }
}