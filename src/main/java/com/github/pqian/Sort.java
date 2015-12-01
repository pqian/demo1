package com.github.pqian;

import java.io.Serializable;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;

@Data
public class Sort implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum Direction {
		ASC, DESC;
	
		public org.springframework.data.domain.Sort.Direction asSpringDirection() {
			return org.springframework.data.domain.Sort.Direction.valueOf(name());
		}
	}

	public static Direction DEFAULT_DIRECTION = Direction.ASC;
	
	private Direction direction;
	private String property;
	
	public Direction getDirection() {
		if (direction != null) {
			return direction;
		}
		return DEFAULT_DIRECTION;
	}
	
	public org.springframework.data.domain.Sort asSpringSort() {
		if (StringUtils.isBlank(property)) {
			return null;
		}
		return new org.springframework.data.domain.Sort(getDirection().asSpringDirection(), property);
	}
}