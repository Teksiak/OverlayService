# OverlayService

Android Service for displaying Jetpack Compose UI as system overlays.

### Permissions

```xml
<!-- Permission for overlay windows -->
<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />

<!-- For foreground service -->
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE_SPECIAL_USE" />
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
```

## Features

- Create floating overlays with Jetpack Compose UI
- Full lifecycle management and state preservation
- Simple API for showing/hiding overlays

## Demo Usage

[Simple floating timer](https://github.com/Teksiak/OverlayService/tree/main/demo/src/main/java/com/teksiak/overlayservice) displaying in the middle of the screen.
