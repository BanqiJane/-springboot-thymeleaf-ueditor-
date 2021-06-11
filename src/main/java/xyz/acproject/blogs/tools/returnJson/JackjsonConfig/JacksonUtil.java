package xyz.acproject.blogs.tools.returnJson.JackjsonConfig;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;


public class JacksonUtil {
	@SuppressWarnings(value={ "unchecked", "rawtypes" })
	private static final Map<String, Jacksons> jacksonsMap = new HashMap();
	@SuppressWarnings(value={ "unchecked", "rawtypes" })
    private static final Map<String, Jacksons> newJacksonsMap = new HashMap();

    public JacksonUtil() {
    }

    private static ObjectMapper getMapper() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    }

    public static Jacksons getReader() throws Exception {
        Jacksons jacksons = (Jacksons)jacksonsMap.get("reader");
        if(jacksons == null) {
            ObjectMapper mapper = getMapper();
            mapper.setDateFormat(new SimpleDateFormat(JacksonUtil.TimeType.yyyyMMddHHmmss.toString()));
            jacksons = new Jacksons(mapper);
            jacksonsMap.put("reader", jacksons);
        }

        return jacksons;
    }

    public static Jacksons get() throws Exception {
        Jacksons jacksons = (Jacksons)jacksonsMap.get("tsp");
        if(jacksons == null) {
            ObjectMapper mapper = getMapper();
            mapper.configure(MapperFeature.USE_ANNOTATIONS, false);
            mapper.setDateFormat(new SimpleDateFormat(JacksonUtil.TimeType.yyyyMMddHHmmssSSS_t.toString()));
            jacksons = new Jacksons(mapper);
            jacksonsMap.put("tsp", jacksons);
        }

        return jacksons;
    }

    public static Jacksons getReaderByTime(JacksonUtil.TimeType timeType) throws Exception {
        Jacksons jacksons = (Jacksons)jacksonsMap.get(timeType.toString());
        if(jacksons == null) {
            ObjectMapper mapper = getMapper();
            if(timeType != JacksonUtil.TimeType.millisecond) {
                mapper.setDateFormat(new SimpleDateFormat(timeType.toString()));
            }

            jacksons = new Jacksons(mapper);
            jacksonsMap.put(timeType.toString(), jacksons);
        }

        return jacksons;
    }

    public static Jacksons getReaderByTime(String format) throws Exception {
        Jacksons jacksons = (Jacksons)jacksonsMap.get(format);
        if(jacksons == null) {
            ObjectMapper mapper = getMapper();
            mapper.setDateFormat(new SimpleDateFormat(format));
            jacksons = new Jacksons(mapper);
            jacksonsMap.put(format, jacksons);
        }

        return jacksons;
    }

    public static void setJacksons(String key, Jacksons jacksons) throws Exception {
        newJacksonsMap.put(key, jacksons);
    }

    public static void setJacksons(String key, ObjectMapper objectMapper) throws Exception {
        newJacksonsMap.put(key, new Jacksons(objectMapper));
    }

    public static Jacksons get(String key) throws Exception {
        Jacksons jacksons = (Jacksons)newJacksonsMap.get(key);
        return jacksons;
    }

    public static enum TimeType {
        yyyyMMddHHmmssSSS("yyyy-MM-dd HH:mm:ss.SSS"),
        yyyyMMddHHmmss("yyyy-MM-dd HH:mm:ss"),
        HHmmss("HH:mm:ss"),
        yyyyMMdd("yyyy-MM-dd"),
        yyyyMMddHHmmssSSS_t("yyyyMMddHHmmssSSS"),
        yyyyMMddHHmmss_t("yyyyMMddHHmmss"),
        HHmmss_t("HHmmss"),
        yyyyMMdd_t("yyyyMMdd"),
        millisecond("");

        private String type;

        private TimeType(String type) {
            this.type = type;
        }

        public String toString() {
            return this.type;
        }
    }
}
