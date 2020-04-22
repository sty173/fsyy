package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

import mapper.ItemMapper;



@Controller
public class ApiController {
	
	@Autowired
	private ItemMapper itemMapper;
	
	@RequestMapping("/submitTable")
	@ResponseBody
	public String test(@RequestParam(required = false) String tablename, 
					   @RequestParam(required = false) String radioItems, 
					   @RequestParam(required = false) String username, 
					   @RequestParam(required = false) String sex,
					   @RequestParam(required = false) String phone,
					   @RequestParam(required = false) String nurse,
					   @RequestParam(required = false) String advice,
					   @RequestParam(required = false) String ageRange,
					   @RequestParam(required = false) String mjDoctor,
					   @RequestParam(required = false) String stfDoctor,
					   @RequestParam(required = false) String reason,
					   @RequestParam(required = false) String ksmc,
					   @RequestParam(required = false) Integer ksdm,
					   @RequestParam(required = false) String address,
					   @RequestParam(required = false) String method,
					   @RequestParam(required = false) String hbbz,
					   @RequestParam(required = false) String tjbz,
					   @RequestParam(required = false) String zlbz,
					   @RequestParam(required = false) String zw,
					   @RequestParam(required = false) String xz,
					   @RequestParam(required = false) String zwlb,
					   @RequestParam(required = false) String gzcs,
					   @RequestParam(required = false) String nickName,
					   @RequestParam(required = false) String loginId) {
		
		List<Map> items = new ArrayList<Map>();
		Map sbtRecord = new HashMap();
		StringBuffer txqk = new StringBuffer("未填写满意度行数：");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String tbsj  = df.format(new Date());
		
		System.out.println("miniapp called success");
		System.out.println("radioItems: " + radioItems);
		System.out.println("username: " + username);
		System.out.println("sex: " + sex);
		System.out.println("phone: " + phone);
		System.out.println("ageRange: " + ageRange);
		System.out.println("advice: " + advice);
		System.out.println("nurse: " + nurse);	
		System.out.println("ksdm:" + ksdm);	

		
		List<List> lists = JSONObject.parseArray(radioItems, List.class);
		
		List unFilledLines = new ArrayList();
		for(int i = 1; i <= lists.size(); i ++) {
			unFilledLines.add(i);
		}
		
		out: for(List list : lists) {
			for(Object obj : list) {
				JSONObject jsonObj = (JSONObject)obj;
				if(jsonObj.get("checked") != null && (boolean) (jsonObj.get("checked"))) {
					
					unFilledLines.remove(jsonObj.get("titleId"));
			    
					Map item = (Map)jsonObj;
					item.put("tablename", tablename);
					item.put("username", username);
					item.put("phone", phone);
					item.put("ageRange", ageRange);
					item.put("advice", advice);
					item.put("nurse", nurse);
					item.put("tbsj", tbsj);
					item.put("mjDoctor", mjDoctor);
					item.put("stfDoctor", stfDoctor);
					item.put("reason", reason);
					item.put("address", address);
					item.put("method", method);
					if(item.get("ksmc") == null) {
						item.put("ksmc", ksmc);
					}
					if(item.get("ksdm") == null) {
						item.put("ksdm", ksdm);
					}
					
					item.put("sex", sex);
					item.put("hbbz", hbbz);
					item.put("qkxh", loginId);
					item.put("tjbz", tjbz);
					item.put("zlbz", zlbz);
					item.put("zw", zw);
					item.put("xz", xz);
					item.put("zwlb", zwlb);
					item.put("gzcs", gzcs);
					
					
					for(Object key : item.keySet()) {
						if("null".equals(item.get(key)) || "undefined".equals(item.get(key)) || "".equals(item.get(key))) {
							item.put(key, null);
						}
					}
					
					items.add(item);	
					continue out;
				}
				
			}
			
			Map item = (Map)list.get(0);
			item.put("value", 0.00);
			item.put("tablename", tablename);
			item.put("username", username);
			item.put("phone", phone);
			item.put("ageRange", ageRange);
			item.put("advice", advice);
			item.put("nurse", nurse);
			item.put("tbsj", tbsj);
			item.put("mjDoctor", mjDoctor);
			item.put("stfDoctor", stfDoctor);
			item.put("reason", reason);
			item.put("address", address);
			item.put("method", method);
			if(item.get("ksmc") == null) {
				item.put("ksmc", ksmc);
			}
			if(item.get("ksdm") == null) {
				item.put("ksdm", ksdm);
			}
			
			item.put("sex", sex);
			item.put("hbbz", hbbz);
			item.put("qkxh", loginId);
			item.put("tjbz", tjbz);
			item.put("zlbz", zlbz);
			item.put("zw", zw);
			item.put("xz", xz);
			item.put("zwlb", zwlb);
			item.put("gzcs", gzcs);
			
			
			for(Object key : item.keySet()) {
				if("null".equals(item.get(key)) || "undefined".equals(item.get(key)) || "".equals(item.get(key))) {
					item.put(key, null);
				}
			}
			items.add(item);
			
		}
		
		for(Object line : unFilledLines) {
			txqk.append("第" + line + "行，");
		}
		
		String zy = null;
		if(ksdm != null) {
			int zydm = itemMapper.getZy(ksdm);
			if(zydm == 5) {
				zy = "总院";
			} else if (zydm == 291) {
				zy = "复兴园区";
			} else {
				zy = "未知";
			}
		}
		
		
	//	sbtRecord.put("jlxh", "");
		sbtRecord.put("ksdm", ksdm);
		sbtRecord.put("ksmc", ksmc);
		sbtRecord.put("ygxm", nickName);
		sbtRecord.put("tbsj", tbsj);
		sbtRecord.put("tablename", tablename);
		sbtRecord.put("tbname", itemMapper.getTbName(tablename));
		sbtRecord.put("txbz", 1);
		sbtRecord.put("txqk", txqk.toString());
		sbtRecord.put("dlzh", loginId);
		sbtRecord.put("zy", zy);
			
		System.out.println("sbtRecord：" + sbtRecord) ;
		
		System.out.println("items: " + items);
		System.out.println("unFilled:" + unFilledLines);
		System.out.println("txqk:" + txqk);
		
		if(itemMapper.getLoginFlag(loginId) == null) {
			itemMapper.addTxqk(sbtRecord);
		} else {
			itemMapper.updateTxqk(sbtRecord);
		}
		
		List<List<Map>> itemsList = Lists.partition(items, 10);
		for(List<Map> list : itemsList) {
			itemMapper.addItems(list);
		}
		
		return "hello miniapp";
		
		
	}
	
	@RequestMapping("/getItemDic")
	@ResponseBody
	public String getItemDic(String tablename) {
		
		List itemDic = itemMapper.getItemDic(tablename);
		System.out.println(itemDic);
		return JSON.toJSONString(itemDic);
	}
	
	@RequestMapping("/getDeparts")
	@ResponseBody
	public String getDeparts(int sjks) {
		
		List departs = itemMapper.getDeparts(sjks);
		System.out.println(departs);
		return JSON.toJSONString(departs);
	}
	
	
	@RequestMapping("/getFuncDeparts")
	@ResponseBody
	public String getFuncDeparts(String bz) {
		
		List departs = itemMapper.getFuncDeparts(bz);
		System.out.println(departs);
		return JSON.toJSONString(departs);
	}
	
	@RequestMapping("/getLoginId")
	@ResponseBody
	public int getLoginId() {
		
		int loginId = itemMapper.generateLoginId();
		itemMapper.addLoginFlag(loginId);
		System.out.println(loginId);
		return loginId;
	}
	
	@RequestMapping("/getBackData")
	@ResponseBody
	public String getBackData(String bz, String tablename) {
		
		Map backData = new HashMap();
		String jsonString = null;
		
		List departs = itemMapper.getFuncDeparts(bz);
		List itemDic = itemMapper.getItemDic(tablename);
		
		backData.put("departs", departs);
		backData.put("itemDic", itemDic);
		
		jsonString = JSON.toJSONString(backData);
		//jsonString = "{\"name\": \"haha\"}";
		
		System.out.println(jsonString);
		return JSON.toJSONString(jsonString);
	}

}
