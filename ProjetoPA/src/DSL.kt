fun document(version: Double, encoding: String, name: String, nestedText: String = "", build: Document.() -> Unit): Document =
    Document(version, encoding, Entity(name, nestedText)).apply { build() }

fun Document.entity(name: String, nestedText: String = "", build: Entity.() -> Unit): Entity {
    val entity = Entity(name, nestedText)
    addEntity(entity)
    entity.build()
    return entity
}

fun Entity.entity(name: String, nestedText: String = "", build: Entity.() -> Unit = {}): Entity =
    Entity(name, nestedText, this).apply { build() }

fun Entity.attribute(name: String, value: String) {
    addAttribute(Attribute(name, value))
}

operator fun Entity.get(name: String): Entity? =
    getChildren().find { it.getName() == name }

operator fun Entity.invoke(build: Entity.() -> Unit) {
    build()
}

infix fun Entity.add(entity: Entity) {
    addEntityChild(entity)
}
