package com.vvwyy.cowpea.demo;

import java.util.concurrent.Callable;

public interface CancellableTask<T> extends Callable<T> {
    void cancel();
}
