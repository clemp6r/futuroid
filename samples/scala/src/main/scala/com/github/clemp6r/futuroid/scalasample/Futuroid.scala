package com.github.clemp6r.futuroid.scalasample

import com.github.clemp6r.futuroid.{Async, SuccessCallback}
import java.util.concurrent.Callable

trait Futuroid {
  implicit def funcToSuccessCallback[T](f: (T) => Unit) = new SuccessCallback[T] {
    def onSuccess(result: T) = f(result)
  }

  implicit def funcToCallable[T](f: () => T) = new Callable[T] {
    override def call() = f()
  }

  def async[T](f: => T) = Async.submit(() => f)
}