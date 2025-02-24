package com.zio.ziorpc.serializer;

import com.zio.ziorpc.spi.SpiLoader;

/**
 * 序列化器工厂（用于获取序列化器对象）
 *
 * @author Zio1037
 * @date 2025-02-24 14:39
 */
public class SerializerFactory {

    static {
        SpiLoader.load(Serializer.class);
    }

    /**
     * 默认序列化器
     */
    private static final Serializer DEFAULT_SERIALIZER = new JdkSerializer();

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static Serializer getInstance(String key) {
        return SpiLoader.getInstance(Serializer.class, key);
    }

}

