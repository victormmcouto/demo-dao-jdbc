package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		
		DepartmentDao deparmentDao = DaoFactory.createDepartmentDao();
		Department department = new Department(null, "Music");
		
		System.out.println("======== TESTING insert ========");
		deparmentDao.insert(department);
		System.out.println("Inserted!");
	}

}
