import NativeRnEventLog from './NativeRnEventLog';
import Logger from './utils/logger';
import type { AnalyticsConfig } from './types/config';

class Analytics {
  private config: AnalyticsConfig = {};

  init(config: AnalyticsConfig = {}) {
    this.config = config;

    Logger.log('init called', config);

    NativeRnEventLog.init(config);
  }

  track(event: string, properties = {}) {
    Logger.log('track event', event, properties);

    NativeRnEventLog.track(event, properties);
  }

  trackScreen(screen: string, properties = {}) {
    Logger.log('track screen', screen, properties);

    NativeRnEventLog.trackScreen(screen, properties);
  }

  identify(userId: string, traits = {}) {
    Logger.log('identify user', userId, traits);

    NativeRnEventLog.identify(userId, traits);
  }

  flush() {
    Logger.log('flush events');

    NativeRnEventLog.flush();
  }

  getSession() {
    Logger.log('getSession');

    return NativeRnEventLog.getSession();
  }

  startSession() {
    Logger.log('startSession');

    NativeRnEventLog.startSession();
  }

  closeSession() {
    Logger.log('closeSession');

    NativeRnEventLog.closeSession();
  }

  onDebug(callback: (event: { message: string }) => void) {
    return NativeRnEventLog.onDebug(callback);
  }

  getStoredEvents() {
    return NativeRnEventLog.getStoredEvents();
  }

  setGlobalProperties(properties = {}) {
    NativeRnEventLog.setGlobalProperties(properties);
  }

  isAutoTrackScreensEnabled() {
    return this.config?.autoTrackScreens !== false;
  }
}

export default new Analytics();
