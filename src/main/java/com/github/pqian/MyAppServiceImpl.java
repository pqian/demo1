package com.github.pqian;

import javax.ws.rs.BadRequestException;

import lombok.extern.slf4j.Slf4j;

import org.apache.cxf.annotations.Logging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pqian.entity.Product;
import com.github.pqian.repository.ProductRepository;
import com.google.common.collect.Range;

@Service("myAppService")
@Transactional
@Slf4j
@Logging
public class MyAppServiceImpl implements MyAppService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	@Transactional(readOnly = true)
	public Page<Product> searchProducts(SearchCriteria searchCriteria) {
		log.debug("searchCriteria: {}", searchCriteria);
		checkRange("pageSize", searchCriteria.getPageSize(), RANGE_PAGE_SIZE);
		checkRange("pageNumber", searchCriteria.getPageNumber(), RANGE_PAGE_NUMBER);
		
		return productRepository.findAll(searchCriteria.asSpringPageRequest());
	}

	private final <T extends Comparable<T>> void checkRange(String name, T value, Range<T> range) {
		if (!range.contains(value)) {
			log.warn("{}: {} is out of bounds of the range", name, value);
			throw new BadRequestException(name + " is out of bounds of the range");
		}
	}
}
