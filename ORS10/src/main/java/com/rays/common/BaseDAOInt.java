package com.rays.common;

import java.util.List;

public interface BaseDAOInt<T extends BaseDTO> {

	public long add(T dto);

	public void update(T dto);

	public void delete(T dto);

	public T findByUniqueKey(String attribute, String value);

	public T findByPk(Long id);

	public List search(T dto, int pageNo, int pageSize);

}
