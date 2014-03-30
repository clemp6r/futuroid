Futuroid - Android library for asynchronous tasks
=================================================
Futuroid is an Android library that allows running asynchronous tasks and attaching callbacks thanks to a convenient syntax. It offers an alternative to the Android [AsyncTask][1] class.

Features
--------

- Future-based API (similar to Guava's ListenableFutures, Scala/Akka Futures, Javascript promises...)
- Allows registering callbacks to be run on the Android UI/main thread
- Provides a default ExecutorService (fixed thread pool with 5 threads)
- Each task can be run on the default ExecutorService or on a custom one
- Allows task chaining (sample code to be added...)
- Functional language friendly
 

Sample code
-----------

Image download asynchronous service:

    public class DownloadService {
        
        /**
         * Downloads an image asynchronously.
         */
        public Future<Bitmap> downloadImage(String url) {
            return Async.submit(new Callable<Bitmap>() {
                @Override
                public Thread call() {
                    Bitmap bitmap;
                    
                    // HTTP request goes here...
                    
                    return bitmap;
                }
            });
        }
    }

Client code:

    // download an image from a URL
    imageService.downloadImage(url).addUiCallback(new FutureCallback<Bitmap>() {
        @Override
        public void onSuccess(Bitmap bitmap) {
            // display the image
            imageView.setImageBitmap(bitmap);
        }

        @Override
        public void onFailure(Throwable t) {
            Log.e(TAG, "Unable to download image", t);
        }
    });

Current status
--------------
Futuroid is in its early stage of development and should not be used in production code yet. 
The public API is not stable and may change without notice until version 1.0.0 is released.

Adding Futuroid to your project
-------------------------------
Futuroid is available on the [Maven Central Repository][2].

Maven-based configuration:

    <dependency>
        <groupId>com.github.clemp6r.futuroid</groupId>
        <artifactId>futuroid</artifactId>
        <version>0.1</version>
    </dependency>

Gradle-based configuration:

    dependencies {
        compile 'com.github.clemp6r.futuroid:futuroid:0.1'
    }
    
Links
-----------------
[API documentation][3]

[![Build Status](https://travis-ci.org/clemp6r/futuroid.svg?branch=master)](https://travis-ci.org/clemp6r/futuroid)

  [1]: http://developer.android.com/reference/android/os/AsyncTask.html
  [2]: http://search.maven.org/#search%7Cga%7C1%7Cg:%22com.github.clemp6r.futuroid%22
  [3]: http://clemp6r.github.io/futuroid/apidocs/