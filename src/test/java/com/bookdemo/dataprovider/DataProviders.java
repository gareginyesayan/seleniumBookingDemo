package com.bookdemo.dataprovider;

import com.bookdemo.utilities.ExcelUtils;
import org.testng.annotations.DataProvider;

import java.util.List;

public class DataProviders {
    private ExcelUtils exl = new ExcelUtils();


    @DataProvider(name="childrendata")
    public Object[][] getChildrenData(){
        int rows = exl.getRowCount("Childrens");
        Object [][] data = new Object[rows-1][1];
        for(int i = 0; i < rows-1; i++){
            data[i][0] = exl.getCellDataNumber("Childrens", i+1, 0);
        }

        return data;
    }

    @DataProvider(name="destinationdata")
    public Object[][] getDestinations(){
        int rows = exl.getRowCount("Destinations");
        Object [][] data = new Object[rows-1][1];
        for(int i = 0; i < rows-1; i++){
            data[i][0] = exl.getCellDataString("Destinations", i+1, 0);
        }

        return data;
    }

    @DataProvider(name="Indexes")
    public Object[][] getBFAvailableIndexes(){
        Object [][] data = new Object[3][1];
        for(int i = 0; i < 3; i++){
            data[i][0] = i;
        }

        return data;
    }

}
