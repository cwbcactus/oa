package com.cwb.oa.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.cwb.oa.domain.Department;

public class DepartmentUtils {
	
	/**
	 * 遍历部门树，把所有的部门都改掉名称后放到同一个List中返回。通过名称中的空格表示层次
	 * 
	 * @param topList
	 * @param removedDepartment
	 *            这个部门和这个部门的子孙部门都不要，如果为null，表示没有要移除的部门分支
	 * @return
	 */
	public static List<Department> getAllDepartmentList(List<Department> topList, Department removedDepartment){
		List<Department> list = new ArrayList<Department>();
		
		walkTree(topList, "┣", list, removedDepartment);
		
		return list;
	}

	private static void walkTree(Collection<Department> topList, String prefix,
			List<Department> list, Department removedDepartment) {
		// TODO Auto-generated method stub
		for(Department top : topList){
			
			if(removedDepartment != null && top.getId() == removedDepartment.getId()){
				continue;
			}
			
			// 不要修改Session缓存中的对象，最好使用副本
			Department copy = new Department();
			copy.setId(top.getId());
			copy.setName(prefix + top.getName());
			
			list.add(copy);
			
			 // 要使用全角的空格，要不然在html中只显示一个空格
			walkTree(top.getChildren(), "　" + prefix, list, removedDepartment);
		}
	}
	
}
