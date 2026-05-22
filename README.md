# rn-event-log

A lightweight high-performance analytics SDK for React Native built with TurboModules and the New Architecture.

Supports:

- Session tracking
- Screen tracking
- Offline persistence
- Batch flushing
- Retry-safe delivery
- Lifecycle tracking
- Real-time debug events

Built using:

- React Native TurboModules
- Kotlin
- Room Database
- OkHttp
- Coroutines

---

# Features

✅ React Native New Architecture (TurboModules)

✅ Offline-first event persistence using Room DB

✅ Automatic batching and flushing

✅ Session tracking

✅ App lifecycle tracking

✅ Screen time tracking

✅ Retry-safe transport layer

✅ Configurable flush intervals

✅ Configurable session strategies

✅ Real-time debug overlay support

✅ TypeScript support

---

# Installation

```sh
npm install rn-event-log
```

---

# Android Setup

No additional setup required for React Native New Architecture projects.

Minimum requirements:

React Native 0.76+
Android minSdk 24+

```
import Analytics from 'rn-event-log';

Analytics.init({

  // DEBUG

  debug: __DEV__,


  // SESSION CONFIG

  // "timeout" | "app_state"
  sessionStrategy: 'timeout',

  // milliseconds
  sessionTimeout: 30000,


  // FLUSH CONFIG

  // auto flush after N events
  flushAt: 20,

  // flush every N milliseconds
  flushInterval: 30000,


  // API CONFIG

  endpoint:
    'https://your-api.com/analytics',

  apiKey:
    'your-api-key',


  // STORAGE

  // maximum locally stored events
  maxStoredEvents: 10000,


  // RETRY

  // enable retry on failure
  retryEnabled: true,

  // maximum retry attempts
  maxRetries: 5,

  // retry base delay
  retryDelay: 5000,


  // NETWORK

  // only flush when internet available
  networkAware: true,


  // METADATA

  // automatically enrich events
  metadata: {

    platform: true,

    osVersion: true,

    appVersion: true,

    sdkVersion: true,

    deviceModel: true,

    manufacturer: true,

  },


  // SCREEN TRACKING

  autoTrackScreens: true,


  // TRANSPORT

  // request timeout
  requestTimeout: 15000,

  // batch size for API requests
  batchSize: 20,


  // OPTIONAL CUSTOM HEADERS

  headers: {

    Authorization:
      'Bearer token',

    'X-App-Version':
      '1.0.0',
  },
});
```

## Track Events

```
Analytics.track(
  'button_click',
  {
    button: 'login'
  }
);
```

## Identify

Used to associate events with a specific user.

Useful after login/signup.

```ts
Analytics.identify('user_123', {
  name: 'John',
  email: 'john@example.com',
  plan: 'premium',
});
```

# Roadmap

## iOS Support

Native iOS implementation using Swift TurboModules.

This will provide:

- session tracking on iOS
- lifecycle tracking
- persistent storage
- event batching
- full cross-platform parity

---

## Retry Backoff Strategy

Currently failed events retry on next flush.

Planned improvement:

- exponential retry delays
- smarter retry intervals
- reduced battery/network usage

Example:

```txt
1st retry → 5s
2nd retry → 15s
3rd retry → 30s
```
