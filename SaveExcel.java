package androidtown.org.myplants;



import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class SaveExcel extends AppCompatActivity {
    void saveExcel(ArrayList<Item> mItems){
        Workbook workbook = new HSSFWorkbook();

        Sheet sheet = workbook.createSheet(); // 새로운 시트 생성

        Row row = sheet.createRow(0); // 새로운 행 생성
        Cell cell;

        cell = row.createCell(0); // 1번 셀 생성
        cell.setCellValue("사진"); // 1번 셀 값 입력

        cell = row.createCell(1); // 1번 셀 생성
        cell.setCellValue("식물명"); // 1번 셀 값 입력

        cell = row.createCell(2); // 2번 셀 생성
        cell.setCellValue("식물 크기"); // 2번 셀 값 입력

        cell = row.createCell(3); // 3번 셀 생성
        cell.setCellValue("식물 난이도"); // 3번 셀 값 입력

        cell = row.createCell(4); // 4번 셀 생성
        cell.setCellValue("식물 특징"); // 4번 셀 값 입력

        cell = row.createCell(5); // 5 셀 생성
        cell.setCellValue("식물 물주기"); // 5번 셀 값 입력

        cell = row.createCell(6); // 6번 셀 생성
        cell.setCellValue("식물 애칭"); // 6번 셀 값 입력

        for(int i = 0; i < mItems.size() ; i++){ // 데이터 엑셀에 입력
            row = sheet.createRow(i+1);
            cell = row.createCell(0);
            cell.setCellValue(mItems.get(i).getPhoto());
            cell = row.createCell(1);
            cell.setCellValue(mItems.get(i).getName());
            cell = row.createCell(2);
            cell.setCellValue(mItems.get(i).getMsize());
            cell = row.createCell(3);
            cell.setCellValue(mItems.get(i).getMlevel());
            cell = row.createCell(4);
            cell.setCellValue(mItems.get(i).getMfeature());
            cell = row.createCell(5);
            cell.setCellValue(mItems.get(i).getMwatering());
            cell = row.createCell(6);
            cell.setCellValue(mItems.get(i).getMnickname());

        }
        File directory = ((MainActivity) MainActivity.context_main).directory; //or getExternalFilesDir(null); for external storage

        File xlsFile = new File(directory, "mine.xls");
        try{
            FileOutputStream os = new FileOutputStream(xlsFile);
            workbook.write(os); // 외부 저장소에 엑셀 파일 생성
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}