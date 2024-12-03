import { StyleSheet, View, Text, Button } from 'react-native';
import JcTextDisplay from 'react-native-jc-text-display';

const { multiply, greet } = JcTextDisplay;

const result = multiply(3, 7);

export default function App() {
  const handleSolidRed = () => {
    JcTextDisplay.startLedSolid(JcTextDisplay.getRED());
  };

  const handleBlinkingBlue = () => {
    JcTextDisplay.startLedBlinking(JcTextDisplay.getBLUE());
  };

  const handleCycleColors = () => {
    JcTextDisplay.loopInCyclingColors();
  };

  const handleHolidayMode = () => {
    JcTextDisplay.startHolidayBlinking();
  };

  const handleTurnOff = () => {
    JcTextDisplay.turnLedsOffAndDisableCallbacks();
  };

  return (
    <View style={styles.container}>
      <Text style={styles.bigText}>Result: {result}</Text>
      <Text style={styles.bigText}>
        Greeting: {greet('SANAY KA NA MAG KOTLIN')}
      </Text>

      <View style={styles.buttonContainer}>
        <Button title="Solid Red" onPress={handleSolidRed} />
        <Button title="Blinking Blue" onPress={handleBlinkingBlue} />
        <Button title="Cycle Colors" onPress={handleCycleColors} />
        <Button title="Holiday Mode" onPress={handleHolidayMode} />
        <Button title="Turn Off LEDs" onPress={handleTurnOff} />
      </View>
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
    marginBottom: 20,
  },
  buttonContainer: {
    gap: 10,
    width: '80%',
  },
});
