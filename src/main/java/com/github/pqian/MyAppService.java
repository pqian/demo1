package com.github.pqian;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.annotations.Logging;
import org.springframework.data.domain.Page;

import com.github.pqian.entity.Product;
import com.google.common.collect.Range;

@Path("/v1")
@Logging
public interface MyAppService {
	
	public static final Range<Integer> RANGE_PAGE_NUMBER = Range.closedOpen(0, 100);
	public static final Range<Integer> RANGE_PAGE_SIZE = Range.closed(1, 100);
	
	@GET
	@Path("products")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	Page<Product> searchProducts(@QueryParam("") @Valid @NotNull SearchCriteria searchCriteria); 
	
}
