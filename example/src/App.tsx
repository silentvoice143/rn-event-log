import React, { useEffect } from 'react';
import { Text, View, StyleSheet } from 'react-native';

import Analytics from 'rn-event-log';

export default function App() {
  useEffect(() => {
    Analytics.init({
      debug: true,
      flushInterval: 30,
    });

    Analytics.startSession();

    Analytics.track('app_opened', {
      screen: 'Home',
      platform: 'android',
    });

    Analytics.flush();

    return () => {
      Analytics.closeSession();
    };
  }, []);

  return (
    <View style={styles.container}>
      <Text>RN Event Log Working 🚀</Text>
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
