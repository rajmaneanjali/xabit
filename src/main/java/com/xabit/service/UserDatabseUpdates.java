package com.xabit.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.xabit.utility.ENUM;

@Repository
public class UserDatabseUpdates {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional            //?: String ,Int return Value
	public List<?> findByValue(String value, String tableName) {

		String query = "SELECT " + value + " from xabit." + tableName + "";
		List<?> resultList = entityManager.createNativeQuery(query).getResultList();
		return resultList;
	}
	

	@Transactional
	public void alterMyTableAddMyColumn(String tableName, String columnName, String columnType, String tableValues,
			String uniqueConstraints, String id, Integer columnId, String length, String operation) {

		// add column

		try {

			if (null != operation && operation.equalsIgnoreCase(ENUM.ADD_COLUMN.toString())) {
				if (null != tableName && null != columnName && null != columnType) {
					String query = null;
					if (null != length) {
						query = "ALTER TABLE xabit." + tableName + " ADD " + columnName + " " + columnType + "("
								+ length + ")" + "";

					} else {
						query = "ALTER TABLE xabit. " + tableName + " ADD " + columnName + " " + columnType + "";
					}

					System.out.println("Url=" + query);
					entityManager.createNativeQuery(query).executeUpdate();

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			// add unique constraints
			if (null != uniqueConstraints && null != tableName) {
				String query2 = "ALTER TABLE xabit. " + tableName + " ADD CONSTRAINT" + " " + "UC_" + uniqueConstraints
						+ " " + "UNIQUE" + " " + "(" + uniqueConstraints + ")" + "";

				System.out.println("Url=" + query2);
				entityManager.createNativeQuery(query2).executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// add values

		if (null != operation && operation.equalsIgnoreCase(ENUM.ADD_VAUES.toString())) {

			try {
				if (null != tableValues && null != tableName && null != columnName && null != id && null != columnId
						&& null != columnType) {
					String query1 = null;
					if (columnType.equalsIgnoreCase("VARCHAR") || columnType.equalsIgnoreCase("DATE")) {
						query1 = "UPDATE " + tableName + " SET" + " " + columnName + "='" + tableValues + "' where "
								+ id + " = " + columnId + " ";
					} else if (columnType.equalsIgnoreCase("INT") || columnType.equalsIgnoreCase("LONG")
							|| columnType.equalsIgnoreCase("FLOAT")) {
						query1 = "UPDATE " + tableName + " SET" + " " + columnName + "=" + Integer.parseInt(tableValues)
								+ " where " + id + " = " + columnId + " ";
					} else if (columnType.equalsIgnoreCase("BIT")) {
						query1 = "UPDATE " + tableName + " SET" + " " + columnName + "=" + Boolean.valueOf(tableValues)
								+ " where " + id + " = " + columnId + " ";
					}

					System.out.println("Url1=" + query1);
					entityManager.createNativeQuery(query1).executeUpdate();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (operation.equalsIgnoreCase(ENUM.BOTH.toString())) {
			if (null != tableName && null != columnName && null != columnType) {
				String query = null;
				if (null != length) {
					query = "ALTER TABLE xabit. " + tableName + " ADD " + columnName + " " + columnType + "(" + length
							+ ")" + "";

				} else {
					query = "ALTER TABLE xabit. " + tableName + " ADD " + columnName + " " + columnType + "";
				}

				System.out.println("Url=" + query);
				entityManager.createNativeQuery(query).executeUpdate();

			}

			try {
				if (null != tableValues && null != tableName && null != columnName && null != id && null != columnId
						&& null != columnType) {
					String query1 = null;
					if (columnType.equalsIgnoreCase("VARCHAR") || columnType.equalsIgnoreCase("DATE")) {
						query1 = "UPDATE " + tableName + " SET" + " " + columnName + "='" + tableValues + "' where "
								+ id + " = " + columnId + " ";
					} else if (columnType.equalsIgnoreCase("INT") || columnType.equalsIgnoreCase("LONG")
							|| columnType.equalsIgnoreCase("FLOAT")) {
						query1 = "UPDATE " + tableName + " SET" + " " + columnName + "=" + Integer.parseInt(tableValues)
								+ " where " + id + " = " + columnId + " ";
					} else if (columnType.equalsIgnoreCase("BIT")) {
						query1 = "UPDATE " + tableName + " SET" + " " + columnName + "=" + Boolean.valueOf(tableValues)
								+ " where " + id + " = " + columnId + " ";
					}

					System.out.println("Url1=" + query1);
					entityManager.createNativeQuery(query1).executeUpdate();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}
