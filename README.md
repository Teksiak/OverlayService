# OverlayService

Android Service for displaying Jetpack Compose UI as system overlays.

### Features

- Create floating overlays with Jetpack Compose UI
- Full lifecycle management and state preservation
- Simple API for showing/hiding overlays

## Usage

### Permissions

Make sure you've added the ```android.permission.SYSTEM_ALERT_WINDOW``` permission to your ```AndroidManifest.xml```.

Full permission list for usage with ForegroundService:
```xml
<!-- Permission for overlay windows -->
<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />

<!-- For foreground service -->
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE_SPECIAL_USE" />
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
```

### Service implementation

Copy the [OverlayService](overlay-service/src/main/java/com/teksiak/overlay_service/OverlayService.kt) to your project, customize it and implement your own service:
```kotlin
class MyOverlayService : OverlayService() {
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showOverlay {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.White.copy(alpha = 0.9f), RoundedCornerShape(8.dp))
            ) {
                Text("Hello from overlay!")
                Button(onClick = { hideOverlay() }) {
                    Text("Close")
                }
            }
        }
        return START_STICKY
    }
}
```

Register your service in the ```AndroidManifest.xml```:
```xml
<service
    android:name=".MyOverlayService"
    android:exported="false" />
```

Start your service:
```kotlin
Intent(this, MyOverlayService::class.java).also {
    startService(it)
}
```

### Example Usage

[Simple floating timer](https://github.com/Teksiak/OverlayService/tree/main/demo/src/main/java/com/teksiak/overlayservice) displaying in the middle of the screen.
