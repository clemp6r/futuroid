package com.github.clemp6r.futuroid.scalasample

import android.app.Activity
import android.os.Bundle
import android.widget.TextView

class SimpleActivity extends Activity with FuturoidImplicits {

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_simple)

    val textView = findViewById(R.id.scala_text_view).asInstanceOf[TextView]
    textView.setText("Computing PI")

    MyAsyncService.computePi.addSuccessUiCallback { (pi: Double) => textView.setText("PI = " + pi) }
  }
}
