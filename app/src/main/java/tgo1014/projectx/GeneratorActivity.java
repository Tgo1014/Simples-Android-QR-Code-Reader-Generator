package tgo1014.projectx;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class GeneratorActivity extends AppCompatActivity {

    private android.widget.EditText editTextoQR;
    private android.widget.Button btnGerarQR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generator);

        this.btnGerarQR = (Button) findViewById(R.id.btnGerarQR);
        this.editTextoQR = (EditText) findViewById(R.id.editTextoQR);

        btnGerarQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                geraQR(editTextoQR.toString());
            }
        });
    }

    private void geraQR(String texto){
        QRCodeWriter writer = new QRCodeWriter();
        ImageView qrCode = (ImageView)findViewById(R.id.imageView);
        try {
            BitMatrix bitMatrix = writer.encode(texto, BarcodeFormat.QR_CODE, 512, 512);
            int width = 512;
            int height = 512;
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (bitMatrix.get(x,y))
                        bmp.setPixel(x, y, Color.BLACK);
                    else
                        bmp.setPixel(x, y, Color.WHITE);
                }
            }
            qrCode.setImageBitmap(bmp);
        } catch (WriterException e) {
            Log.e("QR ERROR", e.toString());
        }
    }
}
