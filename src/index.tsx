const JcTextDisplay = require('./NativeJcTextDisplay').default;

export function multiply(a: number, b: number): number {
  return JcTextDisplay.multiply(a, b);
}

export function greet(name: string): string {
  return JcTextDisplay.greet(name);
}
