package com.lidroid.test.MainContainer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lidroid.plugin.Module;
import com.lidroid.plugin.PluginManager;
import com.lidroid.plugin.PluginMessage;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class MainActivity extends Activity implements View.OnClickListener {

    private MainContainer mContainer;

    private TextView mTextView;
    private Button mButton;
    private LinearLayout mViewHost;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mViewHost = (LinearLayout) findViewById(R.id.view_host);
        mTextView = (TextView) findViewById(R.id.result_txt);
        mButton = (Button) findViewById(R.id.send_msg_button);
        mButton.setOnClickListener(this);

        mContainer = new MainContainer(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mContainer.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mContainer.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mContainer.onStop();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        // send test msg
        ConcurrentHashMap<String, Module> modules = PluginManager.getInstance().getAllModules();
        PluginMessage msg = new PluginMessage("biu biu, i'm a msg.", 123, new Date(), mTextView);
        for (Module module : modules.values()) {
            mContainer.sendMessage(msg, module);
        }

        // test mop
        mViewHost.addView(mContainer.test(mTextView));
    }

    public void textViewSetText(String result) {
        mTextView.setText(result);
    }
}
