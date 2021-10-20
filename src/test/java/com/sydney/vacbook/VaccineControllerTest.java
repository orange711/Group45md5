package com.sydney.vacbook;

import com.sydney.vacbook.controller.VaccineController;
import org.springframework.boot.test.context.SpringBootTest;

import com.sydney.vacbook.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class VaccineControllerTest {
    @Autowired

    private VaccineController vaccineController;
    Vaccine vaccineTest = new Vaccine(38, "mRNA", "Pfizer", 17, 100);

    //cause the entity have @Auto id so you can not set the insert id by yourself
    @Test
    void saveVaccineTest() {


        boolean saveVaccine = vaccineController.saveVaccine(vaccineTest);

        assertTrue(saveVaccine == true);
    }

    //please enter the id what you want to delete
    @Test
    void delVaccineTest() {
        Vaccine vaccineTest1 = new Vaccine(38, "mRNA", "Pfizer", 17, 100);
        assertTrue(vaccineController.delVaccine(vaccineTest1) == true);
    }

    @Test
    void updateVaccineTest() {
        Vaccine vaccineTest2 = new Vaccine(38, "mRNA", "Pfizer", 17, 1100);
        assertTrue(vaccineController.updateVaccine(vaccineTest2) == true);
    }

    @Test
    void getVaccine() {

        assertTrue(vaccineController.getVaccine(38).getVaccineId() == 38);
    }

    @Test
    void getVaccineListByAdminId(){
        List<Vaccine> testList = new ArrayList<Vaccine>();
        testList.add(vaccineController.getVaccine(33));
        testList.add(vaccineController.getVaccine(34));
        testList.add(vaccineController.getVaccine(35));
        testList.add(vaccineController.getVaccine(38));
        assertTrue(vaccineController.getVaccineListByAdminId(17) .equals(testList) );
    }

}
