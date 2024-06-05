import kotlin.reflect.full.createInstance
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation

fun createElement(obj: Any): Entity {
    val clazz = obj::class
    val properties = clazz.declaredMemberProperties.sortedBy { it.findAnnotation<Order>()?.value?: Int.MAX_VALUE }.map { Pair(it.name, it) }
    val entity = Entity(clazz.findAnnotation<ClassName>()!!.name)
    properties.forEach {(name, prop) ->
        if (prop.findAnnotation<Ignore>() != null) {
            return@forEach
        }
        val value = prop.call(obj)
        if (prop.findAnnotation<Attributes>() != null) {
            val transformedValue = value?.let {
                val transformerAnnotation = prop.findAnnotation<XMLString>()
                if (transformerAnnotation != null) {
                    val transformer = transformerAnnotation.transformer.objectInstance?: transformerAnnotation.transformer.createInstance()
                    transformer.transform(value.toString())
                } else {
                    value.toString()
                }
            }
            transformedValue?.let {
                entity.addAttribute(Attribute(name.lowercase(), it))
            }
        } else if (prop.findAnnotation<Entitys>() != null) {
            val entityChild = Entity(name.lowercase(), value.toString())
            (value as? List<*>)?.filterIsInstance<Any>()?.forEach { item ->
                entityChild.addEntityChild(createElement(item))
            }
            entity.addEntityChild(entityChild)
        }
    }
    clazz.findAnnotation<XMLAdapter>()?.let { annotation ->
        val adapter = annotation.adapter.objectInstance?: annotation.adapter.createInstance()
        adapter.adapt(entity)
    }
    return entity
}
