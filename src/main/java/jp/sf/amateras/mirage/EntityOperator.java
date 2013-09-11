package jp.sf.amateras.mirage;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.bean.BeanDesc;
import jp.sf.amateras.mirage.bean.PropertyDesc;
import jp.sf.amateras.mirage.dialect.Dialect;
import jp.sf.amateras.mirage.naming.NameConverter;
import jp.sf.amateras.mirage.type.ValueType;

/**
 * An interface for operation for the entity. This interface is used for the following operations:
 * <ul>
 *   <li>Creating an entity instance from ResultSet</li>
 *   <li>Retrieving metadata from the entity class</li>
 * </ul>
 * You can implement your own EntityOperator and enable it by {@link SqlManager#setEntityOperator(EntityOperator)}.
 *
 * @author Naoki Takezoe
 */
public interface EntityOperator {

	/**
	 * Creates and returns one entity instance from the ResultSet.
	 *
	 * @param <T> the type parameter of entity class
	 * @param clazz the entity class
	 * @param rs the ResultSet
	 * @param beanDesc the BeanDesc of the entity class
	 * @param dialect the Dialect
	 * @param valueTypes the list of ValueTypes
	 * @param nameConverter the NameConverter
	 * @return the instance of entity class or Map
	 * @throws EntityCreationFailedException if {@link EntityOperator} failed to create a result entity
	 */
	public <T> T createEntity(Class<T> clazz, ResultSet rs, BeanDesc beanDesc,
			Dialect dialect, List<ValueType<?>> valueTypes, NameConverter nameConverter) throws SQLException;

	/**
	 * Retrieves the metadata of the primary key from the given PropertyDesc.
	 *
	 * @param clazz the entity class
	 * @param propertyDesc the property description
	 * @param nameConverter the NameConverter
	 * @return the metadata of the primary key
	 */
	public PrimaryKeyInfo getPrimaryKeyInfo(Class<?> clazz,
			PropertyDesc propertyDesc, NameConverter nameConverter);

	/**
	 * Retrieves the metadata of the column from the given PropertyDesc.
	 *
	 * @param clazz the entity class
	 * @param propertyDesc the property description
	 * @param nameConverter the NameConverter
	 * @return the metadata of the column
	 */
	public ColumnInfo getColumnInfo(Class<?> clazz,
			PropertyDesc propertyDesc, NameConverter nameConverter);

	/**
	 * DTO for metadata of the primary key which is specified by {@link PrimaryKey} annotation.
	 *
	 * @see PrimaryKey
	 */
	public class PrimaryKeyInfo {
		public GenerationType generationType;
		public String generator;

		public PrimaryKeyInfo(GenerationType generationType){
			this(generationType, null);
		}

		public PrimaryKeyInfo(GenerationType generationType, String generator){
			this.generationType = generationType;
			this.generator = generator;
		}
	}

	/**
	 * DTO for metadata of the column which is specified by {@link Column} annotation.
	 *
	 * @see Column
	 */
	public class ColumnInfo {
		public String name;

		public ColumnInfo(String name){
			this.name = name;
		}
	}

}
