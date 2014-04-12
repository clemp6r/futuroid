package com.github.clemp6r.futuroid.scalasample

import android.app.Activity
import android.os.Bundle
import android.widget.TextView

class SimpleActivity extends Activity with Futuroid {

  lazy val textView = findViewById(R.id.scala_text_view).asInstanceOf[TextView]

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_simple)

    textView.setText("Computing PI")

    MyAsyncService.computePi.addSuccessUiCallback ( showResult _ )
  }

  def showResult(pi: Double) {
    textView.setText("PI=" + pi)
  }
}
