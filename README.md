# AnalizadorLexico

[Análisis Léxico usando Jflex](https://jonathanbucaro.com/2015/04/26/analisis-lexico-usando-jflex/)

## TODO

* [X] Aceptar múltiples archivos.
* [X] Salida de un archivo por cada archivo analizado con extensión .tok
     * [ ] Término: 30
     * [ ] Frecuencia: 12
     * [ ] Frecuencia normalizada: 20
* [ ] Salida de un solo archivo con todos los términos de los archivos anteriores llamado "Vocabulario"
     * [ ] Término: 30
     * [ ] Documentos donde aparece: 12
     * [ ] Frecuencia inversa: 20

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

  ### Entrega
  
     * [ ] Código fuente del programa
     * [ ] Ejecutable
     * [ ] Colección de documentos usada
     * [ ] Archivos obtenidos: URLS, .tok y Vocabulario. 
     * [ ] Documentación

  ### Documentación

     * [ ] Descripción general del programa.
     * [ ] Los pasos a seguir al realizar esta II Etapa.
     * [ ] Las reglas utilizadas para formatear los documentos y la explicación del propósito de cada una, la justificación de agregar o eliminar alguna regla.
     * [ ] Los formatos de salida de los archivos, la justificación de cambios hechos al formato establecido.
     * [ ] La descripción de cada una de las clases, las variables y los métodos utilizados en cada clase (UML).
     * [ ] Los problemas surgidos, tanto resueltos como no resueltos, soluciones y mejoras para la siguiente etapa.
     * [ ] La división de trabajo.

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
