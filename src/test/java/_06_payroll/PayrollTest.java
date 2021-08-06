package _06_payroll;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PayrollTest {

    Payroll payroll = new Payroll();

    @Test
    void itShouldCalculatePaycheck() {
        //given
    	double hourlyWage = 15.25;
    	int numHours = 8;
    	double expected = 122.0;
        //when
    	double actual = payroll.calculatePaycheck(hourlyWage, numHours);
        //then
    	assertEquals(expected, actual);
    }

    @Test
    void itShouldCalculateMileageReimbursement() {
        //given
    	int milesTraveled = 1;
    	double expected = .575;
        //when
    	double actual = payroll.calculateMileageReimbursement(milesTraveled);
        //then
    	assertEquals(expected, actual);
    }

    @Test
    void itShouldCreateOfferLetter() {
        //given
    	String employeeName = "Robert";
    	double hourlyWage = 16.25;
    	String expected = "Hello Robert, We are pleased to offer you an hourly wage of 16.25";
        //when
    	String actual = payroll.createOfferLetter(employeeName, hourlyWage);
        //then
    	assertEquals(expected, actual);
    }

}