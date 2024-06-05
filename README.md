# Goal of the project
In this project, we tried to create a tool that would manage and **manipulate XML in Kotlin**, which was the language covered in class. The main classes are Document, Entity, and Attribute.
The structure you will find in this project is as follows:

ProjetoPA/
�
+-- src/
�   +-- Annotations.kt
�   +-- Attribute.kt
�   +-- Document.kt
�   +-- DSL.kt
�   +-- Entity.kt
�   +-- Interfaces.kt
�   +-- main.kt
�   +-- Mapping.kt
+-- tests/
    +-- Test File.txt
    +-- Tests.kt

# Some features of the project
Over the last few weeks, we've been following a statement proposed by the teacher. 
The list below shows the points that are present and functional in our project:

## Phase 1 - Model
Here we start by implementing some basic XML manipulation
- [x] Adding and removing Entities
- [x] Add, remove, and change Attributes in Entities
- [x] Access the parent entity and child entities of an Entity
- [x] Pretty Print in String format, and write to file
- [x] Visitor
- [x] Add attributes globally to the document
- [x] Renaming entities globally in the document
  - [x] Providing entity name, and old and new attribute name
  - [x] Providing name
- [x] Removing attributes globally from the document
  - [x] Providing entity name, and attribute name

- [x] Micro-XPath
After implementing the basic manipulation, we moved on to the creation of Micro-XPath, which provides the entities of a received path.

## Phase 2 - Class Mapping - XML
At this stage, the goal was already to have some automation of the tool to obtain XML entities from objects.
- [x] Change identifier names in the XML translation
- [x] Determine how object attributes are translated (XML attribute or entity)
- [x] Delete object attributes

## Phase 3 - Internal DSL
- [x] **(Extra)** Provide an API that facilitates the instantiation of XML models via an internal DSL in Kotlin

# Examples
Most of the features in this document have been tested. These tests can be found in the [tests folder](https://github.com/madalena-jorge/ProjetoPA/tree/main/ProjetoPA/tests)

The tests cover document creation, entity manipulation, object mapping as well as all the functions found in the project. You can check it in the [Tests.kt](https://github.com/madalena-jorge/ProjetoPA/blob/main/ProjetoPA/tests/Tests.kt) file.

## Create an XML Document 

```
val document = Document(1.0, "UTF-8", Entity("plano",""))
val curso = Entity("curso","Mestrado em Engenharia Informática")
val fuc1 = Entity("fuc", "")
val fuc2 = Entity("fuc", "")
val codigo1 = Attribute("codigo", "M4310")
val codigo2 = Attribute("codigo", "03782")
val nome1 = Entity("nome", "Programação Avançada")
val nome2 = Entity("nome", "Dissertação")
val ects1 = Entity("ects", "6.0")
val ects2 = Entity("ects", "42.0")
val avaliacao1 = Entity("avaliacao", "")
val avaliacao2 = Entity("avaliacao", "")
val componente1 = Entity("componente", "")
val componente2 = Entity("componente", "")
val componente3 = Entity("componente", "")
val componente4 = Entity("componente", "")
val componente5 = Entity("componente", "")
val nome11 = Attribute("nome", "Quizzes")
val nome22 = Attribute("nome", "Projeto")
val nome33 = Attribute("nome", "Dissertação")
val nome44 = Attribute("nome", "Apresentação")
val nome55 = Attribute("nome", "Discussão")
val peso1 = Attribute("peso", "20")
val peso2 = Attribute("peso", "80")
val peso3 = Attribute("peso", "60")

fun main() {
    fuc1.addAttribute(codigo1)
    fuc2.addAttribute(codigo2)
    componente1.addAttribute(nome11)
    componente2.addAttribute(nome22)
    componente3.addAttribute(nome33)
    componente4.addAttribute(nome44)
    componente5.addAttribute(nome55)
    componente1.addAttribute(peso1)
    componente2.addAttribute(peso2)
    componente3.addAttribute(peso3)
    componente4.addAttribute(peso1)
    componente5.addAttribute(peso1)
    avaliacao1.addEntityChild(componente1)
    avaliacao1.addEntityChild(componente2)
    avaliacao2.addEntityChild(componente3)
    avaliacao2.addEntityChild(componente4)
    avaliacao2.addEntityChild(componente5)
    fuc1.addEntityChild(nome1)
    fuc2.addEntityChild(nome2)
    fuc1.addEntityChild(ects1)
    fuc2.addEntityChild(ects2)
    fuc1.addEntityChild(avaliacao1)
    fuc2.addEntityChild(avaliacao2)
    document.addEntity(curso)
    document.addEntity(fuc1)
    document.addEntity(fuc2)
    document.writeToFile("XML File.txt")
    println(document.toXMLString())
}
```

After generating this code, we would expect output similar to the following
```
<?xml version="1.0" encoding="UTF-8"?>
<plano>
  <curso>Mestrado em Engenharia Informática</curso>
  <fuc codigo="M4310">
    <nome>Programação Avançada</nome>
    <ects>6.0</ects>
    <avaliacao>
      <componente nome="Quizzes" peso="20"/>
      <componente nome="Projeto" peso="80"/>
    </avaliacao>
  </fuc>
  <fuc codigo="03782">
    <nome>Dissertação</nome>
    <ects>42.0</ects>
    <avaliacao>
      <componente nome="Dissertação" peso="60"/>
      <componente nome="Apresentação" peso="20"/>
      <componente nome="Discussão" peso="20"/>
    </avaliacao>
  </fuc>
</plano>
```

## Manipulating Entities
In this part we can add, remove and modify as described above.
```
val novaEntidade = Entity("novaEntidade")
doc.addEntity(novaEntidade)
doc.removeEntity("novaEntidade")
fuc.addAttribute("novaAtributo", "valor")
```

## Simple XPath 
```
val results = doc.query("fuc/avaliacao/componente")
results.forEach { println(it.prettyPrint()) }
```

## Mapping Classes - XML
```
@ClassName("componente")
data class ComponenteAvaliacao(
    @Attributes
    @Order(2)
    @XMLString(ToUpperCase::class)
    val nome: String,
    @Attributes
    @Order(1)
    @XMLString(AddPercentage::class)
    val peso: Int
)

@ClassName("fuc")
@XMLAdapter(FUCAdapter::class)
data class FUC(
    @Attributes
    val codigo: String,
    @Entitys
    @Order(1)
    val nome: String,
    @Entitys
    @Order(2)
    val ects: Double,
    @Entitys
    @Order(5)
    @Ignore
    val observacoes: String,
    @Entitys
    @Order(3)
    val avaliacao: List<ComponenteAvaliacao>
)

val f = FUC("M4310", "Programação Avançada", 6.0, "Excelentes alunos",
            listOf(
                ComponenteAvaliacao("Quizzes", 20),
                ComponenteAvaliacao("Projeto", 80)
            )
        )
val fucEntity = createElement(f)
println(fucEntity.toXMLString())
```

## Internal DSL 
```
val xmlStructure = document(1.0, "UTF-8", "plano") {
            entity("fuc") {
                attribute("codigo", "M4310")
                entity("nome", "Programação Avançada")
                entity("ects", "6.0")
                entity("observacoes", "la la...")
                entity("avaliacao") {
                    entity("componente") {
                        attribute("nome", "Quizzes")
                        attribute("peso", "20%")
                    }
                    entity("componente") {
                        attribute("nome", "Projeto")
                        attribute("peso", "80%")
                    }
                }
            }
        }
println(xmlStructure.toXMLString())
```
