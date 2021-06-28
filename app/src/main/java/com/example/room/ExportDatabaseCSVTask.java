//call this task
/*ExportDatabaseCSVTask task=new ExportDatabaseCSVTask();
task.execute();*/

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.SyncStateContract;
import android.util.Log;
import android.widget.Toast;

import com.example.room.MainActivity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//new async task for file export to csv
private class ExportDatabaseCSVTask extends AsyncTask<String, String, Boolean> {
    private final ProgressDialog dialog = new ProgressDialog(SearchResultActivity.this);
    boolean memoryErr = false;

    // to show Loading dialog box

private class ExportDatabaseCSVTask extends AsyncTask<String ,String, String>{
    private final ProgressDialog dialog = new ProgressDialog(MainActivity.this);

    @Override
    protected void onPreExecute() {
        this.dialog.setMessage("Exporting database...");
        this.dialog.show();
    }

    // to write process
    protected Boolean doInBackground(final String... args) {

        boolean success = false;

        String currentDateString = new SimpleDateFormat(Constants.SimpleDtFrmt_ddMMyyyy).format(new Date());

        File dbFile = getDatabasePath("HLPL_FRETE.db");
        Log.v(TAG, "Db path is: " + dbFile); // get the path of db
        File exportDir = new File(Environment.getExternalStorageDirectory() + File.separator + Constants.FileNm.FILE_DIR_NM, "");

        long freeBytesInternal = new File(getApplicationContext().getFilesDir().getAbsoluteFile().toString()).getFreeSpace();
        long megAvailable = freeBytesInternal / 1048576;

        if (megAvailable < 0.1) {
            System.out.println("Please check"+megAvailable);
            memoryErr = true;
        }else {
            exportDirStr = exportDir.toString();// to show in dialogbox
            Log.v(TAG, "exportDir path::" + exportDir);
            if (!exportDir.exists()) {
                exportDir.mkdirs();
            }
            try {
                List<SalesActivity> listdata = salesLst;
                SalesActivity sa = null;
                String lob = null;
                for (int index = 0; index < listdata.size();) {
                    sa = listdata.get(index);
                    lob = sa.getLob();
                    break;
                }
                if (Constants.Common.OCEAN_LOB.equals(lob)) {

                    file = new File(exportDir, Constants.FileNm.FILE_OFS + currentDateString + ".csv");
                } else {
                    file = new File(exportDir, Constants.FileNm.FILE_AFS + currentDateString + ".csv");
                }
                file.createNewFile();
                CSVWriter csvWrite = new CSVWriter(new FileWriter(file));


                // this is the Column of the table and same for Header of CSV
                // file
                if (Constants.Common.OCEAN_LOB.equals(lob)) {
                    csvWrite.writeNext(Constants.FileNm.CSV_O_HEADER);
                }else{
                    csvWrite.writeNext(Constants.FileNm.CSV_A_HEADER);
                }
                String arrStr1[] = { "SR.No", "CUTSOMER NAME", "PROSPECT", "PORT OF LOAD", "PORT OF DISCHARGE" };
                csvWrite.writeNext(arrStr1);

                if (listdata.size() > 0) {
                    for (int index = 0; index < listdata.size(); index++) {
                        sa = listdata.get(index);
                        String pol;
                        String pod;
                        if (Constants.Common.OCEAN_LOB.equals(sa.getLob())) {
                            pol = sa.getPortOfLoadingOENm();
                            pod = sa.getPortOfDischargeOENm();
                        } else {
                            pol = sa.getAirportOfLoadNm();
                            pod = sa.getAirportOfDischargeNm();
                        }
                        int srNo = index;
                        String arrStr[] = { String.valueOf(srNo + 1), sa.getCustomerNm(), sa.getProspectNm(), pol, pod };
                        csvWrite.writeNext(arrStr);
                    }
                    success = true;
                }
                csvWrite.close();

            } catch (IOException e) {
                Log.e("SearchResultActivity", e.getMessage(), e);
                return success;
            }
        }
        return success;
    }

    // close dialog and give msg
    protected void onPostExecute(Boolean success) {
        if (this.dialog.isShowing()) {
            this.dialog.dismiss();
        }
        if (success) {
            dialogBox(Constants.Flag.FLAG_EXPRT_S);
        } else {
            if (memoryErr==true) {
                dialogBox(Constants.Flag.FLAG_MEMORY_ERR);
            } else {
                dialogBox(Constants.Flag.FLAG_EXPRT_F);
            }
        }
    }
}

    protected String doInBackground(final String... args){
        File exportDir = new File(Environment.getExternalStorageDirectory(), "");
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }

        File file = new File(exportDir, "ExcelFile.csv");
        try {

            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));

            //data     
            ArrayList<String> listdata= new ArrayList<String>();
            listdata.add("Aniket");
            listdata.add("Shinde");
            listdata.add("pune");
            listdata.add("anything@anything");
            //Headers
            String arrStr1[] ={"First Name", "Last Name", "Address", "Email"};
            csvWrite.writeNext(arrStr1);

            String arrStr[] ={listdata.get(0), listdata.get(1), listdata.get(2), listdata.get(3)};
            csvWrite.writeNext(arrStr);

            csvWrite.close();
            return "";
        }
        catch (IOException e){
            Log.e("MainActivity", e.getMessage(), e);
            return "";
        }
    }

    @SuppressLint("NewApi")
    @Override
    protected void onPostExecute(final String success) {

        if (this.dialog.isShowing()){
            this.dialog.dismiss();
        }
        if (success.isEmpty()){
            Toast.makeText(MainActivity.this, "Export successful!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(MainActivity.this, "Export failed!", Toast.LENGTH_SHORT).show();
        }
    }
}