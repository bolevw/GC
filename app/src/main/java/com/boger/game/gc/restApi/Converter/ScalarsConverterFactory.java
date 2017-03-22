package com.boger.game.gc.restApi.Converter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by liubo on 2016/5/24.
 */
public final class ScalarsConverterFactory extends Converter.Factory {
    public static ScalarsConverterFactory create() {
        return new ScalarsConverterFactory();
    }

    private ScalarsConverterFactory() {
    }


    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        if (type == String.class
                || type == boolean.class
                || type == Boolean.class
                || type == byte.class
                || type == Byte.class
                || type == char.class
                || type == Character.class
                || type == double.class
                || type == Double.class
                || type == float.class
                || type == Float.class
                || type == int.class
                || type == Integer.class
                || type == long.class
                || type == Long.class
                || type == short.class
                || type == Short.class
                || type == ResponseBody.class
                ) {
            return ScalarRequestBodyConverter.INSTANCE;
        }
        return null;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        if (type == String.class) {
            return ScalarResponseBodyConverters.StringResponseBodyConverter.INSTANCE;
        }
        if (type == Boolean.class || type == boolean.class) {
            return ScalarResponseBodyConverters.BooleanResponseBodyConverter.INSTANCE;
        }
        if (type == Byte.class || type == byte.class) {
            return ScalarResponseBodyConverters.ByteResponseBodyConverter.INSTANCE;
        }
        if (type == Character.class || type == char.class) {
            return ScalarResponseBodyConverters.CharacterResponseBodyConverter.INSTANCE;
        }
        if (type == Double.class || type == double.class) {
            return ScalarResponseBodyConverters.DoubleResponseBodyConverter.INSTANCE;
        }
        if (type == Float.class || type == float.class) {
            return ScalarResponseBodyConverters.FloatResponseBodyConverter.INSTANCE;
        }
        if (type == Integer.class || type == int.class) {
            return ScalarResponseBodyConverters.IntegerResponseBodyConverter.INSTANCE;
        }
        if (type == Long.class || type == long.class) {
            return ScalarResponseBodyConverters.LongResponseBodyConverter.INSTANCE;
        }
        if (type == Short.class || type == short.class) {
            return ScalarResponseBodyConverters.ShortResponseBodyConverter.INSTANCE;
        }
        if (type == ResponseBody.class) {
            return ScalarResponseBodyConverters.ScalarResponseBodyConverter.INSTANCE;
        }
        return null;
    }


}

