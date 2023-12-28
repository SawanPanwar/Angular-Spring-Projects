package com.rays.common;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public abstract class BaseDAOImpl<T extends BaseDTO> implements BaseDAOInt<T> {

	@PersistenceContext
	public EntityManager entityManager;

	public abstract Class<T> getDTOClass();

	public abstract List<Predicate> getWhereClause(T dto, CriteriaBuilder builder, Root qRoot);

	public void populate(T dto) {
		
	}
	
	public long add(T dto) {
		populate(dto);
		entityManager.persist(dto);
		return dto.getId();
	}

	public void update(T dto) {
		populate(dto);
		entityManager.merge(dto);
	}

	public void delete(T dto) {
		entityManager.remove(dto);
	}

	public T findByPk(Long id) {
		T dto = entityManager.find(getDTOClass(), id);
		return dto;
	}

	public T findByUniqueKey(String attribute, String value) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<T> cq = builder.createQuery(getDTOClass());

		Root<T> qRoot = cq.from(getDTOClass());

		Predicate condition = builder.equal(qRoot.get(attribute), value);

		cq.where(condition);

		TypedQuery<T> tq = entityManager.createQuery(cq);

		List list = tq.getResultList();

		T dto = null;

		if (list.size() > 0) {
			dto = (T) list.get(0);
		}

		return dto;
	}

	public List search(T dto, int pageNo, int pageSize) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<T> cq = builder.createQuery(getDTOClass());

		Root<T> qRoot = cq.from(getDTOClass());

		List<Predicate> condition = getWhereClause(dto, builder, qRoot);

		cq.where(condition.toArray(new Predicate[condition.size()]));

		TypedQuery<T> tq = entityManager.createQuery(cq);

		if (pageSize > 0) {
			tq.setFirstResult(pageNo * pageSize);
			tq.setMaxResults(pageSize);
		}

		List list = tq.getResultList();

		return list;
	}

	public boolean isEmptyString(String value) {
		return value == null || value.trim().length() == 0;
	}

}
