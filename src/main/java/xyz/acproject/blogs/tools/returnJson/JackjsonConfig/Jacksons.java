package xyz.acproject.blogs.tools.returnJson.JackjsonConfig;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import java.util.List;
import java.util.Map;

public class Jacksons {
	private ObjectMapper objectMapper;

    public Jacksons(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Jacksons filter(String filterName, String... properties) {
        FilterProvider filterProvider = (new SimpleFilterProvider()).addFilter(filterName, SimpleBeanPropertyFilter.serializeAllExcept(properties));
        this.objectMapper.setFilterProvider(filterProvider);
        return this;
    }

    public <T> T json2Obj(String json, Class<T> clazz) {
        try {
            return this.objectMapper.readValue(json, clazz);
        } catch (Exception var4) {
            var4.printStackTrace();
            throw new RuntimeException("解析json错误");
        }
    }

    public <T> T node2Obj(JsonNode node, Class<T> clazz) {
        try {
            return this.objectMapper.readValue(new TreeTraversingParser(node), clazz);
        } catch (Exception var4) {
            var4.printStackTrace();
            throw new RuntimeException("解析json错误");
        }
    }

    public JsonNode json2Node(String json) {
        try {
            return this.objectMapper.readTree(json);
        } catch (Exception var3) {
            var3.printStackTrace();
            throw new RuntimeException("解析json错误");
        }
    }

    public <T> T node2Obj(JsonNode node, TypeReference<T> typeReference) {
        try {
            return this.objectMapper.readValue(new TreeTraversingParser(node), typeReference);
        } catch (Exception var4) {
            var4.printStackTrace();
            throw new RuntimeException("解析json错误");
        }
    }

    public <T> T json2Obj(String json, TypeReference<T> typeReference) {
        try {
            return this.objectMapper.readValue(json, typeReference);
        } catch (Exception var4) {
            var4.printStackTrace();
            throw new RuntimeException("解析json错误");
        }
    }

    public String readAsString(Object obj) {
        try {
            return this.objectMapper.writeValueAsString(obj);
        } catch (Exception var3) {
            var3.printStackTrace();
            throw new RuntimeException("解析对象错误");
        }
    }

    @SuppressWarnings(value ={ "unchecked", "rawtypes" })
	public List<Map<String, Object>> json2List(String json) {
        try {
            return (List)this.objectMapper.readValue(json, List.class);
        } catch (Exception var3) {
            var3.printStackTrace();
            throw new RuntimeException("解析json错误");
        }
    }

    public ObjectNode createNode() {
        return this.objectMapper.createObjectNode();
    }

    public ArrayNode createArrayNode() {
        return this.objectMapper.createArrayNode();
    }
}
