import { useEffect, useState } from 'react';
import { Text, View, StyleSheet } from 'react-native';

import Analytics from 'rn-event-log';

import { DebugOverlay } from './DebugOverlay';

export default function App() {
  const [session, setSession] = useState<any>(null);

  const [events, setEvents] = useState<any[]>([]);

  useEffect(() => {
    Analytics.init({
      sessionStrategy: 'timeout',
      sessionTimeout: 30000,
      autoTrackScreens: true,
      debug: true,
    });

    Analytics.track('button_click');

    const loadEvents = async () => {
      const storedEvents = await Analytics.getStoredEvents();

      setEvents(storedEvents);
    };

    loadEvents();

    const interval = setInterval(() => {
      setSession(Analytics.getSession());
    }, 1000);

    return () => {
      clearInterval(interval);
    };
  }, []);

  console.log(session, events);

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

      {/* <View style={styles.card}>
        <Text style={styles.heading}>Stored Events</Text>

        {events.map((item) => (
          <View key={item.id} style={styles.event}>
            <Text style={styles.eventName}>{item.event}</Text>

            <Text style={styles.eventProps}>{item.properties}</Text>
          </View>
        ))}
      </View> */}

      <DebugOverlay />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    height: '100%',
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

  event: {
    marginBottom: 12,
    borderBottomWidth: 1,
    borderBottomColor: '#333',
    paddingBottom: 10,
  },

  eventName: {
    color: '#fff',
    fontWeight: '700',
  },

  eventProps: {
    color: '#00ff88',
    marginTop: 4,
  },
});
