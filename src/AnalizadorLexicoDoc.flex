/* Sección de declaraciones de JFlex */
%%
%public
%class AnalizadorLexicoDoc
%{
 
 /* Código personalizado */
 
 // Se agregó una propiedad para verificar si existen tokens pendientes
 private boolean _existenTokens = false;
 
 public boolean existenTokens(){
 return this._existenTokens;
 }
 
%}
 
 /* Al utilizar esta instrucción, se le indica a JFlex que devuelva objetos del tipo TokenPersonalizado */
%type TokenPersonalizado
 
%init{
 /* Código que se ejecutará en el constructor de la clase */
%init}
 
%eof{
 
 /* Código a ejecutar al finalizar el análisis, en este caso cambiaremos el valor de una variable bandera */
 this._existenTokens = false;
 
%eof}
 
/* Inicio de Expresiones regulares */
 
 Etiqueta = <[^>]+>|<\/[a-zA-Z]*>
 Digito = [0-9]
 Numero = {Digito} {Digito}*
 Articulo =  el | la | los | las | un | unos | una | unas | lo
 Contraccion = del | al
 Pronombre = ella | el | nosotros | nosotras | usted | ustedes
 Conjuncion = y | e | ni | mas | pero | aunque | sin embargo | sino | no obstante | empero | ya | sea | o | u | esto es | es decir | o sea | si | a condición de que | con tal de que | como | porque | pues | puesto que | dado que | pues que | ya que | tan | tal | luego | conque | así pues | aunque | a pesar de que | aun cuando | si bien | para que | a que | a fin de que | con objeto de | con la intención de que  
 Preposicion =  a | ante | bajo | con | de | desde | durante | en | entre | excepto | hacia | hasta | mediante | para | por | salvo | según | sin | sobre | tras
 Letra = \p{L}
 Termino = {Letra} {Letra}*
 Espacio = " "
 SaltoDeLinea = \n|\r|\r\n
 
 
/* Finaliza expresiones regulares */
 
%%
/* Finaliza la sección de declaraciones de JFlex */
 
/* Inicia sección de reglas */
 
// Cada regla está formada por una {expresión} espacio {código}
  
{Etiqueta} {
 TokenPersonalizado t = new TokenPersonalizado(yytext(), "Etiqueta");
 this._existenTokens = true;
 return t;
}

{Numero} {
 TokenPersonalizado t = new TokenPersonalizado(yytext(), "NUMERO");
 this._existenTokens = true;
 return t;
}

{Articulo} {
 TokenPersonalizado t = new TokenPersonalizado(yytext(), "Articulo");
 this._existenTokens = true;
 return t;
}

{Contraccion} {
 TokenPersonalizado t = new TokenPersonalizado(yytext(), "Contraccion");
 this._existenTokens = true;
 return t;
}

{Pronombre} {
 TokenPersonalizado t = new TokenPersonalizado(yytext(), "Pronombre");
 this._existenTokens = true;
 return t;
}

{Conjuncion} {
 TokenPersonalizado t = new TokenPersonalizado(yytext(), "Conjuncion");
 this._existenTokens = true;
 return t;
}

{Preposicion} {
 TokenPersonalizado t = new TokenPersonalizado(yytext(), "Preposicion");
 this._existenTokens = true;
 return t;
}
 
{Termino} {
 TokenPersonalizado t = new TokenPersonalizado(yytext(), "Termino");
 this._existenTokens = true;
 return t;
}

{Espacio} {
 // Ignorar cuando se ingrese un espacio
}
 
{SaltoDeLinea} {
}

[^] {TokenPersonalizado t = new TokenPersonalizado(yytext(), "Sin utilidad");
 this._existenTokens = true;
 return t;}