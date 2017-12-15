package com.tnd.config;

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
import org.springframework.messaging.converter.AbstractMessageConverter;
import org.springframework.util.ClassUtils;
import org.springframework.util.MimeTypeUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;

public class CustomBusJacksonMessageConverter extends AbstractMessageConverter implements InitializingBean {

    private static final String DEFAULT_PACKAGE = ClassUtils
            .getPackageName(RemoteApplicationEvent.class);

    private ObjectMapper mapper = new ObjectMapper();

    private String[] packagesToScan = new String[] { DEFAULT_PACKAGE };

    public CustomBusJacksonMessageConverter() {
        super(MimeTypeUtils.APPLICATION_JSON);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }

    public void setPackagesToScan(String[] packagesToScan) {
        List<String> packages = new ArrayList<>(Arrays.asList(packagesToScan));
        if (!packages.contains(DEFAULT_PACKAGE)) {
            packages.add(DEFAULT_PACKAGE);
        }
        this.packagesToScan = packages.toArray(new String[0]);
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
                    result = this.mapper.readValue((byte[]) payload, targetClass);
                } catch (InvalidTypeIdException e) {
                    return new UnknownRemoteApplicationEvent(new Object(), e.getTypeId(), (byte[]) payload);
                }
            } else if (payload instanceof String) {
                try {
                    result = this.mapper.readValue((String) payload, targetClass);
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

    @Override
    public void afterPropertiesSet() throws Exception {
        this.mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        this.mapper.registerModule(new SubtypeModule(findSubTypes()));
    }
}

