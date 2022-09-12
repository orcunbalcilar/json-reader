package balcilar.orcun

import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.databind.node.TextNode
import org.testng.annotations.Test

class JsonReader_Test {

    private final String json = JsonReader_Test.getResourceAsStream('/sample-json.json').text

    @Test
    void always_return_list() {
        List expensiveValues = JsonReader.readValuesAsList(json, '$.expensive')
        assert expensiveValues == [10] // You can use asList if you are not sure how many values there are in the json.
        ArrayNode expensiveNodes = JsonReader.readJsonNodesAsList(json, '$.expensive')
        assert expensiveNodes instanceof ArrayNode && expensiveValues.size() == 1
    }

    @Test
    void multiple_values() {
        List categories = JsonReader.readValue(json, '$.store.book[*].category')
        assert categories == ['reference', 'fiction', 'fiction', 'fiction']
    }

    @Test
    void relative_path_returns_value_instead_of_list() {
        String color = JsonReader.readValue(json, '$..bicycle.color') // relative path
        assert color == 'red'
        assert color == JsonReader.readValue(json, '$.store.bicycle.color') // absolute path
    }

    @Test
    void reads_different_node_types() {
        def color = JsonReader.readJsonNode(json, '$.store.bicycle.color')
        assert color instanceof TextNode
        ObjectNode bicycle = JsonReader.readJsonNode(json, '$.store.bicycle')
        assert bicycle instanceof ObjectNode
        assert JsonReader.readJsonNode(json, '$..store.bicycle') instanceof ObjectNode // it may be a relative path.
    }

}
