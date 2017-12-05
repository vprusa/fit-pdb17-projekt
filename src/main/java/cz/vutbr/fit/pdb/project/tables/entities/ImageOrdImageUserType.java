package cz.vutbr.fit.pdb.project.tables.entities;

import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.imageio.ImageIO;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.internal.OracleResultSet;
import oracle.ord.im.OrdImage;
import oracle.ord.im.OrdImageBase;
import oracle.sql.ORAData;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;
import org.jboss.logging.Logger;

public class ImageOrdImageUserType implements UserType {

	private static final Logger LOGGER = Logger.getLogger(ImageOrdImageUserType.class);

	@Override
	public Object assemble(final Serializable pCached, final Object pOwner) throws HibernateException {
		return pCached;
	}

	@Override
	public Object deepCopy(final Object pValue) throws HibernateException {
		return pValue;
	}

	@Override
	public Serializable disassemble(final Object pValue) throws HibernateException {
		return (Serializable) pValue;
	}

	@Override
	public boolean equals(final Object pObject0, final Object pObject1) throws HibernateException {
		if (pObject0 == pObject1) {
			return true;
		}
		if (null == pObject0 || null == pObject1) {
			return false;
		}
		return pObject0.equals(pObject1);
	}

	@Override
	public int hashCode(final Object pObject) throws HibernateException {
		return pObject.hashCode();
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	private OrdImage getOrdImage(final Connection conn) throws SQLException {
		final OracleCallableStatement lStatement = (OracleCallableStatement) conn
				.prepareCall("DECLARE" + "  IMG ORDSYS.ORDIMAGE;" + "BEGIN" + "  IMG := ORDImage.init();" // init an
																											// image
						+ "  DBMS_LOB.CreateTemporary(IMG.source.localdata, TRUE);" // an empty BLOB for the image
						+ "  ? := IMG;" // return both the image and its signature as values of output parameters
						+ "END;");
		try {
			lStatement.registerOutParameter(1, OrdImageBase._SQL_TYPECODE, OrdImageBase._SQL_NAME);
			lStatement.executeUpdate();
			final OrdImage lOrdImage = (OrdImage) lStatement.getORAData(1, OrdImage.getORADataFactory());
			return lOrdImage;
		} finally {
			lStatement.close();
		}
	}

	@Override
	public Object nullSafeGet(final ResultSet pResultSet, final String[] pNames, final SessionImplementor pSession,
			final Object pOwner) throws HibernateException, SQLException {

		LOGGER.info(new StringBuilder("Lecture dans la colonne ").append(pNames[0]));
		final OracleResultSet lOracleResultSet = (OracleResultSet) pResultSet;
		final ORAData lORAData = lOracleResultSet.getORAData(pNames[0], OrdImage.getORADataFactory());
		final OrdImage lOrdImage = (OrdImage) lORAData;
		Image lImage = null;
		try {
			lImage = ImageIO.read(lOrdImage.getDataInStream());
		} catch (final IOException lIOException) {
			LOGGER.error(lIOException.getLocalizedMessage(), lIOException);
		}
		return lImage;
	}

	@Override
	public void nullSafeSet(final PreparedStatement pStatement, final Object pValue, final int pIndex,
			final SessionImplementor pSession) throws HibernateException, SQLException {

		LOGGER.info(new StringBuilder("Enregistrement à l'index ").append(pIndex));
		if (pValue == null) {
			pStatement.setNull(pIndex, sqlTypes()[0]);
		} else {
			final java.awt.Image lImage = (java.awt.Image) pValue;
			final OrdImage lOrdImage = getOrdImage(pStatement.getConnection());
			final ByteArrayOutputStream lBos = new ByteArrayOutputStream();
			try {
				ImageIO.write((RenderedImage) lImage, "png", lBos);
				lOrdImage.loadDataFromByteArray(lBos.toByteArray());
			} catch (final IOException lIOException) {
				LOGGER.error(lIOException.getLocalizedMessage(), lIOException);
			}
			lOrdImage.setProperties();
			lOrdImage.checkProperties();
			final OraclePreparedStatement lOraclePreparedStatement = (OraclePreparedStatement) pStatement;
			lOraclePreparedStatement.setORAData(pIndex, lOrdImage);
		}
	}

	@Override
	public Object replace(final Object pOriginal, final Object pTarget, final Object pOwner) throws HibernateException {
		return pOriginal;
	}

	@Override
	public Class<?> returnedClass() {
		return java.awt.Image.class;
	}

	@Override
	public int[] sqlTypes() {
		return new int[] { Types.STRUCT };
	}
}