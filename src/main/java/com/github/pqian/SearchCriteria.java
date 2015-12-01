package com.github.pqian;

import java.io.Serializable;

import lombok.Data;

import org.springframework.data.domain.PageRequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class SearchCriteria implements Serializable {

	private static final long serialVersionUID = 1L;

	private int pageSize = 5;
	private int pageNumber = 0;
	private Sort sort;
	
	public PageRequest asSpringPageRequest() {
		org.springframework.data.domain.Sort springSort = null;
		if (sort != null) {
			springSort = sort.asSpringSort();
		}
		return new PageRequest(pageNumber, pageSize, springSort);
	}
}
