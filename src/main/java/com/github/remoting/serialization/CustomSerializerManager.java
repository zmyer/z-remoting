package com.github.remoting.serialization;

import com.github.remoting.CommandCode;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zifeng
 *
 */
public class CustomSerializerManager {

    /** For rpc */
    private static ConcurrentHashMap<String/* class name */, CustomSerializer> classCustomSerializer =
            new ConcurrentHashMap<String, CustomSerializer>();

    /** For user defined command */
    private static ConcurrentHashMap<CommandCode/* command code */, CustomSerializer> commandCustomSerializer =
            new ConcurrentHashMap<CommandCode, CustomSerializer>();

    /**
     * Register custom serializer for class name.
     *
     * @param className
     * @param serializer
     * @return
     */
    public static void registerCustomSerializer(String className, CustomSerializer serializer) {
        CustomSerializer prevSerializer = classCustomSerializer.putIfAbsent(className, serializer);
        if (prevSerializer != null) {
            throw new RuntimeException("CustomSerializer has been registered for class: "
                    + className + ", the custom serializer is: "
                    + prevSerializer.getClass().getName());
        }
    }

    /**
     * Get the custom serializer for class name.
     *
     * @param className
     * @return
     */
    public static CustomSerializer getCustomSerializer(String className) {
        if (!classCustomSerializer.isEmpty()) {
            return classCustomSerializer.get(className);
        }
        return null;
    }

    /**
     * Register custom serializer for command code.
     *
     * @param code
     * @param serializer
     * @return
     */
    public static void registerCustomSerializer(CommandCode code, CustomSerializer serializer) {
        CustomSerializer prevSerializer = commandCustomSerializer.putIfAbsent(code, serializer);
        if (prevSerializer != null) {
            throw new RuntimeException("CustomSerializer has been registered for command code: "
                    + code + ", the custom serializer is: "
                    + prevSerializer.getClass().getName());
        }
    }

    /**
     * Get the custom serializer for command code.
     *
     * @param code
     * @return
     */
    public static CustomSerializer getCustomSerializer(CommandCode code) {
        if (!commandCustomSerializer.isEmpty()) {
            return commandCustomSerializer.get(code);
        }
        return null;
    }

    /**
     * clear the custom serializers.
     */
    public static void clear() {
        classCustomSerializer.clear();
        commandCustomSerializer.clear();
    }
}
