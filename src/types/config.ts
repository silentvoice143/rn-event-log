export type SessionStrategy = 'timeout' | 'app_state';

export interface AnalyticsConfig {
  sessionStrategy?: SessionStrategy;

  sessionTimeout?: number;

  debug?: boolean;

  autoTrackScreens?: boolean;
}
