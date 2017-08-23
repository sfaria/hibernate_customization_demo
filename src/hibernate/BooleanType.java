package hibernate;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author Scott Faria
 */
public final class BooleanType implements UserType {

	// -------------------- Public Statics --------------------

	public static final String NAME = "hibernate.BooleanType";

	// -------------------- Overridden Methods --------------------

	@Override
	public final int[] sqlTypes() {
		return new int[] { Types.VARCHAR };
	}

	@Override
	public final Class returnedClass() {
		return Boolean.class;
	}

	@Override
	public final Object nullSafeGet(final ResultSet rs, final String[] names, final SharedSessionContractImplementor session, final Object owner) throws SQLException {
		String value = rs.getString(names[0]);
		if (value == null) {
			return Boolean.FALSE;
		}
		return "Y".equals(value.toUpperCase());
	}

	@Override
	public final void nullSafeSet(final PreparedStatement st, final Object value, final int index, final SharedSessionContractImplementor session) throws SQLException {
		if (value == null) {
			st.setNull(index, Types.VARCHAR);
		} else {
			Boolean val = (Boolean) value;
			st.setString(index, val ? "Y" : "N");
		}
	}

	@Override public final Object deepCopy(final Object value) { return value; }
	@Override public final boolean isMutable() { return false; }
	@Override public final Serializable disassemble(final Object value) { return (Serializable) value; }
	@Override public final Object assemble(final Serializable cached, final Object owner) { return cached; }
	@Override public final Object replace(final Object original, final Object target, final Object owner) {  return original; }
	@Override public final int hashCode(final Object x) { return x.hashCode(); }
	@Override public final boolean equals(final Object x, final Object y) { return x == null && y == null || !(x == null || y == null) && x.equals(y); }
}
