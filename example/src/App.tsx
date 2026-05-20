import { useEffect } from 'react';
import { Text, View, StyleSheet } from 'react-native';

import Analytics from 'rn-event-log';
import { DebugOverlay } from './DebugOverlay';

export default function App() {
  useEffect(() => {
    Analytics.init();

    Analytics.startSession();

    Analytics.track('button_click');

    Analytics.trackScreen('Home');

    Analytics.trackScreen('Profile');

    Analytics.flush();

    return () => {
      Analytics.closeSession();
    };
  }, []);

  return (
    <View style={styles.container}>
      <Text>RN Event Log Working 🚀</Text>
      <DebugOverlay />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
});
