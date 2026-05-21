# rn-event-log

event log

## Installation

```sh
npm install rn-event-log
```

## Usage

```js
import { multiply } from 'rn-event-log';

// ...

const result = multiply(3, 7);
```

## Contributing

- [Development workflow](CONTRIBUTING.md#development-workflow)
- [Sending a pull request](CONTRIBUTING.md#sending-a-pull-request)
- [Code of conduct](CODE_OF_CONDUCT.md)

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)

# Config

```
Analytics.init({

  // SESSION CONFIG

  sessionStrategy: 'timeout',

  sessionTimeout: 30000,


  // FLUSH CONFIG

  flushAt: 20,

  flushInterval: 30000,
});
```

---
