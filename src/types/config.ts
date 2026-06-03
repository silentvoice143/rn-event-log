export type SessionStrategy = 'timeout' | 'app_state';

export interface MetadataConfig {
  platform?: boolean;

  osVersion?: boolean;

  appVersion?: boolean;

  sdkVersion?: boolean;

  deviceModel?: boolean;

  manufacturer?: boolean;
}

export interface AnalyticsConfig {
  // DEBUG

  debug?: boolean;

  // SESSION

  sessionStrategy?: SessionStrategy;

  sessionTimeout?: number;

  // FLUSH

  flushAt?: number;

  flushInterval?: number;

  // API

  endpoint?: string;

  apiKey?: string;

  // STORAGE

  maxStoredEvents?: number;

  // RETRY

  retryEnabled?: boolean;

  maxRetries?: number;

  retryDelay?: number;

  // SCREENS

  autoTrackScreens?: boolean;

  // TRANSPORT

  requestTimeout?: number;

  batchSize?: number;

  // HEADERS

  headers?: Record<string, string>;

  // METADATA

  metadata?: MetadataConfig;

  // NETWORK

  allowCellular?: boolean; // for allowing cellular network

  allowMetered?: boolean; // for network which has limit
}
