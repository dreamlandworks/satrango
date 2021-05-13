package com.satrangolimitless.Vendor_UI.vendor_profile;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.satrangolimitless.R;
import com.satrangolimitless.Utils.MultipartUtility;
import com.satrangolimitless.Utils.Utils;
import com.satrangolimitless.session.Session_vendor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.satrangolimitless.Utils.Base_Url.BaseUrl;
import static com.satrangolimitless.Utils.Base_Url.Upload_video;

public class VendorProfilFourActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    Button CAMERANEXT, btnstart;
    ProgressDialog progressDialog;
    public static SurfaceView mSurfaceView;
    public static SurfaceHolder mSurfaceHolder;
    ImageView img_start;
    private static final String TAG = "VendorProfilFourActivity";
    private static Camera mServiceCamera;

    private MediaRecorder mMediaRecorder;
    File mOutputFile;
    File uploadFileI;
    String img_path = "", Result, msg;
    TextView txtname, txttimer;
    public int counter;
    Session_vendor session_vendor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_profil_four);

        session_vendor = new Session_vendor(getApplicationContext());
        img_start = findViewById(R.id.img_start);
        btnstart = findViewById(R.id.btnstart);
        txtname = findViewById(R.id.txtname);
        txttimer = findViewById(R.id.txttimer);
        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView1);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);


        img_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                img_start.setImageResource(R.drawable.ic_baseline_stop_circle_24);

                timer();
                startRecording();
                img_start.setClickable(false);
                btnstart.setClickable(false);
            }
        });
        CAMERANEXT = findViewById(R.id.CAMERANEXT);

        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_start.setImageResource(R.drawable.ic_baseline_stop_circle_24);
                timer();
                startRecording();
                img_start.setClickable(false);
                btnstart.setClickable(false);

            }
        });


        findViewById(R.id.CAMERANEXT).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stopRecording();

                if (Utils.isInternetConnected(VendorProfilFourActivity.this)) {
                    new VideoUploadTask().execute();

                } else {
                    Toast.makeText(VendorProfilFourActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    //    -------------------start recording---------------------------------------------------------
    @SuppressLint("LongLogTag")
    public boolean startRecording() {
        try {
            Toast.makeText(getBaseContext(), "Recording Started", Toast.LENGTH_SHORT).show();

            mServiceCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
            mServiceCamera.setDisplayOrientation(90);

            Camera.Parameters params = mServiceCamera.getParameters();
            mServiceCamera.setParameters(params);
            Camera.Parameters p = mServiceCamera.getParameters();

            final List<Camera.Size> listPreviewSize = p.getSupportedPreviewSizes();
            for (Camera.Size size : listPreviewSize) {
                Log.i(TAG, String.format("Supported Preview Size (%d, %d)", size.width, size.height));
            }

            Camera.Size previewSize = listPreviewSize.get(0);

            p.setPreviewSize(previewSize.width, previewSize.height);

            mServiceCamera.setParameters(p);

            try {
                mServiceCamera.setPreviewDisplay(mSurfaceHolder);
                mServiceCamera.startPreview();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }

            mServiceCamera.unlock();

            mMediaRecorder = new MediaRecorder();
            mMediaRecorder.setCamera(mServiceCamera);
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
            mOutputFile = getOutputFile();
            mOutputFile.getParentFile().mkdirs();
            mMediaRecorder.setOutputFile(mOutputFile.getAbsolutePath());
            mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());

            System.out.println("Video PAth>>>>>>  " + mOutputFile.getAbsolutePath());
            img_path = mOutputFile.getAbsolutePath();

            mMediaRecorder.prepare();
            try {
                mMediaRecorder.start();
            } catch (Exception e) {
                e.printStackTrace();
            }


            return true;

        } catch (IllegalStateException e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
            return false;

        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
            return false;
        }

    }

    /* Stop recording--------------------------------------------------------------- */


    private File getOutputFile() {
        SimpleDateFormat dateFormat = new SimpleDateFormat
                ("yyyyMMdd_HHmmssSSS", Locale.ITALY);
        return new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS)
                + "/Video Recorder/RECORDING_"
                + dateFormat.format(new Date())
                + ".mp4");


    }

    public void stopRecording() {
        try {
            if (mServiceCamera != null) {
                mServiceCamera.reconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            mMediaRecorder.stop();
            mMediaRecorder.reset();

            mServiceCamera.stopPreview();
            mMediaRecorder.release();

            mServiceCamera.release();
            mServiceCamera = null;
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }


    public void timer() {
        new CountDownTimer(31000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                txttimer.setText("REC 00:00:" + String.valueOf(counter));
                counter++;
            }

            @Override
            public void onFinish() {

                Toast.makeText(getApplicationContext(), "Press Next button to continue", Toast.LENGTH_SHORT).show();
                img_start.setImageResource(R.drawable.ic_play_svg);

                stopRecording();
                img_start.setClickable(true);
                btnstart.setClickable(true);
            }
        }.start();

    }


    class VideoUploadTask extends AsyncTask<Void, Void, String> {

        protected void onPreExecute() {

            progressDialog = new ProgressDialog(VendorProfilFourActivity.this);
            progressDialog.setTitle("Loading...");
            progressDialog.show();

        }

        protected String doInBackground(Void... params) {
            try {


                uploadFileI = new File(img_path);


                String charset = "UTF-8";
                MultipartUtility multipart = new MultipartUtility(BaseUrl + Upload_video, charset);
                String sid = null;


                multipart.addFormField("user_id", session_vendor.getUserId());

                multipart.addFormField("type", "1");
                multipart.addFilePart("video", uploadFileI);

                System.out.println(" my service video   uploadFileI>>>>>>>>>>>>> ** " + uploadFileI + " ");


                List<String> response = multipart.finish();


                for (String line : response) {
                    System.out.println("ye hai   " + line);
                    String responseupdate = line;
                    System.out.println("Upload Response>>>>>>>>>>>>> ** " + responseupdate);

                    JSONObject json = new JSONObject(responseupdate);    // create JSON obj from string
                    Result = json.getString("result");    // this will return correct
                    msg = json.getString("msg");    // this will return correct
                    System.out.println("result-------------       " + Result);

                }


            } catch (IOException | JSONException ex) {
                System.err.println(ex);
            }
            return null;
        }

        protected void onPostExecute(String result) {
            progressDialog.dismiss();

            if (Result != null && Result.equalsIgnoreCase("true")) {

                Intent intent = new Intent(VendorProfilFourActivity.this, VendorProfilFiveActivity.class);
                startActivity(intent);
            }

        }
    }


}