package com.github.pqian;

import net.minidev.json.JSONArray;

import org.junit.Assert;
import org.junit.Test;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

public class DataInitTest {

	@Test
	public void test() {
		ReadContext ctx = JsonPath.parse(DataInitTest.class.getResourceAsStream("/products.json"));
		Assert.assertTrue((Boolean)ctx.read("$.success"));
		Assert.assertEquals(10, ctx.read("$.metadata.results.length()"));
		Assert.assertEquals("431327", ctx.read("$.metadata.results[0].id"));
		Assert.assertEquals("Palazzo With Pocket", ctx.read("$.metadata.results[0].data.name"));
		Assert.assertEquals("Ethnic Chic", ctx.read("$.metadata.results[0].data.brand"));
		Assert.assertEquals("75.00", ctx.read("$.metadata.results[0].data.price"));
		JSONArray array = ctx.read("$.metadata.results[0].images[?(@.default)].path");
		Assert.assertEquals("http://static.my.zalora.net/p/ethnic-chic-9160-723134-1-catalogmobile.jpg", array.get(0));
	}

}
