
#ifdef RCT_NEW_ARCH_ENABLED
#import "RNJcTextDisplaySpec.h"

@interface JcTextDisplay : NSObject <NativeJcTextDisplaySpec>
#else
#import <React/RCTBridgeModule.h>

@interface JcTextDisplay : NSObject <RCTBridgeModule>
#endif

@end
