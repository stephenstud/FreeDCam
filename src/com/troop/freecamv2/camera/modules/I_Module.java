package com.troop.freecamv2.camera.modules;

/**
 * Created by troop on 15.08.2014.
 */
public interface I_Module
{
    public String ModuleName();

    /**
     * Let the Module start its work
     */
    public void DoWork();
    public boolean IsWorking();
}