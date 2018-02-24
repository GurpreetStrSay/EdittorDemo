package com.example.instasite.edittordemo;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.instasite.MarshmallowPermissions.Permission;
import com.example.instasite.MarshmallowPermissions.PermissionActivity;
import com.example.instasite.MarshmallowPermissions.PermissionUtils;
import com.example.instasite.MarshmallowPermissions.SinglePermissionCallback;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

public class MainActivity extends PermissionActivity implements View.OnLongClickListener, View.OnTouchListener, View.OnClickListener {
    private ImageView txt_addText, txt_undoText, txt_moveArrow, txt_DrawText, txt_Save, txt_download, txt_UndoDraw;
    private AlertDialog alertDialog;
    private String textToRight = "";
    private TextView _view, newview = null;
    private ViewGroup _root;
    private EditText txtsize;
    private String textColor = "00000000";
    private String textcolorpicked = "000000";
    private int _xDelta;
    private EditText edit_addText;
    private int _yDelta;
    private HashMap<String, TextView> editTextMap;
    private ImageView img, colorpicker;
    int count = 0;
    private ZoomLayout zoomlayout;
    private DrawingView removePath, newDrwa;

    private static final int CAMERA_REQUEST = 1888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = (ImageView) findViewById(R.id.img);

        initViews();

        zoomlayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                zoomlayout.init(MainActivity.this);
                return false;
            }
        });


        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            img.setImageBitmap(bitmap);

            /*Intent i = new Intent(CameraActivity.this, MainActivity.class);
            Uri uri = data.getData();
            i.putExtra("uri", uri);
            //i.putExtra("data", (Bitmap) data.getExtras().get("data"));
            startActivity(i);*/

        }
    }


    private void initViews() {
        removePath = new DrawingView(MainActivity.this);
//        drawingView = (DrawingView) findViewById(R.id.drawingView);
        txt_undoText = (ImageView) findViewById(R.id.txt_undoText);
        txt_addText = (ImageView) findViewById(R.id.txt_addText);
        txt_download = (ImageView) findViewById(R.id.txt_download);

//        cardView = findViewById(R.id.cardView);
//        txt_UndoDraw = findViewById(R.id.txt_UndoDraw);
        txt_moveArrow = (ImageView) findViewById(R.id.txt_moveArrow);
        txt_Save = (ImageView) findViewById(R.id.txt_Save);
        txt_DrawText = (ImageView) findViewById(R.id.txt_DrawText);
        _root = (ViewGroup) findViewById(R.id.root);
        editTextMap = new HashMap<>();
        zoomlayout = (ZoomLayout) findViewById(R.id.mroot);
        txt_addText.setOnClickListener(MainActivity.this);
        txt_DrawText.setOnClickListener(MainActivity.this);
//        txt_UndoDraw.setOnClickListener(MainActivity.this);
        txt_undoText.setOnClickListener(MainActivity.this);
        txt_Save.setOnClickListener(MainActivity.this);
        txt_download.setOnClickListener(MainActivity.this);
        txt_moveArrow.setOnClickListener(MainActivity.this);
    }

    private void DialogViewCorrect() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_text, null);
        dialogBuilder.setView(dialogView);
//        correct = dialogView.findViewById(R.id.correct);
//        correct.setText(correctAnswer);
        ImageView color = (ImageView) dialogView.findViewById(R.id.color);
        colorpicker = (ImageView) dialogView.findViewById(R.id.colorpicker);
        Button btn_submitText = (Button) dialogView.findViewById(R.id.btn_submitText);
        txtsize = (EditText) dialogView.findViewById(R.id.txtsize);
        edit_addText = (EditText) dialogView.findViewById(R.id.edit_addText);
        if (newview != null) {
            edit_addText.setText(newview.getTag().toString());
        }
        alertDialog = dialogBuilder.create();
//        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
//        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();
        btn_submitText.setOnClickListener(MainActivity.this);
        color.setOnClickListener(MainActivity.this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                _xDelta = X - lParams.leftMargin;
                _yDelta = Y - lParams.topMargin;
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                layoutParams.leftMargin = X - _xDelta;
                layoutParams.topMargin = Y - _yDelta;
                layoutParams.rightMargin = -250;
                layoutParams.bottomMargin = -250;
                view.setLayoutParams(layoutParams);
                break;
        }
        _root.invalidate();
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submitText:
//                drawingView.setVisibility(View.INVISIBLE);

                textToRight = edit_addText.getText().toString();
                if (textToRight.equalsIgnoreCase("")) {
                    Toast.makeText(MainActivity.this, "Please fill text", Toast.LENGTH_SHORT).show();
                } else {
                    _view = null;
                    if (newview != null) {
                        _view = newview;
                        newview = null;
                        _view.setText(edit_addText.getText().toString());
                        _view.setTag(edit_addText.getText().toString());

                        if (textColor.equalsIgnoreCase("")) {
                            textcolorpicked = "000000";
                        } else {
                            textcolorpicked = textColor + "";
                            textcolorpicked = textcolorpicked.substring(2, textcolorpicked.length());
                        }


                        _view.setTextColor(Color.parseColor("#" + textcolorpicked));
                        if (txtsize.getText().toString().equalsIgnoreCase("")) {
                            _view.setTextSize(18);
                        } else {
                            int txt = Integer.parseInt(txtsize.getText().toString());
                            _view.setTextSize(txt);
                        }
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.leftMargin = 50;
                        layoutParams.topMargin = 50;
                        layoutParams.bottomMargin = -20;
                        layoutParams.rightMargin = -20;
                        _view.setLayoutParams(layoutParams);

                        _view.setOnTouchListener(MainActivity.this);
                        _view.setOnLongClickListener(MainActivity.this);
//                        _root.addView(_view);
                    } else {
                        _view = new TextView(MainActivity.this);
                        _view.setText(edit_addText.getText().toString());
                        _view.setTag(edit_addText.getText().toString());
                        _view.setTextColor(Color.parseColor("#" + textcolorpicked));
                        if (txtsize.getText().toString().equalsIgnoreCase("")) {
                            _view.setTextSize(18);
                        } else {
                            int txt = Integer.parseInt(txtsize.getText().toString());
                            _view.setTextSize(txt);
                        }

                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.leftMargin = 50;
                        layoutParams.topMargin = 50;
                        layoutParams.bottomMargin = -20;
                        layoutParams.rightMargin = -20;
                        _view.setLayoutParams(layoutParams);

                        _view.setOnTouchListener(MainActivity.this);
                        _view.setOnLongClickListener(MainActivity.this);
                        _root.addView(_view);
                    }


                    editTextMap.put(count + "", _view);
                    count++;
                }

                alertDialog.dismiss();
                break;

            case R.id.txt_addText:
//                drawingView.setVisibility(View.INVISIBLE);
                DialogViewCorrect();
                break;
            case R.id.txt_DrawText:
                newDrwa = new DrawingView(this);
//                drawingView.setVisibility(View.VISIBLE);

                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams
                        (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                newDrwa.setLayoutParams(layoutParams);

                _root.addView(newDrwa);
                break;

            case R.id.color:
                ColorPickerDialogBuilder
                        .with(this)
                        .setTitle("Choose color")
                        .initialColor(R.color.colorAccent)
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setOnColorSelectedListener(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int selectedColor) {
//                                toast("onColorSelected: 0x" + Integer.toHexString(selectedColor));
                            }
                        })
                        .setPositiveButton("ok", new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                textColor = Integer.toHexString(selectedColor);
                                textcolorpicked = textColor + "";
                                if (textcolorpicked.length() == 8) {
                                    textcolorpicked = textcolorpicked.substring(2, textcolorpicked.length());
                                } else if (textcolorpicked.length() == 5) {
                                    textcolorpicked = "0" + textColor;
                                } else if (textcolorpicked.length() == 4) {
                                    textcolorpicked = "00" + textColor;
                                } else if (textcolorpicked.length() == 3) {
                                    textcolorpicked = "000" + textColor;
                                } else if (textcolorpicked.length() == 2) {
                                    textcolorpicked = "0000" + textColor;
                                } else if (textcolorpicked.length() == 1) {
                                    textcolorpicked = "00000" + textColor;
                                }
                                colorpicker.setBackgroundColor(Color.parseColor("#" + textcolorpicked));

                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .build()
                        .show();
                break;
            case R.id.txt_download:
                if (PermissionUtils.isMarshmallowOrHigher()) {
                    requestPermission(Permission.WRITE_EXTERNAL_STORAGE, new SinglePermissionCallback() {
                        @Override
                        public void onPermissionResult(boolean permissionGranted, boolean isPermissionDeniedForever) {
                            if (permissionGranted) {


                            }
                        }
                    });
                }
                downLoadImage();
                break;
            case R.id.txt_Save:
                _root.setDrawingCacheEnabled(true);
                Bitmap bitmap = Bitmap.createBitmap(_root.getDrawingCache());
                _root.setDrawingCacheEnabled(false);
                BitmapDrawable background = new BitmapDrawable(bitmap);
                for (int i = 1; i < _root.getChildCount(); i++) {

                    _root.removeViewAt(i);
                }
                img.setImageBitmap(bitmap);
                Toast.makeText(MainActivity.this, "Changes Saved", Toast.LENGTH_LONG).show();
                break;
            case R.id.txt_undoText:
                int size = _root.getChildCount();
                if (size > 1) {

                    _root.removeViewAt(size - 1);
                }

                break;
            case R.id.txt_moveArrow:
                moveArrow();
                break;
        }
    }

    public void moveArrow() {
        try {
            newDrwa.moveArrow();
            newDrwa.setOnTouchListener(MainActivity.this);
            newDrwa.setOnLongClickListener(MainActivity.this);
            newDrwa = null;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void downLoadImage() {
        File root = Environment.getExternalStorageDirectory();
        File cachePath = new File(root.getAbsolutePath() + "/DCIM/Camera/image" + System.currentTimeMillis() + ".jpg");

        try {
            img.setDrawingCacheEnabled(true);
            Bitmap bitmap = img.getDrawingCache();
            cachePath.createNewFile();
            FileOutputStream ostream = new FileOutputStream(cachePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
            ostream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(MainActivity.this, "Image Downloaded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onLongClick(View v) {

        newview = (TextView) v;
        DialogViewCorrect();

        return true;
    }
}