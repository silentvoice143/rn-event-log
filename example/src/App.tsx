import { useEffect, useState } from 'react';
import { Text, View, StyleSheet } from 'react-native';

import Analytics from 'rn-event-log';

import { DebugOverlay } from './DebugOverlay';

export default function App() {
  const [session, setSession] = useState<any>(null);

  useEffect(() => {
    Analytics.init({
      sessionStrategy: 'timeout',
      sessionTimeout: 30000,
      autoTrackScreens: true,
    });

    Analytics.track('button_click');

    const interval = setInterval(() => {
      setSession(Analytics.getSession());
    }, 1000);

    return () => {
      clearInterval(interval);
    };
  }, []);

  console.log(session);

  return (
    <View style={styles.container}>
      <Text style={styles.title}>RN Event Log Working 🚀</Text>

      <View style={styles.card}>
        <Text style={styles.heading}>Current Session</Text>

        <Text>Session ID:</Text>

        <Text style={styles.value}>{session?.sessionId || 'N/A'}</Text>

        <Text>Duration:</Text>

        <Text style={styles.value}>{session?.duration || 0} ms</Text>
      </View>

      <DebugOverlay />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    padding: 20,
  },

  title: {
    fontSize: 22,
    fontWeight: '700',
    marginBottom: 20,
  },

  card: {
    width: '100%',
    backgroundColor: '#111',
    padding: 20,
    borderRadius: 12,
    marginBottom: 20,
  },

  heading: {
    color: '#fff',
    fontSize: 18,
    fontWeight: '700',
    marginBottom: 10,
  },

  value: {
    color: '#00ff88',
    marginBottom: 12,
  },
});
