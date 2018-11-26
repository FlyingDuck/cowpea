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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public final class ServiceLocator implements ServiceProvider<Service> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceLocator.class);


    @Override
    public <U extends Service> U getService(Class<U> serviceType) {
        return null;
    }

    @Override
    public <U extends Service> Collection<U> getServicesOfType(Class<U> serviceType) {
        return null;
    }



    private static Collection<Class<?>> getAllInterfaces(final Class<?> clazz) {
        ArrayList<Class<?>> interfaces = new ArrayList<>();
        for (Class<?> c = clazz; null != c; c = c.getSuperclass()) {
            for (Class<?> i : c.getInterfaces()) {
                interfaces.add(i);
                interfaces.addAll(getAllInterfaces(i));
            }
        }
        return interfaces;
    }


    private static class ServiceMap {
        private final Map<Class<? extends Service>, Set<Service>> services;

        public ServiceMap() {
            this.services = new HashMap<>();
        }

        public ServiceMap(ServiceMap resolved) {
            this.services = new HashMap<>();
            for (Map.Entry<Class<? extends Service>, Set<Service>> entry : resolved.services.entrySet()) {
                Set<Service> copy = Collections.newSetFromMap(new IdentityHashMap<>());
                copy.addAll(entry.getValue());
                this.services.put(entry.getKey(), copy);
            }
        }

        public <T extends Service> Set<T> get(Class<T> serviceType) {
            @SuppressWarnings("unchecked")
            Set<T> set = (Set<T>) this.services.get(serviceType);
            if (null == set)
                return Collections.emptySet();
            else
                return Collections.unmodifiableSet(set);
        }

        public Set<Service> all() {
            Set<Service> all = Collections.newSetFromMap(new IdentityHashMap<>());
            for (Set<Service> set : services.values()) {
                all.addAll(set);
            }
            return Collections.unmodifiableSet(all);
        }

        public boolean contains(Class<? extends Service> request) {
            return services.containsKey(request);
        }

        public ServiceMap addAll(Iterable<? extends Service> services) {
            for (Service service : services) {
                this.add(service);
            }
            return this;
        }

        public ServiceMap add(Service service) {
            Set<Class<? extends Service>> serviceClazzes = new HashSet<>();

            serviceClazzes.add(service.getClass());
            for (Class<?> i : getAllInterfaces(service.getClass())) {
                if (i != Service.class && Service.class.isAssignableFrom(i)) {
                    @SuppressWarnings("unchecked")
                    Class<? extends Service> serviceClazz = (Class<? extends Service>) i;
                    serviceClazzes.add(serviceClazz);
                }
            }

            for (Class<? extends Service> serviceClazz : serviceClazzes) {
                if (serviceClazz.isAnnotationPresent(PluralService.class)) {
                    // Permit multiple registrations
                    Set<Service> registeredServices = this.services.computeIfAbsent(serviceClazz, k -> new LinkedHashSet<>());
                    registeredServices.add(service);
                } else {
                    // Only a single registration permitted
                    Set<Service> registeredServices = this.services.get(serviceClazz);
                    if (null == registeredServices || registeredServices.isEmpty()) {
                        this.services.put(serviceClazz, Collections.singleton(service));
                    } else if (!registeredServices.contains(service)) {
                        final StringBuilder message = new StringBuilder("Duplicate service implementation(s) found for ").append(service.getClass());
                        for (Class<? extends Service> serviceClass : serviceClazzes) {
                            if (!serviceClass.isAnnotationPresent(PluralService.class)) {
                                Set<Service> s = this.services.get(serviceClass);
                                final Service declaredService = s == null ? null : s.iterator().next();
                                if (declaredService != null) {
                                    message.append("\n\t\t- ")
                                            .append(serviceClass)
                                            .append(" already has ")
                                            .append(declaredService.getClass());
                                }
                            }
                        }
                        throw new IllegalStateException(message.toString());
                    }

                }
            }

            return this;
        }

    }

}
