
package com.inocreativeStudio.IncreaseHeightWorkout.WalkandStep;

import android.content.pm.PackageManager;

import com.inocreativeStudio.IncreaseHeightWorkout.WalkandStep.services.AbstractStepDetectorService;
import com.inocreativeStudio.IncreaseHeightWorkout.WalkandStep.services.AccelerometerStepDetectorService;
import com.inocreativeStudio.IncreaseHeightWorkout.WalkandStep.services.HardwareStepDetectorService;
import com.inocreativeStudio.IncreaseHeightWorkout.WalkandStep.utils.AndroidVersionHelper;




public class Factory {



    public static Class<? extends AbstractStepDetectorService> getStepDetectorServiceClass(PackageManager pm){
        if(pm != null && AndroidVersionHelper.supportsStepDetector(pm)) {
            return HardwareStepDetectorService.class;
        }else{
            return AccelerometerStepDetectorService.class;
        }
    }
}
