package com.troop.freecam.menu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.troop.freecam.MainActivity;
import com.troop.freecam.R;
import com.troop.freecam.camera.CameraManager;
import com.troop.freecam.controls.MenuItemControl;
import com.troop.freecam.manager.camera_parameters.ParametersManager;
import com.troop.freecam.menu.popupmenu.AFPriorityMenu;
import com.troop.freecam.menu.popupmenu.ColorMenu;
import com.troop.freecam.menu.popupmenu.ExposureMenu;
import com.troop.freecam.menu.popupmenu.IsoMenu;
import com.troop.freecam.menu.popupmenu.MeteringMenu;
import com.troop.freecam.menu.popupmenu.SceneMenu;
import com.troop.freecam.menu.popupmenu.WhiteBalanceMenu;

/**
 * Created by troop on 02.01.14.
 */
public class AutoMenuControl extends LinearLayout
{
    MenuItemControl switchAF;
    MenuItemControl switchScene;
    MenuItemControl switchWhiteBalance;
    MenuItemControl switchColor;
    MenuItemControl switchIso;
    MenuItemControl switchExposure;
    MenuItemControl switchMetering;
    CameraManager camMan;
    MainActivity activity;

    /*public AutoMenuControl(CameraManager camMan, MainActivity activity)
    {
        super(camMan,activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.automenufragment, container, false);
        init();
        return view;
    }*/

    public AutoMenuControl(Context context) {
        super(context);
    }

    public AutoMenuControl(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public AutoMenuControl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void SetCameraManager(CameraManager camMan, MainActivity activity)
    {
        this.camMan = camMan;
        this.activity = activity;
        init();
    }

    private void init()
    {
        LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.automenufragment, this);

        switchAF = (MenuItemControl)findViewById(R.id.switch_AfPriority_control);
        switchAF.SetOnClickListner(new AFPriorityMenu(camMan,activity));

        switchScene = (MenuItemControl)findViewById(R.id.switch_scenemode_control);
        switchScene.SetOnClickListner(new SceneMenu(camMan,activity));

        switchWhiteBalance = (MenuItemControl)findViewById(R.id.switch_wbModes_control);
        switchWhiteBalance.SetOnClickListner(new WhiteBalanceMenu(camMan,activity));

        switchColor = (MenuItemControl)findViewById(R.id.switch_colormode_control);
        switchColor.SetOnClickListner(new ColorMenu(camMan,activity));

        switchIso = (MenuItemControl)findViewById(R.id.switch_isomode_control);
        switchIso.SetOnClickListner(new IsoMenu(camMan,activity));

        switchExposure = (MenuItemControl)findViewById(R.id.switch_exposuremode_control);
        switchExposure.SetOnClickListner(new ExposureMenu(camMan,activity));

        switchMetering = (MenuItemControl)findViewById(R.id.switch_meteringmode_control);
        switchMetering.SetOnClickListner(new MeteringMenu(camMan,activity));
    }

    public void UpdateUI(boolean settingsReloaded, ParametersManager.enumParameters paras)
    {
        if (settingsReloaded)
            checkVisibility();
        updateValues(paras);
    }

    private void checkVisibility()
    {
        if (camMan.parametersManager.getSupportAfpPriority())
            switchAF.setVisibility(View.VISIBLE);
        else
            switchAF.setVisibility(View.GONE);

        if (!camMan.parametersManager.getSupportAutoExposure())
        {
            switchMetering.setVisibility(View.GONE);
        }
        else
            switchMetering.setVisibility(View.VISIBLE);
        if (!camMan.parametersManager.getSupportWhiteBalance())
        {
            switchWhiteBalance.setVisibility(View.GONE);
        }
        else
            switchWhiteBalance.setVisibility(View.VISIBLE);
        if (camMan.parametersManager.getSupportIso())
            switchIso.setVisibility(View.VISIBLE);
        else
            switchIso.setVisibility(View.GONE);
        if (camMan.parametersManager.getSupportExposureMode())
            switchExposure.setVisibility(View.VISIBLE);
        else
            switchExposure.setVisibility(View.GONE);
        if (camMan.parametersManager.getSupportScene())
            switchScene.setVisibility(View.VISIBLE);
        else
            switchScene.setVisibility(View.GONE);
        //findViewById(R.id.auto_menu_fragment_layout).requestLayout();
    }

    private void updateValues(ParametersManager.enumParameters paras) {
        if (camMan.parametersManager.getSupportScene() && (paras == ParametersManager.enumParameters.Scene) || paras == ParametersManager.enumParameters.All)
            switchScene.SetButtonText(camMan.parametersManager.getParameters().getSceneMode());
        //TODO add ColoreMOdes To parametersMAnager
        switchColor.SetButtonText(camMan.parametersManager.getParameters().getColorEffect());
        if (camMan.parametersManager.getSupportExposureMode() && (paras == ParametersManager.enumParameters.ExposureMode) || paras == ParametersManager.enumParameters.All)
            switchExposure.SetButtonText(camMan.parametersManager.ExposureMode.get());
        if (camMan.parametersManager.getSupportIso()&& (paras == ParametersManager.enumParameters.Iso || paras == ParametersManager.enumParameters.All))
            switchIso.SetButtonText(camMan.parametersManager.Iso.get());
        //AF Priority
        if (camMan.parametersManager.getSupportAfpPriority()&& (paras == ParametersManager.enumParameters.AfPriority || paras == ParametersManager.enumParameters.All))
        {
            switchAF.SetButtonText(camMan.parametersManager.AfPriority.Get());
        }

        //AutoExposure
        if (camMan.parametersManager.getSupportAutoExposure()&& (paras == ParametersManager.enumParameters.ExposureMode || paras == ParametersManager.enumParameters.All))
        {
            switchMetering.SetButtonText(camMan.parametersManager.getParameters().get("auto-exposure"));
        }
        if (camMan.parametersManager.getSupportWhiteBalance()&& (paras == ParametersManager.enumParameters.WhiteBalanceMode || paras == ParametersManager.enumParameters.All))
        {
            switchWhiteBalance.SetButtonText(camMan.parametersManager.WhiteBalance.get());
        }
        if (paras == ParametersManager.enumParameters.Color || paras == ParametersManager.enumParameters.All)
            switchColor.SetButtonText(camMan.parametersManager.getParameters().getColorEffect());
    }
}