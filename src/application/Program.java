package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.println("=== TESTING findById id = 3 ===");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);
	}

}
