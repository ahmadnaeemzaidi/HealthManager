
package com.techgeeks.workout.WalkandStep;

import android.content.pm.PackageManager;

import com.techgeeks.workout.WalkandStep.services.AbstractStepDetectorService;
import com.techgeeks.workout.WalkandStep.services.AccelerometerStepDetectorService;
import com.techgeeks.workout.WalkandStep.services.HardwareStepDetectorService;
import com.techgeeks.workout.WalkandStep.utils.AndroidVersionHelper;




public class Factory {



    public static Class<? extends AbstractStepDetectorService> getStepDetectorServiceClass(PackageManager pm){
        if(pm != null && AndroidVersionHelper.supportsStepDetector(pm)) {
            return HardwareStepDetectorService.class;
        }else{
            return AccelerometerStepDetectorService.class;
        }
    }
}
