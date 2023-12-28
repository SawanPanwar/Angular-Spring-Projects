package com.rays.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public class BaseCtl<F extends BaseForm, T extends BaseDTO, S extends BaseServiceInt<T>> {

	@Autowired
	public S baseService;

	public ORSResponse validat(BindingResult bindingResult) {

		ORSResponse res = new ORSResponse(true);

		if (bindingResult.hasErrors()) {

			res.setSuccess(false);

			Map<String, String> errors = new HashMap<String, String>();

			List<FieldError> list = bindingResult.getFieldErrors();

			list.forEach(e -> {
				errors.put(e.getField(), e.getDefaultMessage());
			});

			res.addInputError(errors);
		}
		return res;
	}

	@PostMapping("save")
	public ORSResponse save(@RequestBody @Valid F form, BindingResult bindingResult) {

		ORSResponse res = validat(bindingResult);

		if (!(res.isSuccess())) {
			return res;
		}

		T dto = (T) form.getDto();

		if (dto.getId() != null && dto.getId() > 0) {
			baseService.update(dto);
			res.addMessage("User Updated Successfully..!!!");
		} else {
			baseService.add(dto);
			res.addMessage("User Registered Successfully..!!!");
		}
		res.addData(dto.getId());
		return res;
	}

	@GetMapping("/get/{id}")
	public ORSResponse get(@PathVariable Long id) {

		ORSResponse res = new ORSResponse(true);

		T dto = baseService.findById(id);

		res.addData(dto);

		return res;
	}

	@PostMapping("/deleteMany/{ids}")
	public ORSResponse deleteMany(@PathVariable String[] ids, @RequestBody F form, @RequestParam int pageNo) {

		ORSResponse res = new ORSResponse(true);

		try {

			for (String id : ids) {
				baseService.delete(Long.parseLong(id));
			}

			T dto = (T) form.getDto();

			List list = baseService.search(dto, pageNo, 5);

			res.addData(list);

			res.addMessage("Records deleted successfully..!!!");

		} catch (Exception e) {
			res.addMessage(e.getMessage());
		}

		return res;
	}

	@RequestMapping(value = "/search/{pageNo}", method = { RequestMethod.GET, RequestMethod.POST })
	public ORSResponse search(@RequestBody F form, @PathVariable int pageNo) {

		ORSResponse res = new ORSResponse(true);

		T dto = (T) form.getDto();

		List list = baseService.search(dto, pageNo, 5);

		res.addData(list);

		return res;
	}
}
