
package com.xabit.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xabit.model.DynamicException;
import com.xabit.model.RequestData;
import com.xabit.service.MasterService;
import com.xabit.service.UserDatabseUpdates;
import com.xabit.utility.ENUM;

import lombok.AllArgsConstructor;

@RequestMapping("masterfield")
@RestController
@AllArgsConstructor
public class MasterController {
	@Autowired
	private MasterService masterService;
	@Autowired
	private UserDatabseUpdates userDatabseUpdates;

	/*
	 * @PostMapping("/saveMaster") public MasterEntity saveMasterField(@RequestBody
	 * MasterEntity masterEntity) { return
	 * masterService.saveMasterField(masterEntity); }
	 * 
	 * @PostMapping("/getMaster") public List<MasterEntity> getMaster() { return
	 * masterService.findAllMasterData(); }
	 */

	/*
	 * @PostMapping("/addColumn") public Map<String, String>
	 * updateTable(@RequestBody RequestData requestData,
	 * 
	 * @RequestParam(value = "id", required = false) String id,
	 * 
	 * @RequestParam(value = "value", required = false) Integer columnId,
	 * 
	 * @RequestParam(value = "stage", required = false) ENUM enumValue) throws
	 * DynamicException {
	 * 
	 * Map<String, String> map = new HashMap<>();
	 * userDatabseUpdates.alterMyTableAddMyColumn(requestData.getTableName(),
	 * requestData.getColumnName(), requestData.getColumnType(),
	 * requestData.getTableValues(), requestData.getUniqueConstraints(), id,
	 * columnId, requestData.getLength(), enumValue.toString());
	 * 
	 * map.put("message", "Column Added Successfully"); throw new
	 * DynamicException("Duplicate column name"); return map; }
	 */
//}
	
	@PostMapping("/addColumn")
	public Map<String, String> updateTable(@RequestBody RequestData requestData,
	                                       @RequestParam(value = "id", required = false) String id,
	                                       @RequestParam(value = "value", required = false) Integer columnId,
	                                       @RequestParam(value = "stage", required = false) ENUM enumValue) throws DynamicException {
	    Map<String, String> map = new HashMap<>();

	    try {
	        userDatabseUpdates.alterMyTableAddMyColumn(requestData.getTableName(), requestData.getColumnName(),
	                requestData.getColumnType(), requestData.getTableValues(), requestData.getUniqueConstraints(), id,
	                columnId, requestData.getLength(), enumValue.toString());

	        map.put("message", "Column Added Successfully");
	    } catch (Exception e) {
	    	throw new DynamicException(e.getMessage());	        // Handle or log the exception as needed
	    }

	    return map;
	}
}

