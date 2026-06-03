import { useEffect } from 'react';

import {
  NavigationContainer,
  createNavigationContainerRef,
} from '@react-navigation/native';

import { createNativeStackNavigator } from '@react-navigation/native-stack';

import Analytics, { trackNavigation } from 'rn-event-log';

import { DebugOverlay } from './DebugOverlay';
import Home from './Home';
import Profile from './Profile';

const Stack = createNativeStackNavigator();

const navigationRef = createNavigationContainerRef();

export default function App() {
  useEffect(() => {
    Analytics.init({
      sessionStrategy: 'timeout',

      sessionTimeout: 30000,

      autoTrackScreens: true,

      // API

      endpoint: 'https://dummy-api.example.com/analytics',

      apiKey: 'test-api-key-123',

      debug: true,

      metadata: {
        osVersion: true,
        platform: true,
      },

      allowCellular: true, // if false then mobile network cant flush events

      allowMetered: true, // to allow metered connection or disable it
    });
  }, []);

  return (
    <>
      <NavigationContainer
        ref={navigationRef}
        onReady={() => {
          trackNavigation(navigationRef);
        }}
        onStateChange={() => {
          trackNavigation(navigationRef);
        }}
      >
        <Stack.Navigator>
          <Stack.Screen name="Home" component={Home} />

          <Stack.Screen name="Profile" component={Profile} />
        </Stack.Navigator>
      </NavigationContainer>

      <DebugOverlay />
    </>
  );
}
