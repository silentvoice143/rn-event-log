import { useEffect, useState } from 'react';

import { View, Text, ScrollView } from 'react-native';

import { subscribeDebug } from './DebugStore';

export function DebugOverlay() {
  const [logs, setLogs] = useState<any[]>([]);

  useEffect(() => {
    return subscribeDebug((event: Event) => {
      setLogs((prev) => [event, ...prev]);
    });
  }, []);

  return (
    <View
      style={{
        position: 'absolute',
        bottom: 0,
        left: 0,
        right: 0,
        height: 250,
        backgroundColor: 'black',
        padding: 10,
      }}
    >
      <ScrollView>
        {logs.map((log, index) => (
          <Text
            key={index}
            style={{
              color: 'lime',
              marginBottom: 4,
              fontSize: 12,
            }}
          >
            {log.message}
          </Text>
        ))}
      </ScrollView>
    </View>
  );
}
