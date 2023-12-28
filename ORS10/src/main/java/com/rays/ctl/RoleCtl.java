package com.rays.ctl;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rays.common.BaseCtl;
import com.rays.common.DropDownList;
import com.rays.common.ORSResponse;
import com.rays.dto.RoleDTO;
import com.rays.form.RoleForm;
import com.rays.service.RoleServiceInt;

@RestController
@RequestMapping(value = "Role")
public class RoleCtl extends BaseCtl<RoleForm, RoleDTO, RoleServiceInt> {

	@GetMapping("preload")
	public ORSResponse preload() {

		System.out.println("role ctl = preload()");

		ORSResponse res = new ORSResponse();

		RoleDTO dto = new RoleDTO();

		List<DropDownList> roleList = baseService.search(dto, 0, 0);

		res.addResult("roleList", roleList);

		return res;

	}

}
