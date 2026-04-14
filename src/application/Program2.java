package application;

import java.util.List;
import java.util.Scanner;

import db.DbException;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		
		DepartmentDao deparmentDao = DaoFactory.createDepartmentDao();
		Department department = new Department(null, "Music");
		Scanner sc = new Scanner(System.in);
		
		System.out.println("======== TESTING insert ========");
		deparmentDao.insert(department);
		System.out.println("Inserted! Generated ID = " + department.getId());
		
		System.out.println("======== TESTING update ========");
		department.setId(1);
		department.setName("Clothing");
		deparmentDao.update(department);
		System.out.println("Updated!");
		
		System.out.println("======== TESTING delete ========");
		try {
			deparmentDao.deleteById(7);
			System.out.println("Deleted!");
		} catch (DbException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("======== TESTING findById ========");
		System.out.print("Id: ");
		int id = sc.nextInt();
		department = deparmentDao.findById(id);
		System.out.println(department);
		
		System.out.println("======== TESTING findAll ========");
		List<Department> departments = deparmentDao.findAll();
		departments.stream().forEach(System.out::println);
		
		sc.close();
	}

}
