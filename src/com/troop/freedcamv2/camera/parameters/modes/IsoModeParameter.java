package com.troop.freedcamv2.camera.parameters.modes;

import android.hardware.Camera;

import com.troop.freedcamv2.camera.BaseCameraHolder;
import com.troop.freedcamv2.camera.parameters.I_ParameterChanged;

/**
 * Created by troop on 17.08.2014.
 */
public class IsoModeParameter extends BaseModeParameter
{
    BaseCameraHolder baseCameraHolder;

    public IsoModeParameter(Camera.Parameters parameters, I_ParameterChanged parameterChanged, String value, String values)
    {
        super(parameters, parameterChanged, value, values);
        isIso();
    }

    public IsoModeParameter(Camera.Parameters parameters, I_ParameterChanged parameterChanged, String value, String values, BaseCameraHolder baseCameraHolder)
    {
        super(parameters, parameterChanged, value, values);
        isIso();
        this.baseCameraHolder = baseCameraHolder;
    }

    private void isIso()
    {
        try
        {
            String isomodes = parameters.get("iso-mode-values");
            if (isomodes != null && !isomodes.equals("")) {
                this.value = "iso";
                this.values = "iso-mode-values";
                isSupported = true;
            }
        }
        catch (Exception ex){}
        if (!isSupported)
        {
            try {
                String isomodes = parameters.get("iso-values");
                if (isomodes != null && !isomodes.equals("")) {
                    this.value = "iso";
                    this.values = "iso-values";
                    isSupported = true;
                }
            } catch (Exception ex) {}
        }
        if(!isSupported)
        {
            try {
                String isomodes = parameters.get("iso-speed-values");
                if (isomodes != null && !isomodes.equals("")) {
                    this.value = "iso-speed";
                    this.values = "iso-speed-values";
                    isSupported = true;
                }
            } catch (Exception ex) {}
        }
    }

    @Override
    public void SetValue(String valueToSet, boolean setToCam)
    {
        if (setToCam)
        {
            baseCameraHolder.StopPreview();
            super.SetValue(valueToSet, setToCam);
            baseCameraHolder.StartPreview();
        }
        else
            super.SetValue(valueToSet, setToCam);
    }
}