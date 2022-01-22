package be.lmenten.sps.pattern;

import be.lmenten.sps.pattern.constraints.Constraint;

public class ConstraintedPoint
	extends Point
{
	private final Constraint constraint;

	protected ConstraintedPoint( Constraint constraint )
	{
		this.constraint = constraint;
	}

	public void Constrain()
	{
		constraint.apply( this );
	}
}
