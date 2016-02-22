Futuroid - Android library for asynchronous tasks
=================================================
Futuroid is an Android library that allows running asynchronous tasks and attaching callbacks thanks to a convenient syntax. It offers an alternative to the Android [AsyncTask][1] class.


Features
--------

- Future-based API (similar to Guava's ListenableFutures, Scala/Akka Futures, Javascript promises...)
- Allows registering callbacks to be run on the Android UI/main thread
- Provides a default ExecutorService (fixed thread pool with 5 threads) that can be replaced by a custom one
- Each task can be run on the default ExecutorService or on a custom one
- Allows task chaining (monad-style)


Sample code
-----------

Image download asynchronous service:
```java
    public class DownloadService {
        
        /**
         * Downloads an image asynchronously.
         */
        public Future<Bitmap> downloadImage(String url) {
            return Async.submit(new Callable<Bitmap>() {
                @Override
                public Bitmap call() {
                    Bitmap bitmap;
                    
                    // HTTP request goes here...
                    
                    return bitmap;
                }
            });
        }
    }
```
Client code:
```java
    // download an image from a URL
    imageService.downloadImage(url).addCallback(new FutureCallback<Bitmap>() {
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
```

Adding Futuroid to your project
-------------------------------

Futuroid is available on the [Maven Central Repository][2].

Maven-based configuration:
```xml
    <dependency>
        <groupId>com.github.clemp6r.futuroid</groupId>
        <artifactId>futuroid</artifactId>
        <version>1.0.0</version>
    </dependency>
```
Gradle-based configuration:
```groovy
    dependencies {
        compile 'com.github.clemp6r.futuroid:futuroid:1.0.0'
    }
```    
Links
-----------------

[API documentation][3]


  [1]: http://developer.android.com/reference/android/os/AsyncTask.html
  [2]: http://search.maven.org/#search%7Cga%7C1%7Cg:%22com.github.clemp6r.futuroid%22
  [3]: http://clemp6r.github.io/futuroid/apidocs/
