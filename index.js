/**
 * Created by lcg on 2016/10/10.
 */

import { NativeModules } from 'react-native';

const NativeSpeaker = NativeModules.Speaker;

const Speaker = {
    switchSpeaker: (speaker) => {
        NativeSpeaker.switchSpeaker(speaker);
    },
    getSpeaker: () => {
        return new Promise((resolve, reject) => {
            NativeSpeaker.getSpeaker((err,speaker) => {
                resolve(speaker);
            });
        });
    },
    hasRecordPermissionAndroid: () => {
        return new Promise((resolve, reject) => {
            NativeSpeaker.hasRecordPermission((err,result) => {
                if (err){
                    reject(err)
                }
                else {
                    resolve(result);
                }
            });
        });
    },
    requestRecordPermissionAndroid: () => {
        return new Promise((resolve, reject) => {
            NativeSpeaker.requestRecordPermission((err) => {
                if (err){
                    reject(err)
                }
                else {
                    resolve();
                }
            });
        });
    }
};

const SpeakerType = {
    NORMAL: "speaker",
    EARPIECE: "earpiece",
    UNKNOWN: "unknown"
};

export {
    Speaker,
    SpeakerType
};