package com.github.clemp6r.futuroid.scalasample

import com.github.clemp6r.futuroid.SuccessCallback

trait FuturoidImplicits {
  implicit def functionToSuccessCallback[T](f: (T) => Unit): SuccessCallback[T] = new SuccessCallback[T] {
    def onSuccess(result: T): Unit = f(result)
  }
}