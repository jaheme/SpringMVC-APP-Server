package com.tony.core.db;

import java.util.ArrayList;
import java.util.List;

/**
 * set集合的列表获取
 * @author jahe.lai
 *
 */
public class SscanResult {
	
		public String cursor;
		public List<String> stringList;
		
		public List<Long> getLongList() {
			List<Long> ids = new ArrayList<Long>();
			if(stringList != null) {
				for(String s : stringList) {
					if(s!=null) 
						ids.add(Long.parseLong(s));
				}
			}
			return ids;
		}
		

}
