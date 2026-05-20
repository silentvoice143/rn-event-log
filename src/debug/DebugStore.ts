// import { NativeEventEmitter, NativeModules } from 'react-native';
// import logger from '../utils/logger';

// // Pass the native module to the emitter
// const { RnEventLog } = NativeModules;
// logger.log('RnEventLog module:', RnEventLog);
// const emitter = new NativeEventEmitter(RnEventLog);

// const listeners: any[] = [];

// emitter.addListener('RnEventLog_Debug', (event) => {
//   logger.log('[Native]', event);
//   listeners.forEach((cb) => cb(event));
// });

// export function subscribeDebug(callback: (event: { message: string }) => void) {
//   listeners.push(callback);

//   return () => {
//     const index = listeners.indexOf(callback);
//     if (index > -1) {
//       listeners.splice(index, 1);
//     }
//   };
// }

import { DeviceEventEmitter } from 'react-native';
import logger from '../utils/logger';

const listeners: any[] = [];

DeviceEventEmitter.addListener('RnEventLog_Debug', (event) => {
  logger.log('EVENT RECEIVED', event);

  listeners.forEach((cb) => {
    cb(event);
  });
});

export function subscribeDebug(callback: (event: { message: string }) => void) {
  listeners.push(callback);

  return () => {
    const index = listeners.indexOf(callback);

    if (index > -1) {
      listeners.splice(index, 1);
    }
  };
}
