import { useEffect } from 'react';
import { Text, View, StyleSheet } from 'react-native';

import Analytics from 'rn-event-log';
import { DebugOverlay } from './DebugOverlay';

export default function App() {
  useEffect(() => {
    Analytics.init({
      sessionStrategy: 'timeout',
    });

    Analytics.track('button_click');
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
