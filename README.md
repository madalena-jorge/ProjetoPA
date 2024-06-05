# Goal of the project
In this project, we tried to create a tool that would manage and **manipulate XML in Kotlin**, which was the language covered in class.

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

```kotlin

```
