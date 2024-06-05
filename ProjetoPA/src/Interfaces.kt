interface Visitor {
    fun visit(entity: Entity): Boolean = true
    fun endVisit(entity: Entity) { }
    fun visit(attribute: Attribute) { }
}

abstract class XML {
    abstract fun accept(visitor: Visitor)
}

interface StringTransformer {
    fun transform(value: String): String
}

interface Adapter {
    fun adapt(entity: Entity)
}
