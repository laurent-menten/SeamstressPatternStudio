package be.lmenten.sps.math;

public interface ImperialHelper
{
	/**
	 * Convert a length in centimeter to a length in inches.
	 *
	 * @param cm a length in centimeter
	 * @return the length in inches
	 */
	public static double toInches( double cm )
	{
		return cm / 2.54D;
	}

	/**
	 * Convert a length in inches to a length in centimeters.
	 *
	 * @param in a length in inches
	 * @return the length in centimeters
	 */
	public static double toCentimeters( double in )
	{
		return in * 2.54D;
	}

	// ========================================================================
	// = Imperial fraction converters =========================================
	// ========================================================================

	public static String cmToFractionString( double value )
	{
		return inchesToFractionString( value / 2.54D );
	}

	public static String cmToFractionString( double value, ImperialPrecision round )
	{
		return inchesToFractionString( value / 2.54D, round );
	}

	// ------------------------------------------------------------------------

	public static String mmToFractionString( double value )
	{
		return inchesToFractionString( value / 25.4D );
	}

	public static String mmToFractionString( double value, ImperialPrecision round )
	{
		return inchesToFractionString( value / 25.4D, round );
	}

	// ------------------------------------------------------------------------

	public static String inchesToFractionString( double value )
	{
		return inchesToFractionString( value, ImperialPrecision.ONE_64TH );
	}

	public static String inchesToFractionString( double value, ImperialPrecision round )
	{
		int feet = (int) (value / 12);
		int inches = (int) (value % 12);
		double rem = value - (feet*12) - inches;

		int a = (int) ((rem + round.getDivisor()) / round.getDivisor());
		int b = round.getPowerOf2();

		for( int i = round.getPowerOf2() ; i >= 2 ; i /= 2 )
		{
			if( (a % i) == 0 )
			{
				a = a / i;
				b = b / i;
				break;
			}
		}

		StringBuilder s = new StringBuilder();
		if( feet != 0 )
		{
			s.append( feet ).append( "' " );
		}

		if( inches != 0 || a == 0 )
		{
			s.append( inches ).append( " " );
		}

		if( a != 0 )
		{
			s.append( a ).append( "/" ).append( b ).append( "\"" );
		}

		return s.toString();
    }

	// ------------------------------------------------------------------------

	public static double fractionStringToInches( String s )
	{
		double res = 0.0D;

		return res;
	}
}
