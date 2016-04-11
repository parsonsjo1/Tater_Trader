package com.joshwa.tater_trader;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BarcodeScanner extends AppCompatActivity
{
    private Button mBarcodeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_barcode_scanner);

        mBarcodeScanner = (Button) findViewById(R.id.bScan);
        mBarcodeScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                IntentIntegrator integrator = new IntentIntegrator(BarcodeScanner.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                integrator.setPrompt("Scan a barcode");
                integrator.setCameraId(0);  // Use a specific camera of the device
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(true);
                integrator.initiateScan();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null)
        {
            String upc = scanResult.getContents();
            UPCInfoTask mUPCTask = new UPCInfoTask(upc);
            mUPCTask.execute((Void) null);
        }
        // else continue with any other code you need in the method

    }

    /**
     * Represents an asynchronous get upc code information task used to get the information for the
     * specified upc code.
     */
    public class UPCInfoTask extends AsyncTask<Void, Void, Boolean>
    {
        private final String mUPC;

        UPCInfoTask(String upc)
        {
            mUPC = upc;
        }

        @Override
        protected Boolean doInBackground(Void... params)
        {
            UPCInfo upcInfo = ServerProxy.getInstance().getUPCInfo(mUPC);
            System.out.print(upcInfo.toString());
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success)
        {
            if (success)
            {
                //finish();

            } else
            {
                //.setError(getString(R.string.error_incorrect_password));
                //mPasswordView.requestFocus();
            }
        }
    }
}
