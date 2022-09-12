package balcilar.orcun

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ArrayNode
import com.jayway.jsonpath.Configuration
import com.jayway.jsonpath.DocumentContext
import com.jayway.jsonpath.JsonPath
import com.jayway.jsonpath.Option
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider
import groovy.transform.CompileStatic

@CompileStatic
class JsonReader {

    private static final Option ALWAYS_RETURN_LIST = Option.ALWAYS_RETURN_LIST
    private static final Configuration OBJECT_CONFIGURATION = Configuration.builder().options(ALWAYS_RETURN_LIST).build()
    private static final Configuration JACKSON_JSON_NODE_CONFIGURATION = Configuration.builder().mappingProvider(new JacksonMappingProvider()).jsonProvider(new JacksonJsonNodeJsonProvider()).options(ALWAYS_RETURN_LIST).build()

    static def readValue(String json, String path) {
        List values = readValuesAsList(json, path)
        return (values.size() == 1) ? values[0] : values
    }

    static List readValuesAsList(String json, String path) { parseValues(json).read(path) }

    static JsonNode readJsonNode(String json, String path) {
        ArrayNode nodes = readJsonNodesAsList(json, path)
        return (nodes.size() == 1) ? nodes[0] : nodes
    }

    static ArrayNode readJsonNodesAsList(String json, String path) { parseJsonNodes(json).read(path) }

    private static DocumentContext parseValues(String json) { JsonPath.parse(json, OBJECT_CONFIGURATION) }

    private static DocumentContext parseJsonNodes(String json) { JsonPath.parse(json, JACKSON_JSON_NODE_CONFIGURATION) }

}
