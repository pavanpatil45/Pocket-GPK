package com.example.android;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import jxl.Cell;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;


public class takeAttendance extends AppCompatActivity {
    String teacher_id;
    String class_selected;
    Spinner period;
    String periodno;
    ArrayList<String> selectedItems;
    ArrayList<String> nonselectedItems;
    Toolbar mToolbar;

    ArrayList<String> ul;
    ListView listView;
    private ArrayAdapter adapter;
    ArrayList Userlist = new ArrayList<>();
    ArrayList Usernames = new ArrayList<>();

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbAttendance;
    String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);
        mToolbar= findViewById(R.id.takeattendancebar);

        getSupportActionBar().setTitle("Attendance");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        period = findViewById(R.id.spinner4);

        selectedItems = new ArrayList<String>();

        TextView classname = findViewById(R.id.textView);
        classname.setText("CSE");

        Bundle bundle1 = getIntent().getExtras();
        class_selected = bundle1.getString("class_selected");
        teacher_id = bundle1.getString("tid");

        classname.setText(class_selected);


        DatabaseReference dbuser = ref.child("Student");

        dbuser.orderByChild("classes").equalTo(class_selected).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Userlist.add(dsp.child("sid").getValue().toString());
                    Usernames.add(dsp.child("sname").getValue().toString());


                }
                OnStart(Userlist);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
            }

        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public void OnStart(ArrayList<String> userlist) {
        nonselectedItems = userlist;
        ListView chl = findViewById(R.id.checkable_list);
        chl.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, R.layout.checkable_list_layout, R.id.txt_title, userlist);
        chl.setAdapter(aa);
        chl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = ((TextView) view).getText().toString();
                if (selectedItems.contains(selectedItem))
                    selectedItems.remove(selectedItem);
                else
                    selectedItems.add(selectedItem);

            }

        });


    }

    public void showSelectedItems(View view) {
        String selItems = "";
        periodno = period.getSelectedItem().toString();
        if (periodno.equals("Select Period")) {
            Toast.makeText(this, "Select a class", Toast.LENGTH_LONG).show();

        } else {
            ref = FirebaseDatabase.getInstance().getReference();

            dbAttendance = ref.child("attendance").child(date);

            for (String item : selectedItems) {
                Toast.makeText(this, "Attendance created Successfully", Toast.LENGTH_SHORT).show();
                nonselectedItems.remove(item);
                dbAttendance.child(item).child(periodno).setValue("P" + " / " + teacher_id);
                if (selItems == "")
                    selItems = item;
                else
                    selItems += "/" + item;
            }




            for (String item : nonselectedItems) {
                Toast.makeText(this, "Attendance created Successfully", Toast.LENGTH_SHORT).show();
                dbAttendance.child(item).child(periodno).setValue("A" + " / " + teacher_id);

            }
        }


    }

    public void addtoreport(View v) {


        Workbook workbook=null;
        WritableWorkbook wb=null;
        WritableSheet s=null;
        try {
            workbook = Workbook.getWorkbook(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/online_attendance/" + class_selected+"_month_"+date.substring(3,5)+ ".xls"));
            wb = createWorkbook(class_selected+"_month_"+date.substring(3,5),workbook);
            s = wb.getSheet(0);

        }
        catch (Exception e){
            File wbfile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/online_attendance/" + class_selected + ".xls");
            wb = createWorkbook(class_selected+"_month_"+date.substring(3,5));
               s = createSheet(wb, "month_", 0);
        }








        int i = s.getColumns();
        if(i==0){
            try {
                //for header
                Label newCell=new Label(0,0,"Student_id");
                Label newCell2=new Label(1,0,"Student_name");
                WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
                WritableCellFormat headerFormat = new WritableCellFormat(headerFont);
                //center align the cells' contents
                headerFormat.setAlignment(Alignment.CENTRE);
                newCell.setCellFormat(headerFormat);
                newCell2.setCellFormat(headerFormat);
                s.addCell(newCell);
                s.addCell(newCell2);
            } catch (WriteException e) {
                e.printStackTrace();
            }
            for (Object item : Userlist) {
                int j = s.getRows();
                String name=Usernames.get(j-1).toString();

                Label label = new Label(0, j, item.toString());
                Label label2 = new Label(1, j, name);

                try {
                    s.addCell(label);
                    s.addCell(label2);


                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }



        }
        i=s.getColumns();
        // Toast.makeText(this, i  , Toast.LENGTH_LONG).show();
        int j=1;
        try {
            Label newCell=new Label(i,0, date);
            WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            WritableCellFormat headerFormat = new WritableCellFormat(headerFont);
            //center align the cells' contents
            headerFormat.setAlignment(Alignment.CENTRE);
            newCell.setCellFormat(headerFormat);
            s.addCell(newCell);

        } catch (WriteException e) {
            e.printStackTrace();
        }
        for (Object item : Userlist) {


            Label label2;
            // Label label2;

            if (selectedItems.contains(item)) {
                label2=new Label(i,j,"P");
                //Toast.makeText(this, item.toString() + "  present :", Toast.LENGTH_LONG).show();


            } else {
                label2=new Label(i,j,"A");
                //Toast.makeText(this, item.toString() + "  absent :", Toast.LENGTH_LONG).show();

            }
            j++;
            try {


                s.addCell(label2);



            } catch (Exception e) {
                Toast.makeText(this, "Unable to create sheet", Toast.LENGTH_LONG).show();
                e.printStackTrace();

            }


        }
        Date today = new Date();

        String tomorrow =new SimpleDateFormat("dd-MM-yyyy").format(new Date(today.getTime() + (1000 * 60 * 60 * 24)));// new Date(today.getTime() + (1000 * 60 * 60 * 24));
        if(tomorrow.substring(0,2).equals("01")){

            int row =s.getRows();
            int col=s.getColumns();
            String xx="";
            int nop,tc;

            for(i = 0; i<row; i++)
            {
                nop=0;
                tc=-2;
                for (int c=0;c<col;c++)
                {
                    Cell z=s.getCell(c,i);

                    xx=z.getContents();
                    if(xx.equals("P"))
                        nop++;
                    if(!xx.isEmpty()||!xx.equals("")) {
                        tc++;
                    }

                }
                xx=xx+"\n";
                Label label = new Label(col, i,""+nop);

                Label label2 = new Label(col+1,i,nop*100/tc+"%");
                try {
                    if(i==0) {
                        label = new Label(col, i, "Total=" + tc);
                        label2 = new Label(col+1, i, "percentage");

                    }
                    s.addCell(label);
                    s.addCell(label2);
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }
        }



        try {
            wb.write();
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }

        Toast.makeText(this,"sheet  created successfully",Toast.LENGTH_LONG).show();

    }





    public WritableWorkbook createWorkbook(String fileName, Workbook workbook){
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setUseTemporaryFileDuringWrite(true);
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath() + "/online_attendance");
        dir.mkdirs();
        File wbfile = new File(dir,fileName+".xls");

        WritableWorkbook wb = null;

        try{
            wb = Workbook.createWorkbook(wbfile,workbook);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return wb;
    }

    public WritableWorkbook createWorkbook(String fileName)  {

        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setUseTemporaryFileDuringWrite(true);


        File sdCard = Environment.getExternalStorageDirectory();

        File dir = new File(sdCard.getAbsolutePath() + "/online_attendance");

        dir.mkdirs();

        File wbfile = new File(dir,fileName+".xls");

        WritableWorkbook wb = null;

        try{

            wb = Workbook.createWorkbook(wbfile,wbSettings);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return wb;
    }

    public WritableSheet createSheet(WritableWorkbook wb, String sheetName, int sheetIndex){


        return wb.createSheet(sheetName, sheetIndex);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}


