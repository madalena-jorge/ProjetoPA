import org.junit.jupiter.api.DisplayName
import org.testng.Assert.assertEquals
import org.testng.AssertJUnit.assertFalse
import org.testng.AssertJUnit.assertTrue
import org.testng.annotations.Test
import java.io.File

@DisplayName("This is the test class")
class Tests {

    val document = Document(1.0, "UTF-8", Entity("plano",""))
    val curso = Entity("curso","Mestrado em Engenharia Informática")
    val fuc1 = Entity("fuc")
    val fuc2 = Entity("fuc", "")
    val codigo1 = Attribute("codigo", "M4310")
    val codigo2 = Attribute("codigo", "03782")
    val nome1 = Entity("nome", "Programação Avançada")
    val nome2 = Entity("nome", "Dissertação")
    val avaliacao1 = Entity("avaliacao", "")
    val componente1 = Entity("componente", "")
    val componente2 = Entity("componente", "")
    val nome11 = Attribute("nome", "Quizzes")
    val nome22 = Attribute("nome", "Projeto")
    val peso1 = Attribute("peso", "20")
    val peso2 = Attribute("peso", "80")

    @Test
    fun testRegex() {
        assertTrue("validName".matches(Regex("^[a-zA-Z_:][a-zA-Z0-9._:-]*$")))
        assertFalse("123invalid*".matches(Regex("^[a-zA-Z_:][a-zA-Z0-9._:-]*$")))
    }

    @Test
    fun testAddAttribute() {
        fuc1.addAttribute(codigo1)
        assertEquals(listOf(codigo1), fuc1.getAttributes())
    }

    @Test
    fun testRemoveAttribute() {
        fuc1.addAttribute(codigo1)
        fuc1.addAttribute(codigo2)
        assertEquals(listOf(codigo1, codigo2), fuc1.getAttributes())
        fuc1.removeAttribute(codigo2)
        assertEquals(listOf(codigo1), fuc1.getAttributes())
    }

    @Test
    fun testChangeAttributeBody() {
        fuc1.addAttribute(codigo1)
        assertEquals("M4310", codigo1.body)
        fuc1.changeAttributeBody(codigo1, "M4000")
        assertEquals("M4000", codigo1.body)
    }

    @Test
    fun testChangeAttributeName() {
        fuc1.addAttribute(codigo1)
        assertEquals("codigo", codigo1.getName())
        fuc1.changeAttributeName(codigo1, "code")
        assertEquals("code", codigo1.getName())
    }

    @Test
    fun testGetChildren() {
        document.addEntity(curso)
        assertEquals(listOf(curso), document.root_entity?.getChildren())
    }

    @Test
    fun testGetParent() {
        fuc1.addEntityChild(nome1)
        assertEquals(fuc1, nome1.getParent())
    }

    @Test
    fun testGetAttributes() {
        fuc1.addAttribute(codigo1)
        fuc1.addAttribute(codigo2)
        assertEquals(listOf(codigo1, codigo2), fuc1.getAttributes())
    }

    @Test
    fun testAddEntityChild() {
        fuc1.addEntityChild(nome1)
        assertEquals(listOf(nome1), fuc1.getChildren())
    }

    @Test
    fun testRemoveEntityChild() {
        fuc1.addEntityChild(nome1)
        fuc1.addEntityChild(nome2)
        assertEquals(listOf(nome1, nome2), fuc1.getChildren())
        fuc1.removeEntityChild(nome1)
        assertEquals(listOf(nome2), fuc1.getChildren())
    }

    @Test
    fun testAddEntity() {
        document.addEntity(curso)
        assertEquals(listOf(curso), document.root_entity?.getChildren())
    }

    @Test
    fun testRemoveEntity() {
        document.addEntity(fuc1)
        document.addEntity(fuc2)
        assertEquals(listOf(fuc1, fuc2), document.root_entity?.getChildren())
        document.removeEntity(fuc1)
        assertEquals(listOf(fuc2), document.root_entity?.getChildren())
    }

    @Test
    fun testAddAttributeGlobally() {
        document.addEntity(curso)
        document.addEntity(fuc1)
        document.addEntity(fuc2)
        document.root_entity?.addAttributeGlobally("fuc", "something", "M16")
        assertTrue(curso.getAttributes().isEmpty())
        assertEquals(listOf(Attribute("something", "M16")), fuc1.getAttributes())
        assertEquals(listOf(Attribute("something", "M16")), fuc2.getAttributes())
    }

    @Test
    fun testRenameEntityGlobally() {
        document.addEntity(curso)
        document.addEntity(fuc1)
        document.addEntity(fuc2)
        document.root_entity?.renameEntityGlobally("fuc", "something")
        assertEquals("curso", curso.getName())
        assertEquals("something", fuc1.getName())
        assertEquals("something", fuc2.getName())
    }

    @Test
    fun testRenameAttributeGlobally() {
        document.addEntity(fuc1)
        document.addEntity(fuc2)
        document.root_entity?.addAttributeGlobally("fuc", "something", "M16")
        document.root_entity?.renameAttributeGlobally("fuc", "something", "some")
        assertEquals(listOf(Attribute("some", "M16")), fuc1.getAttributes())
        assertEquals(listOf(Attribute("some", "M16")), fuc2.getAttributes())
    }

    @Test
    fun testRemoveEntityGlobally() {
        curso.addEntityChild(nome1)
        curso.addEntityChild(fuc1)
        curso.addEntityChild(fuc2)
        assertEquals(listOf(nome1, fuc1, fuc2), curso.getChildren())
        curso.removeEntityGlobally("fuc")
        assertEquals(listOf(nome1), curso.getChildren())
    }

    @Test
    fun testRemoveAttributeGlobally() {
        document.addEntity(fuc1)
        document.addEntity(fuc2)
        document.root_entity?.addAttributeGlobally("fuc", "something", "M16")
        assertEquals(listOf(Attribute("something", "M16")), fuc1.getAttributes())
        assertEquals(listOf(Attribute("something", "M16")), fuc2.getAttributes())
        document.root_entity?.removeAttributeGlobally("fuc", "something")
        assertTrue(fuc1.getAttributes().isEmpty())
        assertTrue(fuc2.getAttributes().isEmpty())
    }

    @Test
    fun testToXmlString() {
        val document = Document(1.0, "UTF-8", Entity("plano", ""))
        val curso = Entity("curso", "Mestrado em Engenharia Informática")
        val codigo1 = Attribute("codigo", "M4310")
        curso.addAttribute(codigo1)
        document.addEntity(curso)
        val expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<plano>\n  <curso codigo=\"M4310\">Mestrado em Engenharia Informática</curso>\n</plano>\n"
        val actualXml = document.toXMLString()
        assertEquals(expectedXml, actualXml)
    }

    @Test
    fun testWriteToFile() {
        curso.addAttribute(codigo1)
        document.addEntity(curso)
        val expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<plano>\n  <curso codigo=\"M4310\">Mestrado em Engenharia Informática</curso>\n</plano>\n"
        val file = File("tests/Test File.txt")
        document.writeToFile()
        val actualXML = file.readText()
        assertEquals(expectedXml, actualXML)
    }

    @Test
    fun testXPath() {
        document.addEntity(fuc1)
        fuc1.addEntityChild(avaliacao1)
        avaliacao1.addEntityChild(componente1)
        avaliacao1.addEntityChild(componente2)
        componente1.addAttribute(nome11)
        componente2.addAttribute(nome22)
        componente1.addAttribute(peso1)
        componente2.addAttribute(peso2)
        val result = document.XPath("fuc/avaliacao/componente")
        assertEquals(listOf(componente1, componente2), result)
    }

    @Test
    fun testCreateElementSimple() {
        val componente = ComponenteAvaliacao("Quizzes", 20)
        val entity = createElement(componente)
        assertEquals("componente", entity.getName())
        val attributes = entity.getAttributes()
        assertEquals(1, attributes.size)
        assertEquals("nome", attributes[0].getName())
        assertEquals("QUIZZES", attributes[0].body)
    }

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

    class AddPercentage: StringTransformer {
        override fun transform(value: String): String {
            return "$value%"
        }
    }

    class ToUpperCase: StringTransformer {
        override fun transform(value: String): String {
            return value.uppercase()
        }
    }

    class FUCAdapter: Adapter {
        override fun adapt(entity: Entity) {
            entity.addAttributeGlobally("componente", "test", "test")
        }
    }

    @Test
    fun testCreateElement() {
        val f = FUC("M4310", "Programação Avançada", 6.0, "Excelentes alunos",
            listOf(
                ComponenteAvaliacao("Quizzes", 20),
                ComponenteAvaliacao("Projeto", 80)
            )
        )
        val fucEntity = createElement(f)
        val actualXML = fucEntity.toXMLString()
        val expectedXML = """
    <fuc codigo="M4310">
      <nome>Programação Avançada</nome>
      <ects>6.0</ects>
      <avaliacao>
        <componente peso="20%" nome="QUIZZES" test="test"/>
        <componente peso="80%" nome="PROJETO" test="test"/>
      </avaliacao>
    </fuc>
    
    """.trimIndent()
        assertEquals(expectedXML, actualXML)
    }

    @Test
    fun testDSL() {
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
        val result = """
        <?xml version="1.0" encoding="UTF-8"?>
        <plano>
          <fuc codigo="M4310">
            <nome>Programação Avançada</nome>
            <ects>6.0</ects>
            <observacoes>la la...</observacoes>
            <avaliacao>
              <componente nome="Quizzes" peso="20%"/>
              <componente nome="Projeto" peso="80%"/>
            </avaliacao>
          </fuc>
        </plano>
        
        """.trimIndent()
        assertEquals(result, xmlStructure.toXMLString())
    }

}
