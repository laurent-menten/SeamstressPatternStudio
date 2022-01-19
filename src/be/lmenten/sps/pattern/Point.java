package be.lmenten.sps.pattern;

/**
 * A point on the pattern.
 */
public class Point
{
	private static final String UNAMED_POINT_NAME_PREFIX = "Unamed_";

	private static long nextPointId = 0l;

	// ------------------------------------------------------------------------

	private final long id = nextPointId++;
	private String name;
	private boolean showName;

	private double x;
	private double y;

	// ========================================================================
	// =
	// ========================================================================

	/**
	 *
	 * @param x
	 * @param y
	 */
	public Point( double x, double y )
	{
		this( null, x, y, false );
	}

	/**
	 *
	 * @param name
	 * @param x
	 * @param y
	 */
	public Point( String name, double x, double y )
	{
		this( name, x, y, name != null );
	}

	/**
	 *
	 * @param name
	 * @param x
	 * @param y
	 * @param showName
	 */
	public Point( String name, double x, double y, boolean showName )
	{
		setName( name );
		if( name == null )
		{
			this.showName = false;
		}

		setX( x );
		setY( y );
	}

	// ------------------------------------------------------------------------

	protected Point()
	{
		this( null, 0.0D, 0.0D, false );
	}

	protected Point( String name )
	{
		this( name, 0.0D, 0.0D, name != null );
	}

	protected Point( String name, boolean showName )
	{
		this( name, 0.0D, 0.0D, showName );
	}

	// ========================================================================
	// =
	// ========================================================================

	public long getNodeId()
	{
		return id;
	}

	// ------------------------------------------------------------------------

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		if( name == null )
		{
			this.name = UNAMED_POINT_NAME_PREFIX + id;
		}
		else
		{
			this.name = name;
		}
	}

	public void showName( boolean showName )
	{
		this.showName = showName;
	}

	public boolean showName()
	{
		return this.showName;
	}

	// ------------------------------------------------------------------------

	public double getX()
	{
		return this.x;
	}

	public void setX( double x )
	{
		this.x = x;
	}

	// ------------------------------------------------------------------------

	public double getY()
	{
		return this.y;
	}

	public void setY( double y )
	{
		this.y = y;
	}
}
