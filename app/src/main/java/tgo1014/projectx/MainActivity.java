package tgo1014.projectx;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnGerarQR;
    private Button btnLerQR;
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

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
                AlertDialog.Builder sobre = new AlertDialog.Builder(this);

                sobre.setMessage("Desenvolvido por Tiago Araujo");
                sobre.setCancelable(true);

                AlertDialog mostrarSobre = sobre.create();
                mostrarSobre.show();
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
