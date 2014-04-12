package com.github.clemp6r.futuroid.scalasample

import com.github.clemp6r.futuroid.Async
import java.util.concurrent.Callable

object MyAsyncService {
  def computePi = Async.submit(new Callable[Double] {
    override def call() = computePiSync
  })

  private def computePiSync = {
    Thread.sleep(3000)
    3.14
  }
}