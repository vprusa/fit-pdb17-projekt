package cz.vutbr.fit.pdb.project.tables.entities;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;

import javax.sql.PooledConnection;

import oracle.jdbc.driver.OracleConnection;
import oracle.spatial.geometry.JGeometry;
import oracle.sql.STRUCT;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;
import org.jboss.logging.Logger;

//http://info.michael-simons.eu/2007/02/05/using-hibernate-with-oracle-spatial/

/**
 * This object is a UserType that represents an SDO_GEOMETRY type for use with
 * Oracle 10g databases and the Oracle Spatial Libraries
 * 
 * It represents an SDO_GEOMETRY database type by wrapping the
 * oracle.spatial.geometry.JGeometry type and implementing
 * org.hibernate.usertype.UserType.
 * 
 * This class should be used with the OracleSpacialDialect class.
 * 
 * (NOTE: I tried just extending the JGeometry instead of aggregating it, that
 * doesn't work. The static load returns a JGeometry that can't be cast to the
 * sub-class)
 * 
 * {@linkplain http://info.michael-simons.eu/2007/02/05/using-hibernate-with-oracle-spatial/}
 * {@linkplain http://community.jboss.org/wiki/mappingspatialoracletypesdogeometrytojgeometry}
 * 
 * @author Joel Schuster - The NAVSYS Corporation
 * @author michael.simons
 */
public class JGeometryType implements UserType, Serializable {
	private static final long serialVersionUID = 1L;
	private JGeometry geometryInstance = null;
	private static final Logger LOGGER = Logger.getLogger(JGeometryType.class);

	/**
	 * This default constructor does create an instance of 1 point at origin
	 */
	public JGeometryType() {
		LOGGER.info("\n\n\nStoring not null");
		geometryInstance = new JGeometry(0, 0, 0);
	}

	public JGeometryType(JGeometry geometryInstance) {
		this.geometryInstance = geometryInstance;
	}

	public JGeometryType(JGeometryType geometryType) {
		this.geometryInstance = geometryType.getJGeometry();
	}

	/* User Type Information */
	/*
	 * Note that the type that is reported is OTHER (1111) not STRUCT (2002), see:
	 * org.hibernate.dialect.Dialect
	 */
	private static final int[] SQL_TYPES = { Types.OTHER };

	public int[] sqlTypes() {
		return SQL_TYPES;
	}

	public Class<? extends Object> returnedClass() {
		return JGeometryType.class;
	}

	/**
	 * This method gives back the equals functionality that was deprecated by using
	 * the equals that's needed for the UserType
	 */
	@Override
	public boolean equals(Object arg0) {
		return equals(this, arg0);
	}

	public boolean equals(Object obj1, Object obj2) throws HibernateException {
		boolean rv = false;
		if (obj1 == null && obj2 == null) {
			rv = true;
			// check we are dealing with non-null objects of the correct type
		} else if (obj1 instanceof JGeometryType && obj2 instanceof JGeometryType && obj1 != null && obj2 != null) {
			JGeometry geo1 = ((JGeometryType) obj1).getJGeometry();
			JGeometry geo2 = ((JGeometryType) obj2).getJGeometry();

			// check that they are the same type
			if (geo1.getType() != geo2.getType()) {
				rv = false;
			} else { // go through the different types and check for equality
				switch (geo1.getType()) {
				case JGeometry.GTYPE_POINT:
					rv = geo1.getJavaPoint().equals(geo2.getJavaPoint());
					break;
				case JGeometry.GTYPE_MULTIPOINT:
				case JGeometry.GTYPE_MULTICURVE:
				case JGeometry.GTYPE_MULTIPOLYGON:
				case JGeometry.GTYPE_POLYGON:
				case JGeometry.GTYPE_CURVE:
					rv = Arrays.equals(geo1.getOrdinatesOfElements(), geo2.getOrdinatesOfElements());
					break;
				default:
					throw new UnsupportedOperationException(
							String.format("Geometry type %d is not supported", geo1.getType()));
				}
			}
		}

		return rv;
	}

	public int hashCode(Object o) throws HibernateException {
		final JGeometry geo1 = ((JGeometryType) o).geometryInstance;
		int rv = -1;

		switch (geo1.getType()) {
		case JGeometry.GTYPE_POINT:
			rv = geo1.getJavaPoint().hashCode();
			break;
		case JGeometry.GTYPE_MULTIPOINT:
		case JGeometry.GTYPE_MULTICURVE:
		case JGeometry.GTYPE_MULTIPOLYGON:
		case JGeometry.GTYPE_POLYGON:
		case JGeometry.GTYPE_CURVE:
			rv = Arrays.hashCode(geo1.getOrdinatesOfElements());
			break;
		default:
			throw new UnsupportedOperationException(String.format("Geometry type %d is not supported", geo1.getType()));
		}
		return rv;
	}

	/* calls the load method */
	@Override
	public Object nullSafeGet(ResultSet resultSet, String[] strings, final SessionImplementor sessionImplementor,
			Object o) throws HibernateException, SQLException {
		LOGGER.info("\n\nullSafeGet");
		final STRUCT geometry = (STRUCT) resultSet.getObject(strings[0]);
		return resultSet.wasNull() || geometry == null ? null : new JGeometryType(JGeometry.load(geometry));
	}

	/* calls the store method */
	@Override
	public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i, SessionImplementor sessionImplementor)
			throws HibernateException, SQLException {
		LOGGER.info("\n\nnullSafeSet");
		if (o == null) {
			preparedStatement.setNull(i, Types.STRUCT, "MDSYS.SDO_GEOMETRY");
		} else {
			if (o instanceof JGeometryType) {
				JGeometryType gt = (JGeometryType) o;
				final Connection hlp = preparedStatement.getConnection().getMetaData().getConnection();
				OracleConnection oc = null;
				if (hlp instanceof OracleConnection) {
					oc = (OracleConnection) hlp;
				} else if (hlp instanceof PooledConnection) {
					oc = (OracleConnection) ((PooledConnection) hlp).getConnection();
				}
				if (gt.getJGeometry() == null) {
					preparedStatement.setNull(i, Types.STRUCT, "MDSYS.SDO_GEOMETRY");
				} else {
					preparedStatement.setObject(i, JGeometry.store((JGeometry) (gt).getJGeometry(), oc));
				}
			}
		}
	}

	/* uses the 'copy' constructor */
	public Object deepCopy(Object o) throws HibernateException {
		if (o == null)
			return null;
		if (o instanceof JGeometryType) {
			return new JGeometryType(((JGeometryType) o).getJGeometry());
		} else {
			return null;
		}
	}

	public boolean isMutable() {
		return false;
	}

	public Serializable disassemble(Object o) throws HibernateException {
		return (Serializable) deepCopy(o);
	}

	public Object assemble(Serializable serializable, Object o) throws HibernateException {
		return deepCopy(serializable);
	}

	public Object replace(Object o, Object o1, Object o2) throws HibernateException {
		return (JGeometryType) o;
	}

	/* accessor */
	public JGeometry getJGeometry() {
		return geometryInstance;
	}
}