// import { TurboModuleRegistry, type TurboModule } from 'react-native';

// export interface Spec extends TurboModule {
//   multiply(a: number, b: number): number;
// }

// export default TurboModuleRegistry.getEnforcing<Spec>('RnEventLog');

import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';
import type { CodegenTypes } from 'react-native';

export interface Spec extends TurboModule {
  init(config?: Object): void;

  track(event: string, properties?: Object): void;

  trackScreen(screen: string, properties?: Object): void;

  identify(userId: string, traits?: Object): void;

  flush(): void;

  getSession(): Object;

  startSession(): void;

  closeSession(): void;

  // onAppStateChange(state: string): void;
  // Required for NativeEventEmitter
  // addListener(eventName: string): void;
  // removeListeners(count: number): void;

  readonly onDebug: CodegenTypes.EventEmitter<{
    message: string;
  }>;
}

export default TurboModuleRegistry.getEnforcing<Spec>('RnEventLog');
