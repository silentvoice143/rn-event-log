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

      debug: true,
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
