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
  getRED(): number;
  getGREEN(): number;
  getBLUE(): number;
  getTEAL(): number;
  getPINK(): number;
  getYELLOW(): number;
  getWHITE(): number;
}

export default TurboModuleRegistry.getEnforcing<Spec>('JcTextDisplay');
