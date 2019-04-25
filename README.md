# AnalizadorLexico

[Análisis Léxico usando Jflex](https://jonathanbucaro.com/2015/04/26/analisis-lexico-usando-jflex/)

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
