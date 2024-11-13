import { StyleSheet, View, Text } from 'react-native';
import { multiply, greet } from 'react-native-jc-text-display';

const result = multiply(3, 7);

export default function App() {
  return (
    <View style={styles.container}>
      <Text style={styles.bigText}>Result: {result}</Text>
      <Text style={styles.bigText}>
        Greeting: {greet('SANAY KA NA MAG KOTLIN')}
      </Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
  bigText: {
    fontSize: 20,
  },
});
