/*
 * Copyright 2017 original authors
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package io.micronaut.core.convert.format;

import io.micronaut.core.convert.ConversionContext;
import io.micronaut.core.convert.ConversionService;

import java.util.Locale;
import java.util.Optional;

/**
 * Converts String's to readable bytes
 *ƒ
 * @author Graeme Rocher
 * @since 1.0
 */
public class ReadableBytesTypeConverter implements FormattingTypeConverter<CharSequence, Number, ReadableBytes> {
    @Override
    public Class<ReadableBytes> annotationType() {
        return ReadableBytes.class;
    }

    @Override
    public Optional<Number> convert(CharSequence object, Class<Number> targetType, ConversionContext context) {
        String value = object.toString().toUpperCase(Locale.ENGLISH);
        try {
            if (value.endsWith("KB")) {
                long size = Long.valueOf(value.substring(0, value.length() - 2)) * 1024;
                return ConversionService.SHARED.convert(size, targetType);
            }
            if (value.endsWith("MB")) {
                long size = Long.valueOf(value.substring(0, value.length() - 2)) * 1024 * 1024;
                return ConversionService.SHARED.convert(size, targetType);
            }
            if (value.endsWith("GB")) {
                long size = Long.valueOf(value.substring(0, value.length() - 2)) * 1024 * 1024 * 1024;
                return ConversionService.SHARED.convert(size, targetType);
            }
            Long size = Long.valueOf(value);
            return ConversionService.SHARED.convert(size, targetType);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}