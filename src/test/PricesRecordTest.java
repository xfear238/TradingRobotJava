package test;

import static org.junit.Assert.*;

import org.junit.Test;

import records.PricesRecord;

public class PricesRecordTest {

	@Test
	public void test() {
		PricesRecord pricesrecord = new PricesRecord();
		
		pricesrecord.addPrice(1.0);
		pricesrecord.addPrice(2.0);
		pricesrecord.addPrice(3.0);
		
		assertTrue(pricesrecord.getCount() == 3);
		assertTrue(pricesrecord.getLastPrice() == 3.0);
		assertTrue(pricesrecord.getPreviousPrice() == 2.0);
	}

}
