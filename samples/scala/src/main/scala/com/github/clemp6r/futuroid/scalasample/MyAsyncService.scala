package com.github.clemp6r.futuroid.scalasample

object MyAsyncService extends Futuroid {
  def computePi = async { computePiSync }

  private def computePiSync = {
    Thread.sleep(3000)
    3.14
  }
}