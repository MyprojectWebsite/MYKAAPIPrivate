package org.kaapi.app.controllers.elearning;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kaapi.app.entities.Category;
import org.kaapi.app.entities.MainCategory;
import org.kaapi.app.entities.Pagination;
import org.kaapi.app.entities.University;
import org.kaapi.app.services.CategoryService;
import org.kaapi.app.services.MainCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("api/category")
public class CategoryController {
	
	@Autowired
	@Qualifier("CategoryService")
	CategoryService categoryService;

	@RequestMapping(value = "/listcategory", method = RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> listCategory(Pagination pagination, @RequestParam( value = "keyword" , required = false , defaultValue="") String keyword){
		List<Category> lstcategory = categoryService.listCategory(pagination, keyword);
		
		
		Map<String , Object> map = new HashMap<String , Object>();
		if(lstcategory  == null){
			map.put("MESSAGE", "Not found!");
			return new ResponseEntity<Map<String,Object>>(map , HttpStatus.NOT_FOUND);
		}
		System.err.println(lstcategory.size());
		pagination.setTotalCount(categoryService.countCategory());
		pagination.setTotalPages(pagination.totalPages());
		map.put("RESP_DATA", lstcategory );
		map.put("PAGINATION", pagination);
		return new ResponseEntity<Map<String,Object>>(map , HttpStatus.OK);
	}
	
	
	public void ListVideosInCategory(){}
	public void ViewCategory(){}
	
	
	
	public void AddCategory(){}
	public void UpdateCategory(){}
	public void DeleteCategory(){}
	public void GetCategory(){}
	public void CategoryVideo(){}		
	public void ListCategory(){}	
	public void ToAddCategory(){}
	
	
}
