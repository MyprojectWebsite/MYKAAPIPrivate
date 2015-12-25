package org.kaapi.app.controllers.elearning;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kaapi.app.entities.MainCategory;
import org.kaapi.app.services.MainCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/maincategory")
public class MainCategoryController {

	@Autowired
	@Qualifier("MainCategoryService")
	MainCategoryService mainCategoryService;

	@RequestMapping(value = "/listmaincategory", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listMainCategory() {
		List<MainCategory> list = mainCategoryService.listMainCategory("");
		Map<String, Object> map = new HashMap<String, Object>();
		if (list.isEmpty()) {
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NOT_FOUND);
		}
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");
		map.put("RES_DATA", list);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getmaincategory/{id}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getMainCategory(@PathVariable("id") int maincategoryid) {
		MainCategory list = mainCategoryService.getMainCategory(maincategoryid);
		Map<String, Object> map = new HashMap<String, Object>();
		if (list==null) {
			map.put("STATUS", false);
			map.put("MESSAGE", "RECORD NOT FOUND");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NOT_FOUND);
		}
		map.put("STATUS", true);
		map.put("MESSAGE", "RECORD FOUND");
		map.put("RES_DATA", list);
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Map<String, Object>> deleteMainCategory(@PathVariable("id") int maincategoryid) {

		Map<String, Object> map = new HashMap<String, Object>();
		if (mainCategoryService.deleteMainCategory(maincategoryid)) {
			map.put("STATUS", true);
			map.put("MESSAGE", "DELETE SUCCESS");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		map.put("MESSAGE", "DELETE NOT SUCCESS");
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NOT_FOUND);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Map<String, Object>> updateMainCategory(@RequestBody MainCategory maincategory) {

		Map<String, Object> map = new HashMap<String, Object>();
		if (mainCategoryService.updateMainCategory(maincategory)) {
			map.put("STATUS", true);
			map.put("MESSAGE", "UPDATE SUCCESS");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		map.put("STATUS", false);
		map.put("MESSAGE", "UPDATE NOT SUCCESS");
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NOT_FOUND);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> addMainCategory(@RequestBody MainCategory maincategory) {

		Map<String, Object> map = new HashMap<String, Object>();
		if (mainCategoryService.insertMainCategory(maincategory)) {
			map.put("STATUS", true);
			map.put("MESSAGE", "ADD SUCCESS");
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}
		map.put("STATUS", false);
		map.put("MESSAGE", "ADD NOT SUCCESS");
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.NOT_FOUND);
	}

	
	
	/*
	 * public void AddMainCategory(){} public void DeleteMainCategory(){} public
	 * void UpdateMainCategory(){} public void GetMainCategory(){} public void
	 * ListMainCategory(){}
	 */

}
