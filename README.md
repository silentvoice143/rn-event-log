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

# Configuration

```ts
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

  endpoint: 'https://your-api.com/analytics',

  apiKey: 'your-api-key',

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

  // SCREEN TRACKING

  autoTrackScreens: true,

  // TRANSPORT

  // batch size for API requests
  batchSize: 20,

  // OPTIONAL CUSTOM HEADERS

  headers: {
    'Authorization': 'Bearer token',

    'X-App-Version': '1.0.0',
  },

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
});
```

---

## Config Options

| Option           | Type                       | Description                                         |
| ---------------- | -------------------------- | --------------------------------------------------- |
| debug            | boolean                    | Enables debug logs and debug overlay events         |
| sessionStrategy  | `'timeout' \| 'app_state'` | Session handling strategy                           |
| sessionTimeout   | number                     | Session timeout duration in milliseconds            |
| flushAt          | number                     | Automatically flush after N queued events           |
| flushInterval    | number                     | Automatically flush every N milliseconds            |
| endpoint         | string                     | API endpoint for sending analytics events           |
| apiKey           | string                     | Authentication token/API key                        |
| maxStoredEvents  | number                     | Maximum locally persisted events                    |
| retryEnabled     | boolean                    | Enables retry mechanism on transport failure        |
| maxRetries       | number                     | Maximum retry attempts before stopping              |
| retryDelay       | number                     | Base retry delay in milliseconds                    |
| autoTrackScreens | boolean                    | Automatically track React Navigation screen changes |
| batchSize        | number                     | Number of events sent per request                   |
| headers          | Record<string, string>     | Custom request headers                              |
| metadata         | MetadataConfig             | Metadata enrichment configuration                   |

---

## Metadata Config

```ts
metadata: {

  platform: true,

  osVersion: true,

  appVersion: true,

  sdkVersion: true,

  deviceModel: true,

  manufacturer: true,
}
```

| Metadata Option | Description              |
| --------------- | ------------------------ |
| platform        | Adds current platform    |
| osVersion       | Adds Android OS version  |
| appVersion      | Adds app version         |
| sdkVersion      | Adds SDK version         |
| deviceModel     | Adds device model        |
| manufacturer    | Adds device manufacturer |

---

## Session Strategies

### timeout

Creates a new session only after the timeout duration expires.

```ts
sessionStrategy: 'timeout';
```

---

### app_state

Creates a new session every time the app returns from background.

```ts
sessionStrategy: 'app_state';
```

---

# Analytics API

## init()

Initializes the analytics SDK with configuration options.

Must be called before using any other method.

```ts
Analytics.init({
  debug: true,

  endpoint: 'https://your-api.com/analytics',

  apiKey: 'your-api-key',
});
```

### Use Cases

- Configure sessions
- Configure retries
- Configure batching
- Setup API transport
- Enable debug logs
- Enable automatic screen tracking

---

# track()

Tracks a custom analytics event.

```ts
Analytics.track('button_click');
```

### With Properties

```ts
Analytics.track(
  'purchase',

  {
    productId: '123',

    price: 99,
  }
);
```

### Use Cases

- Button clicks
- Purchases
- User actions
- Feature usage
- Custom business events

---

# trackScreen()

Tracks a screen view manually.

```ts
Analytics.trackScreen('Home');
```

### With Properties

```ts
Analytics.trackScreen(
  'Profile',

  {
    source: 'push_notification',
  }
);
```

### Use Cases

- Manual screen tracking
- Custom navigation systems
- Non React Navigation apps

---

# identify()

Associates events with a specific user.

```ts
Analytics.identify(
  'user_123',

  {
    name: 'John',

    email: 'john@example.com',
  }
);
```

### Use Cases

- User login
- User signup
- Persisting user identity
- User profile enrichment

### Notes

- Calling identify multiple times updates the current user
- By default events are tracked anonymously until identify is called

---

# flush()

Immediately sends queued events to the configured API endpoint.

```ts
Analytics.flush();
```

### Use Cases

- Before app exit
- Critical event delivery
- Testing
- Manual transport control

---

# getSession()

Returns current active session information.

```ts
const session = Analytics.getSession();

console.log(session);
```

### Example Response

```ts
{
  sessionId:
    'abc123',

  startTime:
    123456789,

  currentTime:
    123456999,

  duration:
    2000
}
```

### Use Cases

- Debugging
- Session analytics
- Monitoring active session duration

---

# startSession()

Manually starts a new session.

```ts
Analytics.startSession();
```

### Use Cases

- Custom session control
- Gaming sessions
- Manual session resets

---

# closeSession()

Manually closes the current session.

```ts
Analytics.closeSession();
```

### Use Cases

- Logout
- Manual session termination
- Explicit session lifecycle management

---

# onDebug()

Subscribes to internal SDK debug events.

```ts
const unsubscribe = Analytics.onDebug((event) => {
  console.log(event.message);
});
```

### Use Cases

- SDK debugging
- Transport monitoring
- Queue monitoring
- Development logging

### Notes

Works only when:

```ts
debug: true;
```

is enabled.

---

# getStoredEvents()

Returns all locally persisted events from storage.

```ts
const events = await Analytics.getStoredEvents();

console.log(events);
```

### Use Cases

- Debugging
- Inspecting offline queue
- Verifying event persistence
- Local analytics inspection

---

# setGlobalProperties()

Adds properties automatically to every tracked event.

```ts
Analytics.setGlobalProperties({
  appVersion: '1.0.0',

  platform: 'android',

  environment: 'production',
});
```

### Example

```ts
Analytics.track('button_click');
```

Automatically becomes:

```ts
{
  event: 'button_click',

  properties: {

    appVersion: '1.0.0',

    platform: 'android',

    environment: 'production',
  }
}
```

### Use Cases

- App-wide metadata
- Environment tagging
- Shared analytics context
- Tenant/account info

---

# Roadmap

## iOS Support

Native iOS implementation using Swift TurboModules.

This will provide:

- session tracking on iOS
- lifecycle tracking
- persistent storage
- event batching
- retry mechanisms
- background workers
- full cross-platform parity

---

## Retry Backoff Strategy

Currently failed events retry using a simple linear retry strategy.

Planned improvement:

- exponential retry delays
- smarter retry intervals
- retry jitter
- reduced battery/network usage
- improved offline handling

Example:

```txt
1st retry → 5s
2nd retry → 15s
3rd retry → 30s
4th retry → 60s
```

---

## Network Awareness Improvements

Current implementation supports basic connectivity detection.

Planned improvements:

- real-time connectivity listeners
- automatic flush on reconnect
- metered network awareness
- WiFi/mobile data handling
- airplane mode detection

---

## Event Compression

Large payloads can increase bandwidth usage.

Planned improvements:

- gzip compression
- payload optimization
- reduced request size
- faster transport performance

---

## Metadata Enrichment

Current metadata support is configurable.

Planned improvements:

- locale detection
- timezone tracking
- app build number
- install source
- carrier information
- device capabilities

---

## Background Workers

Current Android background flushing uses WorkManager.

Planned improvements:

- smarter scheduling
- adaptive sync intervals
- battery optimization
- idle-aware flushing
- foreground service fallback

---

## Dashboard Integration

Future hosted analytics dashboard support.

Planned features:

- real-time event viewer
- session analytics
- user analytics
- funnel tracking
- crash/event correlation
- custom dashboards
- event filtering
- export tools

---

## Event Interceptors

Middleware system for modifying events before persistence/transport.

Planned features:

- event mutation
- event filtering
- custom enrichment
- event blocking
- encryption hooks

Example:

```ts
Analytics.addInterceptor((event) => {
  event.properties.timestamp = Date.now();

  return event;
});
```

---

## Anonymous User Tracking

Automatic anonymous user identifiers before login.

Planned features:

- persistent anonymous IDs
- anonymous-to-user migration
- install-level tracking
- pre-auth analytics continuity

---

## Request Timeout Configuration

Configurable transport timeout support.

Example:

```ts
Analytics.init({
  requestTimeout: 15000,
});
```

---

## Advanced Batch Controls

More configurable batching system.

Planned features:

- dynamic batch sizing
- payload size limits
- priority queues
- manual batch policies

---

# Automatic Screen Tracking

Supports automatic React Navigation screen tracking.

```ts
import {

  NavigationContainer,

  createNavigationContainerRef,

} from '@react-navigation/native';

import Analytics, {

  trackNavigation,

} from 'rn-event-log';

const navigationRef =
  createNavigationContainerRef();

<NavigationContainer

  ref={navigationRef}

  onReady={() => {

    trackNavigation(
      navigationRef
    );
  }}

  onStateChange={() => {

    trackNavigation(
      navigationRef
    );
  }}
>
```

---

# Debug Events

```ts
Analytics.onDebug((event) => {
  console.log(event.message);
});
```

Example logs:

```txt
Flush Success
Queue => purchase
Transport Success
Process Foreground
```

---

# Offline Persistence

Events are automatically persisted locally using Room Database.

This provides:

- offline event storage
- retry-safe delivery
- background flushing
- crash-safe persistence
- process death recovery

---

# Architecture

rn-event-log is built using:

- React Native TurboModules
- Kotlin
- Room Database
- OkHttp
- Coroutines
- WorkManager

Core systems include:

- persistent event queue
- batching system
- retry manager
- lifecycle tracking
- session management
- background workers

---

# Performance

Designed for minimal runtime overhead.

Features:

- async database writes
- batched transport
- background flushing
- offline queueing
- retry-safe networking
- low memory footprint

---

# License

MIT

# Contributing

Pull requests are welcome.

For major changes, please open an issue first to discuss proposed changes.
