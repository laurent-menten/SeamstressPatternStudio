package be.lmenten.sps.pattern.measurements;

public class Measurement
{
	private final String name;
	private double value;

	public Measurement( String name )
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public double getValue()
	{
		return value;
	}
}
