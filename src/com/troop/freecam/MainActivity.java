package com.troop.freecam;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.troop.freecam.manager.ManualSaturationManager;
import com.troop.menu.ColorMenu;
import com.troop.menu.ExposureMenu;
import com.troop.menu.FlashMenu;
import com.troop.menu.FocusMenu;
import com.troop.menu.IppMenu;
import com.troop.menu.IsoMenu;
import com.troop.menu.PictureSizeMenu;
import com.troop.menu.PreviewSizeMenu;
import com.troop.menu.SceneMenu;
import com.troop.menu.WhiteBalanceMenu;
import com.troop.menu.switchcameramenu;

import java.util.List;

public class MainActivity extends Activity {

    //focus-mode-values=off,auto,infinity,infinity,portrait,extended
    //flash-mode-values=on,off,auto,torch
    //exposure-mode-values=manual,auto,night,backlighting,spotlight,sports,snow,beach,aperture,small-aperture
    //auto-convergence-mode-values=disable,frame,center,touch,manual
    //preview-format-values=yuv420sp,yuv420p,yuv420p
    //scene-mode-values=auto,closeup,landscape,aqua,sports,mood,night-portrait,night-indoor,fireworks,document,barcode,super-night,cine,old-film,action,beach,candlelight,night,party,portrait,snow,steadyphoto,sunset,theatre
    //supported-picture-sidebyside-size-values=4096x1536,3200x1200,2560x960,2048x768,1280x480,640x240
    //whitebalance-values=auto,daylight,cloudy-daylight,tungsten,fluorescent,incandescent,horizon,sunset,shade,twilight,warm-fluorescent
    //iso-mode-values=auto,100,200,400,800
    //exposure-mode-values=manual,auto,night,backlighting,spotlight,sports,snow,beach,aperture,small-aperture
    //focal-length=4.76
    //sharpness=100;
    //contrast=100
    //jpeg-quality=95;
    //brightness=50




	public CamPreview mPreview;
	private ImageButton shotButton;
	String imagepath;
	public Button flashButton;
    public Button focusButton;
    public Button sceneButton;
    public Button whitebalanceButton;
    public Button colorButton;
    public Button isoButton;
    public Button exposureButton;
    public Button switch3dButton;
    public Button pictureSizeButton;
    public Button previewSizeButton;
    public Button ippButton;
	Camera.Parameters paras;
    SurfaceHolder holder;
    CameraManager camMan;
    public TextView flashText;
    public TextView focusText;
    public TextView sceneText;
    public TextView whitebalanceText;
    public TextView colorText;
    public  ViewGroup appViewGroup;
    public SeekBar exposureSeekbar;
    public ImageButton thumbButton;
    public CheckBox manualExposure;
    TableRow exposureRow;
    public CheckBox manualShaprness;
    TableRow sharpnessRow;
    public SeekBar sharpnessSeekBar;
    TableLayout tableLayout;
    public TextView sharpnessTextView;
    public TextView exposureTextView;

    public  TextView contrastTextView;
    TableRow contrastRow;
    public CheckBox contrastRadioButton;
    public SeekBar contrastSeekBar;

    public TextView brightnessTextView;
    public SeekBar brightnessSeekBar;
    TableRow brightnessRow;
    CheckBox brightnessCheckBox;
    Button showHideMenuButton;
    ScrollView menuLayout;
    LinearLayout menuLinearLayout;

    public TextView saturationTextView;
    public SeekBar saturationSeekBar;
    public CheckBox saturationCheckBox;
    public TableRow saturationRow;


    int currentZoom = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        appViewGroup = (ViewGroup) inflater.inflate(R.layout.activity_main, null);




        setContentView(R.layout.activity_main);

		mPreview = (CamPreview) findViewById(R.id.camPreview1);
        mPreview.setKeepScreenOn(true);
        holder = mPreview.getHolder();
        camMan = new CameraManager(mPreview, this);

        mPreview.SetCameraManager(camMan);

        initButtons();

	}

    private  boolean hide = false;

    private void initButtons()
    {
        flashButton = (Button) findViewById(R.id.button_flash);
        flashButton.setOnClickListener(new FlashMenu(camMan, this));
        shotButton = (ImageButton) findViewById(R.id.imageButton1);
        shotButton.setOnClickListener(shotListner);
        focusButton = (Button) findViewById(R.id.button_focus);
        focusButton.setOnClickListener(new FocusMenu(camMan, this));
        sceneButton = (Button) findViewById(R.id.buttonScene);
        sceneButton.setOnClickListener(new SceneMenu(camMan, this));
        whitebalanceButton = (Button) findViewById(R.id.buttonwhiteBalance);
        whitebalanceButton.setOnClickListener(new WhiteBalanceMenu(camMan, this));
        colorButton = (Button) findViewById(R.id.buttoncolor);
        colorButton.setOnClickListener(new ColorMenu(camMan, this));
        isoButton = (Button) findViewById(R.id.buttoniso);
        isoButton.setOnClickListener(new IsoMenu(camMan, this));
        exposureButton = (Button) findViewById(R.id.button_exposure);
        exposureButton.setOnClickListener(new ExposureMenu(camMan, this));
        pictureSizeButton = (Button) findViewById(R.id.button_pictureSize);
        pictureSizeButton.setOnClickListener(new PictureSizeMenu(camMan, this));
        previewSizeButton = (Button)findViewById(R.id.button_previewsize);
        previewSizeButton.setOnClickListener(new PreviewSizeMenu(camMan,this));
        ippButton = (Button)findViewById(R.id.button_ipp);
        ippButton.setOnClickListener(new IppMenu(camMan, this));

        showHideMenuButton = (Button)findViewById(R.id.button_ShowHideMenu);
        showHideMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (hide == false){
                    menuLayout.removeView(menuLinearLayout);
                    hide = true;
                }
                else {
                    menuLayout.addView(menuLinearLayout);
                    hide = false;
                }

            }
        });
        menuLayout = (ScrollView) findViewById(R.id.scrollView_menu);
        menuLinearLayout = (LinearLayout)findViewById(R.id.linearLayoutMenu);



        //exposureSeekbar.setVisibility(View.INVISIBLE);
        switch3dButton = (Button) findViewById(R.id.button_switch3d);
        switch3dButton.setOnClickListener(new switchcameramenu(camMan, this));
        thumbButton = (ImageButton)findViewById(R.id.imageButton_thumb);
        thumbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://" + camMan.lastPicturePath);
                Intent i=new Intent(Intent.ACTION_VIEW, uri);
                i.setType("image/*");
                startActivity(i);
            }
        });

        tableLayout = (TableLayout) findViewById(R.id.tableVIEW);


        exposureTextView = (TextView) findViewById(R.id.textViewexposure);
        exposureSeekbar  = (SeekBar) findViewById(R.id.seekBar_exposure);
        exposureSeekbar.setProgress(30);
        exposureSeekbar.setOnSeekBarChangeListener(camMan.manualExposureManager);
        exposureRow = (TableRow) findViewById(R.id.tableRowExposure);
        tableLayout.removeView(exposureRow);
        manualExposure = (CheckBox)findViewById(R.id.checkBox_exposureManual);
        manualExposure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (manualExposure.isChecked())
                {
                    tableLayout.addView(exposureRow);
                }
                else
                {
                    tableLayout.removeView(exposureRow);
                }
            }
        });


        sharpnessTextView = (TextView)findViewById(R.id.textView_sharpness);
        sharpnessSeekBar = (SeekBar)findViewById(R.id.seekBar_sharpness);
        sharpnessSeekBar.setProgress(100);
        sharpnessSeekBar.setOnSeekBarChangeListener(camMan.manualSharpnessManager);
        sharpnessRow = (TableRow) findViewById(R.id.tableRowSharpness);
        tableLayout.removeView(sharpnessRow);
        manualShaprness = (CheckBox) findViewById(R.id.checkBox_sharpness);
        manualShaprness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (manualShaprness.isChecked())
                {
                    tableLayout.addView(sharpnessRow);
                    //manualShaprness.setVisibility(View.VISIBLE);

                    //sharpnessRow.bringToFront();
                }
                else
                {
                    tableLayout.removeView(sharpnessRow);
                }
                //sharpnessRow.invalidate();
            }
        });

        contrastRow = (TableRow)findViewById(R.id.tableRowContrast);

        contrastSeekBar = (SeekBar) findViewById(R.id.seekBar_contrast);
        contrastSeekBar.setProgress(100);
        contrastSeekBar.setOnSeekBarChangeListener(camMan.manualContrastManager);

        contrastTextView = (TextView) findViewById(R.id.textView_contrast);
        contrastRadioButton = (CheckBox)findViewById(R.id.radioButton_contrast);
        contrastRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contrastRadioButton.isChecked())
                    tableLayout.addView(contrastRow);
                else
                    tableLayout.removeView(contrastRow);
            }
        });
        tableLayout.removeView(contrastRow);


        brightnessRow = (TableRow)findViewById(R.id.tableRowBrightness);
        brightnessSeekBar = (SeekBar)findViewById(R.id.seekBar_brightness);
        brightnessCheckBox = (CheckBox)findViewById(R.id.checkBox_brightness);
        brightnessTextView = (TextView)(findViewById(R.id.textView_brightness));
        brightnessSeekBar.setOnSeekBarChangeListener(camMan.manualBrightnessManager);

        brightnessCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (brightnessCheckBox.isChecked())
                    tableLayout.addView(brightnessRow);
                else
                    tableLayout.removeView(brightnessRow);
            }
        });
        tableLayout.removeView(brightnessRow);

        saturationCheckBox = (CheckBox) findViewById(R.id.checkBox_saturation);
        saturationRow = (TableRow)findViewById(R.id.tableRowsaturation);
        saturationTextView = (TextView)findViewById(R.id.textViewSaturation);
        saturationSeekBar = (SeekBar)findViewById(R.id.seekBarSaturation);
        saturationSeekBar.setProgress(100);
        saturationSeekBar.setOnSeekBarChangeListener(new ManualSaturationManager(camMan));
        saturationCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (saturationCheckBox.isChecked())
                    tableLayout.addView(saturationRow);
                else
                    tableLayout.removeView(saturationRow);
            }
        });
        tableLayout.removeView(saturationRow);

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int key = event.getKeyCode();

            if(key == KeyEvent.KEYCODE_VOLUME_UP)
            {
                camMan.zoomManager.setZoom(1);
            }
            else if(key == KeyEvent.KEYCODE_VOLUME_DOWN)
            {
                camMan.zoomManager.setZoom(-1);
            }
            else if(key == KeyEvent.KEYCODE_3D_MODE)
            {
                camMan.StartTakePicture();
            }
            else
            {
                super.dispatchKeyEvent(event);
            }

        return true;
    }


    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	View.OnClickListener shotListner = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			camMan.StartTakePicture();
		}
	};



}
	



