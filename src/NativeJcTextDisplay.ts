import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';

export interface Spec extends TurboModule {
  multiply(a: number, b: number): number;
  greet(name: string): string;
  turnLedsOffAndDisableCallbacks(): void;
  startLedSolid(
    ledColor: number,
    serialUsbDevice?: any,
    rgbColorForSerial?: string
  ): void;
  startLedBlinking(
    ledColor: number,
    serialUsbDevice?: any,
    rgbColorForSerial?: string
  ): void;
  startHolidayBlinking(serialUsbDevice?: any): void;
  loopInCyclingColors(): void;
  RED: number;
  GREEN: number;
  BLUE: number;
  TEAL: number;
  PINK: number;
  YELLOW: number;
  WHITE: number;
}

const JcTextDisplay = TurboModuleRegistry.getEnforcing<Spec>('JcTextDisplay');

export const { RED, GREEN, BLUE, TEAL, PINK, YELLOW, WHITE } = JcTextDisplay;

export default JcTextDisplay;
