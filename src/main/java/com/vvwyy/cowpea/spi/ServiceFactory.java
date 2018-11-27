/*
 *   Copyright 2018 Bennett Dong
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.vvwyy.cowpea.spi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

public interface ServiceFactory<T extends Service> {

    default boolean isMandatory() {
        return false;
    }

    default int rank() {
        return 1;
    }

    T create(ServiceCreationConfiguration<T> configuration);

    Class<? extends T> getServiceType();

    @Retention(RUNTIME)
    @Target(ElementType.TYPE)
    @interface RequiresConfiguration {

    }
}
