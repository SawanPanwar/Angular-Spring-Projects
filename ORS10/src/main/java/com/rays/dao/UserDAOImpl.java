package com.rays.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rays.common.BaseDAOImpl;
import com.rays.common.attachment.AttachmentDAOInt;
import com.rays.common.attachment.AttachmentDTO;
import com.rays.dto.RoleDTO;
import com.rays.dto.UserDTO;

@Repository
public class UserDAOImpl extends BaseDAOImpl<UserDTO> implements UserDAOInt {

	@Autowired
	public RoleDAOInt roleDao;

	@Autowired
	public AttachmentDAOInt attachmentService;

	public Class<UserDTO> getDTOClass() {
		return UserDTO.class;
	}

	@Override
	public void populate(UserDTO dto) {

		if (dto.getRoleId() != null && dto.getRoleId() > 0) {
			RoleDTO roleDto = roleDao.findByPk(dto.getRoleId());
			dto.setRoleName(roleDto.getName());
		}

		if (dto.getId() != null && dto.getId() > 0) {
			UserDTO userData = findByPk(dto.getId());
			dto.setImageId(userData.getImageId());
		}

	}

	public void delete(UserDTO dto) {
		super.delete(dto);
		Long id = dto.getImageId();
		System.out.println(id);
		AttachmentDTO attachmentDto = attachmentService.findByPk(id);
		attachmentService.delete(attachmentDto);
	}

	public List<Predicate> getWhereClause(UserDTO dto, CriteriaBuilder builder, Root qRoot) {

		List<Predicate> whereCondition = new ArrayList<Predicate>();

		if (!(isEmptyString(dto.getFirstName()))) {

			whereCondition.add(builder.like(qRoot.get("firstName"), dto.getFirstName() + "%"));

		}
		return whereCondition;
	}
}
