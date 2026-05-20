import { useEffect, useState } from 'react';

import { View, Text, ScrollView } from 'react-native';

import Analytics from 'rn-event-log';

export function DebugOverlay() {
  const [logs, setLogs] = useState<{ message: string }[]>([]);

  useEffect(() => {
    const subscription = Analytics.onDebug((event) => {
      setLogs((prev) => [event, ...prev]);
    });

    return () => {
      subscription.remove();
    };
  }, []);

  console.log(logs);

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
              color: 'white',
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
