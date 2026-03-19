package application;

import java.util.Date;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		Department department = new Department(2, null);
		
		System.out.println("=== TESTING findById id = 3 ===");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);
		
		System.out.println("\n=== TESTING findByDepartment department.Id = 2 ===");
		for (Seller s : sellerDao.findByDepartment(department)) {
			System.out.println(s);
		}
		
		System.out.println("\n=== TESTING findAll ===");
		for (Seller s : sellerDao.findAll()) {
			System.out.println(s);	 
		}
		
		System.out.println("\n=== TESTING insert ===");
		Seller seller2 = new Seller(null, "Augusto", "augusto@gmail.com", new Date(), 4000.0, department);
		sellerDao.insert(seller2);
		System.out.println("Inserted! New id = " + seller2.getId());
	}

}
