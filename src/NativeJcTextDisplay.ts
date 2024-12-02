import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';

export interface Spec extends TurboModule {
  multiply(a: number, b: number): number;
  greet(name: string): string;
  turnLedsOffAndDisableCallbacks(): void;
  startLedSolid(
    ledColor: number,
    serialUsbDevice?: string,
    rgbColorForSerial?: string
  ): void;
  startLedBlinking(
    ledColor: number,
    serialUsbDevice?: string,
    rgbColorForSerial?: string
  ): void;
  startHolidayBlinking(serialUsbDevice?: string): void;
  loopInCyclingColors(): void;
  RED: number;
  GREEN: number;
  BLUE: number;
  TEAL: number;
  PINK: number;
  YELLOW: number;
  WHITE: number;
}

export default TurboModuleRegistry.getEnforcing<Spec>('JcTextDisplay');
