package com.tnd.config.customized.bus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;
import org.springframework.cloud.bus.event.UnknownRemoteApplicationEvent;
import org.springframework.cloud.bus.jackson.SubtypeModule;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.util.ClassUtils;
import org.springframework.util.MimeTypeUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;

/**
 * Custom BusJacksonMessageConverter is implemented in
 * class [org.springframework.cloud.bus.jackson.BusJacksonAutoConfiguration]
 *
 * @Bean with name 'busJsonConverter' must be defined
 * <p>
 * Cloud Stream serialized/deserialized in
 * class [org.springframework.messaging.converter.CompositeMessageConverter] method toMessage/fromMessage
 * This class has some of class extends [AbtractMessageConverter].
 * If object after a converter is not null, other converter will not run
 * <p>
 * In original code, converters have [BusJacksonMessageConverter] and [MappingJackson2MessageConverter]
 * [BusJacksonMessageConverter]
 * deserialized JSON message to Java object of event of package [class org.springframework.cloud.bus.event], default:
 * [UnknownRemoteApplicationEvent]
 * [AckRemoteApplicationEvent]
 * [RefreshRemoteApplicationEvent]
 * [EnvironmentChangeRemoteApplicationEvent]
 * [MappingJackson2MessageConverter]
 * serialized java object to JSON message
 * <p>
 * This file is merge [BusJacksonMessageConverter] to [MappingJackson2MessageConverter]
 */

public class CustomBusJacksonMessageConverter extends MappingJackson2MessageConverter implements InitializingBean {

    private static final String DEFAULT_PACKAGE = ClassUtils.getPackageName(RemoteApplicationEvent.class);

    private ObjectMapper objectMapper /*= new ObjectMapper()*/;

    private String[] packagesToScan = new String[] { DEFAULT_PACKAGE };

    public CustomBusJacksonMessageConverter() {
        super(MimeTypeUtils.APPLICATION_JSON);
        initObjectMapper();
    }

    public void setPackagesToScan(String[] packagesToScan) {
        List<String> packages = new ArrayList<>(Arrays.asList(packagesToScan));
        if (!packages.contains(DEFAULT_PACKAGE)) {
            packages.add(DEFAULT_PACKAGE);
        }
        this.packagesToScan = packages.toArray(new String[0]);
    }

    private void initObjectMapper() {
//        this.objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
//        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        setSerializedPayloadClass(String.class);
        objectMapper = getObjectMapper();
//        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
    }

    private Class<?>[] findSubTypes() {
        List<Class<?>> types = new ArrayList<>();
        if (this.packagesToScan != null) {
            for (String pkg : this.packagesToScan) {
                ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(
                        false);
                provider.addIncludeFilter(
                        new AssignableTypeFilter(RemoteApplicationEvent.class));

                Set<BeanDefinition> components = provider.findCandidateComponents(pkg);
                for (BeanDefinition component : components) {
                    try {
                        types.add(Class.forName(component.getBeanClassName()));
                    } catch (ClassNotFoundException e) {
                        throw new IllegalStateException(
                                "Failed to scan classpath for remote event classes", e);
                    }
                }
            }
        }
        return types.toArray(new Class<?>[0]);
    }

    @Override
    protected boolean supports(Class<?> aClass) {
        // This converter applies only to RemoteApplicationEvent and subclasses
        return RemoteApplicationEvent.class.isAssignableFrom(aClass);
    }

    @Override
    public Object convertFromInternal(Message<?> message, Class<?> targetClass,
                                      Object conversionHint) {
        Object result = null;
        try {
            Object payload = message.getPayload();

            if (payload instanceof byte[]) {
                try {
                    result = this.objectMapper.readValue((byte[]) payload, targetClass);
                } catch (InvalidTypeIdException e) {
                    return new UnknownRemoteApplicationEvent(new Object(), e.getTypeId(), (byte[]) payload);
                }
            } else if (payload instanceof String) {
                try {
                    result = this.objectMapper.readValue((String) payload, targetClass);
                } catch (InvalidTypeIdException e) {
                    return new UnknownRemoteApplicationEvent(new Object(), e.getTypeId(),
                                                             ((String) payload).getBytes());
                }
            }
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
            return null;
        }
        return result;
    }

//    @Override
//    public Object convertToInternal(Object payload, MessageHeaders headers, Object conversionHint) {
//        try {
//            Class<?> view = getSerializationView(conversionHint);
//            if (byte[].class == getSerializedPayloadClass()) {
//                ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
//                JsonEncoding encoding = getJsonEncoding(getMimeType(headers));
//                JsonGenerator generator = this.objectMapper.getFactory().createGenerator(out, encoding);
//                if (view != null) {
//                    this.objectMapper.writerWithView(view).writeValue(generator, payload);
//                }
//                else {
//                    this.objectMapper.writeValue(generator, payload);
//                }
//                payload = out.toByteArray();
//            }
//            else {
//                Writer writer = new StringWriter();
//                if (view != null) {
//                    this.objectMapper.writerWithView(view).writeValue(writer, payload);
//                }
//                else {
//                    this.objectMapper.writeValue(writer, payload);
//                }
//                payload = writer.toString();
//            }
//        }
//        catch (IOException ex) {
//            throw new MessageConversionException("Could not write JSON: " + ex.getMessage(), ex);
//        }
//        return payload;
//    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        this.objectMapper.registerModule(new SubtypeModule(findSubTypes()));
    }
}

