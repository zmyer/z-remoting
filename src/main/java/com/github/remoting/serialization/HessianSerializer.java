package com.github.remoting.serialization;

import com.alipay.hessian.ClassNameResolver;
import com.alipay.hessian.internal.InternalNameBlackListFilter;
import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.SerializerFactory;
import io.netty.handler.codec.CodecException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author zifeng
 *
 */
public class HessianSerializer implements Serializer {

    private SerializerFactory serializerFactory = new SerializerFactory();

    public HessianSerializer() {
        //initialize with default black list in hessian
        ClassNameResolver resolver = new ClassNameResolver();
        resolver.addFilter(new InternalNameBlackListFilter(8192));
        serializerFactory.setClassNameResolver(resolver);
    }

    @Override
    public byte[] serialize(Object obj) throws CodecException {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        Hessian2Output output = new Hessian2Output(byteArray);
        output.setSerializerFactory(serializerFactory);
        try {
            output.writeObject(obj);
            output.close();
        } catch (IOException e) {
            throw new CodecException("IOException occurred when Hessian serializer encode!", e);
        }

        byte[] bytes = byteArray.toByteArray();
        return bytes;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T deserialize(byte[] data, String classOfT) throws CodecException {
        Hessian2Input input = new Hessian2Input(new ByteArrayInputStream(data));
        input.setSerializerFactory(serializerFactory);
        Object resultObject;
        try {
            resultObject = input.readObject();
            input.close();
        } catch (IOException e) {
            throw new CodecException("IOException occurred when Hessian serializer decode!", e);
        }
        return (T) resultObject;
    }
}
