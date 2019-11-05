package com.icheero.app.activity;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.icheero.app.R;
import com.icheero.app.activity.data.CustomSettingActivity;
import com.icheero.app.activity.data.DatabaseActivity;
import com.icheero.app.activity.data.SystemSettingActivity;
import com.icheero.app.activity.data.ViewModelActivity;
import com.icheero.app.activity.feature.LollipopActivity;
import com.icheero.app.activity.feature.oreo.NotificationActivity;
import com.icheero.app.activity.framework.EventBusActivity;
import com.icheero.app.activity.framework.FlutterContainerActivity;
import com.icheero.app.activity.framework.RxJavaActivity;
import com.icheero.app.activity.media.GLSurfaceViewActivity;
import com.icheero.app.activity.media.SurfaceViewActivity;
import com.icheero.app.activity.media.SystemCameraActivity;
import com.icheero.app.activity.media.TextureViewActivity;
import com.icheero.app.activity.network.DownloadActivity;
import com.icheero.app.activity.network.ImageDownloadActivity;
import com.icheero.app.activity.network.RequestActivity;
import com.icheero.app.activity.network.RetrofitActivity;
import com.icheero.app.activity.network.WebViewActivity;
import com.icheero.app.activity.reverse.DisposeDexActivity;
import com.icheero.app.activity.reverse.DisposeManifestActivity;
import com.icheero.app.activity.reverse.DisposeResourceActivity;
import com.icheero.app.activity.reverse.DisposeSoActivity;
import com.icheero.app.activity.reverse.JniActivity;
import com.icheero.app.activity.ui.AnimActivity;
import com.icheero.app.activity.ui.CustomViewActivity;
import com.icheero.app.activity.ui.DialogActivity;
import com.icheero.app.activity.ui.MoveViewActivity;
import com.icheero.app.activity.ui.OptionActivity;
import com.icheero.app.activity.ui.SectionsActivity;
import com.icheero.app.activity.ui.StyledActivity;
import com.icheero.app.activity.ui.touch.PanGestureScrollActivity;
import com.icheero.app.activity.ui.touch.PanScrollActivity;
import com.icheero.app.activity.framework.xposed.XposedActivity;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.core.manager.CameraManager;
import com.icheero.sdk.core.manager.IOManager;
import com.icheero.sdk.util.Common;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity
{
    @BindView(R.id.to_styled_activity)
    Button toStyledActivity;
    @BindView(R.id.to_custom_view_activity)
    Button toCustomViewActivity;
    @BindView(R.id.to_sections_activity)
    Button toSectionsActivity;
    @BindView(R.id.to_anim_activity)
    Button toAnimActivity;
    @BindView(R.id.to_dialog_activity)
    Button toDialogActivity;
    @BindView(R.id.to_option_activity)
    Button toOptionActivity;
    @BindView(R.id.to_touch_pan_gesture_scroll_activity)
    Button toPanGestureScrollActivity;
    @BindView(R.id.to_touch_pan_scroll_activity)
    Button toPanScrollActivity;
    @BindView(R.id.to_move_view_activity)
    Button toMoveViewActivity;
    @BindView(R.id.to_notification_activity)
    Button toNotificationActivity;
    @BindView(R.id.to_lollipop_activity)
    Button toTabLayoutActivity;
    @BindView(R.id.to_web_view_activity)
    Button toWebViewActivity;
    @BindView(R.id.to_image_download_activity)
    Button toImageDownloadActivity;
    @BindView(R.id.to_download_activity)
    Button toDownloadActivity;
    @BindView(R.id.to_retrofit_activity)
    Button toRetrofitActivity;
    @BindView(R.id.to_request_activity)
    Button toRequestActivity;
    @BindView(R.id.to_camera_activity)
    Button toCameraActivity;
    @BindView(R.id.to_surface_view_activity)
    Button toSurfaceViewActivity;
    @BindView(R.id.to_texture_view_activity)
    Button toTextureViewActivity;
    @BindView(R.id.to_glsurface_view_activity)
    Button toGLSurfaceViewActivity;
    @BindView(R.id.to_custom_setting_activity)
    Button toCustomSettingActivity;
    @BindView(R.id.to_system_setting_activity)
    Button toSystemSettingActivity;
    @BindView(R.id.to_database_activity)
    Button toDatabaseActivity;
    @BindView(R.id.to_view_model_activity)
    Button toViewModelActivity;
    @BindView(R.id.to_jni_activity)
    Button toJniActivity;
    @BindView(R.id.to_dispose_so_activity)
    Button toDisposeSoActivity;
    @BindView(R.id.to_dispose_manifest_activity)
    Button toDisposeManifestActivity;
    @BindView(R.id.to_dispose_resource_activity)
    Button toDisposeResourceActivity;
    @BindView(R.id.to_dispose_dex_activity)
    Button toDisposeDexActivity;
    @BindView(R.id.to_load_plugin_activity)
    Button toLoadPluginActivity;
    @BindView(R.id.to_xposed_activity)
    Button toXposedActivity;
    @BindView(R.id.to_faceid_activity)
    Button toFaceIDActivity;
    @BindView(R.id.to_rx_java_activity)
    Button toRxJavaActivity;
    @BindView(R.id.to_event_bus_activity)
    Button toEventBusActivity;
    @BindView(R.id.to_flutter_activity)
    Button toFlutterActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        if (!mPermissionManager.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE))
        {
            mPermissionManager.permissionRequest(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        else
        {
            IOManager.getInstance().createRootFolder();
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @OnClick({R.id.to_custom_setting_activity, R.id.to_system_setting_activity, R.id.to_database_activity, R.id.to_view_model_activity})
    public void OnDataClickEvent(View v)
    {
        switch (v.getId())
        {
            case R.id.to_custom_setting_activity:
            {
                startActivity(new Intent(this, CustomSettingActivity.class));
                break;
            }
            case R.id.to_system_setting_activity:
            {
                startActivity(new Intent(this, SystemSettingActivity.class));
                break;
            }
            case R.id.to_database_activity:
            {
                startActivity(new Intent(this, DatabaseActivity.class));
                break;
            }
            case R.id.to_view_model_activity:
            {
                startActivity(new Intent(this, ViewModelActivity.class));
                break;
            }
        }
    }

    @OnClick({R.id.to_glsurface_view_activity, R.id.to_texture_view_activity, R.id.to_surface_view_activity, R.id.to_camera_activity})
    public void OnMediaClickEvent(View v)
    {
        Intent intent = new Intent();
        switch (v.getId())
        {
            case R.id.to_camera_activity:
            {
                intent.setClass(this, SystemCameraActivity.class);
                intent.putExtra(SystemCameraActivity.KEY_REQUEST_CODE, CameraManager.REQUEST_CODE_IMAGE);
                break;
            }
            case R.id.to_surface_view_activity:
            {
                intent.setClass(this, SurfaceViewActivity.class);
                break;
            }
            case R.id.to_texture_view_activity:
            {
                intent.setClass(this, TextureViewActivity.class);
                break;
            }
            case R.id.to_glsurface_view_activity:
                intent.setClass(this, GLSurfaceViewActivity.class);
                break;
        }
        startActivity(intent);
    }

    @OnClick({R.id.to_download_activity, R.id.to_image_download_activity, R.id.to_web_view_activity, R.id.to_retrofit_activity, R.id.to_request_activity})
    public void OnNetworkClickEvent(View v)
    {
        switch (v.getId())
        {
            case R.id.to_web_view_activity:
                startActivity(new Intent(this, WebViewActivity.class));
                break;
            case R.id.to_image_download_activity:
                startActivity(new Intent(this, ImageDownloadActivity.class));
                break;
            case R.id.to_download_activity:
                startActivity(new Intent(this, DownloadActivity.class));
                break;
            case R.id.to_retrofit_activity:
                startActivity(new Intent(this, RetrofitActivity.class));
                break;
            case R.id.to_request_activity:
                startActivity(new Intent(this, RequestActivity.class));
                break;
        }
    }

    @OnClick({
            R.id.to_move_view_activity, R.id.to_styled_activity, R.id.to_custom_view_activity, R.id.to_sections_activity, R.id.to_anim_activity, R.id.to_dialog_activity, R.id.to_option_activity,
            R.id.to_touch_pan_scroll_activity, R.id.to_touch_pan_gesture_scroll_activity
    })
    public void OnUIClickEvent(View v)
    {
        Intent toActivity = new Intent();
        switch (v.getId())
        {
            case R.id.to_styled_activity:
            {
                toActivity.setClass(this, StyledActivity.class);
                toActivity.putExtra("transition", "explode");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) startActivity(toActivity, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            }
            case R.id.to_custom_view_activity:
            {
                toActivity.setClass(this, CustomViewActivity.class);
                toActivity.putExtra("transition", "slide");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) startActivity(toActivity, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            }
            case R.id.to_sections_activity:
            {
                toActivity.setClass(this, SectionsActivity.class);
                toActivity.putExtra("transition", "fade");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) startActivity(toActivity, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            }
            case R.id.to_anim_activity:
            {
                toActivity.setClass(this, AnimActivity.class);
                toActivity.putExtra("transition", "fade");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) startActivity(toActivity, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            }
            case R.id.to_move_view_activity:
            {
                toActivity.setClass(this, MoveViewActivity.class);
                startActivity(toActivity);
                break;
            }
            case R.id.to_dialog_activity:
            {
                toActivity.setClass(this, DialogActivity.class);
                startActivity(toActivity);
                break;
            }
            case R.id.to_option_activity:
            {
                toActivity.setClass(this, OptionActivity.class);
                startActivity(toActivity);
                break;
            }
            case R.id.to_touch_pan_gesture_scroll_activity:
            {
                toActivity.setClass(this, PanGestureScrollActivity.class);
                startActivity(toActivity);
                break;
            }
            case R.id.to_touch_pan_scroll_activity:
            {
                toActivity.setClass(this, PanScrollActivity.class);
                startActivity(toActivity);
                break;
            }
        }
    }

    @OnClick({R.id.to_lollipop_activity, R.id.to_notification_activity})
    public void OnFeatureClickEvent(View v)
    {
        Intent toActivity = new Intent();
        switch (v.getId())
        {
            case R.id.to_notification_activity:
            {
                toActivity.setClass(this, NotificationActivity.class);
                break;
            }
            case R.id.to_lollipop_activity:
            {
                toActivity.setClass(this, LollipopActivity.class);
                break;
            }
        }
        startActivity(toActivity);
    }

    @OnClick({R.id.to_jni_activity, R.id.to_dispose_so_activity, R.id.to_dispose_manifest_activity, R.id.to_dispose_resource_activity, R.id.to_dispose_dex_activity})
    public void OnReverseClickEvent(View v)
    {
        Intent intent = new Intent();
        switch (v.getId())
        {
            case R.id.to_jni_activity:
                intent.setClass(this, JniActivity.class);
                break;
            case R.id.to_dispose_so_activity:
                intent.setClass(this, DisposeSoActivity.class);
                break;
            case R.id.to_dispose_manifest_activity:
                intent.setClass(this, DisposeManifestActivity.class);
                break;
            case R.id.to_dispose_resource_activity:
                intent.setClass(this, DisposeResourceActivity.class);
                break;
            case R.id.to_dispose_dex_activity:
                intent.setClass(this, DisposeDexActivity.class);
                break;
        }
        startActivity(intent);
    }

    @OnClick({R.id.to_load_plugin_activity, R.id.to_faceid_activity})
    public void OnModuleClickEvent(View v)
    {
        switch (v.getId())
        {
            case R.id.to_load_plugin_activity:
            {
                ARouter.getInstance().build("/plugin/index").navigation();
                break;
            }
            case R.id.to_faceid_activity:
            {
                ARouter.getInstance().build("/faceid/index").navigation();
                break;
            }
        }
    }

    @OnClick({R.id.to_rx_java_activity, R.id.to_event_bus_activity, R.id.to_xposed_activity, R.id.to_flutter_activity})
    public void onFrameworkClickEvent(View v)
    {
        Intent intent = new Intent();
        switch (v.getId())
        {
            case R.id.to_rx_java_activity:
            {
                intent.setClass(this, RxJavaActivity.class);
                break;
            }
            case R.id.to_event_bus_activity:
            {
                intent.setClass(this, EventBusActivity.class);
                break;
            }
            case R.id.to_xposed_activity:
            {
                intent.setClass(this, XposedActivity.class);
                break;
            }
            case R.id.to_flutter_activity:
            {
                intent.setClass(this, FlutterContainerActivity.class);
                break;
            }
        }
        startActivity(intent);
    }

    @Override
    public void onPermissionRequest(boolean isGranted, String permission)
    {
        super.onPermissionRequest(isGranted, permission);
        if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE))
        {
            if (isGranted)
            {
                IOManager.getInstance().createRootFolder();
            }
            else
            {
                Common.toast(this, "请打开读写权限！", Toast.LENGTH_SHORT);
            }
        }
    }
}
