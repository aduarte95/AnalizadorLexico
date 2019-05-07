# AnalizadorLexico

[Análisis Léxico usando Jflex](https://jonathanbucaro.com/2015/04/26/analisis-lexico-usando-jflex/)

## TODO

* [X] Aceptar múltiples archivos.
* [X] Salida de un archivo por cada archivo analizado.
* [ ] Salida de un solo archivo con todos los términos de los archivos anteriores.

  ### Reglas 
* [ ] Regla para signos de puntuación: ¿?¡!.,;:‘,{}, (), []-, _<,>,=,+,-,*,/,&,^,%,$,#,@
* [ ] Rango de números de 0 y 3000.
* [ ] Arreglar regla sobre acentuación.
* [X] Artículos
* [X] Pronombres personales.
* [X] Proposiciones
* [X] Conjuciones
* [X] Contracciones
* [X] Etiquetas html
* [ ] Script (<script>.*</script>)
* [ ] Aceptar abreviaciones de fechas (tiene que ir antes de eliminar los signos de puntuación)

## Requisitos

[Instalar jflex](https://jonathanbucaro.com/2015/04/11/instalacion-jflex/)

## 1. Compilar archivos .flex

```
jflex [Archivo.flex]
```

## 2. Compilar main

```
javac Main.java
```

## 3. Correr el programa

Correr con el archivo prueba.txt con opción por defecto tipo documento
```
java Main
```
Correr cualquier archivo con opción por defecto tipo documento
```
java Main [documento]
```
Correr con archivo con opción tipo consulta
```
java Main [documento] -c
```
