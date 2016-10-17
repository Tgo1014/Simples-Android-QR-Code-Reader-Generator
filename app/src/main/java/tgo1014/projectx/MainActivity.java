package tgo1014.projectx;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    private Button btnGerarQR;
    private Button btnLerQR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView editSobre = (TextView) findViewById(R.id.txtSobre);
        Button btnLerQR = (Button) findViewById(R.id.btnLerQR);
        Button btnGerarQR = (Button) findViewById(R.id.btnGerarQR);


        btnLerQR.setOnClickListener(this);
        btnGerarQR.setOnClickListener(this);
        editSobre.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnGerarQR:
                intent = new Intent(this, GeneratorActivity.class);
                startActivity(intent);
                break;
            case R.id.btnLerQR:
                //Inicializa camera para scannear QRCode
                //start the scanning activity from the com.google.zxing.client.android.SCAN intent
                intent = new Intent(ACTION_SCAN);
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                startActivityForResult(intent, 0);

                break;
            case R.id.txtSobre:

                new LibsBuilder()
                        //provide a style (optional) (LIGHT, DARK, LIGHT_DARK_TOOLBAR)
                        .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                        .withAboutIconShown(true)
                        .withAboutVersionShown(true)
                        .withLicenseShown(true)
                        .withAboutDescription(getString(R.string.strDesenvolvidoPor) + "<br>" +
                                getString(R.string.strFeedback) + "tgo1014@gmail.com")
                        .start(this);
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                final String contents = intent.getStringExtra("SCAN_RESULT");
                Log.d("tag", "QRCode: " + contents);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                                .setTitle("QRCode")
                                .setMessage(contents);
                        builder.show();
                    }
                });
            }
        }
    }
}
