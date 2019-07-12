//
//  RCTSpeaker.m
//  RCTSpeaker
//
//  Created by lcg on 2016/10/10.
//  Copyright © 2016年 Anren. All rights reserved.
//

#import "RCTSpeaker.h"
#import <AVFoundation/AVFoundation.h>

#define SPEAKER @"speaker"
#define EARPIECE @"earpiece"
#define UNKNOWN @"unknown"

@implementation RCTSpeaker

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(switchSpeaker:(NSString*)speaker)
{
    if ([speaker isEqualToString:SPEAKER])
    {
        [[AVAudioSession sharedInstance] setCategory:AVAudioSessionCategoryPlayback error:nil];
    }
    else if ([speaker isEqualToString:EARPIECE])
    {
        [[AVAudioSession sharedInstance] setCategory:AVAudioSessionCategoryPlayAndRecord error:nil];
    }
    else {
        
    }
}

RCT_EXPORT_METHOD(getSpeaker:(RCTResponseSenderBlock)callback)
{
    NSString *category = [AVAudioSession sharedInstance].category;
    
    if ([category isEqualToString:AVAudioSessionCategoryPlayback])
    {
        callback(@[[NSNull null],SPEAKER]);
    }
    else if ([category isEqualToString:AVAudioSessionCategoryPlayAndRecord])
    {
        callback(@[[NSNull null],EARPIECE]);
    }
    else
    {
        callback(@[[NSNull null],UNKNOWN]);
    }
}

@end
