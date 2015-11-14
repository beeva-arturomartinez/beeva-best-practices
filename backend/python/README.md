# Guía de buenas prácticas en Python

## Índice

### 1. Introducción
### 2. Python Zen (Pep 20)
### 3. Guia de estilo (Pep 8)
#### 3.1. Indentation
Use 4 spaces per indentation level.

#### 3.2. Tabs or Spaces?

Spaces are the preferred indentation method.
Tabs should be used solely to remain consistent with code that is already indented with tabs.

#### 3.3. Maximum Line Length
Limit all lines to a maximum of 79 characters.

with open('/path/to/some/file/you/want/to/read') as file_1, \
     open('/path/to/some/file/being/written', 'w') as file_2:
    file_2.write(file_1.read())

Make sure to indent the continued line appropriately. The preferred place to break around a binary operator is after the operator, not before it. Some examples:

class Rectangle(Blob):

    def __init__(self, width, height,
                 color='black', emphasis=None, highlight=0):
        if (width == 0 and height == 0 and
                color == 'red' and emphasis == 'strong' or
                highlight > 100):
            raise ValueError("sorry, you lose")
        if width == 0 and height == 0 and (color == 'red' or
                                           emphasis is None):
            raise ValueError("I don't think so -- values are %s, %s" %
                             (width, height))
        Blob.__init__(self, width, height,
                      color, emphasis, highlight)
### 4. Python 2 vs Python 3
### 5. Librería estándar
### 6. Importar librerías
### 7. Logging
### 8. Comentarios y documentación
#### 8.1. De una sola línea
#### 8.2. En bloque
#### 8.3. Comentarios  
### 9 Tratamiento de Strings
### 10. Operadores
#### 10.1. Asignación   
#### 10.2. Operadores aritméticos   
#### 10.3. Operadores lógicos  
#### 10.4. Condicionales  
#### 10.5. Bucles 
### 11. Funciones
#### 11.1. Uso de las funciones  
#### 11.2. Lambda
#### 11.3. Decoradores    
### 12. Paradigmas de programación
#### 12.1. Tipo de variables   
#### 12.2. Herencia
#### 12.3. Sobrecarga de funciones   
#### 12.4. Getters y setters  
#### 12.5. Patrones de diseño 
#### OOP, estructura, funcional, ...  
### 13. Excepciones
### 14. E/S
### 15. Ficheros de configuración
### 16. Testing
#### 16.1. Tests
#### 16.2. Librerías útiles 
#### 16.3. Mocks
### 17. Estructura típica de proyecto
### 18. Empaquetado y distribución de aplicaciones
### 19. Entornos de desarrollo (IDEs)
### 20. Gestión de librerías y entornos virtuales
#### 20.1. pip   
#### 20.2. virtualenv 
### 21. Integración continua --> solo si da tiempo
### 23. Bibliografía  


[BEEVA](http://www.beeva.com) | 2015
