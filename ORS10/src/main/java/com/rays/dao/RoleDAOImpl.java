package com.rays.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.rays.common.BaseDAOImpl;
import com.rays.dto.RoleDTO;

@Repository
public class RoleDAOImpl extends BaseDAOImpl<RoleDTO> implements RoleDAOInt {

	public Class<RoleDTO> getDTOClass() {
		return RoleDTO.class;
	}

	public List<Predicate> getWhereClause(RoleDTO dto, CriteriaBuilder builder, Root qRoot) {

		List<Predicate> whereCondition = new ArrayList<Predicate>();

		if (!(isEmptyString(dto.getName()))) {

			whereCondition.add(builder.like(qRoot.get("name"), dto.getName() + "%"));

		}
		return whereCondition;
	}
}
