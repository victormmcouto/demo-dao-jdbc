package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection conn;

	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller seller) {
		PreparedStatement st = null;
		
		try {
			//Statement.RETURN_GENERATED_KEYS é usado para que st retorne as chaves criadas
			st = conn.prepareStatement("INSERT INTO seller " +
									   "(Name, Email, BirthDate, BaseSalary, DepartmentId) " +
									   "VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, seller.getName());
			st.setString(2, seller.getEmail());
			st.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
			st.setDouble(4, seller.getBaseSalary());
			st.setInt(5, seller.getDepartment().getId());
			
			//Executa o update no banco de dados causando inserção
			int rowsAffected = st.executeUpdate();
			
			/* Se a inserção ocorreu, recupera as novas chaves geradas e, como
			   apenas um seller foi inserido, recupera a chave da primeira posição
			   e atribui a seller*/
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					seller.setId(id);
				}
				DB.closeResultSet(rs);
			//Caso a inserção não seja concluida, cause um erro
			} else {
				throw new DbException("Unexpected error! No rows affected!");
			}
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void update(Seller Seller) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT seller.*, department.Name as DepName " + 
		                               "FROM seller INNER JOIN department " + 
		                               "ON seller.DepartmentId = department.Id " + 
		                               "WHERE seller.Id = ?");

			st.setInt(1, id);

			rs = st.executeQuery();

			if (rs.next()) {
				Department department = instantiateDepartment(rs);
				Seller seller = instantiateSeller(rs, department);

				return seller;
			}

			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	private Seller instantiateSeller(ResultSet rs, Department department) throws SQLException {
		Seller seller = new Seller();
		seller.setId(rs.getInt("Id"));
		;
		seller.setName(rs.getString("Name"));
		seller.setEmail(rs.getString("Email"));
		seller.setBaseSalary(rs.getDouble("BaseSalary"));
		seller.setBirthDate(rs.getDate("BirthDate"));
		seller.setDepartment(department);
		return seller;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department department = new Department();
		department.setId(rs.getInt("DepartmentId"));
		department.setName(rs.getString("DepName"));
		return department;
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		List<Seller> sellers = new ArrayList<>();
		Map<Integer, Department> map = new HashMap<>();
		Integer id;
		Department dep;
		
		try {
			st = conn.prepareStatement("SELECT seller.*, department.Name as DepName " +
					                   "FROM seller INNER JOIN department " + 
					                   "ON DepartmentId = department.Id " +
					                   "ORDER BY Name");

			rs = st.executeQuery();
			
			// Testa se a tabela resultante possui registros
			if (rs.next()) {				
				do {
					id = rs.getInt("DepartmentId");
					dep = map.get(id);
					
					if (dep == null) {
						dep = instantiateDepartment(rs);
						map.put(id, dep);
					}
					
					sellers.add(instantiateSeller(rs, dep));
				} while (rs.next());
				
				return sellers;
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
		return null;
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT seller.*, department.Name as DepName " +
					                   "FROM seller INNER JOIN department " + 
					                   "ON DepartmentId = department.Id " +
					                   "WHERE DepartmentId = ? " + 
					                   "ORDER BY Name");

			st.setInt(1, department.getId());

			rs = st.executeQuery();
			
			// Testa se a tabela resultante possui registros
			if (rs.next()) {
				List<Seller> sellers = new ArrayList<>();

				Department dep = instantiateDepartment(rs);
				
				do {
					sellers.add(instantiateSeller(rs, dep));
				} while (rs.next());
				
				return sellers;
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
		return null;
	}
}
