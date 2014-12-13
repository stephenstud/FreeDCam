package com.troop.freedcam.camera2.parameters.modes;

import android.annotation.TargetApi;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Build;
import android.util.Size;

import com.troop.freedcam.camera2.BaseCameraHolderApi2;

/**
 * Created by troop on 12.12.2014.
 */
public class PictureFormatParameterApi2 extends BaseModeApi2 {
    public PictureFormatParameterApi2(BaseCameraHolderApi2 baseCameraHolderApi2)
    {
        super(baseCameraHolderApi2);
    }

    @Override
    public boolean IsSupported() {
        return true;
    }

    @Override
    public void SetValue(String valueToSet, boolean setToCamera)
    {
        super.SetValue(valueToSet, setToCamera);
    }

    @Override
    public String GetValue() {
        return super.GetValue();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public String[] GetValues()
    {

        return super.GetValues();
    }
}